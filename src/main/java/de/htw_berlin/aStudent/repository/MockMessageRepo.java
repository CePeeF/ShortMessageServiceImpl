package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public class MockMessageRepo implements MessageRepoInterface {

    private Hashtable<Long, MessageE> hashMessage;
    private TopicRepoInterface topicRepo;

    public MockMessageRepo(TopicRepoInterface topicRepo) {
        this.topicRepo = topicRepo;
        hashMessage = new Hashtable<Long, MessageE>();
    }

    @Transactional
    public Long createMessage(UserE userE,String message,Topic topic) {
        MessageE mE = new MessageE(userE,message,topic);
        Long id = Integer.toUnsignedLong(hashMessage.size());
        hashMessage.put(id, mE);
        return id;

    }

    @Transactional
    public Long createRespondMessage(UserE userE, String message, Long predecessor) {
        MessageE preMessageE = findById(predecessor);
        Topic topic = preMessageE.getTopic();
        MessageE respondMessageE = new MessageE(userE,message, topic, preMessageE);
        Long id = Integer.toUnsignedLong(hashMessage.size());
        hashMessage.put(id, respondMessageE);
        return id;
    }

    public boolean messageExists(Long id) {
        return hashMessage.containsKey(id);
    }
    
    public boolean messageIsOrigin(Long id) {
        MessageE m = hashMessage.get(id);
        return m.getOrigin();
    }

    @Transactional
    public void deleteMessage(Long id) {
        hashMessage.remove(id);
    }

    @Transactional
    public MessageE findById(Long id) {
        return hashMessage.get(id);
    }

    @Transactional
    private List<MessageE> getOriginMessagesWithTopic(String  topic) {
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
    private List<MessageE> getOriginMessagesWithTopicSinceDate(String topic, Date date) {
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
    private List<MessageE> getMessagesByPredecessor(MessageE predecessor) {
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
    public List<List<MessageE>> getMessagesByTopic(String topic) {
        List<List<MessageE>> messagesByTopicAndDate = new ArrayList<>();
        List<MessageE> originMessagesWithTopic = getOriginMessagesWithTopic(topic);
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
    public List<List<MessageE>> getMessagesByTopicSinceDate(String topic, Date date) {
        List<List<MessageE>> messagesByTopicAndDate = new ArrayList<>();
        List<MessageE> originMessagesWithTopic = getOriginMessagesWithTopicSinceDate(topic,date);
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
