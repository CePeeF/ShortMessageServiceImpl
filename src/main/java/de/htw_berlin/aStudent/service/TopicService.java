package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.repository.TopicRepo;
import de.htw_berlin.aStudent.repository.TopicRepoInterface;
import de.htw_berlin.aStudent.repository.UserRepo;

import javax.persistence.NoResultException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Created by meichris on 23.01.15.
 */
@Service
public class TopicService {

    static TopicRepoInterface topicRepo;

    public TopicService(TopicRepoInterface topicRepo) {
        this.topicRepo = topicRepo;
    }

    public static void createTopic(String userFromTopic, String topicName) {
        topicRepo.createTopic(userFromTopic, topicName);
    }

    public static Topic findByTopicName(String topicName) {
        return topicRepo.findByTopicName(topicName);
    }

    public static List<Topic> getAllTopics() {
        return  topicRepo.getAllTopics();
    }

    public static boolean topicExits(String topic) {
        return topicRepo.topicExits(topic);
    }

    public static void deleteTopic(String name) {
        topicRepo.deleteTopic(name);
    }
}
