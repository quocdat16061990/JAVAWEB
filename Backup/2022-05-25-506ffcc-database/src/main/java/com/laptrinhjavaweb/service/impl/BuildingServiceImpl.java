package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.enums.DistrictsEnums;
import com.laptrinhjavaweb.enums.RentTypesEnums;
import com.laptrinhjavaweb.repository.BuildingRepository;

import com.laptrinhjavaweb.repository.custom.BuildingRepositoryCustom;
import com.laptrinhjavaweb.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BuildingServiceImpl implements IBuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingRepositoryCustom buildingRepositoryCustom;
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
        Map<String,String> renTypes = new LinkedHashMap<>();
        for(RentTypesEnums item : RentTypesEnums.values()){
            renTypes.put(item.name(), item.getRentTypesValue());
        }
        return renTypes;
    }

    @Override
    @Transactional
    public void save(BuildingDTO buildingDTO) throws Exception {
        if(buildingDTO.getName() !=null && !buildingDTO.getName().isEmpty()){
           if(buildingRepository.findByName(buildingDTO.getName()).isPresent()){
               throw new Exception("Value is exits, Please Retype");
                }
           }

        BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
        buildingRepository.save(buildingEntity);
    }

    @Override
    public void delete(Long id) {
        buildingRepository.delete(id);
    }

    @Override
    public void updateBuilding(BuildingDTO buildingDTO,Long id) {
//        buildingDTO.getId() ID moi
        if(buildingRepository.findById(id).isPresent()){
            BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingDTO);
            buildingRepository.save(buildingEntity);
        }
    }



    @Override
    public List<BuildingDTO> findAll(BuildingDTO model) {
        List<BuildingDTO> result = new ArrayList<>();

        List<BuildingEntity> entities = buildingRepositoryCustom.findAll(model);
        for (BuildingEntity item: entities){

            BuildingDTO buildingDTO=buildingConverter.convertToDTO(item);
            result.add(buildingDTO);
        }
        return result;
    }

//    @Override
//    public List<BuildingDTO> findAll(BuildingDTO model) {
//        List<BuildingDTO> results = new ArrayList<>();
//        List<BuildingEntity> entities = buildingRepository.findAll(model);
//        for(BuildingEntity item: entities){
//            BuildingDTO buildingDTO = buildingConverter.convertToDTO(item);
//            results.add(buildingDTO);
//        }
//        return results;
//    }


}
