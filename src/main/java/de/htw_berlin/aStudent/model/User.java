package de.htw_berlin.aStudent.model;

import java.util.Set;
import javax.persistence.*;

/**
 * Created by meichris on 15.01.15.
 */
public class User {

    @Id
    private String name;

    @Column
    private String city;

    @OneToMany(mappedBy = "userTopic", cascade = CascadeType.ALL)
    private Set<Topic> topics;

    @OneToMany(mappedBy = "userMessage", cascade = CascadeType.ALL)
    private Set<Message> messages;

    private User() {}

    public User(String username, String city) {
        this.name = username;
        this.city = city;
    }

    public String getUsername() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        User other = (User) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}

