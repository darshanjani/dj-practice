package com.dj.kafkatests;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTest {

	public static void main(String[] args) {
//		SpringApplication.run(KafkaProducerTest.class, args);
		Properties kafkaProps = new Properties();
		kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
		kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.put("group.id", "test-consumer-group");
		kafkaProps.put("client.id", "test-client-1");
		kafkaProps.put("enable.auto.commit", "false");
		kafkaProps.put("max.poll.records", "1");
		kafkaProps.put("partition.assignment.strategy", RoundRobinAssignor.class.getName());
		kafkaProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

		//SSL properties
		kafkaProps.put("security.protocol", "SSL");
		kafkaProps.put("ssl.truststore.location", "/c/sw/confluent-4.0.0/etc/kafka-certs/kafka.client.truststore.jks");
		kafkaProps.put("ssl.truststore.password", "changeit");
		kafkaProps.put("ssl.truststore.type", "JKS");

		kafkaProps.put("ssl.keystore.location", "/c/sw/confluent-4.0.0/etc/kafka-certs/kafka.client.keystore.jks");
		kafkaProps.put("ssl.keystore.password", "changeit");
		kafkaProps.put("ssl.key.password", "changeit");

		kafkaProps.put("ssl.cipher.suites", "TLS_RSA_WITH_AES_128_CBC_SHA,TLS_RSA_WITH_AES_256_CBC_SHA");
		kafkaProps.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
		kafkaProps.put("ssl.keystore.type", "JKS");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(kafkaProps);
		consumer.subscribe(Collections.singletonList("test"));

		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record: records) {
					System.out.format("topic: %s, " +
							"partition: %s, " +
							"offset: %s, " +
							"key: %s, " +
							"value: %s%n",
							record.topic(), record.partition()
							, record.offset()
					, record.key()
					, record.value());
				}
				try {
					if (!records.isEmpty()) {
						consumer.commitSync();
					    System.out.println("Commit performed");
					}
				} catch (CommitFailedException cfe) {
					cfe.printStackTrace();
				}
			}
		} finally {
			consumer.close();
		}
	}
}
