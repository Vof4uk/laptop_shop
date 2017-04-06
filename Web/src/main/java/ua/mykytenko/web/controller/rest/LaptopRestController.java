package ua.mykytenko.web.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mykytenko.dto.LaptopDto;
import ua.mykytenko.service.LaptopDtoService;

@RestController
@RequestMapping(path = "/r/laptops")
public class LaptopRestController {

    private final LaptopDtoService dtoService;

    @Autowired
    public LaptopRestController(LaptopDtoService dtoService) {
        this.dtoService = dtoService;
    }

    @RequestMapping(path = "/all")
    public ResponseEntity<String> getAllLaptops(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String responseBody = null;
        try {
            responseBody = objectMapper.writeValueAsString(dtoService.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RequestMapping(path = "/id/{id}")
    public ResponseEntity<LaptopDto> getLaptop(@PathVariable("id") int id){
        LaptopDto laptopDto = dtoService.getById(id);
        return new ResponseEntity<>(laptopDto, HttpStatus.OK);
    }
}
