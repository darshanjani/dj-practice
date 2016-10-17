package com.dj.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.function.Consumer;

/**
 * Created by Darshan on 8/28/16.
 */
public class MongoFirstClient {
	public static void main(String[] args) throws Exception {
		MongoClient client = new MongoClient();
		MongoDatabase test = client.getDatabase("test");
		MongoCollection<Document> restaurants = test.getCollection("restaurants");
		MongoCollection<Document> demo = test.getCollection("demo");
//		demo.insertOne(new Document("key2", "value2"));
		System.out.println(demo.count());
		demo.find().sort(Sorts.ascending("key")).forEach(new Consumer<Document>() {
			@Override
			public void accept(Document document) {
				System.out.println(document.get("key2"));
			}
		});
	}
}
