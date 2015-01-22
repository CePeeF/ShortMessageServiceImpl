package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserE;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
public class UserRepoImpl {


    static EntityManager em;

    public void createUser(String userName, String city) {
        em.persist(new UserE(userName, city));
    }

    public static UserE findByUserName(String username) {
        return em.find(UserE.class, username);
    }

    public static List<UserE> getAllUsers() {
        return  em.createQuery("Select u from User u").getResultList();
    }

    public static boolean userExits(String username) {
        boolean exist = false;
        UserE u = null;
        try {
            u = findByUserName(username);
            exist = u.getName().equals(username);
        } catch (NoResultException e) {
            exist = false;
        }
        return exist;
    }

    public static void deleteUser(String name) {
        em.remove(findByUserName(name));
    }




}
