package com.laptrinhjavaweb.enums;

public enum RentTypesEnums {
    TANG_TRET("Tầng Trệt"),
    NGUYEN_CAN("Nguyên Căn"),
    NOI_THAT("Nội Thất");

    private  final String rentTypesValue;
    RentTypesEnums(String rentTypesValue){
        this.rentTypesValue = rentTypesValue;
    }
    public String getRentTypesValue(){
        return  rentTypesValue;
    }


}
