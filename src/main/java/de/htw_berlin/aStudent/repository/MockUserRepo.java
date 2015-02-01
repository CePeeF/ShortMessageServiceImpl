package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserE;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public class MockUserRepo implements UserRepoInterface {

    private Hashtable<String, UserE> hashUser;

    public MockUserRepo() {
        hashUser = new Hashtable<>();
    }

    @Transactional
    @Override
    public void createUser(String userName, String city) {
        hashUser.put(userName, new UserE(userName, city));
    }

    @Transactional
    @Override
    public UserE findByUserName(String username) {
        UserE returnUserE = null;
        if (hashUser.containsKey(username)) {
            returnUserE = hashUser.get(username);
        }
        return returnUserE;
    }

    @Transactional
    @Override
    public List<UserE> getAllUsers() {
        return new ArrayList<>(hashUser.values());
    }

    @Override
    public boolean userExits(String username) {
        boolean exist = false;
        if (hashUser.containsKey(username)) {
            exist = true;
        }
        return exist;
    }

    @Transactional
    @Override
    public void deleteUser(String name) {
        hashUser.remove(name);
    }
}
