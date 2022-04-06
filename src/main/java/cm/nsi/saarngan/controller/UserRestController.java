package cm.nsi.saarngan.controller;

import cm.nsi.saarngan.services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 13:56, mer.
 * saar-ngan
 **/
@RestController
public class UserRestController {
    private final UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
