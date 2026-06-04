package com.laptrinhjavaweb.seed;

import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BulkBuildingSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(BulkBuildingSeeder.class);
    private static final String SEED_PREFIX = "SEED-BUILDING-";

    private final BuildingRepository buildingRepository;

    @Value("${app.seed.bulk-buildings.enabled:true}")
    private boolean enabled;

    @Value("${app.seed.bulk-buildings.count:4000}")
    private int targetSeedCount;

    @Value("${app.seed.bulk-buildings.batch-size:250}")
    private int batchSize;

    public BulkBuildingSeeder(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!enabled || targetSeedCount <= 0) {
            return;
        }

        long existingSeeded = buildingRepository.countByNameStartingWith(SEED_PREFIX);
        if (existingSeeded > targetSeedCount) {
            int excess = (int) (existingSeeded - targetSeedCount);
            log.info("Removing {} excess seeded buildings to match target size.", excess);
            List<BuildingEntity> redundantBuildings = buildingRepository.findByNameStartingWithOrderByNameDesc(
                    SEED_PREFIX,
                    PageRequest.of(0, excess)
            );
            buildingRepository.deleteAll(redundantBuildings);
            log.info("Bulk building seed trimmed. Total seeded buildings now: {}", targetSeedCount);
            return;
        }

        if (existingSeeded == targetSeedCount) {
            log.info("Bulk building seed skipped. Existing seeded buildings: {}", existingSeeded);
            return;
        }

        int missing = (int) (targetSeedCount - existingSeeded);
        log.info("Seeding {} additional buildings for UI/API load testing.", missing);

        List<BuildingEntity> batch = new ArrayList<>();
        int startIndex = (int) existingSeeded + 1;
        int endIndex = (int) existingSeeded + missing;

        for (int i = startIndex; i <= endIndex; i++) {
            batch.add(buildBuilding(i));
            if (batch.size() >= batchSize) {
                buildingRepository.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            buildingRepository.saveAll(batch);
        }

        log.info("Bulk building seed completed. Total seeded buildings now: {}", targetSeedCount);
    }

    private BuildingEntity buildBuilding(int index) {
        String[] districts = {
                "QUAN_1", "QUAN_2", "QUAN_3", "QUAN_4", "QUAN_5", "QUAN_6", "QUAN_7", "QUAN_8",
                "QUAN_9", "QUAN_10", "QUAN_11", "QUAN_12", "QUAN_THU_DUC", "QUAN_TAN_BINH",
                "QUAN_GO_VAP", "QUAN_Tan_Phu", "QUAN_Binh_Chanh"
        };
        String[] wards = {"Phường Linh Trung", "Phường Bình Thọ", "Phường Hiệp Bình", "Phường 7", "Phường 12"};
        String[] streets = {"Đường Hoa Lan", "Đường Nguyễn Văn Cừ", "Đường Pasteur", "Đường Phan Xích Long", "Đường Võ Văn Ngân"};
        String[] directions = {"Đông", "Tây", "Nam", "Bắc", "Đông Nam", "Tây Bắc"};
        String[] rentTypes = {"TANG_TRET", "NGUYEN_CAN", "NOI_THAT"};

        BuildingEntity building = new BuildingEntity();
        building.setName(SEED_PREFIX + String.format("%04d", index));
        building.setDistrict(districts[index % districts.length]);
        building.setWard(wards[index % wards.length]);
        building.setStreet(streets[index % streets.length] + " số " + ((index % 80) + 1));
        building.setFloorArea(120 + (index % 25) * 20);
        building.setNumberOfBasement((index % 4) + 1);
        building.setRentPrice(15_000_000 + (index % 120) * 500_000);
        building.setManagerName("Quản lý " + index);
        building.setManagerPhone(String.format("09%08d", index % 100000000));
        building.setDirection(directions[index % directions.length]);
        building.setNote("Dữ liệu seed để test giao diện và hiệu năng danh sách tòa nhà.");
        building.setType(resolveRentType(index, rentTypes));
        building.setServiceFee((200 + (index % 30) * 10) + " nghìn/m2");
        building.setMotoFee((50 + (index % 10) * 10) + " nghìn");
        building.setCarFee((500 + (index % 15) * 50) + " nghìn");
        building.setWaterFee("Miễn phí");
        building.setElectricityFee("Theo đồng hồ");
        building.setPayment("Theo quý");
        building.setRentTime("12 tháng");
        building.setDeposit("3 tháng");
        building.setCreatedBy("bulk-seeder");
        building.setModifiedBy("bulk-seeder");
        building.setCreatedDate(new Date());
        building.setModifiedDate(new Date());

        List<RentAreaEntity> rentAreas = new ArrayList<>();
        rentAreas.add(createRentArea(building, 50 + (index % 10) * 10));
        rentAreas.add(createRentArea(building, 120 + (index % 15) * 10));
        rentAreas.add(createRentArea(building, 250 + (index % 20) * 10));
        building.setAreaEntities(rentAreas);
        return building;
    }

    private RentAreaEntity createRentArea(BuildingEntity building, int value) {
        RentAreaEntity rentArea = new RentAreaEntity();
        rentArea.setValue(value);
        rentArea.setBuildingEntity(building);
        rentArea.setCreatedBy("bulk-seeder");
        rentArea.setModifiedBy("bulk-seeder");
        rentArea.setCreatedDate(new Date());
        rentArea.setModifiedDate(new Date());
        return rentArea;
    }

    private String resolveRentType(int index, String[] rentTypes) {
        if (index % 3 == 0) {
            return rentTypes[0] + "," + rentTypes[2];
        }
        if (index % 3 == 1) {
            return rentTypes[1];
        }
        return rentTypes[0] + "," + rentTypes[1];
    }
}
