package hw4;

import hw4.model.User;
import hw4.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan
public class UserApp {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserApp.class);

        UserService userService = context.getBean(UserService.class);

        User newUser = userService.createUser("testuser");
        System.out.println("Создан пользователь: " + newUser);

        List<User> users = userService.getAllUsers();
        System.out.println("Все пользователи: " + users);

        User user = userService.getUser(newUser.getId());
        System.out.println("Получен пользователь по ID: " + user);

        user.setUsername("updatedUser");
        userService.updateUser(user);
        System.out.println("Обновленный пользователь: " + userService.getUser(user.getId()));

        userService.deleteUser(user.getId());
        System.out.println("Пользователь удален. Все пользователи: " + userService.getAllUsers());

        context.close();
    }
}
