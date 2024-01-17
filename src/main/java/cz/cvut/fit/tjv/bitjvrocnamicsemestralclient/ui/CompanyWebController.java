package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.ui;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.CompanyClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CompanyDto;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.CompanyWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyWebController extends AbstractWebController<CompanyWebModel, CompanyDto, CompanyClient, Long> {
    public CompanyWebController(CompanyClient companyClient) {
        super(companyClient, "company");
    }
}
