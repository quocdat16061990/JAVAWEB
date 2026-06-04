package com.laptrinhjavaweb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO extends AbstractDTO implements Serializable {

    private static final long serialVersionUID = 7213600440729202783L;

    private Long id;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    private String userName;
    private String fullName;
    private String password;
    private Integer status;
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    private List<RoleDTO> roles = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
