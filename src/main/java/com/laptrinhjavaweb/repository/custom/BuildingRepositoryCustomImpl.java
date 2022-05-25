package com.laptrinhjavaweb.repository.custom;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryCustomImpl implements BuildingRepositoryCustom{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<BuildingEntity> findAll(BuildingDTO model) {
        StringBuilder sql = buildBuildingSearch(model);
        Query query = entityManager.createNativeQuery(String.valueOf(sql),BuildingEntity.class);
        return query.getResultList();
    }

    private StringBuilder buildBuildingSearch(BuildingDTO model) {
        StringBuilder sql = new StringBuilder("select * from building as b ");
        StringBuilder sqlName = new StringBuilder();
        StringBuilder sqlStaff = new StringBuilder();
        StringBuilder sqlStaffId = new StringBuilder();
        StringBuilder sqlFloorArea = new StringBuilder();
        StringBuilder sqlNumberOfBasement = new StringBuilder();
        StringBuilder sqlDistrict = new StringBuilder();
        StringBuilder sqlWard = new StringBuilder();
        StringBuilder sqlStreet = new StringBuilder();
        StringBuilder sqlRentArea = new StringBuilder();
        StringBuilder sqlDirection = new StringBuilder();
        StringBuilder sqlLevel = new StringBuilder();
        StringBuilder sqlManagerName = new StringBuilder();
        StringBuilder sqlManagerPhone = new StringBuilder();
        StringBuilder sqlRentPrice = new StringBuilder();
        StringBuilder sqlTyle = new StringBuilder();

        if(model.getName() != null && !model.getName().isEmpty()){
            sqlName.append(" and b.name like '%" + model.getName() + "%' ");
        }
        if (model.getStaffId() != null && !model.getStaffId().isEmpty()) {
            sqlStaff.append("left join assignmentbuilding ab on ab.buildingid = b.id");
            sqlStaffId.append(" and ab.staffid =" + model.getStaffId());
        }
        if (model.getFloorArea() != null) {
            sqlFloorArea.append(" and b.floorarea = " + model.getFloorArea());
        }
        if (model.getDistrict() != null && !model.getDistrict().isEmpty()) {
            sqlDistrict.append(" and b.district like '%" + model.getDistrict() + "%'");
        }
        if (model.getWard() != null && !model.getWard().isEmpty()) {
            sqlWard.append(" and b.ward like '%" + model.getWard() + "%'");
        }
        if(model.getStreet() !=null && !model.getStreet().isEmpty()){
            sqlStreet.append(" and b.street like '%" + model.getStreet() + "%'");
        }
        if (model.getNumberOfBasement() != null) {
            sqlNumberOfBasement.append(" and b.numberofbasement = " + model.getNumberOfBasement());
        }
        if(model.getDirection() != null && !model.getDirection().isEmpty()){
            sqlDirection.append(" and b.direcion like '%" + model.getDirection()+ "%'");
        }
        if(model.getLevel() != null && !model.getLevel().isEmpty()){
            sqlLevel.append(" and b.level = " + model.getLevel());
        }
        if(model.getManagerName() != null && !model.getManagerName().isEmpty() ){
            sqlManagerName.append(" and b.managerName like '%" + model.getManagerName() +"%'");
        }
        if(model.getManagerPhone() != null && !model.getManagerPhone().isEmpty() ){
            sqlManagerPhone.append(" and b.managerPhone = " + model.getManagerPhone());
        }
        if (model.getAreaRentFrom() != null || model.getAreaRentTo() != null) {
            sqlRentArea.append(checkRentArea(model));
        }
        if (model.getRentPriceFrom() != null || model.getAreaRentTo() != null) {
            sqlRentPrice.append(checkRentPrice(model));
        }
        if (model.getRentTypes()!=null && model.getRentTypes().length > 0) {
            sqlTyle.append(checkType(model));
        }

        sql.append(sqlStaff + " where 1=1 " + sqlName + " " + sqlStaffId + " " + sqlFloorArea + " "
                + sqlDistrict + " " + sqlWard + " " + sqlStreet + " " + sqlNumberOfBasement + " "
                + sqlDirection + " " + sqlLevel + " " + sqlManagerName + " " + sqlManagerPhone + " "
                + sqlRentArea + " " + sqlRentPrice + " " + sqlTyle
                + " group by b.id");

        //Remove multiple spaces to one space in string.
        String sqlTemp = sql.toString();
        sqlTemp = sqlTemp.replaceAll("\\s+", " ");
        StringBuilder sqlResult = new StringBuilder(sqlTemp);

        return sqlResult;
    }

    private String checkRentPrice(BuildingDTO model) {
        String sqlRentPrice = "";

        if (model.getRentPriceFrom() != null && model.getRentPriceTo() != null) {
            sqlRentPrice = " and b.rentprice >= " + model.getRentPriceFrom() + "  and b.rentprice <= "
                    + model.getRentPriceTo();
        } else if (model.getRentPriceFrom() != null) {
            sqlRentPrice = " and b.rentprice >= " + model.getRentPriceFrom();
        } else if (model.getRentPriceTo() != null) {
            sqlRentPrice = " and b.rentprice <= " + model.getRentPriceTo();
        }

        return sqlRentPrice;
    }


    private StringBuilder checkRentArea(BuildingDTO model) {
        StringBuilder sqlRentArea = new StringBuilder();
        if(model.getAreaRentFrom() !=null || model.getAreaRentTo() !=null){
            sqlRentArea.append(" AND EXISTS (select * from rentarea AS ra where ra.buildingid = b.id");
            if(model.getAreaRentFrom() !=null){
                sqlRentArea.append(" AND "+ model.getAreaRentFrom() +" <= ra.value");
            }
            if(model.getAreaRentTo() !=null){
                sqlRentArea.append(" AND  ra.value <= "+ model.getAreaRentTo() +"");
            }
            sqlRentArea.append(")");
        }
        return sqlRentArea;
    }
    private String checkType(BuildingDTO model) {
        String sqlTyle = "", sqlTyles = "";
        for (int i = 0; i < model.getRentTypes().length; i++) {
            if (i < (model.getRentTypes().length - 1)) {
                sqlTyles += "or b.type like '%" + model.getRentTypes()[i] + "%' ";
            } else {
                sqlTyle = " and (b.type like '%" + model.getRentTypes()[i] + "%' " + sqlTyles + ")";
            }
        }

        return sqlTyle;
    }

}
