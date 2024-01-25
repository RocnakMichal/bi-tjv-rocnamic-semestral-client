package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarWebModel extends CarDto {
    public DriverDto driver;
    public Long driverId;
    private String name;

    public CarWebModel() {}

    public CarWebModel(CarDto dto) {
        super(dto);
    }

    public CarWebModel(DriverDto driver) {
        this.driver = driver;
    }

}
