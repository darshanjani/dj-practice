package com.dj.neo4jspringdata.controllers;

import com.dj.neo4jspringdata.services.Neo4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/context")
public class MainController {

	@Autowired
	private Neo4jService service;

	@GetMapping("save/{contextId}")
	public String createContext(@PathVariable("contextId") String contextId) {
		return service.createContext(contextId);
	}

	@GetMapping("save/{contextId}/{profileCount}")
	public String createProfiles(@PathVariable("contextId") String contextId, @PathVariable("profileCount") Integer profileCount) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		service.createProfile(contextId, profileCount);

		stopWatch.stop();
		return "Profiles created successfully " + stopWatch.prettyPrint();
	}
}
