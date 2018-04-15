package com.dj.avrocreator;

import com.dj.model.Actual;
import com.dj.model.ModelCreator;
import com.dj.model.avro.ActualAvro;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;

public class AvroEncoderDecoder {
	public static void main(String[] args) throws Exception {
		new AvroEncoderDecoder().startProcessing();
	}

	public void startProcessing() throws Exception {
		Actual actual = ModelCreator.createActual();
		AvroReflectionCopyUtils avroReflectionCopyUtils = new AvroReflectionCopyUtils();
		ActualAvro actualAvro = avroReflectionCopyUtils.deepCopy(actual, ActualAvro.class);
		byte[] bytes = encodeAvroObjectToBytes(actualAvro, ActualAvro.getClassSchema());
		ActualAvro actualAvroDeserialized = decodeBytesToAvroObject(bytes, ActualAvro.getClassSchema());
		System.out.println("Encode avro bytes: " + bytes.length);
		System.out.println("Comparing objects: " + actualAvro.equals(actualAvroDeserialized));
		Actual actualDeserialized = avroReflectionCopyUtils.deepCopy(actualAvroDeserialized, Actual.class);
		System.out.println("Comparing original object: " + actual.equals(actualDeserialized));
		System.out.println(actual);
		System.out.println(actualDeserialized);
	}

	public byte[] encodeAvroObjectToBytes(Object value, Schema schema) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		DatumWriter writer = new SpecificDatumWriter(schema);

		writer.write(value, encoder);
		encoder.flush();
		out.close();
		byte[] serializedBytes = out.toByteArray();
		return serializedBytes;
	}

	public <T> T decodeBytesToAvroObject(byte[] bytes, Schema schema) throws Exception {
		DatumReader reader = new SpecificDatumReader(schema);
		Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
		return (T)reader.read(null, decoder);
	}
}
