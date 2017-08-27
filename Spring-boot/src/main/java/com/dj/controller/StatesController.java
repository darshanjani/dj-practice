package com.dj.controller;

import com.dj.model.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StatesController {

	private static final Logger logger = LoggerFactory.getLogger(StatesController.class);

	@GetMapping("/states")
	@ResponseBody
	public List<State> getStates(@RequestParam(required = false) String query) {
		logger.info("getStates invoked with query: {}", query);
		List<State> states = new ArrayList<>();
		states.add(new State("1", "Alabama", "South"));
		states.add(new State("2", "Alaska", "West"));
		states.add(new State("3", "Arizona", "West"));
		states.add(new State("4", "Arkansas", "South"));
		states.add(new State("5", "California", "West"));
		states.add(new State("6", "Colorado", "West"));
		states.add(new State("7", "Connecticut", "Northeast"));
		states.add(new State("8", "Delaware", "South"));
		states.add(new State("9", "Florida", "South"));
		states.add(new State("10", "Georgia", "South"));
		states.add(new State("11", "Hawaii", "West"));
		states.add(new State("12", "Idaho", "West"));
		states.add(new State("13", "Illinois", "Midwest"));
		states.add(new State("14", "Indiana", "Midwest"));
		states.add(new State("15", "Iowa", "Midwest"));
		states.add(new State("16", "Kansas", "Midwest"));
		states.add(new State("17", "Kentucky", "South"));
		states.add(new State("18", "Louisiana", "South"));
		states.add(new State("19", "Maine", "Northeast"));
		states.add(new State("21", "Maryland", "South"));
		states.add(new State("22", "Massachusetts", "Northeast"));
		states.add(new State("23", "Michigan", "Midwest"));
		states.add(new State("24", "Minnesota", "Midwest"));
		states.add(new State("25", "Mississippi", "South"));
		states.add(new State("26", "Missouri", "Midwest"));
		states.add(new State("27", "Montana", "West"));
		states.add(new State("28", "Nebraska", "Midwest"));
		states.add(new State("29", "Nevada", "West"));
		states.add(new State("30", "New Hampshire", "Northeast"));
		states.add(new State("31", "New Jersey", "Northeast"));
		states.add(new State("32", "New Mexico", "West"));
		states.add(new State("33", "New York", "Northeast"));
		states.add(new State("34", "North Dakota", "Midwest"));
		states.add(new State("35", "North Carolina", "South"));
		states.add(new State("36", "Ohio", "Midwest"));
		states.add(new State("37", "Oklahoma", "South"));
		states.add(new State("38", "Oregon", "West"));
		states.add(new State("39", "Pennsylvania", "Northeast"));
		states.add(new State("40", "Rhode Island", "Northeast"));
		states.add(new State("41", "South Carolina", "South"));
		states.add(new State("42", "South Dakota", "Midwest"));
		states.add(new State("43", "Tennessee", "South"));
		states.add(new State("44", "Texas", "South"));
		states.add(new State("45", "Utah", "West"));
		states.add(new State("46", "Vermont", "Northeast"));
		states.add(new State("47", "Virginia", "South"));
		states.add(new State("48", "Washington", "South"));
		states.add(new State("49", "West Virginia", "South"));
		states.add(new State("50", "Wisconsin", "Midwest"));
		states.add(new State("51", "Wyoming", "West"));
		if (query != null && !query.isEmpty()) {
			return states.stream().filter(state -> state.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
		}
		return states;
	}
}
