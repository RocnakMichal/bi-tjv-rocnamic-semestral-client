package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.ui;

import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.data.DriverClient;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverWebModel;
import cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.model.DriverDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/driver")
public class DriverWebController extends AbstractWebController<DriverWebModel, DriverDto, DriverClient, Long> {
    public DriverWebController(DriverClient driverClient) {
        super(driverClient, "driver");
    }
}
