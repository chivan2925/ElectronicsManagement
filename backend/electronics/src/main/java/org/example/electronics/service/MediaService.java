package org.example.electronics.service;

import org.example.electronics.dto.response.MediaResponseDTO;
import org.example.electronics.entity.MediaEntity;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.repository.MediaRepository;
import org.example.electronics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final ProductRepository productRepository;

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    public MediaService(MediaRepository mediaRepository, ProductRepository productRepository) {
        this.mediaRepository = mediaRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public MediaResponseDTO upload(Integer productId, MultipartFile file, Boolean isPrimary) throws IOException {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Save file to disk
        Path uploadPath = Paths.get(uploadDir, "products", productId.toString());
        Files.createDirectories(uploadPath);

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";
        String filename = UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(filename);
        file.transferTo(filePath.toFile());

        String imageUrl = "/uploads/products/" + productId + "/" + filename;

        // Clear primary if needed
        if (Boolean.TRUE.equals(isPrimary)) {
            mediaRepository.clearPrimaryByProductId(productId);
        }

        MediaEntity media = MediaEntity.builder()
                .product(product)
                .imageUrl(imageUrl)
                .isPrimary(Boolean.TRUE.equals(isPrimary))
                .sortOrder(0)
                .build();

        mediaRepository.save(media);
        return toResponseDTO(media);
    }

    public void delete(Integer id) {
        MediaEntity media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy media"));
        mediaRepository.delete(media);
    }

    @Transactional
    public MediaResponseDTO setPrimary(Integer id) {
        MediaEntity media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy media"));

        mediaRepository.clearPrimaryByProductId(media.getProduct().getId());
        media.setIsPrimary(true);
        mediaRepository.save(media);

        return toResponseDTO(media);
    }

    public List<MediaResponseDTO> getByProductId(Integer productId) {
        return mediaRepository.findByProductIdOrderBySortOrderAsc(productId)
                .stream().map(this::toResponseDTO).toList();
    }

    private MediaResponseDTO toResponseDTO(MediaEntity m) {
        return new MediaResponseDTO(
                m.getId(), m.getProduct().getId(),
                m.getImageUrl(), m.getIsPrimary(),
                m.getSortOrder(), m.getCreatedAt()
        );
    }
}
