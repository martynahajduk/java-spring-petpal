package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.User;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final FeederService feederService;

    public UserController(UserService userService, FeederService feederService) {
        this.userService = userService;
        this.feederService = feederService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = userService.loginUser(email, password);
            session.setAttribute("user", user);
            model.addAttribute("user", user);

            return "menupage";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid email or password.");
            return "index";
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);


        userService.save(newUser);
        model.addAttribute("success", "User registered successfully!");
        return "index";

    }


}
