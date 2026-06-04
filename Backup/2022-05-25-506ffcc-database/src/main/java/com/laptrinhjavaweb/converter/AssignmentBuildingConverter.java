package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.AssigmentBuildingDTO;
import com.laptrinhjavaweb.entity.AssignmentBuildingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignmentBuildingConverter {
    @Autowired
    private ModelMapper modelMapper;

    public AssigmentBuildingDTO convertToDTO(AssignmentBuildingEntity entity) {
        AssigmentBuildingDTO result = modelMapper.map(entity, AssigmentBuildingDTO.class);
        return result;
    }

    public AssignmentBuildingEntity convertToEntity(AssigmentBuildingDTO dto) {
        AssignmentBuildingEntity result = modelMapper.map(dto, AssignmentBuildingEntity.class);
        return result;
    }
}

