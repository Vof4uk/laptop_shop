package ua.mykytenko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mykytenko.dto.BrandDto;
import ua.mykytenko.service.BrandDtoService;
import ua.mykytenko.service.BrandService;
import ua.mykytenko.utils.DtoUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandDtoServiceImpl implements BrandDtoService{

    private final BrandService service;

    @Autowired
    public BrandDtoServiceImpl(BrandService service) {
        this.service = service;
    }

    @Override
    public BrandDto getById(int id) {
        return new BrandDto(service.getById(id));
    }

    @Override
    public BrandDto getByShortName(String shortName) {
        return new BrandDto(service.getByShortName(shortName));
    }

    @Override
    public List<BrandDto> getAll() {
        return service.getAll().stream()
                .map(BrandDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void add(BrandDto brand) {
        service.add(DtoUtil.dtoToEntity(brand));
    }

    @Override
    public void update(BrandDto brand) {
        service.update(DtoUtil.dtoToEntity(brand));
    }

    @Override
    public void delete(BrandDto brand) {
        service.delete(brand.getId());
    }

    @Override
    public void delete(int id) {
        service.delete(id);
    }
}
