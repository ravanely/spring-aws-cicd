package cm.nsi.saarngan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by : Ravanely
 * create at : 04/04/2022, 13:44, lun.
 * saar-ngan
 **/
@Controller
@RequestMapping("")
public class MainController {

    @GetMapping
    @ResponseBody
    public String home() {
        return "Hello World !";
    }
}
