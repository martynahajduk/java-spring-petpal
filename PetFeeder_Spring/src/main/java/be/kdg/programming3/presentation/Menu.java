package be.kdg.programming3.presentation;

import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.PetService;
import be.kdg.programming3.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class Menu {
    private FeederService feederService;
    private PetService petService;
    private UserService userService;

    public Menu(FeederService feederService, PetService petService, UserService userService) {
        this.feederService = feederService;
        this.petService = petService;
        this.userService = userService;
    }



}
