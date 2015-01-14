package de.htw_berlin.f4.ai.kbe.kurznachrichten;


import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import de.htw_berlin.aStudent.service.AnApplicationService;


public class ShortMessageServiceImpl implements ShortMessageService{

	
	@Autowired
	AnApplicationService anApplicationService;

	@Override
	public Long createMessage(String userName, String message, String topic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long respondToMessage(String userName, String message,
			Long predecessor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMessage(String userName, Long messageId)
			throws AuthorizationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTopic(String userName, String topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getTopics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<Message>> getMessageByTopic(
			String topic, Date since) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(String userName, String city) {
		
		// TODO implement correctly
		//dummy for test
		anApplicationService.doSomeThing();
	}

	@Override
	public void deleteUser(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	


	
	
}
