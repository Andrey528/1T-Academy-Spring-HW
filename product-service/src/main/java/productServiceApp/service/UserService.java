package productServiceApp.service;

import productServiceApp.dto.UserDto;
import productServiceApp.model.User;
import productServiceApp.repository.UserRepository;
import productServiceApp.util.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    public UserDto saveUser(String username) {
        User user = userRepository.save(new User(username));
        return userMapper.toDTO(user, false);
    }

    @Transactional
    public UserDto getUser(Long id, boolean includeProducts) {
        User user = getUser(id);
        return userMapper.toDTO(user, includeProducts);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User not found id: %d", id)));
    }

    @Transactional
    public List<UserDto> getAllUsers(boolean includeProducts) {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            throw new EntityNotFoundException("No users found");

        return userMapper.toDTO(users, includeProducts);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUsername(String username, Long id) {
        userRepository.updateUsernameById(username, id);
    }
}
