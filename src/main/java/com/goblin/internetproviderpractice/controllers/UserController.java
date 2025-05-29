package com.goblin.internetproviderpractice.controllers;

import com.goblin.internetproviderpractice.model.Role;
import com.goblin.internetproviderpractice.model.requests.UserRegisterRequest;
import com.goblin.internetproviderpractice.model.UserRequest;
import com.goblin.internetproviderpractice.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<String> getSelf(@CurrentSecurityContext(expression = "authentication.name") String name) {
        return ResponseEntity.ok(name);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRegisterRequest admin) {
        if (registrationService.registerUser(admin, Role.ROLE_ADMIN)) {
            return ResponseEntity.ok("Created new admin");
        }
        return ResponseEntity.badRequest().body("Admin name is already used");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest user) {
        if (registrationService.registerUser(user, Role.ROLE_USER)) {
            return ResponseEntity.ok("Created new user");
        }
        return ResponseEntity.badRequest().body("User name is already used");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody UserRequest user) {
        if (registrationService.deleteUser(user.name())) {
            return ResponseEntity.ok("Deleted user");
        }
        return ResponseEntity.badRequest().body("No user with given name is present");
    }
}
