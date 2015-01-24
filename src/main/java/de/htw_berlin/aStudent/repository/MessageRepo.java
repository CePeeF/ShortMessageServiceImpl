package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageE;
import de.htw_berlin.aStudent.model.Topic;
import de.htw_berlin.aStudent.model.UserE;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
public class MessageRepo {

    static EntityManager em;

    public static Long createMessage(UserE userE,String message,Topic topic) {
        MessageE mE = new MessageE(userE,message,topic);
        em.persist(mE);
        return mE.getMessageId();

    }

    public static Long createRespondMessage(UserE userE, String message, Long predecessor) {
        MessageE preMessageE = findById(predecessor);
        Topic topic = preMessageE.getTopic();
        MessageE respondMessageE = new MessageE(userE,message, topic, preMessageE);
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

//    public List<MessageE> getOriginMessagesByTopic(Topic topic) {
//        Query q =  em.createQuery("from Message m where m.topic = " + topic + "and m.origin = " + Boolean.TRUE + "order by m.date");
//        List<MessageE> messages = q.getResultList();
//        return messages;
//    }
//
//    public List<MessageE> getOriginMessagesByTopicAndDate(Topic topic, Date date) {
//        Query q = em.createQuery("from Message m where m.topic = " + topic + "and m.origin = "+ Boolean.TRUE + "and m.date >= "+ date + "order by m.date");
//        List<MessageE> messages = q.getResultList();
//        return messages;
//    }
//

    private static List<MessageE> getOriginMessagesWithTopic(Topic topic) {
        List<MessageE> messages = new ArrayList<MessageE>();
        try {
            Query q = em.createQuery("from MessageE m where m.topic = " + topic + "order by m.date");
            messages = q.getResultList();
        } catch (NoResultException e) {}
        return messages;
    }

    private static List<MessageE> getOriginMessagesWithTopicSinceDate(Topic topic, Date date) {
        List<MessageE> messages = new ArrayList<MessageE>();
        try {
            Query q = em.createQuery("from MessageE m where m.topic = " + topic + "and m.date >= " + date + "order by m.date");
            messages = q.getResultList();
        } catch (NoResultException e) {}
        return messages;
    }

    private static List<MessageE> getMessagesByPredecessor(MessageE predecessor) {
        List<MessageE> messages = new ArrayList<MessageE>();
        try {
            Query q = em.createQuery("from MessageE m where m.predecessor = " + predecessor + "order by m.date");
            messages = q.getResultList();
        } catch (NoResultException e) {}
        return messages;
    }

    public static List<List<MessageE>> getMessagesByTopic(String topic) {
        Topic t = TopicRepo.findByTopicName(topic);
        List<List<MessageE>> messagesByTopicAndDate = new ArrayList<List<MessageE>>();
        List<MessageE> originMessagesWithTopic = getOriginMessagesWithTopic(t);
        List<MessageE> originMessageWithRespondMessages = new ArrayList<MessageE>();
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


    public static List<List<MessageE>> getMessagesByTopicSinceDate(String topic, Date date) {
        Topic t = TopicRepo.findByTopicName(topic);
        List<List<MessageE>> messagesByTopicAndDate = new ArrayList<List<MessageE>>();
        List<MessageE> originMessagesWithTopic = getOriginMessagesWithTopicSinceDate(t,date);
        List<MessageE> originMessageWithRespondMessages = new ArrayList<MessageE>();
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
