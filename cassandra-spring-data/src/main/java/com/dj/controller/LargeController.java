package com.dj.controller;

import com.datastax.driver.core.utils.Bytes;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.dj.model.LargeBlob;
import com.dj.model.LargeObject;
import com.dj.repo.LargeBlobAccessor;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.*;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
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
		IntStream.range(0, largeObjects.size())
				.forEach(idx -> {
					LargeBlob blob = new LargeBlob();
					largeObjects.get(idx).setContextId("MyContext");
					largeObjects.get(idx).setRecordNum(idx);
					blob.setContext_id("MyContext");
					blob.setRecord_num(idx);
//					System.out.println(largeObjects.get(idx));
					Output output = null;
//					Output output2 = null;
//					try {
//						output2 = new Output(new FileOutputStream("c:\\Users\\darsh\\Downloads\\obj.dat"));
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
					output = new Output(new ByteArrayOutputStream(), 8092);
					kryo.writeObject(output, largeObjects.get(idx));
//					kryo.writeObject(output2, largeObjects.get(idx));
					compare = largeObjects.get(idx);
					output.flush();
//					output2.flush();
//					System.out.println("Buffer length: " + ((ByteArrayOutputStream)output.getOutputStream()).size());
					byte[] bytes = ((ByteArrayOutputStream)output.getOutputStream()).toByteArray();
//					System.out.println("Bytes length: " + bytes.length);
//					System.out.println("Actual bytes: " + new String(bytes));
//					ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
//					byteBuffer.put(bytes);
					ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
					blob.setObject(byteBuffer);
					blobs.add(blob);
				});
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		IntStream.range(0, blobs.size())
				.forEach(idx -> {
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

		LargeBlob blob1 = accessor.getOne("MyContext", 0);
		ByteBuffer object = blob1.getObject();
		System.out.println("context: " + blob1.getContext_id());
		System.out.println("Size of buffer: " + object.remaining());
		FileOutputStream fos = new FileOutputStream("c:\\Users\\darsh\\Downloads\\obj2.dat");
		byte[] bytes = Bytes.getArray(object);
//		object.get(bytes);
		fos.write(bytes);
		fos.flush();
		fos.close();
		Input input = new Input(bytes);
		LargeObject largeObject = kryo.readObject(input, LargeObject.class);
		stopWatch.stop();
		return "Read " + largeObject.equals(compare) + " object in " + stopWatch.prettyPrint();
	}
}
