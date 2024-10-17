package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.User;
import be.kdg.programming3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    /*@PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }*/
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.ok(createdUser);
    }

//  @PutMapping("/{id}")
//  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//      User updatedUser = userService.updateUser(id, userDetails);
//      return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
//  }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
