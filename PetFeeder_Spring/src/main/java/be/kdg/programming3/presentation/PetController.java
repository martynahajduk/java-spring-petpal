package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.PetService;
import be.kdg.programming3.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
    public class PetController {
    /*
    private FeederService feederService;
    private PetService petService;
    private UserService userService;

    public PetController(FeederService feederService, PetService petService, UserService userService) {
        this.feederService = feederService;
        this.petService = petService;
        this.userService = userService;
    }

    @GetMapping("/PetChoice")
    public String displayPets(Model model) {
        model.addAttribute("PetChoice", petService.getPets());
        return "PetChoice";
    }

    public String displayUsers(Model model) {
        model.addAttribute("", userService.getUser());
        return "";
    }

    public String displayFeeders(Model model) {
        model.addAttribute("", feederService.getAllFeeders());
        return "";
    }

*/
}
