package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.AssignmentBuildingEntity;
import com.laptrinhjavaweb.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuildingEntity, Long> {
//    tim danh sách tòa nhà đã được giao thông qua buildingEntity
    List<AssignmentBuildingEntity> findByBuildingEntity(BuildingEntity buildingEntity);
}
