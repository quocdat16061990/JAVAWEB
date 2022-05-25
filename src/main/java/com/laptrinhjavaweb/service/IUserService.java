package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {
    UserDTO findOneByUserNameAndStatus(String name, int status);
    List<UserDTO> findByRoleBuidlings(Long roleId, Long buildingId);
    Map<Long,String> getStaffMaps();
}
