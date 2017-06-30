package com.dj;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Darshan on 5/31/2017.
 */
public class Streams {

	static List<Student> students;

	@BeforeClass
	public static void beforeClass() {
		Student s1 = new Student(); s1.setName("Aaron");
		Student s2 = new Student(); s2.setName("Paul");
		Student s3 = new Student(); s3.setName("Betty");
		Student s4 = new Student(); s4.setName("Aarti");
		Student s5 = new Student(); s5.setName("Ben");
		Student s6 = new Student(); s6.setName("Brant");
		Student s7 = new Student(); s7.setName("Andy");
		students = Arrays.asList(s1, s2, s3, s4, s5, s6, s7);
	}

	@Test
	public void simpleStreams() {

		List<String> names = new ArrayList<>();
		for (Student student : students) {
			if (student.getName().startsWith("A")) {
				names.add(student.getName());
			}
		}
		System.out.println("Names traditional: " + names);

		List<String> streamNames = students
				.stream()   //Streams the results
				.map(student -> student.getName()) // Convert object to string for further steps, can also be written as Student::getName
				.filter(name -> name.startsWith("A"))   // Apply the filtering logic
				.limit(1)   // Limit the results to a given limit
				.collect(Collectors.toList());  // Terminal operator to collect results to a list
		System.out.println("Names streaming: " + streamNames);

		//Eliminate the System out println with a foreach terminal operator
		students
				.stream()   //Streams the results
				.map(Student::getName) // Convert object to string for further steps, can also be written as Student::getName
				.filter(name -> name.startsWith("A"))   // Apply the filtering logic
				.limit(1)   // Limit the results to a given limit
				.forEach(System.out::println);
	}

	@Test
	public void intStreams() {
		IntStream.range(1, 5).forEach(num -> System.out.println("Range:" + num));
		IntStream.rangeClosed(1, 5).forEach(num -> System.out.println("Range closed:" + num));
	}

	@Test
	public void streamsOf() throws Exception {
		//Creating Stream of hardcoded Strings and printing each String
		Stream.of("This", "is", "Java8", "Stream").forEach(System.out::println);

		//Creating stream of arrays
		String[] stringArray = new String[]{"Streams", "can", "be", "created", "from", "arrays"};
		Arrays.stream(stringArray).forEach(System.out::println);

		//Creating BufferedReader for a file
		BufferedReader reader = Files.newBufferedReader(Paths.get("File.txt"), StandardCharsets.UTF_8);

		//BufferedReader's lines methods returns a stream of all lines
		reader.lines().forEach(System.out::println);
	}

	@Test
	public void streamsAreLazy() throws Exception {
		Stream<String> stream = students
				.stream()
				.map(student -> {
					System.out.println("In map: " + student.getName());
					return student.getName();
				});
		System.out.println("Sleeping 2 seconds, nothing printed before this, only printed after collect");
		Thread.sleep(2000);
		stream.collect(Collectors.toList());
	}

	@Test
	public void streamsStopAtFirstInstance() {
		students.stream()
				.map(n -> {
					System.out.println("In map " + n.getName());
					return n.getName().toUpperCase();
				})
				.filter(upperName -> {
					System.out.println("in filter " + upperName);
					return upperName.startsWith("A");
				})
				.limit(2)
				.collect(Collectors.toList());
	}

	@Test
	public void streamMap() {
		Stream<Student> studentStream = students.stream();
		Stream<String> stringStream = studentStream.map(Student::getName);
		stringStream.forEach(System.out::print);
	}

	@Test
	public void streamSorting() {
		Map<String, Set<String>> mapsOfSets = new HashMap<>();
		Set<String> set1 = new HashSet<>(), set2 = new HashSet<>(), set3 = new HashSet<>(), set4 = new HashSet<>();
		set1.add("1");set1.add("2");set1.add("3");
		set2.add("1");set2.add("2");set2.add("3");set2.add("4");
		set3.add("1");set3.add("2");
		set4.add("1");set4.add("2");set4.add("3");set4.add("4");set4.add("5");
		mapsOfSets.put("A1", set1);mapsOfSets.put("A2", set2);mapsOfSets.put("A3", set3);mapsOfSets.put("A4", set4);
		LinkedHashMap<String, Set<String>> collect = mapsOfSets.entrySet().stream()
//				.sorted((e1, e2) -> e1.getValue().size() - e2.getValue().size())
				.sorted(Map.Entry.<String, Set<String>>comparingByValue((e1, e2) -> e2.size() - e1.size()))
//				.skip(2)
				.limit(2)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		collect.forEach((k, v) -> {
			System.out.println(k + "~" + v);
		});
	}
}
