package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.AssignmentBuildingConverter;
import com.laptrinhjavaweb.dto.AssigmentBuildingDTO;
import com.laptrinhjavaweb.entity.AssignmentBuildingEntity;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.AssignmentBuildingRepository;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.AssignmentBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AssignmentBuildingServiceImpl implements AssignmentBuildingService {

    @Autowired
    private AssignmentBuildingRepository assignmentBuildingRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentBuildingConverter assignmentBuildingConverter;

    @Override
    public List<AssigmentBuildingDTO> save(AssigmentBuildingDTO assignmentBuildingDTO) {
        BuildingEntity buildingEntity = buildingRepository.findById(assignmentBuildingDTO.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + assignmentBuildingDTO.getBuildingId()));
        List<AssignmentBuildingEntity> assignmentBuildingEntities = assignmentBuildingRepository.findByBuildingEntity(buildingEntity);
        List<AssigmentBuildingDTO> list = new ArrayList<>();
        List<Long> listStaffsId = assignmentBuildingDTO.getStaffs() == null
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(assignmentBuildingDTO.getStaffs()));

        for (AssignmentBuildingEntity assignmentBuildingEntity: assignmentBuildingEntities) {
            assignmentBuildingRepository.deleteById(assignmentBuildingEntity.getId());
        }

        for (Long item : listStaffsId) {
            if (item != null) {
                UserEntity userEntity = userRepository.findById(item)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + item));
                AssignmentBuildingEntity assignmentBuildingEntity = new AssignmentBuildingEntity();
                assignmentBuildingEntity.setBuildingEntity(buildingEntity);
                assignmentBuildingEntity.setUserEntity(userEntity);
                assignmentBuildingEntity = assignmentBuildingRepository.save(assignmentBuildingEntity);
                if (assignmentBuildingEntity != null) {
                    list.add(assignmentBuildingConverter.convertToDTO(assignmentBuildingEntity));
                }
            }
        }
        return list;
    }
}
