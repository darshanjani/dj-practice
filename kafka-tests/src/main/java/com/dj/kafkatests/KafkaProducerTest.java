package com.dj.kafkatests;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.Properties;
import java.util.stream.IntStream;

@SpringBootApplication
public class KafkaProducerTest {

	public static void main(String[] args) {
		Properties kafkaProps = new Properties();
		kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		kafkaProps.put(ProducerConfig.ACKS_CONFIG, "all");
		kafkaProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "false");
		kafkaProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1");
//		kafkaProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "producer-1");

		//SSL properties
//		kafkaProps.put("security.protocol", "SSL");
//		kafkaProps.put("ssl.truststore.location", "/c/sw/confluent-4.0.0/etc/kafka-certs/kafka.client.truststore.jks");
//		kafkaProps.put("ssl.truststore.password", "changeit");
//		kafkaProps.put("ssl.truststore.type", "JKS");
//
//		kafkaProps.put("ssl.keystore.location", "/c/sw/confluent-4.0.0/etc/kafka-certs/kafka.client.keystore.jks");
//		kafkaProps.put("ssl.keystore.password", "changeit");
//		kafkaProps.put("ssl.key.password", "changeit");
//
//		kafkaProps.put("ssl.cipher.suites", "TLS_RSA_WITH_AES_128_CBC_SHA,TLS_RSA_WITH_AES_256_CBC_SHA");
//		kafkaProps.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
//		kafkaProps.put("ssl.keystore.type", "JKS");


		Producer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);

		IntStream.range(1, 2).forEach(i -> {
			ProducerRecord<String, String> record = new ProducerRecord<>("test", "Key " + i, "Value " + new Date());
			record.headers().add("account", "1012".getBytes());
			try {
				RecordMetadata recordMetadata = producer.send(record).get();
				if (recordMetadata.hasOffset()) {
					System.out.println("Record stored with offset: " + recordMetadata.offset());
				}
				System.out.println("Record stored in partition: " + recordMetadata.partition());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}
}
