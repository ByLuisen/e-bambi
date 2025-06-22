package com.e.bambi.inventory.domain.image.entity;

import com.e.bambi.inventory.domain.image.valueobject.ImageId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Image extends AggregateRoot<ImageId> {
    private final ProductId productId;
    private final String imageUrl;

    public void initializeImage() {
        super.setId(new ImageId(UUID.randomUUID()));
    }

    private Image(Builder builder) {
        super.setId(builder.imageId);
        productId = builder.productId;
        imageUrl = builder.imageUrl;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private ImageId imageId;
        private ProductId productId;
        private String imageUrl;

        private Builder() {
        }

        public Builder id(ImageId val) {
            imageId = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder imageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Image build() {
            return new Image(this);
        }
    }
}
