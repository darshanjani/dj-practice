package com.dj.neo4jspringdata;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Value("${neo4j.url:bolt://localhost:7687}")
	private String url;

	@Bean
	public Driver neoDriver() {
		Driver driver = GraphDatabase.driver(url);
		return driver;
	}
}
