package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CompanyWebModel extends CompanyDto {
    public CompanyDto company;
    public Long companyId;
    public final Set<DriverDto> drivers;
    public Set<Long> driverIds = new HashSet<>();




    public CompanyWebModel() {
        drivers = new HashSet<>();
    }

    public CompanyWebModel(CompanyDto dto) {
        super(dto);
        drivers = new HashSet<>();
    }

    public CompanyWebModel(CompanyDto dto, Set<DriverDto> drivers) {
        super(dto);
        this.drivers = drivers;
    }





    public Set<Long> getDriverIds() {
        return driverIds;
    }
    public void addDriverId(Long id) {
        driverIds.add(id);
    }

    public void setDriverIds(Set<Long> driverIds) {
        this.driverIds = driverIds;
    }


}
