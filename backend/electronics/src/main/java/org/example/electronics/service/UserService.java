package org.example.electronics.service;

import org.example.electronics.dto.request.ChangePasswordRequestDTO;
import org.example.electronics.dto.request.UpdateUserRequestDTO;
import org.example.electronics.dto.response.UserResponseDTO;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO getProfile(String email) {
        UserEntity user = findByEmail(email);
        return toResponseDTO(user);
    }

    public UserResponseDTO updateProfile(String email, UpdateUserRequestDTO request) {
        UserEntity user = findByEmail(email);

        if (request.name() != null) user.setName(request.name());
        if (request.avatar() != null) user.setAvatar(request.avatar());
        if (request.phoneNumber() != null) user.setPhoneNumber(request.phoneNumber());

        userRepository.save(user);
        return toResponseDTO(user);
    }

    public void changePassword(String email, ChangePasswordRequestDTO request) {
        UserEntity user = findByEmail(email);

        if (!passwordEncoder.matches(request.oldPassword(), user.getHashedPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        user.setHashedPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    private UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    private UserResponseDTO toResponseDTO(UserEntity user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getAvatar(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }
}
