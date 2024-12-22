package be.kdg.programming3.presentation.viewmodel;

import jakarta.validation.constraints.*;


public class RegisterUserViewModel {
    @NotBlank(message = "Name is required.")
    @Size(min = 2, message = "Name must have at least 2 characters.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 7, message = "Password must have at least 7 characters.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter and one digit.")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
