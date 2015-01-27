package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.Topic;

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
public class TopicRepo {

    @PersistenceContext
    static EntityManager em;

    @Transactional
    public static void createTopic(String userFromTopic, String topicName) {
        em.persist(new Topic(topicName, UserRepo.findByUserName(userFromTopic)));
    }

    @Transactional
    public static Topic findByTopicName(String topicName) {
        return em.find(Topic.class, topicName);
    }

    @Transactional
    public static List<Topic> getAllTopics() {
        return  em.createQuery("Select t from Topic t").getResultList();
    }

    public static boolean topicExits(String topicName) {
        boolean exist = false;
        Topic tE = null;
        try {
            tE = findByTopicName(topicName);
            exist = tE.getTopicName().equals(topicName);
        } catch (NoResultException e) {
            exist = false;
        }
        return exist;
    }

    @Transactional
    public static void deleteTopic(String topicName) {
        em.remove(findByTopicName(topicName));
    }
}
