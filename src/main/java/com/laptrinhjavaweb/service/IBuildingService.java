package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IBuildingService {
     Map<String,String> getDisTricts();
     Map<String,String> getRentTypes();
     void save(BuildingDTO buildingDTO) throws Exception;
     void delete(Long id);
     void updateBuilding(BuildingDTO buildingDTO , Long id);

     List<BuildingDTO> findAll(BuildingDTO model);
     Page<BuildingDTO> findAll(BuildingDTO model, Pageable pageable);
     BuildingDTO findById(Long id);
     }
