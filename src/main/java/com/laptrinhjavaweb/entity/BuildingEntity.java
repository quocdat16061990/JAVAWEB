package com.laptrinhjavaweb.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "building")
public class BuildingEntity extends  BaseEntity{

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "brokeragefee")
    private String brokerAgeFee;

    @Column(name = "carfee")
    private String carFee;

    @Column(name = "decorationtime")
    private String decorationTime;

    @Column(name = "deposit")
    private String deposit;
     @Column(name = "card")
    private String card;

     @Column(name = "transaction")
    private String transaction;


    @Column(name = "direcion")
    private String direction;

    @Column(name = "district")
    private String district;

    @Column(name = "electricityfee")
    private String electricityFee;

    @Column(name = "level")
    private String level;

    @Column(name = "linkofbuilding")
    private String linkOfBuilding;

    @Column(name = "map")
    private String map;

    @Column(name = "motofee")
    private String motoFee;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "numberofbasement")
    private Integer numberOfBasement;

    @Column(name = "floorarea")
    private Integer floorArea;

    @Column(name = "overtimefee")
    private String overtimeFee;

    @Column(name = "payment")
    private String payment;

    @Column(name = "rentprice")
    private Integer rentPrice;

    @Lob
    @Column(name = "rentpricedescription")
    private String rentPriceDescription;

    @Column(name = "renttime")
    private String rentTime;

    @Column(name = "servicefee")
    private String serviceFee;

    @Column(name = "street")
    private String street;

    @Column(name = "structure")
    private String structure;

    @Column(name = "type")
    private String type;

    @Column(name = "ward")
    private String ward;

    @Column(name = "waterfee")
    private String waterFee;

    @Column(name = "managername")
    private String managerName;

    @Column(name = "managerphone")
    private String managerPhone;

    @Lob
    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "buildingEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<RentAreaEntity> areaEntities = new ArrayList<>();

    @OneToMany(mappedBy = "buildingEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<AssignmentBuildingEntity> assignmentBuildingEntities = new ArrayList<>();


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBrokerAgeFee() {
        return brokerAgeFee;
    }

    public void setBrokerAgeFee(String brokerAgeFee) {
        this.brokerAgeFee = brokerAgeFee;
    }

    public String getCarFee() {
        return carFee;
    }

    public void setCarFee(String carFee) {
        this.carFee = carFee;
    }

    public String getDecorationTime() {
        return decorationTime;
    }

    public void setDecorationTime(String decorationTime) {
        this.decorationTime = decorationTime;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(String electricityFee) {
        this.electricityFee = electricityFee;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLinkOfBuilding() {
        return linkOfBuilding;
    }

    public void setLinkOfBuilding(String linkOfBuilding) {
        this.linkOfBuilding = linkOfBuilding;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMotoFee() {
        return motoFee;
    }

    public void setMotoFee(String motoFee) {
        this.motoFee = motoFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOvertimeFee() {
        return overtimeFee;
    }

    public void setOvertimeFee(String overtimeFee) {
        this.overtimeFee = overtimeFee;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getRentPriceDescription() {
        return rentPriceDescription;
    }

    public void setRentPriceDescription(String rentPriceDescription) {
        this.rentPriceDescription = rentPriceDescription;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getWaterFee() {
        return waterFee;
    }

    public void setWaterFee(String waterFee) {
        this.waterFee = waterFee;
    }

    public List<RentAreaEntity> getAreaEntities() {
        return areaEntities;
    }

    public void setAreaEntities(List<RentAreaEntity> areaEntities) {
        this.areaEntities = areaEntities;
    }

    public List<AssignmentBuildingEntity> getAssignmentBuildingEntities() {
        return assignmentBuildingEntities;
    }

    public void setAssignmentBuildingEntities(List<AssignmentBuildingEntity> assignmentBuildingEntities) {
        this.assignmentBuildingEntities = assignmentBuildingEntities;
    }

    public Integer getNumberOfBasement() {
        return numberOfBasement;
    }

    public void setNumberOfBasement(Integer numberOfBasement) {
        this.numberOfBasement = numberOfBasement;
    }

    public Integer getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Integer floorArea) {
        this.floorArea = floorArea;
    }

    public void setRentPrice(Integer rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
