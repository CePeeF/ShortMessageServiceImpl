package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageE;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public interface MessageRepoInterface {

    public Long createMessage();

    public Long createRespondMessage();

    public boolean messageExists();
    
    public boolean messageIsOrigin();

    public void deleteMessage();

    public MessageE findById();

    public List<List<MessageE>> getMessagesByTopic();

    public List<List<MessageE>> getMessagesByTopicSinceDate();
}
