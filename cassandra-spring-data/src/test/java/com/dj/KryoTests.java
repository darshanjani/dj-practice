package com.dj;

import com.dj.model.LargeObject;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class KryoTests {

	Kryo kryo;

	@Before
	public void before() {
		kryo = new Kryo();
	}

	@Test
	public void randomObjectEqualsTest() {
		LargeObject object = EnhancedRandom.random(LargeObject.class);
		ByteBufferOutput output = new ByteBufferOutput(4096, -1);
		kryo.writeObject(output, object);
		output.flush();
//		ByteArrayOutputStream outputStream = (ByteArrayOutputStream)output.getOutputStream();
//		byte[] bytes = outputStream.toByteArray();
//		byte[] bytes = output.toBytes();
		ByteBuffer byteBuffer = output.getByteBuffer();
		System.out.println("Bytes size: " + output.toBytes().length);
		Input input = new Input(output.toBytes());
		LargeObject object2 = kryo.readObject(input, LargeObject.class);
		Assert.assertEquals(object, object2);
		System.out.println(object.getContextId());
		System.out.println(object2.getContextId());
	}
}
