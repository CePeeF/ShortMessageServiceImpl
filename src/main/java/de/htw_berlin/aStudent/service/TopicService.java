package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.repository.TopicRepo;
import de.htw_berlin.aStudent.repository.TopicRepoInterface;
import de.htw_berlin.aStudent.repository.UserRepo;

import javax.persistence.NoResultException;
import java.util.List;

import de.htw_berlin.aStudent.repository.UserRepoInterface;
import org.springframework.stereotype.Service;

/**
 * Created by meichris on 23.01.15.
 */
@Service
public class TopicService {

    private TopicRepoInterface topicRepo;
    private UserRepoInterface userRepo;

    public TopicService(TopicRepoInterface topicRepo) {
        this.topicRepo = topicRepo;
    }

    public void createTopic(String userFromTopic, String topicName) {
        topicRepo.createTopic(userFromTopic, topicName);
    }

    public Topic findByTopicName(String topicName) {
        return topicRepo.findByTopicName(topicName);
    }

    public List<Topic> getAllTopics() {
        return  topicRepo.getAllTopics();
    }

    public boolean topicExits(String topic) {
        return topicRepo.topicExits(topic);
    }

    public void deleteTopic(String name) {
        topicRepo.deleteTopic(name);
    }
}
