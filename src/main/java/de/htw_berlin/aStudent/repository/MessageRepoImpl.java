package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.TopicE;
import de.htw_berlin.aStudent.model.UserE;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by meichris on 15.01.15.
 */
public class MessageRepoImpl {

    static EntityManager em;

    public static Long createMessage(String userName,String message,String topic) {
        UserE userE = UserRepoImpl.findByUserName(userName);
        TopicE topicE = TopicRepoImpl.findByTopicName(topic);
        MessageE mE = new MessageE(userE,message,topicE);
        em.persist(mE);
        return mE.getMessageId();

    }

    public static Long createRespondMessage(String userName, String message, Long predecessor) {
        UserE userE = UserRepoImpl.findByUserName(userName);
        MessageE preMessageE = findById(predecessor);
        TopicE topicE = preMessageE.getTopic();
        MessageE respondMessageE = new MessageE(userE,message,topicE, preMessageE);
        em.persist(respondMessageE);
        return respondMessageE.getMessageId();
    }




    public static boolean messageExists(Long id) {
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

    public static boolean messageIsOrigin(Long id) {
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

    public static void deleteMessage(Long Id) {
        em.remove(findById(Id));
    }

    public static MessageE findById(Long Id) {
        return em.find(MessageE.class,Id);
    }

    public List<MessageE> getOriginMessagesByTopic(TopicE topic) {
        Query q =  em.createQuery("from Message m where m.topic = " + topic + "and m.origin = " + Boolean.TRUE + "order by m.date");
        List<MessageE> messages = q.getResultList();
        return messages;
    }

    public List<MessageE> getOriginMessagesByTopicAndDate(TopicE topic, Date date) {
        Query q = em.createQuery("from Message m where m.topic = " + topic + "and m.origin = "+ Boolean.TRUE + "and m._date >= "+ date + "order by m.date");
        List<MessageE> messages = q.getResultList();
        return messages;
    }

    public List<MessageE> getMessagesByPredecessor(MessageE predecessor) {
        Query q = em.createQuery("from Message m where m.predecessor = " + predecessor);
        List<MessageE> messages = q.getResultList();
        return messages;
    }
}
