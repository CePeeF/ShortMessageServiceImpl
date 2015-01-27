package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.UserE;
import de.htw_berlin.aStudent.repository.UserRepo;
import de.htw_berlin.aStudent.repository.UserRepoInterface;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * Created by meichris on 23.01.15.
 */
@Service
public class UserService {

    static UserRepoInterface userRepo;

    public UserService(UserRepoInterface userRepo) {
        this.userRepo = userRepo;
    }

    public static void createUser (User u) {
        userRepo.createUser(u.getName(),u.getCity());
    }

    public static User findByUserName(String username) {
        User u = new User();
        UserE uE = userRepo.findByUserName(username);
        u.setCity(uE.getCity());
        u.setName(uE.getName());
        return u;
    }

    public static Set<User> getAllUsers() {
        Set<User> userSet;
        userSet = new HashSet<>();
        for (UserE uE : userRepo.getAllUsers()) {
            User u = new User();
            u.setName(uE.getName());
            u.setCity(uE.getCity());
            userSet.add(u);
        }
        return userSet;
    }

    public static boolean userExits(String username) {
        return userRepo.userExits(username);
    }

    public static void deleteUser(String name) {
        userRepo.deleteUser(name);
    }

}
