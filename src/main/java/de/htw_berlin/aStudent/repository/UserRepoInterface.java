package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserE;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public interface UserRepoInterface {


    public void createUser(String userName, String city);

    public UserE findByUserName(String username);

    public List<UserE> getAllUsers();
    
    public boolean userExits(String username);

    public void deleteUser(String name);

}
