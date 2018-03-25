package com.dj.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	public Cluster cluster () {
		return Cluster.builder()
				.addContactPoint("localhost")
				.withCodecRegistry(
						new CodecRegistry().register(new BigIntegerCodec())
				)
				.build();
	}

	@Bean
	public Session session() {
		return cluster().connect("darshan");
	}

	@Bean
	public MappingManager mappingManager() {
		return new MappingManager(session());
	}
}
