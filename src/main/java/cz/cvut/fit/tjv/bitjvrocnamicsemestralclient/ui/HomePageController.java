package cz.cvut.fit.tjv.bitjvrocnamicsemestralclient.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {
    @GetMapping
    public String homeAction() {
        return "/general/homePage";
    }

    @GetMapping("/error/{errCode}")
    public String errorAction(@PathVariable int errCode, Model model) {
        model.addAttribute("errCode", errCode);
        switch (errCode) {
            case 400:
                model.addAttribute("errMsg", "Something bad happend");
                break;

            case 404:
                model.addAttribute("errMsg", "Site not found");
                break;

            case 500:
                model.addAttribute("errMsg", "Server error");
                break;

            default:
                model.addAttribute("errMsg", "Unknown error");
        }
        return "general/error";
    }
}
