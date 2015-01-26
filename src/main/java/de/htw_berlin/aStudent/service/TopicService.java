package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.repository.TopicRepo;
import de.htw_berlin.aStudent.repository.UserRepo;

import javax.persistence.NoResultException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Created by meichris on 23.01.15.
 */
@Service
public class TopicService {

    public static void createTopic(String userFromTopic, String topicName) {
        TopicRepo.createTopic(userFromTopic, topicName);
    }

    public static Topic findByTopicName(String topicName) {
        return TopicRepo.findByTopicName(topicName);
    }

    public static List<Topic> getAllTopics() {
        return  TopicRepo.getAllTopics();
    }

    public static boolean topicExits(String topic) {
        return TopicRepo.topicExits(topic);
    }

    public static void deleteTopic(String name) {
        TopicRepo.deleteTopic(name);
    }
}
