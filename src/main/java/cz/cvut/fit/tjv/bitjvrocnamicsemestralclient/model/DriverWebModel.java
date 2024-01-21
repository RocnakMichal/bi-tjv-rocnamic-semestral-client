package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DriverWebModel extends DriverDto {
    public final Set<CarDto> cars;
    public final Set<CompanyDto> attendees;

    public DriverWebModel() {
        cars = new HashSet<>();
        attendees=new HashSet<>();
    }

    public DriverWebModel(DriverDto dto) {
        super(dto);
        cars = new HashSet<>();
        attendees = new HashSet<>();

    }

    public DriverWebModel(DriverDto dto,
                            Set<CarDto> cars,
    Set<CompanyDto> attendees){
        super(dto);

        this.cars = cars;
        this.attendees=attendees;
    }

    public Collection<CarDto> getCars() {
        return cars;
    }


    public Set<CompanyDto> getAttendees() {
        return attendees;
    }


}
