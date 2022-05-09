package com.laptrinhjavaweb.controller.admin;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "buildingControllerOfAdmin")
public class BuildingController {

   @Autowired
   private IBuildingService buildingService;

    @RequestMapping(value = "/admin/building-list", method = RequestMethod.GET)
    public ModelAndView buildingList(@ModelAttribute("modelSearch") BuildingDTO buildingDTO) {
        ModelAndView mav = new ModelAndView("admin/building/list");
        mav.addObject("model", new BuildingDTO());
        mav.addObject("searchBuilding",buildingService.findAll(buildingDTO));
        mav.addObject("districtEnums",buildingService.getDisTricts());
        mav.addObject("rentTypesEnums",buildingService.getRentTypes());

        return mav;
    }
    @RequestMapping(value = "/admin/building-edit", method = RequestMethod.GET)
    public ModelAndView buildingEdit() {
        ModelAndView mav = new ModelAndView("admin/building/edit");
        mav.addObject("building", new BuildingDTO());
        mav.addObject("districtEnums",buildingService.getDisTricts());
        mav.addObject("rentTypesEnums",buildingService.getRentTypes());

        return mav;
    }
}
