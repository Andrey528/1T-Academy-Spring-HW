package hw6.service;

import hw6.dto.UserDto;
import hw6.model.User;
import hw6.repository.UserRepository;
import hw6.util.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto saveUser(String username) {
        User user = userRepository.save(new User(username));
        return UserMapper.toDTO(user, false);
    }

    @Transactional
    public UserDto getUser(Long id, boolean includeProducts) {
        User user = getUser(id);
        return UserMapper.toDTO(user, includeProducts);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<UserDto> getAllUsers(boolean includeProducts) {
        List<User> users = userRepository.findAll();
        return UserMapper.toDTO(users, includeProducts);
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
