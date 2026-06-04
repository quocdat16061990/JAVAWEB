package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BuildingConverter {

    @Autowired
    private ModelMapper modelMapper;

    public BuildingEntity convertToEntity(BuildingDTO dto) {
        BuildingEntity result = modelMapper.map(dto, BuildingEntity.class);
        result.setType(joinRentTypes(dto.getRentTypes()));
        return result;
    }
    public BuildingDTO convertToDTO(BuildingEntity entity) {
        BuildingDTO dto = modelMapper.map(entity,BuildingDTO.class);
        dto.setRentTypes(splitRentTypes(entity.getType()));
        return dto;
    }

    private String joinRentTypes(String[] rentTypes) {
        if (rentTypes == null || rentTypes.length == 0) {
            return null;
        }
        return String.join(",", rentTypes);
    }

    private String[] splitRentTypes(String type) {
        if (type == null || type.isBlank()) {
            return new String[]{};
        }
        return Arrays.stream(type.split(","))
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .toArray(String[]::new);
    }

}
