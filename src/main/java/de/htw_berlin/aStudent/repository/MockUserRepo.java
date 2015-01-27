package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserE;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public class MockUserRepo implements UserRepoInterface {

    List<UserE> luE;

    public MockUserRepo() {
        luE = new ArrayList<UserE>();
    }

    @Transactional
    public void createUser(String userName, String city) {
        luE.add(new UserE(userName, city));
    }

    @Transactional
    public UserE findByUserName(String username) {
        UserE returnUserE = null;
        if (!luE.isEmpty()) {
            for (UserE uE : luE) {
                if (uE.getName().equals(username)) {
                    returnUserE = uE;
                }
            }
        }
        return returnUserE;
    }

    @Transactional
    public List<UserE> getAllUsers() {
        return  luE;
    }
    
    public boolean userExits(String username) {
        boolean exist = false;
        UserE u = null;
        try {
            u = findByUserName(username);
            exist = u.getName().equals(username);
        } catch (NullPointerException e) {
            exist = false;
        }
        return exist;
    }

    @Transactional
    public void deleteUser(String name) {
        for (UserE uE: luE) {
            if (uE.getName().equals(name)) {
                luE.remove(uE);
            }
        }
    }




}
