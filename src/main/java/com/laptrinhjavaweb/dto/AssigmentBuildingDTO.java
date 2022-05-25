package com.laptrinhjavaweb.dto;

public class AssigmentBuildingDTO {

    private Long buildingId; //
    private Long[] staffs;  //
    private Long staffId;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long[] getStaffs() {
        return staffs;
    }

    public void setStaffs(Long[] staffs) {
        this.staffs = staffs;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
}
