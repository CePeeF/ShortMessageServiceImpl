package de.htw_berlin.aStudent.model;

import java.util.Set;
import javax.persistence.*;

/**
 * Created by meichris on 15.01.15.
 */
public class TopicE {

    @Id
    @GeneratedValue
    private Long topicId;

    @Column
    private String topicName;

    @ManyToOne
    @JoinColumn(name = "userTopic")
    private UserE userFromTopic;

    @OneToMany(mappedBy = "topicMessage", cascade = CascadeType.ALL)
    private Set<MessageE> messages;

    public TopicE(String topic, UserE user) {
        this.topicName = topic;
        this.userFromTopic = user;
    }

    public Long getTopicId() {
        return topicId;
    }
    public String getTopicName() {
        return topicName;
    }
    public UserE getUser() {
        return userFromTopic;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((topicId == null) ? 0 : topicId.hashCode());
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
        TopicE other = (TopicE) obj;
        if (topicId == null) {
            if (other.topicId != null) {
                return false;
            }
        } else if (!topicId.equals(other.topicId)) {
            return false;
        }
        return true;
    }

}

