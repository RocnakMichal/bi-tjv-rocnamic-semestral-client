package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CompanyWebModel extends CompanyDto {
    public CompanyDto company;
    public Long companyId;

    public CompanyWebModel() {}

    public CompanyWebModel(CompanyDto dto) {
        super(dto);
    }


}
