package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<BuildingEntity,Long>  {
    Optional<BuildingEntity> findByName(String name);

    Optional<BuildingEntity> findById(Long id);

    long countByNameStartingWith(String prefix);

    List<BuildingEntity> findByNameStartingWithOrderByNameDesc(String prefix, Pageable pageable);
}
