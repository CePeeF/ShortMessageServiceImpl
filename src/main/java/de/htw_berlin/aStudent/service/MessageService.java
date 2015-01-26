package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import de.htw_berlin.aStudent.repository.MessageRepo;
import de.htw_berlin.aStudent.repository.TopicRepo;
import de.htw_berlin.aStudent.repository.UserRepo;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Created by meichris on 23.01.15.
 */
@Service
public class MessageService {

    public static Long createMessage(String userName,String message,String topic) {
        UserE userE = UserRepo.findByUserName(userName);
        Topic topicE = TopicRepo.findByTopicName(topic);
        return MessageRepo.createMessage(userE, message, topicE);
    }

    public static Long createRespondMessage(String userName, String message, Long predecessor) {
        UserE userE = UserRepo.findByUserName(userName);
        return MessageRepo.createRespondMessage(userE, message, predecessor);
    }

    public static boolean messageExists(Long id) {
        return MessageRepo.messageExists(id);
    }

    public static boolean messageIsOrigin(Long predecessor){
        return MessageRepo.messageIsOrigin(predecessor);
    }

    public static void deleteMessage(Long id) {
        MessageRepo.deleteMessage(id);
    }

    public static Message findById(Long id) {
        MessageE mE = MessageRepo.findById(id);
        return MessageE2Message(mE);
    }

    public static List<List<Message>> getMessagesByTopic(String topic) {
        List<List<MessageE>> messagesEByTopicAndDate = MessageRepo.getMessagesByTopic(topic);
        List<List<Message>> messages = new ArrayList<>();
        List<Message> listMessage = new ArrayList<>();
        Message m = null;
        for (List<MessageE> lmE: messagesEByTopicAndDate) {
            for (MessageE mE : lmE) {
                m = MessageE2Message(mE);
                listMessage.add(m);
                m = null;
            }
            messages.add(listMessage);
        }
        return messages;
    }

    public static List<List<Message>> getMessagesByTopicSinceDate(String topic, Date date) {
        List<List<MessageE>> messagesEByTopicAndDate = MessageRepo.getMessagesByTopicSinceDate(topic,date);
        List<List<Message>> messages = new ArrayList<List<Message>>();
        List<Message> listMessage = new ArrayList<Message>();
        Message m = null;
        for (List<MessageE> lmE: messagesEByTopicAndDate) {
            for (MessageE mE : lmE) {
                m = MessageE2Message(mE);
                listMessage.add(m);
                m = null;
            }
            messages.add(listMessage);
        }
        return messages;
    }

    private static Message MessageE2Message(MessageE mE) {
        Message m =  new Message();
        m.setContent(mE.getContent());
        m.setDate(mE.getDate());
        m.setMessageId(mE.getMessageId());
        m.setOrigin(mE.getOrigin());
        m.setTopic(mE.getTopic().getTopicName());
        User u = new User();
        u.setCity(mE.getUser().getCity());
        u.setName(mE.getUser().getName());
        m.setUser(u);
        return m;
    }




}
