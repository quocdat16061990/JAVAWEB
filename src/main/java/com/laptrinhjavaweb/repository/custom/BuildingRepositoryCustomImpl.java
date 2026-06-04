package com.laptrinhjavaweb.repository.custom;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BuildingRepositoryCustomImpl implements BuildingRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<BuildingEntity> findAll(BuildingDTO model) {
        return findAll(model, Pageable.unpaged()).getContent();
    }

    @Override
    public Page<BuildingEntity> findAll(BuildingDTO model, Pageable pageable) {
        String fromAndWhere = buildFromAndWhere(model);
        Query selectQuery = entityManager.createNativeQuery(
                "select distinct b.* " + fromAndWhere + " order by b.id desc",
                BuildingEntity.class
        );

        if (pageable.isPaged()) {
            selectQuery.setFirstResult((int) pageable.getOffset());
            selectQuery.setMaxResults(pageable.getPageSize());
        }

        @SuppressWarnings("unchecked")
        List<BuildingEntity> entities = selectQuery.getResultList();

        Query countQuery = entityManager.createNativeQuery("select count(distinct b.id) " + fromAndWhere);
        Number total = (Number) countQuery.getSingleResult();

        return new PageImpl<>(entities, pageable, total.longValue());
    }

    private String buildFromAndWhere(BuildingDTO model) {
        StringBuilder from = new StringBuilder(" from building as b ");
        StringBuilder where = new StringBuilder(" where 1=1 ");

        if (model.getStaffId() != null && !model.getStaffId().isEmpty()) {
            from.append(" left join assignmentbuilding ab on ab.buildingid = b.id ");
            where.append(" and ab.staffid = ").append(model.getStaffId());
        }
        if (model.getName() != null && !model.getName().isEmpty()) {
            where.append(" and b.name like '%").append(model.getName()).append("%' ");
        }
        if (model.getFloorArea() != null) {
            where.append(" and b.floorarea = ").append(model.getFloorArea());
        }
        if (model.getDistrict() != null && !model.getDistrict().isEmpty()) {
            where.append(" and b.district like '%").append(model.getDistrict()).append("%'");
        }
        if (model.getWard() != null && !model.getWard().isEmpty()) {
            where.append(" and b.ward like '%").append(model.getWard()).append("%'");
        }
        if (model.getStreet() != null && !model.getStreet().isEmpty()) {
            where.append(" and b.street like '%").append(model.getStreet()).append("%'");
        }
        if (model.getNumberOfBasement() != null) {
            where.append(" and b.numberofbasement = ").append(model.getNumberOfBasement());
        }
        if (model.getDirection() != null && !model.getDirection().isEmpty()) {
            where.append(" and b.direcion like '%").append(model.getDirection()).append("%'");
        }
        if (model.getLevel() != null && !model.getLevel().isEmpty()) {
            where.append(" and b.level = ").append(model.getLevel());
        }
        if (model.getManagerName() != null && !model.getManagerName().isEmpty()) {
            where.append(" and b.managername like '%").append(model.getManagerName()).append("%'");
        }
        if (model.getManagerPhone() != null && !model.getManagerPhone().isEmpty()) {
            where.append(" and b.managerphone = ").append(model.getManagerPhone());
        }
        if (model.getAreaRentFrom() != null || model.getAreaRentTo() != null) {
            where.append(buildRentAreaCondition(model));
        }
        if (model.getRentPriceFrom() != null || model.getRentPriceTo() != null) {
            where.append(buildRentPriceCondition(model));
        }
        if (model.getRentTypes() != null && model.getRentTypes().length > 0) {
            where.append(buildTypeCondition(model));
        }

        return (from.toString() + where).replaceAll("\\s+", " ");
    }

    private String buildRentPriceCondition(BuildingDTO model) {
        StringBuilder condition = new StringBuilder();
        if (model.getRentPriceFrom() != null && model.getRentPriceTo() != null) {
            condition.append(" and b.rentprice >= ").append(model.getRentPriceFrom())
                    .append(" and b.rentprice <= ").append(model.getRentPriceTo());
        } else if (model.getRentPriceFrom() != null) {
            condition.append(" and b.rentprice >= ").append(model.getRentPriceFrom());
        } else if (model.getRentPriceTo() != null) {
            condition.append(" and b.rentprice <= ").append(model.getRentPriceTo());
        }
        return condition.toString();
    }

    private String buildRentAreaCondition(BuildingDTO model) {
        StringBuilder condition = new StringBuilder(" and exists (select * from rentarea as ra where ra.buildingid = b.id");
        if (model.getAreaRentFrom() != null) {
            condition.append(" and ").append(model.getAreaRentFrom()).append(" <= ra.value");
        }
        if (model.getAreaRentTo() != null) {
            condition.append(" and ra.value <= ").append(model.getAreaRentTo());
        }
        condition.append(")");
        return condition.toString();
    }

    private String buildTypeCondition(BuildingDTO model) {
        StringBuilder condition = new StringBuilder(" and (");
        for (int i = 0; i < model.getRentTypes().length; i++) {
            if (i > 0) {
                condition.append(" or ");
            }
            condition.append(" b.type like '%").append(model.getRentTypes()[i]).append("%' ");
        }
        condition.append(")");
        return condition.toString();
    }
}
