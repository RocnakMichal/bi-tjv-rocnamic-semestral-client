package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DriverWebModel extends DriverDto {
    public final Set<CarDto> cars;

    public DriverWebModel() {
        cars = new HashSet<>();
    }

    public DriverWebModel(DriverDto dto) {
        super(dto);
        cars = new HashSet<>();

    }

    public DriverWebModel(DriverDto dto,
                            Set<CarDto> cars) {
        super(dto);

        this.cars = cars;
    }

    public Collection<CarDto> getCars() {
        return cars;
    }


}
