package com.laptrinhjavaweb.api.admin;

import com.laptrinhjavaweb.dto.AssigmentBuildingDTO;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.service.AssignmentBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingServiceImpl;
import com.laptrinhjavaweb.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "buildingAPIOfAdmin")
public class BuildingAPI {

    @Autowired
    private BuildingServiceImpl buildingService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssignmentBuildingService assignmentBuilding;

    @GetMapping("/api/building")
    public BuildingDTO showBuilding(@RequestBody BuildingDTO buildingDTO)  {

        return buildingDTO;
    }
    @PostMapping("/api/building")
    public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO) throws Exception {
        buildingService.getDisTricts();
        buildingService.save(buildingDTO);
        return buildingDTO;
    }
    @DeleteMapping("/api/building/{id}")
    public void deleteBuilding(@PathVariable("id") Long id)  {
        buildingService.delete(id);
    }
    @PutMapping("/api/building/{id}")
    public BuildingDTO updateBuilding(@PathVariable("id") Long id, @RequestBody BuildingDTO buildingDTO)  {
        buildingService.updateBuilding(buildingDTO,id);
        return buildingDTO;
    }
    @GetMapping(value = "/api/building/{id}/staffs")
    public List<UserDTO> showStaffs(@PathVariable Long id){
        return userService.findByRoleBuidlings((long) 2,id);
    }

    @PostMapping("/api/building/assignBuilding")
    public String assignBuilding(@RequestBody AssigmentBuildingDTO responseDTO){
        List<AssigmentBuildingDTO> list= assignmentBuilding.save(responseDTO);
        if (list != null){
            return "success";
        }
        return "fail";
    }

//    @GetMapping(value = "api/building/{id}/staffs")
//    public List<UserDTO> showStaffs(@PathVariable Long id){
//        return userService.findByRoleBuidlings((long) 2,id);
//    }
//    @PostMapping("/assignBuilding")
//    public String assignBuilding(@RequestBody AssignmentBuildingDTO responseDTO){
//        List<AssignmentBuildingDTO> list= assignmentBuilding.save(responseDTO);
//        if (list != null){
//            return "success";
//        }
//        return "fail";
//    }

}
