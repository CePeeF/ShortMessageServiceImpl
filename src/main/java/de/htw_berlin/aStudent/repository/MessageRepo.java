package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by meichris on 15.01.15.
 */
@Repository
public class MessageRepo {

    @PersistenceContext
    private EntityManager em;

    private TopicRepoInterface topicRepo;

    public MessageRepo(TopicRepoInterface topicRepo) {
        this.topicRepo = topicRepo;
    }

    @Transactional
    public Long createMessage(UserE userE,String message,Topic topic) {
        MessageE mE = new MessageE(userE,message,topic);
        em.persist(mE);
        return mE.getMessageId();

    }

    @Transactional
    public Long createRespondMessage(UserE userE, String message, Long predecessor) {
        MessageE preMessageE = findById(predecessor);
        Topic topic = preMessageE.getTopic();
        MessageE respondMessageE = new MessageE(userE,message, topic, preMessageE);
        em.persist(respondMessageE);
        return respondMessageE.getMessageId();
    }

    public boolean messageExists(Long id) {
        boolean exist = false;
        MessageE m = null;
        try {
            m = findById(id);
            exist = m.getMessageId().equals(id);
        } catch (NoResultException e) {
            exist = false;
        }
        return exist;
    }
    
    public boolean messageIsOrigin(Long id) {
        boolean origin = false;
        MessageE m = null;
        try {
            m = findById(id);
            origin = m.getOrigin();
        } catch (NoResultException e) {
            origin = false;
        }
        return origin;
    }

    @Transactional
    public void deleteMessage(Long Id) {
        em.remove(findById(Id));
    }

    @Transactional
    public MessageE findById(Long Id) {
        return em.find(MessageE.class,Id);
    }

    @Transactional
    private  List<MessageE> getOriginMessagesWithTopic(String topic) {
        List<MessageE> messages = new ArrayList<>();
        try {
            Query q = em.createQuery("from MessageE m where m.topic = " + topic + "order by m.date");
            messages = q.getResultList();
        } catch (NoResultException e) {}
        return messages;
    }

    @Transactional
    private List<MessageE> getOriginMessagesWithTopicSinceDate(String topic, Date date) {
        List<MessageE> messages = new ArrayList<>();
        try {
            Query q = em.createQuery("from MessageE m where m.topic = " + topic + "and m.date >= " + date + "order by m.date");
            messages = q.getResultList();
        } catch (NoResultException e) {}
        return messages;
    }

    @Transactional
    private List<MessageE> getMessagesByPredecessor(MessageE predecessor) {
        List<MessageE> messages = new ArrayList<>();
        try {
            Query q = em.createQuery("from MessageE m where m.predecessor = " + predecessor + "order by m.date");
            messages = q.getResultList();
        } catch (NoResultException e) {}
        return messages;
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
