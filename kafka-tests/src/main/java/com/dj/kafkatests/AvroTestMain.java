package com.dj.kafkatests;

import com.dj.kafkatests.model.User;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

public class AvroTestMain {
	public static void main(String[] args) throws Exception {
		User user1 = new User();
		user1.setName("John");
		user1.setFavoriteNumber(23);
		user1.setFavoriteColor("Yellow");

		User user2 = new User("Jane", 11, "Red");

		User user3 = User.newBuilder()
				.setName("Peter")
				.setFavoriteNumber(9)
				.setFavoriteColor("Green")
				.build();

		DatumWriter<User> datumWriter = new SpecificDatumWriter<>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<>(datumWriter);
		dataFileWriter.create(user1.getSchema(), new File("users.avro"));
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.append(user3);
		dataFileWriter.close();

		Schema schema = new Schema.Parser().parse(new File("src/main/avro/user.avsc"));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(schema, outputStream);
		datumWriter.write(user1, jsonEncoder);
		jsonEncoder.flush();
		String user1Json = outputStream.toString();
		System.out.println("User 1 in json: " + user1Json);

		DatumReader<User> datumReader = new SpecificDatumReader<>(User.class);
		DataFileReader<User> dataFileReader = new DataFileReader<User>(new File("users.avro"), datumReader);
		User user = null;
		while (dataFileReader.hasNext()) {
			user = dataFileReader.next(user);
//			System.out.println(user);
		}
		JsonDecoder jsonDecoder = DecoderFactory.get().jsonDecoder(User.getClassSchema(), user1Json);
		User read = datumReader.read(null, jsonDecoder);
		System.out.println("User read from json: " + read);
	}
}
