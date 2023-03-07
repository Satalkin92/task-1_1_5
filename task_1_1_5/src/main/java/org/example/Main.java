package org.example;

import org.example.model.User;
import org.example.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Victor", "Golovin", (byte) 29);
        userService.saveUser("Ivan", "Vasilev", (byte) 30);
        userService.saveUser("Anna", "Busova", (byte) 27);
        userService.saveUser("Danil", "Konev", (byte) 31);
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}