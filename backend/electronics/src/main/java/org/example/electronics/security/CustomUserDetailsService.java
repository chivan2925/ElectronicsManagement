package org.example.electronics.security;

import org.example.electronics.entity.RolePermissionEntity;
import org.example.electronics.entity.StaffEntity;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.entity.enums.StaffStatus;
import org.example.electronics.entity.enums.UserStatus;
import org.example.electronics.repository.StaffRepository;
import org.example.electronics.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public CustomUserDetailsService(UserRepository userRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try loading as customer first
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            if (user.getStatus() != UserStatus.ACTIVE) {
                throw new UsernameNotFoundException("Tài khoản đã bị khóa hoặc xóa");
            }
            return new User(
                    user.getEmail(),
                    user.getHashedPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        // Try loading as staff
        Optional<StaffEntity> staffOpt = staffRepository.findByEmail(email);
        if (staffOpt.isPresent()) {
            StaffEntity staff = staffOpt.get();
            if (staff.getStatus() != StaffStatus.ACTIVE) {
                throw new UsernameNotFoundException("Tài khoản nhân viên đã bị khóa hoặc xóa");
            }
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_STAFF"));

            // Add permission-based authorities from the staff's role
            if (staff.getRole() != null && staff.getRole().getRolePermissions() != null) {
                for (RolePermissionEntity rp : staff.getRole().getRolePermissions()) {
                    authorities.add(new SimpleGrantedAuthority(rp.getPermission().getName()));
                }
            }

            return new User(
                    staff.getEmail(),
                    staff.getHashedPassword(),
                    authorities
            );
        }

        throw new UsernameNotFoundException("Không tìm thấy tài khoản với email: " + email);
    }
}
