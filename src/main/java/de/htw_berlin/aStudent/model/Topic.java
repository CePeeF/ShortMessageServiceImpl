package de.htw_berlin.aStudent.model;

import java.util.Set;
import javax.persistence.*;

/**
 * Created by meichris on 15.01.15.
 */
public class Topic {

    @Id
    @GeneratedValue
    private Long topicId;

    @Column
    private String topicName;

    @ManyToOne
    @JoinColumn(name = "userTopic")
    private User userTopic;

    @OneToMany(mappedBy = "topicMessage", cascade = CascadeType.ALL)
    private Set<Message> messages;

    private Topic() {}

    public Topic(String topic, User user) {
        this.topicName = topic;
        this.userTopic = user;
    }

    public Long getTopicId() {
        return topicId;
    }
    public String getTopic() {
        return topicName;
    }

    public User getUser() {
        return userTopic;
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
        Topic other = (Topic) obj;
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

