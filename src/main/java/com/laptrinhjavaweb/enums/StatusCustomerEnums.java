package com.laptrinhjavaweb.enums;

public enum StatusCustomerEnums {
    DANG_THUC_HIEN("Đang Thực Hiện"),
    HOAN_TAT("Hoàn Tất");


    private final String statusValue;
    StatusCustomerEnums(String statusValue) {
        this.statusValue = statusValue;
    }
    public String getStatusValue() {
        return statusValue;
    }
}
