package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public interface MessageRepoInterface {

    public Long createMessage(UserE userE,String message,Topic topic);

    public Long createRespondMessage(UserE userE, String message, Long predecessor);

    public boolean messageExists(Long id);
    
    public boolean messageIsOrigin(Long id);

    public void deleteMessage(Long id);

    public MessageE findById(Long id);

    public List<List<MessageE>> getMessagesByTopic(String topic);

    public List<List<MessageE>> getMessagesByTopicSinceDate(String topic, Date date);
}
