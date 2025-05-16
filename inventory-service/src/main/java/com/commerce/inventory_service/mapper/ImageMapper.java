package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.Image;
import com.commerce.inventory_service.dto.ImageInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image imageInputDTOToImage(ImageInputDTO imageInputDTO);
}
