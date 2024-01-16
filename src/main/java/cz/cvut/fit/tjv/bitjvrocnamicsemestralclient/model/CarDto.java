package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CarDto extends AbstractDtoWithId<Long>{



    private String licence_plate;

    private String brand;

    private String model;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d.M.yyyy")
    private LocalDate boughtIn;




    CarDto(){}
    public CarDto(CarDto other){
        id=other.id;
        licence_plate=other.licence_plate;
        brand=other.brand;
        model=other.model;
        boughtIn=other.boughtIn;
    }
    public CarDto(Long id,String licence_plate, String brand,String model,LocalDate boughtIn){
        this.id=id;
        this.licence_plate=licence_plate;
        this.brand=brand;
        this.model=model;
        this.boughtIn=boughtIn;
    }
}
