package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan on 7/8/2017.
 */
@ConfigurationProperties(prefix = "my")
@Component
public class Config {
	private List<String> servers = new ArrayList<>();

	public List<String> getServers() {
		return servers;
	}

//	public void setServers(List<String> servers) {
//		this.servers = servers;
//	}
}
