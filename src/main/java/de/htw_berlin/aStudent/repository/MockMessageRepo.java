package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public class MockMessageRepo {

    static Hashtable<Long, MessageE> hashMessage;

    public MockMessageRepo() {
        hashMessage = new Hashtable<Long, MessageE>();
    }

    @Transactional
    public static Long createMessage(UserE userE,String message,Topic topic) {
        MessageE mE = new MessageE(userE,message,topic);
        Long id = Integer.toUnsignedLong(hashMessage.size());
        hashMessage.put(id, mE);
        return id;

    }

    @Transactional
    public static Long createRespondMessage(UserE userE, String message, Long predecessor) {
        MessageE preMessageE = findById(predecessor);
        Topic topic = preMessageE.getTopic();
        MessageE respondMessageE = new MessageE(userE,message, topic, preMessageE);
        Long id = Integer.toUnsignedLong(hashMessage.size());
        hashMessage.put(id, respondMessageE);
        return id;
    }

    public static boolean messageExists(Long id) {
        return hashMessage.containsKey(id);
    }
    
    public static boolean messageIsOrigin(Long id) {
        MessageE m = hashMessage.get(id);
        return m.getOrigin();
    }

    @Transactional
    public static void deleteMessage(Long id) {
        hashMessage.remove(id);
    }

    @Transactional
    public static MessageE findById(Long id) {
        return hashMessage.get(id);
    }

    @Transactional
    private static List<MessageE> getOriginMessagesWithTopic(Topic topic) {
        List<MessageE> messages = new ArrayList<>(hashMessage.values());
        List<MessageE> messagesReturn = new ArrayList<>();
        for (MessageE mE: messages) {
            if (mE.getTopic().getTopicName().equals(topic)) {
                messagesReturn.add(mE);
            }
        }
        return messagesReturn;
    }

    @Transactional
    private static List<MessageE> getOriginMessagesWithTopicSinceDate(Topic topic, Date date) {
        List<MessageE> messages = new ArrayList<>(hashMessage.values());
        List<MessageE> messagesReturn = new ArrayList<>();
        for (MessageE mE: messages) {
            if (mE.getTopic().getTopicName().equals(topic) && mE.getDate().after(date)) {
                messagesReturn.add(mE);
            }
        }
        return messagesReturn;
    }

    @Transactional
    private static List<MessageE> getMessagesByPredecessor(MessageE predecessor) {
        List<MessageE> messages = new ArrayList<>(hashMessage.values());
        List<MessageE> messagesReturn = new ArrayList<>();
        for (MessageE mE: messages) {
            if (mE.getPredecessor().equals(predecessor)) {
                messagesReturn.add(mE);
            }
        }
        return messagesReturn;
    }

    @Transactional
    public static List<List<MessageE>> getMessagesByTopic(String topic) {
        Topic t = TopicRepo.findByTopicName(topic);
        List<List<MessageE>> messagesByTopicAndDate = new ArrayList<>();
        List<MessageE> originMessagesWithTopic = getOriginMessagesWithTopic(t);
        List<MessageE> originMessageWithRespondMessages = new ArrayList<>();
        int index = 0;
        if (!originMessagesWithTopic.isEmpty()) {
            for (MessageE mE: originMessagesWithTopic) {
                originMessageWithRespondMessages.add(mE);
                originMessageWithRespondMessages.addAll(getMessagesByPredecessor(mE));
                messagesByTopicAndDate.add(index++, originMessageWithRespondMessages);
                originMessageWithRespondMessages.clear();
            }
        }
        return messagesByTopicAndDate;
    }

    @Transactional
    public static List<List<MessageE>> getMessagesByTopicSinceDate(String topic, Date date) {
        Topic t = TopicRepo.findByTopicName(topic);
        List<List<MessageE>> messagesByTopicAndDate = new ArrayList<>();
        List<MessageE> originMessagesWithTopic = getOriginMessagesWithTopicSinceDate(t,date);
        List<MessageE> originMessageWithRespondMessages = new ArrayList<>();
        int index = 0;
        if (!originMessagesWithTopic.isEmpty()) {
            for (MessageE mE: originMessagesWithTopic) {
                originMessageWithRespondMessages.add(mE);
                originMessageWithRespondMessages.addAll(getMessagesByPredecessor(mE));
                messagesByTopicAndDate.add(index++, originMessageWithRespondMessages);
                originMessageWithRespondMessages.clear();
            }
        }
        return messagesByTopicAndDate;
    }
}
