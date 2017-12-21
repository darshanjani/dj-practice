package com.dj.kafkatests;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class ConsumerBobTest {

	public static void main(String[] args) {
		System.setProperty("java.security.auth.login.config", "/c/sw/confluent-4.0.0/etc/kafka/kafka_client_bob_jaas.conf");
		Properties kafkaProps = new Properties();
		kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
		kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
		kafkaProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "test-client-1");
		kafkaProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		kafkaProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
		kafkaProps.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());

		//SSL properties
		kafkaProps.put("security.protocol", "SASL_SSL");
		kafkaProps.put("sasl.mechanism", "PLAIN");
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
		consumer.subscribe(Collections.singletonList("secure-topic"));

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
