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
        mE.messageId = id;
        hashMessage.put(id, mE);
        return id;

    }

    @Transactional
    public Long createRespondMessage(UserE userE, String message, Long predecessor) {
        MessageE preMessageE = findById(predecessor);
        Topic topic = preMessageE.getTopic();
        MessageE respondMessageE = new MessageE(userE,message, topic, preMessageE);
        Long id = Integer.toUnsignedLong(hashMessage.size());
        respondMessageE.messageId = id;
        hashMessage.put(id, respondMessageE);
        return id;
    }

    public boolean messageExists(Long id) {
        return hashMessage.containsKey(id);
    }
    
    public boolean messageIsOrigin(Long id) {
        MessageE mE = hashMessage.get(id);
        return mE.getOrigin();
    }

    @Transactional
    public void deleteMessage(Long id) {
        System.out.println("delete Message with id: " + id + "...");
        hashMessage.remove(id);
        System.out.println("Message with id: " + id + " deletet");
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
            if (mE.getTopic().getTopicName().equals(topic) && mE.getOrigin()) {
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
            if (mE.getTopic().getTopicName().equals(topic) && mE.getDate().after(date) && mE.getOrigin()) {
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
            if (mE.getPredecessor() != null) {
                if (mE.getPredecessor().messageId == predecessor.messageId) {
                    messagesReturn.add(mE);
                }
            }
        }
        return messagesReturn;
    }

    @Transactional
    public List<List<MessageE>> getMessagesByTopic(String topic) {
        List<MessageE> innerList = new ArrayList<>();
        List<List<MessageE>> returnList = new ArrayList<>();
        List<MessageE> originList = new ArrayList<>();
        List<MessageE> followerList = new ArrayList<>();

        originList = getOriginMessagesWithTopic(topic);

        for (MessageE originME: originList) {
            followerList = getMessagesByPredecessor(originME);
            innerList.add(originME);
            for (MessageE followerME: followerList) {
                innerList.add(followerME);
            }
            returnList.add(new ArrayList<MessageE>(innerList));
            innerList.clear();
            followerList.clear();
        }
        return returnList;
    }


    @Transactional
    public List<List<MessageE>> getMessagesByTopicSinceDate(String topic, Date date) {
        List<MessageE> innerList = new ArrayList<>();
        List<List<MessageE>> returnList = new ArrayList<>();
        List<MessageE> originList = new ArrayList<>();
        List<MessageE> followerList = new ArrayList<>();

        originList = getOriginMessagesWithTopicSinceDate(topic,date);

        for (MessageE originME: originList) {
            followerList = getMessagesByPredecessor(originME);
            innerList.add(originME);
            for (MessageE followerME: followerList) {
                innerList.add(followerME);
            }
            returnList.add(new ArrayList<MessageE>(innerList));
            innerList.clear();
            followerList.clear();
        }
        return returnList;
    }
}
