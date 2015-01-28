package de.htw_berlin.aStudent.service;

import de.htw_berlin.f4.ai.kbe.appconfig.AppConfig;
import org.springframework.stereotype.Service;


@Service
public class AnApplicationServiceImpl implements AnApplicationService {

	public void doSomeThing() {
		// TODO Auto-generated method stub
		AppConfig appConfig = new AppConfig();
		System.out.println("doSomeThing was called");
	}

	
	
}
