package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import de.htw_berlin.aStudent.repository.*;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by meichris on 23.01.15.
 */
@Component
public class MessageService {

    private MessageRepoInterface messageRepo;
    private UserRepoInterface userRepo;
    private TopicRepoInterface topicRepo;


    public MessageService(MessageRepoInterface messageRepo,UserRepoInterface userRepo,TopicRepoInterface topicRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.topicRepo = topicRepo;
    }

    public Long createMessage(String userName,String message,String topic) {
        UserE userE = userRepo.findByUserName(userName);
        Topic t = topicRepo.findByTopicName(topic);
        return messageRepo.createMessage(userE, message, t);
    }

    public Long createRespondMessage(String userName, String message, Long predecessor) {
        UserE userE = userRepo.findByUserName(userName);
        return messageRepo.createRespondMessage(userE, message, predecessor);
    }

    public boolean messageExists(Long id) {
        return messageRepo.messageExists(id);
    }

    public boolean messageIsOrigin(Long predecessor){
        return messageRepo.messageIsOrigin(predecessor);
    }

    public void deleteMessage(Long id) {
        messageRepo.deleteMessage(id);
    }

    public Message findById(Long id) {
        MessageE mE = messageRepo.findById(id);
        return MessageE2Message(mE);
    }

    public List<List<Message>> getMessagesByTopic(String topic) {
        List<Message> returnInnerList = new ArrayList<>();
        List<List<Message>> returnList = new ArrayList<>();
        List<List<MessageE>> RepoTempList = new ArrayList<>();
        List<MessageE> originList = new ArrayList<>();

        RepoTempList = messageRepo.getMessagesByTopic(topic);

        for (List<MessageE> listME: RepoTempList) {
            for (MessageE ME: listME) {
                Message m = MessageE2Message(ME);
                returnInnerList.add(m);
            }
            returnList.add(new ArrayList<>(returnInnerList));
            returnInnerList.clear();
        }
        return returnList;
    }

    public List<List<Message>> getMessagesByTopicSinceDate(String topic, Date date) {
        List<Message> returnInnerList = new ArrayList<>();
        List<List<Message>> returnList = new ArrayList<>();
        List<List<MessageE>> RepoTempList = new ArrayList<>();
        List<MessageE> originList = new ArrayList<>();

        RepoTempList = messageRepo.getMessagesByTopic(topic);

        for (List<MessageE> listME: RepoTempList) {
            for (MessageE ME: listME) {
                Message m = MessageE2Message(ME);
                returnInnerList.add(m);
            }
            returnList.add(new ArrayList<>(returnInnerList));
            returnInnerList.clear();
        }
        return returnList;
    }

    private Message MessageE2Message(MessageE mE) {
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
