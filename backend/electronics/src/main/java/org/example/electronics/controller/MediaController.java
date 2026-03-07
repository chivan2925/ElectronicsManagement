package org.example.electronics.controller;

import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.MediaResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/api/admin/products/{productId}/media")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<MediaResponseDTO>> upload(
            @PathVariable Integer productId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary) throws IOException {
        MediaResponseDTO response = mediaService.upload(productId, file, isPrimary);
        return ResponseEntity.ok(ApiResponse.success("Tải ảnh lên thành công", response));
    }

    @DeleteMapping("/api/admin/media/{id}")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        mediaService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa ảnh thành công", null));
    }

    @PutMapping("/api/admin/media/{id}/primary")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<MediaResponseDTO>> setPrimary(@PathVariable Integer id) {
        MediaResponseDTO response = mediaService.setPrimary(id);
        return ResponseEntity.ok(ApiResponse.success("Đặt ảnh chính thành công", response));
    }
}
