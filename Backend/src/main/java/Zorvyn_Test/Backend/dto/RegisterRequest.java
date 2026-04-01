package Zorvyn_Test.Backend.dto;

import Zorvyn_Test.Backend.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Role must be specified (VIEWER, ANALYST, ADMIN)")
    private Role role;
}
