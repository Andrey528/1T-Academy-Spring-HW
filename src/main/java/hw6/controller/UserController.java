package hw6.controller;

import hw6.dto.UserDto;
import hw6.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "includeProducts") boolean includeProducts
    ) {
        try {
            UserDto user = userService.getUser(id, includeProducts);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(name = "includeProducts") boolean includeProducts
    ) {
        try {
            List<UserDto> users = userService.getAllUsers(includeProducts);
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public UserDto createUser(@RequestParam(name = "username") String username) {
        return userService.saveUser(username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUsername(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "username") String username) {
        try {
            userService.updateUsername(username, id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}