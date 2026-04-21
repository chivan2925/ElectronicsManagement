package org.example.electronics.util;

import org.example.electronics.entity.MediaEntity;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MediaUtils {

    @Named("getPrimaryImage")
    @SuppressWarnings("unused")
    public String getPrimaryImage(List<MediaEntity> mediaEntityList) {
        if (mediaEntityList == null || mediaEntityList.isEmpty()) {
            return null;
        }

        return mediaEntityList.stream()
                .filter(media -> Boolean.TRUE.equals(media.getIsPrimary()))
                .map(MediaEntity::getImageUrl)
                .findFirst()
                .orElse(mediaEntityList.getFirst().getImageUrl());
    }
}
