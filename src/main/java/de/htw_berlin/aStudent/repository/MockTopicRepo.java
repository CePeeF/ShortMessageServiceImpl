package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public class MockTopicRepo {

    static Hashtable<Long, Topic> hashTopic;
    static Hashtable<String, Long> hashNameToLong;

    public MockTopicRepo() {
        hashTopic = new Hashtable<Long, Topic>();
        hashNameToLong = new Hashtable<String, Long>();
    }

    @Transactional
    public static void createTopic(String userFromTopic, String topicName) {
        hashNameToLong.put(topicName,Integer.toUnsignedLong(hashTopic.size()));
        hashTopic.put(Integer.toUnsignedLong(hashTopic.size()),new Topic(topicName, UserRepo.findByUserName(userFromTopic)));

    }

    @Transactional
    public static Topic findByTopicName(String topicName) {
        Long key = hashNameToLong.get(topicName);
        return hashTopic.get(key);
    }

    @Transactional
    public static List<Topic> getAllTopics() {
        return  new ArrayList<>(hashTopic.values());
    }

    public static boolean topicExits(String topic) {
        return hashNameToLong.containsKey(topic);
    }

    @Transactional
    public static void deleteTopic(String name) {
        Long key = hashNameToLong.get(name);
//        if (key != null)
        hashTopic.remove(key);
    }
}
