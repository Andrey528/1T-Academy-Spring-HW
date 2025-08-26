package hw4.service;

import hw4.model.User;
import hw4.repository.UserDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        userDao.create(user);
        return user;
    }

    public User getUser(Long id) {
        return userDao.getById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }
}
