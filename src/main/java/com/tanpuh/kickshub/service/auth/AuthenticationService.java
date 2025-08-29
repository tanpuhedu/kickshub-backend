package com.tanpuh.kickshub.service.auth;

import com.nimbusds.jose.JOSEException;
import com.tanpuh.kickshub.dto.request.AuthenticationRequest;
import com.tanpuh.kickshub.dto.request.IntrospectRequest;
import com.tanpuh.kickshub.dto.request.LogoutRequest;
import com.tanpuh.kickshub.dto.request.RefreshRequest;
import com.tanpuh.kickshub.dto.response.AuthenticationResponse;
import com.tanpuh.kickshub.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
