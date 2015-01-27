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
public class MockTopicRepo implements TopicRepoInterface {

    private Hashtable<String, Topic> hashTopic;
    private UserRepoInterface userRepo;

    public MockTopicRepo(UserRepoInterface userRepo) {
        this.userRepo = userRepo;
        hashTopic = new Hashtable<>();
    }

    @Transactional
    public void createTopic(String userFromTopic, String topicName) {
        hashTopic.put(topicName,new Topic(topicName, userRepo.findByUserName(userFromTopic)));

    }

    @Transactional
    public Topic findByTopicName(String topicName) {
        return hashTopic.get(topicName);
    }

    @Transactional
    public List<Topic> getAllTopics() {
        return  new ArrayList<>(hashTopic.values());
    }

    public boolean topicExits(String topic) {
        boolean exist = false;
        if (hashTopic.containsKey(topic))
            exist = true;
        return exist;
    }

    @Transactional
    public void deleteTopic(String name) {
        hashTopic.remove(name);
    }
}
