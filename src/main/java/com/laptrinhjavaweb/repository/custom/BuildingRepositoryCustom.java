package com.laptrinhjavaweb.repository.custom;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;

import java.util.List;

public interface BuildingRepositoryCustom {

    List<BuildingEntity> findAll(BuildingDTO model);
}
