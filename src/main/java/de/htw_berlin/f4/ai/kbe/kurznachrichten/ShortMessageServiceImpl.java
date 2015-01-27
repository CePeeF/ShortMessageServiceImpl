package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import de.htw_berlin.aStudent.model.Topic;

import java.util.*;

import de.htw_berlin.aStudent.repository.UserRepoInterface;
import de.htw_berlin.aStudent.service.MessageService;
import de.htw_berlin.aStudent.service.TopicService;
import de.htw_berlin.aStudent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import de.htw_berlin.aStudent.service.AnApplicationService;

import javax.persistence.NoResultException;
import org.springframework.stereotype.Service;

@Service
public class ShortMessageServiceImpl implements ShortMessageService {

    @Autowired
    UserService usrService;

    @Autowired
    TopicService topicService;

    @Autowired
    MessageService msgService;
        
    //@Autowired
    //AnApplicationService anApplicationService;

    public ShortMessageServiceImpl(UserRepoInterface userRepo) {
        this.usrService = new UserService(userRepo);
    }

    @Override
    public Long createMessage(String userName, String message, String topic) throws IllegalArgumentException, NullPointerException {
        if (userName == null || message == null || topic == null) {
            throw new NullPointerException();
        } else if (!UserService.userExits(userName) || !TopicService.topicExits(topic) || message.length() > 255 || message.length() < 10) {
            throw new IllegalArgumentException();
        }

        return MessageService.createMessage(userName, message, topic);
    }

    @Override
    public Long respondToMessage(String userName, String message, Long predecessor) throws IllegalArgumentException, NullPointerException {
        if (userName == null || message == null || predecessor == null) {
            throw new NullPointerException();
        } else if (!UserService.userExits(userName) || !MessageService.messageExists(predecessor)
          || !MessageService.messageIsOrigin(predecessor) || message.length() > 255 || message.length() < 10) {
            throw new IllegalArgumentException();
        }

        return MessageService.createRespondMessage(userName, message, predecessor);
    }

    @Override
    public void deleteMessage(String userName, Long messageId) throws AuthorizationException, IllegalArgumentException, NullPointerException {

        Message m = MessageService.findById(messageId);

        if (userName == null || messageId == null) {
            throw new NullPointerException();
        } else if (!MessageService.messageExists(messageId) || !UserService.userExits(userName) || !MessageService.messageIsOrigin(messageId)) {
            throw new IllegalArgumentException();
        } else if (m.getUser().getName().equals(userName)) {
            MessageService.deleteMessage(m.getMessageId());
        } else {
            throw new AuthorizationException("Sie sind nicht der Erzeuger!");
        }

    }

    @Override
    public void createTopic(String userName, String topic) throws IllegalArgumentException, NullPointerException {

        if (userName == null || topic == null) {
            throw new NullPointerException();
        } else if (!UserService.userExits(userName) || !TopicService.topicExits(topic) || topic.length() < 2 || topic.length() > 70) {
            throw new IllegalArgumentException();
        }
        TopicService.createTopic(userName, topic);
    }

    @Override
    public Set<String> getTopics() {
        Set<String> set = new HashSet<>();
        try {
            for (Topic t : TopicService.getAllTopics()) {
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
        } else if (!TopicService.topicExits(topic)) {
            throw new IllegalArgumentException();
        } else if (since == null) {
            listMessage = MessageService.getMessagesByTopic(topic);
        } else {
            listMessage = MessageService.getMessagesByTopicSinceDate(topic, since);
        }

        return listMessage;
    }

    @Override
    public void createUser(String userName, String city) throws IllegalArgumentException, NullPointerException {

        if (userName == null || city == null) {
            throw new NullPointerException();
        }
        if (UserService.userExits(userName) || userName.length() < 4 || userName.length() > 30) {
            throw new IllegalArgumentException();
        }
        User u = new User();
        u.setCity(city);
        u.setName(userName);
        UserService.createUser(u);
        //anApplicationService.doSomeThing();
    }

    @Override
    public void deleteUser(String userName) {

        if (UserService.userExits(userName)) {
            throw new IllegalArgumentException();
        }
        UserService.deleteUser(userName);
    }

    @Override
    public Set<User> getUsers() {
        return UserService.getAllUsers();
    }
}
