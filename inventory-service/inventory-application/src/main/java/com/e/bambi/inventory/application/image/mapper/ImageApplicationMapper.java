package com.e.bambi.inventory.application.image.mapper;

import com.e.bambi.inventory.application.image.dto.command.CreateImageCommand;
import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.inventory.domain.image.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageApplicationMapper {

    public Image toImage(CreateImageCommand command) {
        return Image.builder()
                .productId(command.getProductId())
                .imageUrl(command.getImageUrl())
                .build();
    }

    public ImageResponse toImageResponse(Image image) {
        return new ImageResponse(
                image.getId().getValue(),
                image.getImageUrl()
        );
    }
}
