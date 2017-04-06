package ua.mykytenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mykytenko.dto.LaptopDto;
import ua.mykytenko.service.LaptopDtoService;
import ua.mykytenko.service.LaptopService;

import java.util.List;
import java.util.stream.Collectors;

import static ua.mykytenko.utils.DtoUtil.*;

@Service
public class LaptopDtoServiceImpl implements LaptopDtoService{

    private final LaptopService service;

    @Autowired
    public LaptopDtoServiceImpl(LaptopService service) {
        this.service = service;
    }


    @Override
    public LaptopDto getById(int id) {
        return new LaptopDto(service.getById(id));
    }

    @Override
    public List<LaptopDto> getAll() {
        return service.getAll().stream()
                .map(LaptopDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(int id) {
        return service.delete(id);
    }

    @Override
    public void update(LaptopDto laptop) {
        service.update(dtoToEntity(laptop));
    }

    @Override
    public void add(LaptopDto laptop) {
        service.add(dtoToEntity(laptop));
    }

    @Override
    public List<LaptopDto> getLaptopsInStock() {
        return service.getLaptopsInStock().stream()
                .map(LaptopDto::new)
                .collect(Collectors.toList());
    }
}
