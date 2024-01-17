package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CompanyDto;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CompanyWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CompanyClient extends AbstractClient<CompanyWebModel, CompanyDto, Long>{




    public CompanyClient (@Value("${backend_url}") String backendUrl) {
        super(backendUrl, "/company", CompanyWebModel.class);
    }




    @Override
    public CompanyWebModel newWM() {
        return new CompanyWebModel();
    }

    @Override
    public CompanyWebModel newWM(CompanyDto dto) {
        return new CompanyWebModel(dto);
    }
}