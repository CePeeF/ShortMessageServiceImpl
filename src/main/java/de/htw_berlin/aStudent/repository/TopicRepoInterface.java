package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public interface TopicRepoInterface {

    public void createTopic();

    public Topic findByTopicName();

    public List<Topic> getAllTopics();

    public boolean topicExits();

    public void deleteTopic();
}
