package ua.mykytenko.web.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.mykytenko.dto.BrandDto;
import ua.mykytenko.dto.LaptopDto;
import ua.mykytenko.service.BrandDtoService;
import ua.mykytenko.service.LaptopDtoService;
import ua.mykytenko.web.WebUtil;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping(path = "/r/admin")
public class AdminRestController {
    private static final String LAPTOP_IMG_REL_PATH = "resources" + File.separator
            + "img" + File.separator + "laptop_photos" + File.separator;

    private final BrandDtoService brandService;
    private final ServletContext context;
    private final LaptopDtoService laptopService;

    @Autowired
    public AdminRestController(BrandDtoService brandService, LaptopDtoService laptopService, ServletContext context) {
        this.brandService = brandService;
        this.laptopService = laptopService;
        this.context = context;
    }


    @RequestMapping(path = "/addBrand", method = RequestMethod.POST)
    public ResponseEntity addBrand(@RequestBody BrandDto brand){
        brandService.add(brand);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/saveBrand", method = RequestMethod.POST)
    public ResponseEntity editBrand(@RequestBody BrandDto brand){
        brandService.update(brand);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/deleteBrand/{id}")
    public ResponseEntity deleteBrand(@PathVariable("id") int id){
        brandService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/brand/{id}")
    public ResponseEntity<BrandDto> findBrand(@PathVariable("id") int id){
        BrandDto brandDto = brandService.getById(id);
        MultiValueMap headers = WebUtil.getHeadersRestUtf8();
        return new ResponseEntity<>(brandDto, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/brands")
    public ResponseEntity<String> getAllBrands(){
        String json = convertListToJson(brandService.getAll());
        MultiValueMap headers = WebUtil.getHeadersRestUtf8();
        return new ResponseEntity<>(json, headers , HttpStatus.OK);
    }

    @RequestMapping(path = "/laptops")
    public ResponseEntity<String> getAllLaptops(){
        String json = convertListToJson(laptopService.getAll());
        MultiValueMap headers = WebUtil.getHeadersRestUtf8();
        return new ResponseEntity<>(json, headers , HttpStatus.OK);
    }

    @RequestMapping(path = "/laptop/update", method = RequestMethod.POST)
    public ResponseEntity<String> saveUpdatedLaptop(
            @RequestBody LaptopDto laptopDto){
        laptopService.update(laptopDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/upload/photo", method = RequestMethod.POST)
    public ResponseEntity<String> uploadPhoto(@RequestParam(name = "photo_file", required = false) MultipartFile file){
        if(file == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String appLocation = context.getRealPath("");
        String s = null;
        MultiValueMap headers = WebUtil.getHeadersRestUtf8();
        try {
            s = writeImageOnDisc(appLocation + LAPTOP_IMG_REL_PATH, file);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        s = s.replace(context.getRealPath(""), "");
        return new ResponseEntity<>(s.replace(File.separator, "/"), headers, HttpStatus.OK);
    }

    private String convertListToJson(List list){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String responseBody = null;
        try {
            responseBody = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    private String writeImageOnDisc(String location, MultipartFile file) throws IOException {
        if (file.getContentType().contains("image")) {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(location + file.getOriginalFilename());
        Files.write(path, bytes);
        return path.toString();
        }
        return "";
    }
}
