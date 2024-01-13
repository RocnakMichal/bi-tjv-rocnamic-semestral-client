package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DriverDto extends AbstractDtoWithId<Long>{

    private String name;

    private String surname;


    DriverDto(){}
    public DriverDto(Long id,String name, String surname){
        this.id=id;
        this.name=name;
        this.surname=surname;

    }

    public DriverDto(DriverDto other){
        id=other.id;
        name=other.name;
        surname=other.surname;

    }

    public static class DriverWebModel {
    }
}
