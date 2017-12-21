package com.dj.kafkatests;

import com.dj.kafkatests.model.employeev1.Employee;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;
import java.util.stream.IntStream;

public class AvroProducer {
	public static void main(String[] args) throws Exception {
		Properties kafkaProps = new Properties();
		kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
		kafkaProps.put(ProducerConfig.ACKS_CONFIG, "all");
		kafkaProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1");
		kafkaProps.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081/");
		kafkaProps.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, "false");

		Producer<String, Employee> producer = new KafkaProducer<>(kafkaProps);

		Employee bob = Employee.newBuilder()
				.setFName("Bob")
				.setLName("Lyons")
				.setPhoneNumber("123-456")
				.setAge(32)
				.build();
		IntStream.range(1, 2).forEach(i -> {
			ProducerRecord<String, Employee> record = new ProducerRecord<>(
					"employees", "Key 1", bob);
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
