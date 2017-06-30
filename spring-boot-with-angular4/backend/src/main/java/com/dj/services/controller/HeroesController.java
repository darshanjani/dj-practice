package com.dj.services.controller;

import com.dj.services.model.Hero;
import com.dj.services.model.HeroName;
import com.dj.services.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Darshan on 6/23/2017.
 */
@RestController
@RequestMapping("/api")
public class HeroesController {

	private static final Logger logger = LoggerFactory.getLogger(HeroesController.class);

	static List<Hero> heroes = new ArrayList<>();
	static {
		heroes.add(new Hero(0, "Zero"));
		heroes.add(new Hero(11, "Mr. Nice"));
		heroes.add(new Hero(12, "Narco"));
		heroes.add(new Hero(13, "Bombasto"));
		heroes.add(new Hero(14, "Celeritas"));
		heroes.add(new Hero(15, "Magneta"));
		heroes.add(new Hero(16, "RubberMan"));
		heroes.add(new Hero(17, "Dynama"));
		heroes.add(new Hero(18, "Dr IQ"));
		heroes.add(new Hero(19, "Magma"));
		heroes.add(new Hero(20, "Tornado"));
	}

	@GetMapping(value = "/heroes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Hero> getHeroes() {
		return heroes.stream().sorted(Comparator.comparing(hero -> hero.getId())).collect(Collectors.toList());
	}

	@GetMapping(value = "/heroes/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Hero> getHeroesByName(@RequestParam("name") String name) {
		return heroes.stream()
				.filter(hero -> hero.getName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/heroes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Hero getHeroes(@PathVariable int id) {
		logger.info("Received detail hero request for: {}", id);
		return heroes.stream().filter(hero -> hero.getId() == id).findFirst().orElse(heroes.get(0));
	}

	@PutMapping(value = "/heroes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateHero(@PathVariable int id, @RequestBody Hero hero) {
		logger.info("Received update hero request for: {}", hero);
		heroes.removeIf(hero1 -> hero1.getId() == id);
		heroes.add(hero);
	}

	@PostMapping(value = "/heroes", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Hero createHero(@RequestBody HeroName heroName) {
		logger.info("Received create hero request for: {}", heroName);
		int maxId = heroes.stream().map(hero -> hero.getId()).max(Comparator.naturalOrder()).orElse(1);
		logger.info("Max ID is: {}", maxId);
		maxId++;
		Hero hero = new Hero(maxId, heroName.getName());
		heroes.add(hero);
		logger.info("Heroes Size is: {}", heroes.size());
		return hero;
	}

	@DeleteMapping(value = "/heroes/{id}")
	public void deleteHero(@PathVariable int id) {
		logger.info("Received delete hero request for: {}", id);
		heroes.removeIf(hero1 -> hero1.getId() == id);
	}
}
