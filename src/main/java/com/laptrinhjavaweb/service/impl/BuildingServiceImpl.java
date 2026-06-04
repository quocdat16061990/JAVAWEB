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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        buildingRepository.deleteById(id);
    }

    @Override
    public void updateBuilding(BuildingDTO buildingDTO,Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + id));

        buildingEntity.setName(buildingDTO.getName());
        buildingEntity.setDistrict(buildingDTO.getDistrict());
        buildingEntity.setWard(buildingDTO.getWard());
        buildingEntity.setStreet(buildingDTO.getStreet());
        buildingEntity.setFloorArea(buildingDTO.getFloorArea());
        buildingEntity.setNumberOfBasement(buildingDTO.getNumberOfBasement());
        buildingEntity.setRentPrice(buildingDTO.getRentPrice());
        buildingEntity.setManagerName(buildingDTO.getManagerName());
        buildingEntity.setManagerPhone(buildingDTO.getManagerPhone());
        buildingEntity.setDirection(buildingDTO.getDirection());
        buildingEntity.setNote(buildingDTO.getNote());
        buildingEntity.setType(buildingDTO.getRentTypes() == null || buildingDTO.getRentTypes().length == 0
                ? null
                : String.join(",", buildingDTO.getRentTypes()));

        buildingRepository.save(buildingEntity);
    }



    @Override
    public List<BuildingDTO> findAll(BuildingDTO model) {
        return findAll(model, Pageable.unpaged()).getContent();
    }

    @Override
    public Page<BuildingDTO> findAll(BuildingDTO model, Pageable pageable) {
        Page<BuildingEntity> entities = buildingRepositoryCustom.findAll(model, pageable);
        return entities.map(buildingConverter::convertToDTO);
    }

    @Override
    public BuildingDTO findById(Long id) {
        BuildingEntity entity = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
        return buildingConverter.convertToDTO(entity);
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
