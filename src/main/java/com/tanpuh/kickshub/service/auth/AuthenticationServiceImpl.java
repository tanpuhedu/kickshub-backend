package com.tanpuh.kickshub.service.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tanpuh.kickshub.dto.request.AuthenticationRequest;
import com.tanpuh.kickshub.dto.request.IntrospectRequest;
import com.tanpuh.kickshub.dto.request.LogoutRequest;
import com.tanpuh.kickshub.dto.request.RefreshRequest;
import com.tanpuh.kickshub.dto.response.AuthenticationResponse;
import com.tanpuh.kickshub.dto.response.IntrospectResponse;
import com.tanpuh.kickshub.entity.InvalidatedToken;
import com.tanpuh.kickshub.entity.User;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.repository.InvalidatedTokenRepository;
import com.tanpuh.kickshub.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signer-key}")
    private String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    private long REFRESHABLE_DURATION;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        boolean isValid = true;

        try {
            verifyToken(request.getToken(), false);
        } catch (AppException e) {
            isValid = false;
        }

        return new IntrospectResponse(isValid);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);

        return new AuthenticationResponse(token, true);
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signedJwt = verifyToken(request.getToken(), true);

            String jti = signedJwt.getJWTClaimsSet().getJWTID();
            Date expTime = signedJwt.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = new InvalidatedToken(jti, expTime);
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJwt = verifyToken(request.getToken(), true);

        String jti = signedJwt.getJWTClaimsSet().getJWTID();
        Date expTime = signedJwt.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = new InvalidatedToken(jti, expTime);
        invalidatedTokenRepository.save(invalidatedToken);

        String username = signedJwt.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return new AuthenticationResponse(token, true);
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token); // tách chuỗi JWT thành 3 phần để tạo 1 obj SignedJWT
        Date expTime = (isRefresh)
                ? Date.from(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        )
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        boolean isVerified = signedJWT.verify(verifier); // xác minh chữ ký của JWT

        // ktra token có hợp lệ hay không
        // nếu có thì ktra token còn hiệu lực hay không (so sánh với tgian hiện tại)
        if (!(isVerified && expTime.after(Date.from(Instant.now()))))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        if (invalidatedTokenRepository.existsById(jti))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String generateToken(User user) {
        // tạo header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // tạo claimset, các field trong payload gọi là claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("tanpuh.com") // xác định token này đc issue từ đâu, thông thường là domain của service
                .issueTime(new Date())
                .expirationTime(Date.from(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS)
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        // tạo payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // tạo jwsobject, jwsobject tương ứng với cấu trúc của token, gồm header và payload
        JWSObject jwsObject = new JWSObject(header, payload);

        // kí token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("cannot generate token", e);
            throw new RuntimeException(e);
        }
    }

    /*
        add prefix ROLE_ cho role, để phân biệt role và permission
        vì cả 2 đều ở trong cùng field scope của token
     */
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());

                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission ->
                            stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
}
