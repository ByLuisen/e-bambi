package com.e.bambi.inventory.infrastructure.persistence.image.mapper;

import com.e.bambi.inventory.domain.image.entity.Image;
import com.e.bambi.inventory.domain.image.valueobject.ImageId;
import com.e.bambi.inventory.infrastructure.persistence.image.entity.ImageEntity;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

@Component
public class ImagePersistenceMapper {

    public ImageEntity toImageEntity(Image image) {
        return new ImageEntity(
                image.getId().getValue(),
                image.getProductId().getValue(),
                image.getImageUrl()
        );
    }

    public Image toImage(ImageEntity entity) {
        return Image.builder()
                .id(new ImageId(entity.getId()))
                .productId(new ProductId(entity.getProductId()))
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
