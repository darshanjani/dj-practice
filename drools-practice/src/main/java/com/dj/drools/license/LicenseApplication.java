package com.dj.drools.license;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

public class LicenseApplication {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kContainer = kieServices.getKieClasspathContainer();
		StatelessKieSession kSession = kContainer.newStatelessKieSession();
		
		KieCommands kieCommands = kieServices.getCommands();
		List<Command<Applicant>> cmds = new ArrayList<Command<Applicant>>();
		cmds.add(kieCommands.newInsert(new Applicant("John Doe", 30), "johndoe", true, null));
		cmds.add(kieCommands.newInsert(new Applicant("John Smith", 17), "johnsmith", true, null));
		ExecutionResults results = kSession.execute(kieCommands.newBatchExecution(cmds));
		System.out.println(results.getValue("johnsmith"));
		
//		Applicant applicant = new Applicant( "Mr John Smith", 16 );
//		Application application = new Application(new Date(2017, 7, 15));
//		assertTrue( application.isValid() );
//		kSession.execute( Arrays.asList(new Object[] { applicant, application }) );
//		assertFalse( application.isValid() );
	}

}
