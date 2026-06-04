package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;

import java.util.List;
import java.util.Map;

public interface IBuildingService {
     Map<String,String> getDisTricts();
     Map<String,String> getRentTypes();
     void save(BuildingDTO buildingDTO) throws Exception;
     void delete(Long id);
     void updateBuilding(BuildingDTO buildingDTO , Long id);

     List<BuildingDTO> findAll(BuildingDTO model);
     }
