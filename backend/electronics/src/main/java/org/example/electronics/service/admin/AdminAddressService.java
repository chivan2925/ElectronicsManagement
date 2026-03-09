package org.example.electronics.service.admin;

import org.example.electronics.dto.response.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminAddressService {

    Page<AddressResponseDTO> getAllAddressesByUserId(Integer userId, Pageable pageable);
}
