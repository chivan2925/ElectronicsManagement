package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.admin.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminUserResponseDTO;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.entity.enums.UserStatus;
import org.example.electronics.mapper.UserMapper;
import org.example.electronics.repository.UserRepository;
import org.example.electronics.service.admin.AdminUserService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
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
    public Page<AdminUserResponseDTO> getAllUsers(String keyword, UserStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<UserEntity> userEntityPage = userRepository.findUsersWithFilter(finalKeyword, status, startDateTime, endDateTime, pageable);

        return userEntityPage.map(userMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminUserResponseDTO getUserById(Integer userId) {
        UserEntity existingUserEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy user với id: " + userId
                ));

        return userMapper.toResponseDTO(existingUserEntity);
    }
}
