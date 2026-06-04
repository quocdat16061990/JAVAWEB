package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;

import java.util.Map;

public interface IBuildingService {
     Map<String,String> getDisTricts();
     Map<String,String> getRentTypes();
     void save(BuildingDTO buildingDTO);
}
