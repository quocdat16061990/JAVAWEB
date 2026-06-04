package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.UserDTO;

public interface IUserService {
    UserDTO findOneByUserNameAndStatus(String name, int status);
}
