package cm.nsi.saarngan.controller;

import cm.nsi.saarngan.entity.User;
import cm.nsi.saarngan.security.SaarNganUserDetails;
import cm.nsi.saarngan.services.UserService;
import cm.nsi.saarngan.utils.FileUploadUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 14:10, mer.
 * saar-ngan
 **/
@Controller
public class AccountController {
    private final UserService service;

    public AccountController(UserService service) {
        this.service = service;
    }

    @GetMapping("/account")
    public String account(Model model, @AuthenticationPrincipal SaarNganUserDetails loggedUser) {
        String email = loggedUser.getUsername();
        User user = service.getByEmail(email);
        model.addAttribute("user", user);

        return "users/account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile, @AuthenticationPrincipal SaarNganUserDetails loggedUser) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setPhotos(fileName);
            User savedUser = service.updateAccount(user);

            String uploadDir = "/user-photos/" + savedUser.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            service.updateAccount(user);
        }

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        redirectAttributes.addFlashAttribute("message", "Your account details have been updated");

        return "redirect:/account";
    }

}

