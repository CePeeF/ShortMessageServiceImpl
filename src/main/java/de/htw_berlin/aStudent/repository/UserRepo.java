package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserE;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public class UserRepo {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createUser(String userName, String city) {
        UserE u = new UserE(userName, city);
        em.persist(u);
    }

    @Transactional
    public UserE findByUserName(String username) {
        return em.find(UserE.class, username);
    }

    @Transactional
    public List<UserE> getAllUsers() {
        return  em.createQuery("Select u from UserE u").getResultList();
    }
    
    public boolean userExits(String username) {
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

    @Transactional
    public void deleteUser(String name) {
        em.remove(findByUserName(name));
    }
}
