package ru.job4j.dreamjob.service.city;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.repository.city.CityRepository;

import java.util.Collection;

@Service
public class SimpleCityService implements CityService {
    private final CityRepository cityRepository;

    public SimpleCityService(CityRepository sql2oCityRepositoty) {
        this.cityRepository = sql2oCityRepositoty;
    }

    @Override
    public Collection<City> findAll() {
        return cityRepository.findAll();
    }
}
