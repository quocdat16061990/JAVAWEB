package com.laptrinhjavaweb.repository.custom;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuildingRepositoryCustom {

    List<BuildingEntity> findAll(BuildingDTO model);
    Page<BuildingEntity> findAll(BuildingDTO model, Pageable pageable);
}
