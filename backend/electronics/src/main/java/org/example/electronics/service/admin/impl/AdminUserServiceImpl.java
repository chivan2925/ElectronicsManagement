package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.admin.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminUserResponseDTO;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.entity.enums.UserStatus;
import org.example.electronics.mapper.UserMapper;
import org.example.electronics.repository.UserRepository;
import org.example.electronics.service.admin.AdminUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public AdminUserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public AdminUserResponseDTO updateStatusUser(Integer userId, AdminUpdateUserStatusRequestDTO adminUpdateUserStatusRequestDTO) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy user với id: " + userId
                ));

        userEntity.setStatus(adminUpdateUserStatusRequestDTO.status());

        userEntity = userRepository.save(userEntity);

        return userMapper.toResponseDTO(userEntity);
    }

    @Transactional
    @Override
    public void deleteUser(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy user với id: " + userId
                ));

        userEntity.setStatus(UserStatus.DELETED);

        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminUserResponseDTO> getAllUsers(String keyword, UserStatus status, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        LocalDateTime startDateTime = (fromDate != null) ? fromDate.toLocalDate().atStartOfDay() : null;
        LocalDateTime endDateTime = (toDate != null) ? toDate.toLocalDate().atTime(23, 59, 59, 999999999) : null;

        // 2. Chặn lỗi Frontend gửi chuỗi rỗng "" (Gửi rỗng thì biến thành null để DB bỏ qua)
        String finalKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;

        Page<UserEntity> userEntityPage = userRepository.findUsersWithFilter(finalKeyword, status, startDateTime, endDateTime, pageable);

        return userEntityPage.map(userMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminUserResponseDTO getUserById(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy user với id: " + userId
                ));

        return userMapper.toResponseDTO(userEntity);
    }
}
