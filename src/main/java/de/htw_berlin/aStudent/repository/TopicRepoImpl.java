package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.TopicE;
import de.htw_berlin.aStudent.model.UserE;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
public class TopicRepoImpl {

    static EntityManager em;

    public void createTopic(String userFromTopic, String topicName) {
        em.persist(new TopicE(topicName,UserRepoImpl.findByUserName(userFromTopic)));
    }

    public static TopicE findByTopicName(String topicName) {
        return em.find(TopicE.class, topicName);
    }

    public static List<TopicE> getAllTopics() {
        return  em.createQuery("Select t from TopicE t").getResultList();
    }

    public static boolean topicExits(String topic) {
        boolean exist = false;
        TopicE tE = null;
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
