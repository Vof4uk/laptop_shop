package ua.mykytenko.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.service.BrandService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/admin/brands")
public class AdminBrandController {

    private final BrandService brandService;

    @Autowired
    public AdminBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping(path = "/all")
    public String allBrands(Model model){
        model.addAttribute("brands",brandService.getAll());
        return "brands";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String brandForm(Model model,
                            @RequestParam("id") int id){
        model.addAttribute("action", "update");
        Brand byId = brandService.getById(id);
        model.addAttribute("brand", byId);
        return "brand_form";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String addBrand(Model model){
        model.addAttribute("action", "add");
        return "brand_form";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteBrand(@RequestParam(value = "brand_id", defaultValue = "0") int id){
        if(id != 0) {
            brandService.delete(id);
        }
        return "redirect:/admin/brands/all";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String saveBrand(@RequestParam(value = "id", defaultValue = "0") int id,
                            @RequestParam(value = "short_name") String shortName,
                            @RequestParam(value = "full_name", defaultValue = "") String fullName,
                            @RequestParam(value = "description", defaultValue = "") String description,
                            @RequestParam(value = "action", defaultValue = "add") String action){
        Brand brand = new Brand(shortName, fullName, description);
        if("add".equals(action)){
            brandService.add(brand);
        }else if("update".equals(action)){
            brand.setId(id);
            brandService.update(brand);
        }
        return "redirect:/admin/brands/all";
    }

}







