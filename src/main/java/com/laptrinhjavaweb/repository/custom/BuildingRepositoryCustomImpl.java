package com.laptrinhjavaweb.repository.custom;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class BuildingRepositoryCustomImpl implements BuildingRepositoryCustom{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<BuildingEntity> findAll(BuildingDTO model) {
        StringBuilder sql = sqlSearch(model);
        Query query = entityManager.createNativeQuery(String.valueOf(sql),BuildingEntity.class);
        return query.getResultList();
    }

    private StringBuilder sqlSearch(BuildingDTO model) {
        StringBuilder sql = new StringBuilder("select * from building b ");
        StringBuilder sqlName = new StringBuilder();
        StringBuilder sqlStaff = new StringBuilder();
        StringBuilder sqlStaffId = new StringBuilder();
        StringBuilder sqlFloorArea = new StringBuilder();
        StringBuilder sqlNumberOfBasement = new StringBuilder();
        StringBuilder sqlDistrict = new StringBuilder();
        StringBuilder sqlWard = new StringBuilder();
        StringBuilder sqlStreet = new StringBuilder();
        StringBuilder sqlRentArea = new StringBuilder();
        StringBuilder sqlRentPrice = new StringBuilder();
        StringBuilder sqlTyle = new StringBuilder();

        if(model.getName() !=null && !model.getName().isEmpty()){
            sqlName.append(" AND b.name like '%" + model.getName() + "%' ");
        }
        sql.append("where 1=1"+ sqlName);

        return sql;
    }
}
