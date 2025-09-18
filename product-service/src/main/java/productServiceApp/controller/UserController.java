package productServiceApp.controller;

import productServiceApp.dto.UserDto;
import productServiceApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "includeProducts") boolean includeProducts
    ) {
        return userService.getUser(id, includeProducts);
    }

    @GetMapping()
    public List<UserDto> getUsers(
            @RequestParam(name = "includeProducts") boolean includeProducts
    ) {
        return userService.getAllUsers(includeProducts);
    }

    @PostMapping
    public UserDto createUser(@RequestParam(name = "username") String username) {
        return userService.saveUser(username);
    }

    @PutMapping("/{id}")
    public void updateUsername(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "username") String username) {
            userService.updateUsername(username, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }
}