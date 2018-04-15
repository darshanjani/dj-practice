package com.dj.avrocreator;

import com.dj.model.LargeObject;
import com.dj.model.avro.LargeObjectAvro;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTests {
	private static final int OBJECTS_COUNT = 50_000;

	public static void main(String[] args) throws Exception {
		new PerformanceTests().startProcessing();
	}

	public void startProcessing() throws Exception {
		System.out.println(OBJECTS_COUNT + " - Processing");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Creating Objects ");
		LargeObject largeObject = EnhancedRandom.random(LargeObject.class);
		LargeObjectAvro largeAvroObject = null;
		stopWatch.stop();
		stopWatch.start("Serializing Objects using Kryo: No. of objects " + OBJECTS_COUNT);
		Kryo kryo = new Kryo();
		for (int i = 0; i < OBJECTS_COUNT; i++) {
			ByteBufferOutput output = new ByteBufferOutput(4096, -1);
			kryo.writeObject(output, largeObject);
			if (i == 0) {
				System.out.println("Kryo Objects-to-bytes size: " + output.toBytes().length);
			}
		}
		stopWatch.stop();
		stopWatch.start("Copy Over from Java Objects to Avro: No. of objects " + OBJECTS_COUNT);
		AvroReflectionCopyUtils avroReflectionCopyUtils = new AvroReflectionCopyUtils();
		for (int i = 0; i < OBJECTS_COUNT; i++) {
			largeAvroObject = avroReflectionCopyUtils.deepCopy(largeObject, LargeObjectAvro.class);
		}
		stopWatch.stop();
		stopWatch.start("Serialize objects using Avro: No. of objects " + OBJECTS_COUNT);
		AvroEncoderDecoder avroEncoderDecoder = new AvroEncoderDecoder();
		for (int i = 0; i < OBJECTS_COUNT; i++) {
			byte[] bytes = avroEncoderDecoder.encodeAvroObjectToBytes(largeAvroObject, LargeObjectAvro.getClassSchema());
			if (i == 0) {
				System.out.println("Avro Object-to-bytes size: " + bytes.length);
			}
		}
		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}
}
