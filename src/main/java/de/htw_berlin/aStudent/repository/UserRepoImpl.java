package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.User;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by meichris on 15.01.15.
 */
public class UserRepoImpl {

    static EntityManager em;

    public void createMessage() {
        try {
            em.persist(this);
        } catch (Exception e) {}
    }

    public static void deleteUser(String name) {
        em.remove(findByUsername(name));
    }

    public User findByUsername(String username) {
        return em.find(User.class, username);
    }


}
