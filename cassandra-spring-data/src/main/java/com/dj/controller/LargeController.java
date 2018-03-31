package com.dj.controller;

import com.datastax.driver.core.utils.Bytes;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.dj.model.LargeBlob;
import com.dj.model.LargeObject;
import com.dj.repo.LargeBlobAccessor;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController()
public class LargeController {

	@Autowired
	private MappingManager mappingManager;

	private LargeObject compare = null;
	private String contextId = null;

	@GetMapping("/large/insert/{size}")
	public String insertLargeObjects(@PathVariable int size) {
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();

		Mapper<LargeObject> mapper = mappingManager.mapper(LargeObject.class);
		List<LargeObject> largeObjects = enhancedRandom.randomStreamOf(size, LargeObject.class).collect(Collectors.toList());
		IntStream.range(0, largeObjects.size())
				.forEach(idx -> {
					largeObjects.get(idx).setContextId("MyContext");
					largeObjects.get(idx).setRecordNum(idx);
				});
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		IntStream.range(0, largeObjects.size())
				.forEach(idx -> {
					mapper.save(largeObjects.get(idx));
				});
		stopWatch.stop();
		return "Inserted " + size + " objects in " + stopWatch.prettyPrint();
	}

	@GetMapping("/large/insertBlob/{size}")
	public String insertLargeBlobs(@PathVariable int size) {
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
		Kryo kryo = new Kryo();

		Mapper<LargeBlob> mapper = mappingManager.mapper(LargeBlob.class);
		List<LargeObject> largeObjects = enhancedRandom.randomStreamOf(size, LargeObject.class).collect(Collectors.toList());
		List<LargeBlob> blobs = new ArrayList<>(largeObjects.size());
		compare = largeObjects.get(0);
		IntStream.range(0, largeObjects.size())
				.forEach(idx -> {
					LargeBlob blob = new LargeBlob();
//					largeObjects.get(idx).setContextId("MyContext");
					largeObjects.get(idx).setRecordNum(idx);
					contextId = largeObjects.get(idx).getContextId();
					blob.setContext_id(contextId);
					blob.setRecord_num(idx);
//					byte[] bytes = objectToBytesUsingKryo(kryo, largeObjects.get(idx));
					byte[] bytes = objectToByteBufferUsingKryo(kryo, largeObjects.get(idx));
					ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
					System.out.println("Size: " + bytes.length);
					System.out.println("ContextId: " + contextId);
					blob.setObject(byteBuffer);
					blobs.add(blob);
				});
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		IntStream.range(0, blobs.size())
				.forEach(idx -> {
					System.out.println("Saving: " + blobs.get(idx).getContext_id());
					mapper.save(blobs.get(idx));
				});
		stopWatch.stop();
		return "Inserted " + size + " objects in " + stopWatch.prettyPrint();
	}

	@GetMapping("/large/readBlob")
	public String readBlob() throws Exception {
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
		Kryo kryo = new Kryo();

		LargeBlobAccessor accessor = mappingManager.createAccessor(LargeBlobAccessor.class);
//		LargeBlob blob = new LargeBlob();
//		blob.setContext_id("MyContext");
//		blob.setRecord_num(1);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		LargeBlob blob1 = accessor.getOne(contextId, 0);
		ByteBuffer object = blob1.getObject();
		System.out.println("context: " + blob1.getContext_id());
		System.out.println("Size of buffer: " + object.remaining());
//		FileOutputStream fos = new FileOutputStream("c:\\Users\\darsh\\Downloads\\obj2.dat");
		byte[] bytes = Bytes.getArray(object);
//		object.get(bytes);
//		fos.write(bytes);
//		fos.flush();
//		fos.close();
		Input input = new Input(bytes);
		LargeObject largeObject = kryo.readObject(input, LargeObject.class);
		stopWatch.stop();
		return "Read " + largeObject.equals(compare) + " object in " + stopWatch.prettyPrint();
	}

	@GetMapping("/large/javaVsKryo/{size}")
	public String javaVsKryoSerialization(@PathVariable int size) {
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
		List<LargeObject> largeObjects = enhancedRandom.randomStreamOf(size, LargeObject.class).collect(Collectors.toList());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Kryo object creation");
		Kryo kryo = new Kryo();
		stopWatch.stop();
		stopWatch.start(size  + " kryo object serialization");
		largeObjects.stream().forEach(largeObject -> {
			byte[] bytes = objectToBytesUsingKryo(kryo, largeObject);
		});
		stopWatch.stop();
		stopWatch.start(size + " java object serialization");
		largeObjects.stream().forEach(largeObject -> {
			try {
				byte[] bytes = objectToBytesUsingJava(largeObject);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		stopWatch.stop();
		return stopWatch.prettyPrint();
	}

	private byte[] objectToBytesUsingKryo(Kryo kryo, LargeObject largeObject) {
		Output output = new Output(new ByteArrayOutputStream());
		kryo.writeObject(output, largeObject);
		output.flush();
		return ((ByteArrayOutputStream) output.getOutputStream()).toByteArray();
	}

	private byte[] objectToByteBufferUsingKryo(Kryo kryo, LargeObject largeObject) {
		ByteBufferOutput output = new ByteBufferOutput(4096, -1);
		kryo.writeObject(output, largeObject);
//		output.flush();
		return output.toBytes();
	}

	private byte[] objectToBytesUsingJava(LargeObject largeObject) throws IOException {
		return SerializationUtils.serialize(largeObject);
	}
}
