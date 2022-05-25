package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.UserConverter;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDTO findOneByUserNameAndStatus(String name, int status) {
        return userConverter.convertToDto(userRepository.findOneByUserNameAndStatus(name, status));
    }

    @Override
    public Map<Long, String> getStaffMaps() {
        Map<Long,String> results = new HashMap<>();
        List<UserEntity> staffs = userRepository.findByRoles((long) 2);
        for (UserEntity item: staffs){
            results.put(item.getId(),item.getFullName());
        }
        return results;
    }


    @Override
    public List<UserDTO> findByRoleBuidlings(Long roleId, Long buildingId) {
        List<UserEntity> entityList = userRepository.findByRoles(roleId);

        List<UserEntity> usersByBuildingId = null;
        if (buildingId > 0 && buildingId != null) {
            usersByBuildingId = userRepository.findByBuilding(buildingId);
        }
        List<UserDTO> dtoList = entityList.stream().map(item -> userConverter.convertToDto(item)).collect(Collectors.toList());
        for (UserDTO dto : dtoList) {
            if (usersByBuildingId != null) {
                for (UserEntity item : usersByBuildingId) {
                    if (dto.getId() == item.getId()) {
                        dto.setChecked("checked");
                    }
                }
            }
        }
        return dtoList;
    }
}
