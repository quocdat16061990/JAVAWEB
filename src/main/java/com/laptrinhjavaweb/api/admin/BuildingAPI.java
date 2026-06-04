package com.laptrinhjavaweb.api.admin;

import com.laptrinhjavaweb.dto.AssigmentBuildingDTO;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.PageResponseDTO;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.service.AssignmentBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingServiceImpl;
import com.laptrinhjavaweb.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "buildingAPIOfAdmin")
@Tag(name = "Building", description = "Building management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class BuildingAPI {

    @Autowired
    private BuildingServiceImpl buildingService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssignmentBuildingService assignmentBuilding;

    @GetMapping("/api/building")
    @Operation(summary = "Get paginated buildings with filters")
    public PageResponseDTO<BuildingDTO> showBuilding(BuildingDTO buildingDTO,
                                                     @RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "20") int size)  {
        int normalizedPage = Math.max(page, 1);
        int normalizedSize = Math.min(Math.max(size, 1), 100);
        Page<BuildingDTO> pageData = buildingService.findAll(
                buildingDTO,
                PageRequest.of(normalizedPage - 1, normalizedSize)
        );
        return PageResponseDTO.from(pageData);
    }
    @PostMapping("/api/building")
    @Operation(summary = "Create a building")
    public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO) throws Exception {
        buildingService.getDisTricts();
        buildingService.save(buildingDTO);
        return buildingDTO;
    }
    @DeleteMapping("/api/building/{id}")
    @Operation(summary = "Delete a building by id")
    public void deleteBuilding(@PathVariable("id") Long id)  {
        buildingService.delete(id);
    }
    @PutMapping("/api/building/{id}")
    @Operation(summary = "Update a building by id")
    public BuildingDTO updateBuilding(@PathVariable("id") Long id, @RequestBody BuildingDTO buildingDTO)  {
        buildingService.updateBuilding(buildingDTO,id);
        return buildingDTO;
    }
    @GetMapping(value = "/api/building/{id}/staffs")
    @Operation(summary = "Get staffs assigned to a building")
    public List<UserDTO> showStaffs(@PathVariable Long id){
        return userService.findByRoleBuidlings((long) 2,id);
    }

    @PostMapping("/api/building/assignBuilding")
    @Operation(summary = "Assign staffs to a building")
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
