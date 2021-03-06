package com.dj.avrocreator;

import com.dj.model.LargeObject;
import com.dj.model.SmallConsumerObject;
import com.dj.model.avro.LargeObjectAvro;
import com.dj.model.avro.SmallConsumerObjectAvro;
import io.github.benas.randombeans.api.EnhancedRandom;

public class ConsumerDrivenContract {
	public static void main(String[] args) throws Exception {
		new ConsumerDrivenContract().startProcessing();
	}

	public void startProcessing() throws Exception {
		AvroReflectionCopyUtils avroReflectionCopyUtils = new AvroReflectionCopyUtils();
		AvroEncoderDecoder avroEncoderDecoder = new AvroEncoderDecoder();

		LargeObject largeObject = EnhancedRandom.random(LargeObject.class);
//		largeObject.setVar1(null);
		LargeObjectAvro largeObjectAvro = avroReflectionCopyUtils.deepCopy(largeObject, LargeObjectAvro.class);
		byte[] bytes = avroEncoderDecoder.encodeAvroObjectToBytes(largeObjectAvro, LargeObjectAvro.getClassSchema());
		System.out.println(largeObjectAvro);
		SmallConsumerObjectAvro smallConsumerObjectAvro =
				(SmallConsumerObjectAvro)avroEncoderDecoder.decodeBytesToAvroObject(
						bytes,
						LargeObjectAvro.getClassSchema(),
						SmallConsumerObjectAvro.getClassSchema());
		System.out.println(smallConsumerObjectAvro);
		SmallConsumerObject smallConsumerObject = avroReflectionCopyUtils.deepCopy(
				smallConsumerObjectAvro, SmallConsumerObject.class);
		System.out.println(smallConsumerObject);
	}
}
