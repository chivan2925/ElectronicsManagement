package org.example.electronics.service.admin.impl;

import org.example.electronics.dto.response.AddressResponseDTO;
import org.example.electronics.entity.AddressEntity;
import org.example.electronics.mapper.AddressMapper;
import org.example.electronics.repository.AddressRepository;
import org.example.electronics.service.admin.AdminAddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminAddressServiceImpl implements AdminAddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AdminAddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public Page<AddressResponseDTO> getAllAddressesByUserId(Integer userId, Pageable pageable) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID không được null");
        }

        Page<AddressEntity> addressEntityPage = addressRepository.findByUserId(userId, pageable);

        return addressEntityPage.map(addressMapper::toResponseDTO);
    }
}
