package com.dj.kafkatests;

import com.dj.kafkatests.model.employeev1.Employee;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.Properties;
import java.util.stream.IntStream;

public class AvroConsumer {
	public static void main(String[] args) {
		Properties kafkaProps = new Properties();
		kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
		kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, "avro-test-consumer-group");
		kafkaProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "avro-test-client-1");
		kafkaProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		kafkaProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
		kafkaProps.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());

		kafkaProps.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
		kafkaProps.put(KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS, "false");
		kafkaProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

		KafkaConsumer<String, Employee> consumer = new KafkaConsumer<>(kafkaProps);
		consumer.subscribe(Collections.singletonList("employees"));

		try {
			while (true) {
				ConsumerRecords<String, Employee> records = consumer.poll(100);
				for (ConsumerRecord<String, Employee> record: records) {
					Employee employee = record.value();
					System.out.format("topic: %s, " +
									"partition: %s, " +
									"offset: %s, " +
									"key: %s, " +
									"value: %s%n",
							record.topic(), record.partition()
							, record.offset()
							, record.key()
							, employee);
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
