package com.tanpuh.kickshub.service.user;

import com.tanpuh.kickshub.dto.request.UserCreationRequest;
import com.tanpuh.kickshub.dto.request.UserUpdateRequest;
import com.tanpuh.kickshub.dto.response.UserResponse;
import com.tanpuh.kickshub.entity.Cart;
import com.tanpuh.kickshub.entity.Role;
import com.tanpuh.kickshub.entity.User;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.UserMapper;
import com.tanpuh.kickshub.repository.CartRepository;
import com.tanpuh.kickshub.repository.RoleRepository;
import com.tanpuh.kickshub.repository.UserRepository;
import com.tanpuh.kickshub.utils.enums.EntityStatus;
import com.tanpuh.kickshub.utils.enums.PredefinedRole;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    CartRepository cartRepository;
    UserMapper mapper;
    PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return mapper.toResponse(user);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return mapper.toResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public UserResponse create(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = mapper.toEntity(request);
        user.setPhone(formatPhoneNumber(request.getPhone()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(EntityStatus.ACTIVE);

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER.getId()).ifPresent(roles::add);
        user.setRoles(roles);
        userRepository.save(user);

        Cart cart = Cart.builder()
                .totalQty(0)
                .user(user)
                .build();
        cartRepository.save(cart);

        return mapper.toResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public UserResponse update(UserUpdateRequest request, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        mapper.update(user, request);
        user.setPhone(formatPhoneNumber(request.getPhone()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role> roles = roleRepository.findAllById(request.getRoleIds());
        user.setRoles(new HashSet<>(roles));

        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
    }

    private String formatPhoneNumber(String phone) {
        String digitsOnly = phone.replaceAll("[\\s\\-\\.]", "");

        if (digitsOnly.startsWith("+84"))
            digitsOnly = "0" + digitsOnly.substring(3);

        return digitsOnly;
    }
}
