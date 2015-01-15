package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.Message;
import de.htw_berlin.aStudent.model.Topic;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by meichris on 15.01.15.
 */
public class MessageRepoImpl {

    static EntityManager em;

    public void createMessage() {
        try {
            em.persist(this);
        } catch (Exception e) {}
    }

    public static void deleteMessage(Long Id) {
        em.remove(findById(Id));
    }

    public static Message findById(Long Id) {
        return em.find(Message.class,Id);
    }

    public List<Message> getOriginMessagesByTopic(Topic topic) {
        Query q =  em.createQuery("from Message m where m.topic = " + topic + "and m.origin = " + Boolean.TRUE + "order by m.date");
        List<Message> messages = q.getResultList();
        return messages;
    }

    public List<Message> getOriginMessagesByTopicAndDate(Topic topic, Date date) {
        Query q = em.createQuery("from Message m where m.topic = " + topic + "and m.origin = "+ Boolean.TRUE + "and m._date >= "+ date + "order by m.date");
        List<Message> messages = q.getResultList();
        return messages;
    }

    public List<Message> getMessagesByPredecessor(Message predecessor) {
        Query q = em.createQuery("from Message m where m.predecessor = " + predecessor);
        List<Message> messages = q.getResultList();
        return messages;
    }
}
