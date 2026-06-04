package com.laptrinhjavaweb.controller;

import com.laptrinhjavaweb.dto.AssigmentBuildingDTO;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.service.AssignmentBuildingService;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AssignmentBuildingService assignmentBuildingService;

    @GetMapping("/")
    public String root() {
        return "redirect:/web";
    }

    @GetMapping("/docs")
    public String docsRedirect() {
        return "redirect:/web/tai-lieu";
    }

    @GetMapping("/web")
    public String home(Model model) {
        fillSharedModel(model);
        return "web/index";
    }

    @GetMapping("/web/login")
    public String loginRedirect() {
        return "redirect:/web/dang-nhap";
    }

    @GetMapping("/web/docs")
    public String legacyDocsRedirect() {
        return "redirect:/web/tai-lieu";
    }

    @GetMapping("/web/dang-nhap")
    public String login(Model model) {
        fillSharedModel(model);
        return "web/login";
    }

    @GetMapping("/web/tai-lieu")
    public String docs(Model model) {
        fillSharedModel(model);
        return "web/docs";
    }

    @GetMapping("/web/toa-nha")
    public String buildings(@ModelAttribute("searchForm") BuildingDTO searchForm,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "20") int size,
                            Model model) {
        int normalizedPage = Math.max(page, 1);
        int normalizedSize = Math.min(Math.max(size, 20), 100);
        Page<BuildingDTO> buildingPage = buildingService.findAll(searchForm, PageRequest.of(normalizedPage - 1, normalizedSize));

        if (buildingPage.getTotalPages() > 0 && normalizedPage > buildingPage.getTotalPages()) {
            normalizedPage = buildingPage.getTotalPages();
            buildingPage = buildingService.findAll(searchForm, PageRequest.of(normalizedPage - 1, normalizedSize));
        }

        fillSharedModel(model);
        fillBuildingOptions(model);
        model.addAttribute("buildingPage", buildingPage);
        model.addAttribute("buildings", buildingPage.getContent());
        model.addAttribute("currentPage", normalizedPage);
        model.addAttribute("pageSize", normalizedSize);
        model.addAttribute("totalPages", buildingPage.getTotalPages());
        model.addAttribute("totalElements", buildingPage.getTotalElements());
        model.addAttribute("pageNumbers", buildPageNumbers(buildingPage));
        if (!model.containsAttribute("buildingForm")) {
            model.addAttribute("buildingForm", new BuildingDTO());
        }
        return "web/buildings";
    }

    @PostMapping("/web/toa-nha/tao-moi")
    public String createBuilding(@ModelAttribute("buildingForm") BuildingDTO buildingForm,
                                 RedirectAttributes redirectAttributes) {
        try {
            buildingService.save(buildingForm);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo tòa nhà thành công.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            redirectAttributes.addFlashAttribute("buildingForm", buildingForm);
        }
        return "redirect:/web/toa-nha";
    }

    @GetMapping("/web/toa-nha/{id}/sua")
    public String editBuilding(@PathVariable Long id, Model model) {
        fillSharedModel(model);
        fillBuildingOptions(model);
        model.addAttribute("building", buildingService.findById(id));
        return "web/building-edit";
    }

    @PostMapping("/web/toa-nha/{id}/cap-nhat")
    public String updateBuilding(@PathVariable Long id,
                                 @ModelAttribute("building") BuildingDTO buildingForm,
                                 RedirectAttributes redirectAttributes) {
        try {
            buildingForm.setId(id);
            buildingService.updateBuilding(buildingForm, id);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật tòa nhà thành công.");
            return "redirect:/web/toa-nha/" + id + "/sua";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/web/toa-nha/" + id + "/sua";
        }
    }

    @PostMapping("/web/toa-nha/{id}/xoa")
    public String deleteBuilding(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        buildingService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa tòa nhà có mã " + id + ".");
        return "redirect:/web/toa-nha";
    }

    @GetMapping("/web/toa-nha/{id}/nhan-vien")
    public String assignStaffPage(@PathVariable Long id, Model model) {
        fillSharedModel(model);
        fillBuildingOptions(model);
        model.addAttribute("building", buildingService.findById(id));
        model.addAttribute("staffs", userService.findByRoleBuidlings(2L, id));
        if (!model.containsAttribute("assignmentForm")) {
            AssigmentBuildingDTO assignmentForm = new AssigmentBuildingDTO();
            assignmentForm.setBuildingId(id);
            model.addAttribute("assignmentForm", assignmentForm);
        }
        return "web/building-staff";
    }

    @PostMapping("/web/toa-nha/{id}/nhan-vien")
    public String assignStaff(@PathVariable Long id,
                              @ModelAttribute("assignmentForm") AssigmentBuildingDTO assignmentForm,
                              RedirectAttributes redirectAttributes) {
        try {
            assignmentForm.setBuildingId(id);
            assignmentBuildingService.save(assignmentForm);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật danh sách nhân viên thành công.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/web/toa-nha/" + id + "/nhan-vien";
    }

    private void fillSharedModel(Model model) {
        model.addAttribute("appName", "Website quản lý bất động sản");
        model.addAttribute("swaggerUiUrl", "/swagger-ui.html");
        model.addAttribute("openApiJsonUrl", "/v3/api-docs");
        model.addAttribute("loginUrl", "/api/auth/login");
        model.addAttribute("webHomeUrl", "/web");
        model.addAttribute("webLoginUrl", "/web/dang-nhap");
        model.addAttribute("webDocsUrl", "/web/tai-lieu");
        model.addAttribute("webBuildingsUrl", "/web/toa-nha");
    }

    private void fillBuildingOptions(Model model) {
        model.addAttribute("districtOptions", districtOptions());
        model.addAttribute("rentTypeOptions", rentTypeOptions());
    }

    private Map<String, String> districtOptions() {
        Map<String, String> districts = new LinkedHashMap<>();
        districts.put("QUAN_1", "Quận 1");
        districts.put("QUAN_2", "Quận 2");
        districts.put("QUAN_3", "Quận 3");
        districts.put("QUAN_4", "Quận 4");
        districts.put("QUAN_5", "Quận 5");
        districts.put("QUAN_6", "Quận 6");
        districts.put("QUAN_7", "Quận 7");
        districts.put("QUAN_8", "Quận 8");
        districts.put("QUAN_9", "Quận 9");
        districts.put("QUAN_10", "Quận 10");
        districts.put("QUAN_11", "Quận 11");
        districts.put("QUAN_12", "Quận 12");
        districts.put("QUAN_THU_DUC", "Quận Thủ Đức");
        districts.put("QUAN_TAN_BINH", "Quận Tân Bình");
        districts.put("QUAN_GO_VAP", "Quận Gò Vấp");
        districts.put("QUAN_Tan_Phu", "Quận Tân Phú");
        districts.put("QUAN_Binh_Chanh", "Quận Bình Chánh");
        return districts;
    }

    private Map<String, String> rentTypeOptions() {
        Map<String, String> rentTypes = new LinkedHashMap<>();
        rentTypes.put("TANG_TRET", "Tầng trệt");
        rentTypes.put("NGUYEN_CAN", "Nguyên căn");
        rentTypes.put("NOI_THAT", "Nội thất");
        return rentTypes;
    }

    private List<Integer> buildPageNumbers(Page<BuildingDTO> page) {
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        if (totalPages == 0) {
            return pageNumbers;
        }

        int currentPage = page.getNumber() + 1;
        if (totalPages <= 7) {
            for (int pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
                pageNumbers.add(pageNumber);
            }
            return pageNumbers;
        }

        int start = Math.max(2, currentPage - 1);
        int end = Math.min(totalPages - 1, currentPage + 1);

        if (currentPage <= 4) {
            start = 2;
            end = 5;
        } else if (currentPage >= totalPages - 3) {
            start = totalPages - 4;
            end = totalPages - 1;
        }

        pageNumbers.add(1);
        if (start > 2) {
            pageNumbers.add(null);
        }
        for (int pageNumber = start; pageNumber <= end; pageNumber++) {
            pageNumbers.add(pageNumber);
        }
        if (end < totalPages - 1) {
            pageNumbers.add(null);
        }
        pageNumbers.add(totalPages);
        return pageNumbers;
    }
}
