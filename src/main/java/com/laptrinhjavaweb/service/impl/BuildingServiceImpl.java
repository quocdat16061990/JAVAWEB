package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.enums.DistrictsEnums;
import com.laptrinhjavaweb.enums.RentTypesEnums;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class BuildingServiceImpl implements IBuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingConverter buildingConverter;

    @Override
    public Map<String, String> getDisTricts() {
        Map<String,String> district = new HashMap<>();
        for(DistrictsEnums item : DistrictsEnums.values()){
            district.put(item.toString(),item.getDistrictValue());
        }
        return district;
    }

    @Override
    public Map<String, String> getRentTypes() {
        Map<String,String> renTypes = new HashMap<>();
        for(RentTypesEnums item : RentTypesEnums.values()){
            renTypes.put(item.toString(),item.getRentTypesValue());
        }
        return renTypes;
    }

    @Override
    @Transactional
    public void save(BuildingDTO buildingDTO) {
        BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
        buildingRepository.save(buildingEntity);
    }
}
