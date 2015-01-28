package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import de.htw_berlin.aStudent.model.Topic;

import java.util.*;

import de.htw_berlin.aStudent.repository.MessageRepoInterface;
import de.htw_berlin.aStudent.repository.TopicRepoInterface;
import de.htw_berlin.aStudent.repository.UserRepoInterface;
import de.htw_berlin.aStudent.service.AnApplicationService;
import de.htw_berlin.aStudent.service.MessageService;
import de.htw_berlin.aStudent.service.TopicService;
import de.htw_berlin.aStudent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import de.htw_berlin.aStudent.service.AnApplicationService;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ShortMessageServiceImpl implements ShortMessageService {

//    @Autowired
    UserService usrService;

//    @Autowired
    TopicService topicService;

//    @Autowired
    MessageService msgService;
        
    @Autowired
    AnApplicationService anApplicationService;

    public ShortMessageServiceImpl(UserRepoInterface userRepo, TopicRepoInterface topicRepo, MessageRepoInterface messageRepo) {
        this.usrService = new UserService(userRepo);
        this.topicService = new TopicService(topicRepo);
        this.msgService = new MessageService(messageRepo,userRepo,topicRepo);
    }

    @Override
    public Long createMessage(String userName, String message, String topic) throws IllegalArgumentException, NullPointerException {
        if (userName == null || message == null || topic == null) {
            throw new NullPointerException();
        } else if (!usrService.userExits(userName) || !topicService.topicExits(topic) || message.length() > 255 || message.length() < 10) {
            throw new IllegalArgumentException();
        }

        return msgService.createMessage(userName, message, topic);
    }

    @Override
    public Long respondToMessage(String userName, String message, Long predecessor) throws IllegalArgumentException, NullPointerException {
        if (userName == null || message == null || predecessor == null) {
            throw new NullPointerException();
        } else if (!usrService.userExits(userName) || !msgService.messageExists(predecessor)
          || !msgService.messageIsOrigin(predecessor) || message.length() > 255 || message.length() < 10) {
            throw new IllegalArgumentException();
        }

        return msgService.createRespondMessage(userName, message, predecessor);
    }

    @Override
    public void deleteMessage(String userName, Long messageId) throws AuthorizationException, IllegalArgumentException, NullPointerException {



        if (userName == null || messageId == null) {
            throw new NullPointerException();
        }

        if (!msgService.messageExists(messageId) || !usrService.userExits(userName) || !msgService.messageIsOrigin(messageId)) {
            throw new IllegalArgumentException();
        }

        Message m = msgService.findById(messageId);

        if (m.getUser().getName().equals(userName)) {
            msgService.deleteMessage(messageId);
        } else {
            throw new AuthorizationException("Sie sind nicht der Erzeuger!");
        }

    }

    @Override
    public void createTopic(String userName, String topic) throws IllegalArgumentException, NullPointerException {

        if (userName == null || topic == null) {
            throw new NullPointerException();
        } else if (!usrService.userExits(userName) || topicService.topicExits(topic) || topic.length() < 2 || topic.length() > 70) {
            throw new IllegalArgumentException();
        }
        topicService.createTopic(userName, topic);
    }

    @Override
    public Set<String> getTopics() {
        Set<String> set = new HashSet<>();
        try {
            for (Topic t : topicService.getAllTopics()) {
                set.add(t.getTopicName());
            }
        } catch (NoResultException e) {
        }

        return set;
    }

    @Override
    public List<List<Message>> getMessageByTopic(String topic, Date since) throws IllegalArgumentException, NullPointerException {

        List<List<Message>> listMessage = new ArrayList<>();

        if (topic == null) {
            throw new NullPointerException();
        } else if (!topicService.topicExits(topic)) {
            throw new IllegalArgumentException();
        } else if (since == null) {
            listMessage = msgService.getMessagesByTopic(topic);
        } else {
            listMessage = msgService.getMessagesByTopicSinceDate(topic, since);
        }

        return listMessage;
    }

    @Override
    public void createUser(String userName, String city) throws IllegalArgumentException, NullPointerException {

        if (userName == null || city == null) {
            throw new NullPointerException();
        }
        if (usrService.userExits(userName) || userName.length() < 4 || userName.length() > 30) {
            throw new IllegalArgumentException();
        }
        User u = new User();
        u.setCity(city);
        u.setName(userName);
        usrService.createUser(u);
        //anApplicationService.doSomeThing();
    }

    @Override
    public void deleteUser(String userName) {

        if (!usrService.userExits(userName)) {
            throw new IllegalArgumentException();
        }
        usrService.deleteUser(userName);
    }

    @Override
    public Set<User> getUsers() {
        return usrService.getAllUsers();
    }
}
