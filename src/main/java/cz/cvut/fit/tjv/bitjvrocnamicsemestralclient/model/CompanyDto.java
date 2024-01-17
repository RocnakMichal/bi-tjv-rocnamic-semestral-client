package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CompanyDto extends AbstractDtoWithId<Long>{





    private String name;
    long number_of_products;


    CompanyDto(){}
    public CompanyDto(CompanyDto other){
        id=other.id;
        name=other.name;
        number_of_products=other.number_of_products;
    }
    public CompanyDto(Long id,String name, Long number_of_products){
        this.id=id;
        this.name=name;
        this.number_of_products=number_of_products;

    }
}