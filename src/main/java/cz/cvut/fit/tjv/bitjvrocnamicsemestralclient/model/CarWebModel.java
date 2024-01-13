package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;



public class CarWebModel extends CarDto {
    public DriverDto driver;
    public Long driverId;

    public CarWebModel() {}

    public CarWebModel(CarDto dto) {
        super(dto);
    }

    public CarWebModel(DriverDto driver) {
        this.driver = driver;
    }

    public DriverDto getDriver() {
        return driver;
    }

    public void setDriver(DriverDto driver) {
        this.driver = driver;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
}
