package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.presentation.viewmodel.RegisterUserViewModel;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String registerUser(@Valid @ModelAttribute RegisterUserViewModel registerUserViewModel,
                               Model model) {

        try {
            User newUser = new User();
            newUser.setName(registerUserViewModel.getName());
            newUser.setEmail(registerUserViewModel.getEmail());
            newUser.setPassword(registerUserViewModel.getPassword());
            userService.save(newUser);

            model.addAttribute("success", "User registered successfully! Try to log in.");
            return "index";
        } catch (Exception e) {
            logger.error("Error registering user", e);
            model.addAttribute("error", "An error occurred during registration.");
            return "index";
        }
    }


}
