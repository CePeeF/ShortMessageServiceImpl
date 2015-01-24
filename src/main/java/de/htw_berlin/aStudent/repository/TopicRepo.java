package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.Topic;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
public class TopicRepo {

    static EntityManager em;

    public static void createTopic(String userFromTopic, String topicName) {
        em.persist(new Topic(topicName, UserRepo.findByUserName(userFromTopic)));
    }

    public static Topic findByTopicName(String topicName) {
        return em.find(Topic.class, topicName);
    }

    public static List<Topic> getAllTopics() {
        return  em.createQuery("Select t from Topic t").getResultList();
    }

    public static boolean topicExits(String topic) {
        boolean exist = false;
        Topic tE = null;
        try {
            tE = findByTopicName(topic);
            exist = tE.getTopicName().equals(topic);
        } catch (NoResultException e) {
            exist = false;
        }
        return exist;
    }

    public static void deleteTopic(String name) {
        em.remove(findByTopicName(name));
    }
}
