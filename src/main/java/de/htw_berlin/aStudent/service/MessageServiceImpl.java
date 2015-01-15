package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.Message;
import de.htw_berlin.aStudent.repository.MessageRepoImpl;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.AuthorizationException;

/**
 * Created by meichris on 15.01.15.
 */
public class MessageServiceImpl {

    public Long createMessage(String userName, String message, String topic) {
        Long ret = null;


        return ret;
    }

    public void deleteMessage(String userName, Long messageId) throws AuthorizationException {
        if (userName == null || messageId == null) {
            throw new NullPointerException();
        }
        Message m = MessageRepoImpl.findById(messageId);

        if (m == null || !m.getUser().getUsername().equals(userName) ||) {
            throw new IllegalArgumentException();
        }
        if (m.getUser().getUsername().equals(userName))
            MessageRepoImpl.deleteMessage(messageId);
        else throw new AuthorizationException("error!");
    }
}

