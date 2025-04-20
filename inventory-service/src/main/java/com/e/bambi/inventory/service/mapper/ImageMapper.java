package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.Image;
import com.e.bambi.inventory.service.dto.ImageInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image imageInputDTOToImage(ImageInputDTO imageInputDTO);
}
