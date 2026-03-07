package org.example.electronics.service;

import org.example.electronics.dto.request.AddressRequestDTO;
import org.example.electronics.dto.response.AddressResponseDTO;
import org.example.electronics.entity.AddressEntity;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.repository.AddressRepository;
import org.example.electronics.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public List<AddressResponseDTO> getAddresses(String email) {
        UserEntity user = findUserByEmail(email);
        return addressRepository.findByUserId(user.getId())
                .stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public AddressResponseDTO createAddress(String email, AddressRequestDTO request) {
        UserEntity user = findUserByEmail(email);

        AddressEntity address = AddressEntity.builder()
                .user(user)
                .receiverName(request.receiverName())
                .phone(request.phone())
                .line(request.line())
                .ward(request.ward())
                .district(request.district())
                .province(request.province())
                .isDefault(request.isDefault() != null && request.isDefault())
                .build();

        // If this is default, clear others
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            addressRepository.clearDefaultByUserId(user.getId());
        }

        addressRepository.save(address);
        return toResponseDTO(address);
    }

    @Transactional
    public AddressResponseDTO updateAddress(String email, Integer addressId, AddressRequestDTO request) {
        UserEntity user = findUserByEmail(email);
        AddressEntity address = addressRepository.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));

        address.setReceiverName(request.receiverName());
        address.setPhone(request.phone());
        address.setLine(request.line());
        address.setWard(request.ward());
        address.setDistrict(request.district());
        address.setProvince(request.province());

        if (request.isDefault() != null) {
            if (Boolean.TRUE.equals(request.isDefault())) {
                addressRepository.clearDefaultByUserId(user.getId());
            }
            address.setIsDefault(request.isDefault());
        }

        addressRepository.save(address);
        return toResponseDTO(address);
    }

    @Transactional
    public void deleteAddress(String email, Integer addressId) {
        UserEntity user = findUserByEmail(email);
        AddressEntity address = addressRepository.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));
        addressRepository.delete(address);
    }

    @Transactional
    public AddressResponseDTO setDefault(String email, Integer addressId) {
        UserEntity user = findUserByEmail(email);
        AddressEntity address = addressRepository.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));

        addressRepository.clearDefaultByUserId(user.getId());
        address.setIsDefault(true);
        addressRepository.save(address);

        return toResponseDTO(address);
    }

    private UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    private AddressResponseDTO toResponseDTO(AddressEntity address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getReceiverName(),
                address.getPhone(),
                address.getLine(),
                address.getWard(),
                address.getDistrict(),
                address.getProvince(),
                address.getIsDefault(),
                address.getCreatedAt()
        );
    }
}
