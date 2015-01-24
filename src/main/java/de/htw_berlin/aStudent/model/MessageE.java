package de.htw_berlin.aStudent.model;


import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 * Created by meichris on 15.01.15.
 */
@Entity
public class MessageE {

    public static final String QUERY_FETCH_ALL = "Message.fetchAll";

    @Id
    @GeneratedValue
    private Long messageId;

    @Column
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private Boolean origin;

    @ManyToOne
    @JoinColumn(name = "userMessage")
    private UserE userMessage;

    @ManyToOne
    @JoinColumn(name = "topicMessage")
    private Topic topicMessage;

    @ManyToOne
    @JoinColumn(name = "predecessor")
    private MessageE predecessor;

    @OneToMany(mappedBy = "predecessor", cascade = CascadeType.ALL)
    private Set<MessageE> responds;


    public MessageE(UserE user, String content, Topic topic) {
        this.content = content;
        this.userMessage = user;
        this.topicMessage = topic;
        this.origin = true;
        this.predecessor = null;
        this.date = new Date();
    }

    public MessageE(UserE user, String content,Topic topic, MessageE predecessor) {
        this.content = content;
        this.userMessage = user;
        this.topicMessage = topic;
        this.origin = false;
        this.predecessor = predecessor;
        this.date = new Date();
    }

    public Long getMessageId() {
        return messageId;
    }

    public Boolean getOrigin() {
        return origin;
    }

    public UserE getUser() {
        return userMessage;
    }

    public Topic getTopic() {
        return topicMessage;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((messageId == null) ? 0 : messageId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MessageE other = (MessageE) obj;
        if (messageId == null) {
            if (other.messageId != null) {
                return false;
            }
        } else if (!messageId.equals(other.messageId)) {
            return false;
        }
        return true;
    }

}

