package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DriverWebModel extends DriverDto {
    public final Set<CarDto> cars;
    public final Set<CompanyDto> workers;

    public DriverWebModel() {
        cars = new HashSet<>();
        workers=new HashSet<>();
    }

    public DriverWebModel(DriverDto dto) {
        super(dto);
        cars = new HashSet<>();
        workers = new HashSet<>();

    }

    public DriverWebModel(DriverDto dto,
                            Set<CarDto> cars,
    Set<CompanyDto> workers){
        super(dto);

        this.cars = cars;
        this.workers=workers;
    }

    public Collection<CarDto> getCars() {
        return cars;
    }


    public Set<CompanyDto> getWorkers() {
        return workers;
    }


}
