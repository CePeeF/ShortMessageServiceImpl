package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import de.htw_berlin.aStudent.model.TopicE;
import de.htw_berlin.aStudent.model.UserE;

import java.util.*;

import de.htw_berlin.aStudent.model.MessageE;

import de.htw_berlin.aStudent.repository.MessageRepoImpl;
import de.htw_berlin.aStudent.repository.TopicRepoImpl;
import de.htw_berlin.aStudent.repository.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw_berlin.aStudent.service.AnApplicationService;

import javax.persistence.NoResultException;


public class ShortMessageServiceImpl implements ShortMessageService{

	
	@Autowired
	AnApplicationService anApplicationService;

	@Override
	public Long createMessage(String userName, String message, String topic) throws IllegalArgumentException, NullPointerException {
		if (userName == null || message == null || topic ==  null) {
			throw new NullPointerException();
		}
		if (!UserRepoImpl.userExits(userName) || !TopicRepoImpl.topicExits(topic) || message.length() > 255 || message.length() < 10) {
			throw new IllegalArgumentException();
		}

		return MessageRepoImpl.createMessage(userName, message, topic);
	}

	@Override
	public Long respondToMessage(String userName, String message, Long predecessor) throws IllegalArgumentException, NullPointerException {
		if (userName == null || message == null || predecessor ==  null) {
			throw new NullPointerException();
		}
		if (!UserRepoImpl.userExits(userName) || !MessageRepoImpl.messageExists(predecessor) ||
				!MessageRepoImpl.messageIsOrigin(predecessor) || message.length() > 255 || message.length() < 10) {
			throw new IllegalArgumentException();
		}

		return MessageRepoImpl.createRespondMessage(userName, message, predecessor);
	}

	@Override
	public void deleteMessage(String userName, Long messageId) throws AuthorizationException, IllegalArgumentException, NullPointerException {
		if (userName == null || messageId == null) {
			throw new NullPointerException();
		}

		MessageE m = MessageRepoImpl.findById(messageId);

		if (!m.getUser().getName().equals(userName)) {
			throw new AuthorizationException("Sie sind nicht der Erzeuger!");
		}

		// TO DO


		if (m == null || !m.getUser().getUsername().equals(userName)){
			throw new IllegalArgumentException();
		}
		if (m.getUser().getName().equals(userName))
			MessageRepoImpl.deleteMessage(messageId);
		else throw new AuthorizationException("error!");
		
	}

	@Override
	public void createTopic(String userName, String topic)throws IllegalArgumentException, NullPointerException {

		if(userName == null || topic == null) {
			throw new NullPointerException();
		}
		if(!UserRepoImpl.userExits(userName) || !TopicRepoImpl.topicExits(topic) || topic.length() < 2 || topic.length() > 70) {
			throw new IllegalArgumentException();
		}

		TopicRepoImpl u = new TopicRepoImpl();
		u.createTopic(userName, topic);
		
	}

	@Override
	public Set<String> getTopics() {
		List<TopicE> te = new ArrayList<TopicE>();
		Set<String> set = new HashSet<>();
		try {
			for (TopicE tE : TopicRepoImpl.getAllTopics()) {
				set.add(tE.getTopicName());
			}
		} catch (NoResultException e){}

		return set;
	}

	@Override
	public List<List<Message>> getMessageByTopic(String topic, Date since) {

		// TO DO
		return null;
	}

	@Override
	public void createUser(String userName, String city) throws IllegalArgumentException, NullPointerException {

		if(userName == null || city == null) {
			throw new NullPointerException();
		}
		if(UserRepoImpl.userExits(userName) || userName.length() < 4 || userName.length() > 30) {
			throw new IllegalArgumentException();
		}
		UserRepoImpl u = new UserRepoImpl();
		u.createUser(userName,city);

		anApplicationService.doSomeThing();
	}

	@Override
	public void deleteUser(String userName) {

		if(UserRepoImpl.userExits(userName)) {
			throw new IllegalArgumentException();
		}
		UserRepoImpl u = new UserRepoImpl();
		u.deleteUser(userName);
	}

	@Override
	public Set<User> getUsers() {
		Set<User> set;
		set = new HashSet<>();
		for (UserE putIn : UserRepoImpl.getAllUsers()) {
			User u = new User();
			u.setName(putIn.getName());
			u.setCity(putIn.getCity());
			set.add(u);
		}
		return set;
	}
	
	
	


	
	
}
