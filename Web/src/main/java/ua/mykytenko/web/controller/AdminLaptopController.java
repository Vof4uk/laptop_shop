package ua.mykytenko.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.mykytenko.entities.Brand;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.service.BrandService;
import ua.mykytenko.service.LaptopService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/admin/laptops")
public class AdminLaptopController {

    private static final int MAX_IMAGES = 3;
    private static final String LAPTOP_IMG_REL_PATH = "resources" + File.separator
            + "img" + File.separator + "laptop_photos" + File.separator;

    private final LaptopService laptopService;
    private final BrandService brandService;
    private final ServletContext servletContext;

    @Autowired
    public AdminLaptopController(LaptopService laptopService, BrandService brandService, ServletContext servletContext) {
        this.laptopService = laptopService;
        this.brandService = brandService;
        this.servletContext = servletContext;
    }

    @RequestMapping(path = "/all")
    public String allLaptops(Model model) {
        model.addAttribute("laptops", laptopService.getAll());
//        model.addAttribute("msg",);
        return "laptops";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String laptopForm(Model model,
                             @RequestParam(value = "id", defaultValue = "0") int id) {
        model.addAttribute("action", "update");
        model.addAttribute("brands", brandService.getAll());
        Laptop byId = laptopService.getById(id);
        //TODo
        model.addAttribute("laptop", byId);
        return "laptop_form";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String addLaptop(Model model) {
        model.addAttribute("action", "add");
        model.addAttribute("brands", brandService.getAll());
        return "laptop_form";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteBrand(@RequestParam(value = "laptop_id", defaultValue = "0") int id,
                              RedirectAttributes attributes) {
        //TODo
        if (id != 0) {
            if(!laptopService.delete(id)){
                attributes.addAttribute("message", "cannot delete laptop id=" + id);
            }
        }
        return "redirect:/admin/laptops/all";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String saveLaptop(@RequestParam(value = "id", defaultValue = "0") int id,
                             @RequestParam(value = "model") String model,
                             @RequestParam(value = "brand_id", defaultValue = "1") int brandId,
                             @RequestParam(value = "description", defaultValue = "") String description,
                             @RequestParam(value = "price", defaultValue = "0") BigDecimal price,
                             @RequestParam(value = "cpu_frequency", defaultValue = "0") double cpuFrequency,
                             @RequestParam(value = "ram", defaultValue = "0") double ram,
                             @RequestParam(value = "stock", defaultValue = "0") int stock,
                             @RequestParam(value = "old_image_path_list", defaultValue = "[]") List<String> imagesLocations,
                             @RequestParam(value = "action", defaultValue = "add") String action) {
        Brand brand = brandService.getById(brandId);
        Laptop laptop = new Laptop(model, description, price, ram, cpuFrequency, brand, stock);
        imagesLocations = imagesLocations.stream()
                .filter(s -> !s.equals(""))
                .map(str -> str.replaceAll("[]\\[]", ""))
                .collect(Collectors.toList());

        laptop.setImagesLocations(imagesLocations);
        if ("update".equals(action)) {
            laptop.setId(id);
            laptopService.update(laptop);
        } else if ("add".equals(action)) {
            laptopService.add(laptop);
        }
        return "redirect:/admin/laptops/all";
    }

    @RequestMapping(path = "/edit_photos", method = RequestMethod.GET)
    public String editPhotos(Model model
            , @RequestParam("id") int id) {
        Laptop laptop = laptopService.getById(id);
        model.addAttribute("laptop", laptop);
        return "laptop_photos_form";
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String processUploadedImages(@RequestParam("id") int id,
                                        @RequestParam(value = "main_photo", required = false) MultipartFile mainPhoto,
                                        @RequestParam(value = "new_photos", required = false) MultipartFile[] newPhotos) {
        List<String> newImgLocations = new ArrayList<>();
        String warPath = (String) servletContext.getAttribute("war_path");
        String imgPath = warPath + LAPTOP_IMG_REL_PATH;

        try {
            newImgLocations.add(writeImageOnDisc(imgPath, mainPhoto).toString());
        } catch (IOException e) {
            e.printStackTrace();
            newImgLocations.add("");
        }

        for (MultipartFile file : newPhotos) {
            try {
                newImgLocations.add(writeImageOnDisc(imgPath, file).toString());
            } catch (IOException e) {
                e.printStackTrace();
                newImgLocations.add("");
            }
        }

        Laptop byId = laptopService.getById(id);
        List<String> updatedImgLocations = mergeLists(byId.getImagesLocations(), newImgLocations);
        updatedImgLocations = updatedImgLocations.stream()
                .map((str) -> str.replace(warPath, ""))
                .collect(Collectors.toList());
        byId.setImagesLocations(updatedImgLocations);
        laptopService.update(byId);

        return "redirect:/admin/laptops/all";
    }

    private Path writeImageOnDisc(String location, MultipartFile file) throws IOException {
        if (!file.getContentType().contains("image")) {
            throw new IOException("file is not an image");
        }
        byte[] bytes = file.getBytes();
        Path path = Paths.get(location + file.getOriginalFilename());
        Files.write(path, bytes);
        return path;
    }

    private List<String> mergeLists(List<String> secondaryList, List<String> primaryList) {
        int size = primaryList.size();

        for (int i = 0; i < size || i < MAX_IMAGES; i++) {
            if (!primaryList.get(i).isEmpty()) {
                if (secondaryList.size() > i) {
                    if (!primaryList.get(i).equals("")) {
                        secondaryList.set(i, primaryList.get(i));
                    }
                } else {
                    secondaryList.add(primaryList.get(i));
                }
            }
        }
        return secondaryList;
    }

}






