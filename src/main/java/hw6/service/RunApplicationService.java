package hw6.service;

import hw6.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class RunApplicationService implements CommandLineRunner {
//
//    private final UserService userService;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        User newUser = userService.saveUser("testUser3");
//        log.info("Создан пользователь: " + newUser);
//
//        List<User> users = userService.getAllUsers();
//        log.info("Все пользователи: " + users);
//
//        User user = userService.getUser(newUser.getId());
//        log.info("Получен пользователь по ID: " + user);
//
//        userService.updateUsername("testUser", 2L);
//        log.info("Обновленный пользователь: " + userService.getUser(user.getId()));
//
//        userService.deleteUser(user.getId());
//        log.info("Пользователь удален. Все пользователи: " + userService.getAllUsers());
//    }
//}
