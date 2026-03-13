package org.example.electronics.repository;

import org.example.electronics.entity.UserEntity;
import org.example.electronics.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE 1=1 " + // Thêm 1=1 để nối AND cho dễ

            // NHÓM 1: Lọc theo Keyword (Dùng ngoặc đơn bọc lại toàn bộ cục OR)
            "AND (:keyword IS NULL OR ( " +
            "    CAST(u.id AS string) LIKE CONCAT('%', :keyword, '%') " + // Ép kiểu ID thành chuỗi để tìm kiếm LIKE
            "    OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " + // Đóng ngoặc nhóm 1

            // NHÓM 2: Lọc theo Trạng thái
            "AND (:status IS NULL OR u.status = :status) " +

            // NHÓM 3: Lọc theo Khoảng thời gian (Lưu ý dùng LocalDateTime)
            "AND (:fromDate IS NULL OR u.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR u.createdAt <= :toDate)")
    Page<UserEntity> findUsersWithFilter(
            @Param("keyword") String keyword,
            @Param("status") UserStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
