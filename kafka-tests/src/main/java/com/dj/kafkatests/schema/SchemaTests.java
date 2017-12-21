package com.dj.kafkatests.schema;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchemaTests {
	private final static MediaType SCHEMA_CONTENT =
			MediaType.parse("application/vnd.schemaregistry.v1+json");

	private static final String SCHEMA_REGISTRY_BASE = "http://localhost:8081/";
	private static final String TOP_LEVEL_CONFIG = "config/";
	private static final String SUBJECTS_LIST = "subjects/";
	private static final String SCHEMAS_LIST = "schemas/";
	private static final String SCHEMA_NAME = "employees-value";

	private final static String EMPLOYEE_SCHEMA_V1 = "{\n" +
			"  \"schema\": \"" +
			"  {" +
			"    \\\"type\\\": \\\"record\\\"," +
			"    \\\"name\\\": \\\"Employee\\\"," +
			"    \\\"namespace\\\": \\\"com.dj.kafkatests.model.employeev1\\\"," +
			"    \\\"fields\\\": [" +
			"        {\\\"name\\\": \\\"fName\\\", \\\"type\\\": \\\"string\\\"}," +
			"        {\\\"name\\\": \\\"lName\\\", \\\"type\\\": \\\"string\\\"}," +
//			"        {\\\"name\\\": \\\"age\\\",  \\\"type\\\": \\\"int\\\", \\\"default\\\": -1 }," +
			"        {\\\"name\\\": \\\"age\\\",  \\\"type\\\": \\\"int\\\" }," +
			"        {\\\"name\\\": \\\"phoneNumber\\\",  \\\"type\\\": \\\"string\\\"}" +
			"    ]" +
			"  }\"" +
			"}";

	private final static String EMPLOYEE_SCHEMA_V2 = "{ " +
			"  \"schema\": \"" +
			"{  \\\"namespace\\\": \\\"com.dj.kafkatests.model.employeev2\\\","+
			"  \\\"type\\\": \\\"record\\\", "+
			"  \\\"name\\\": \\\"Employee\\\", "+
			"  \\\"fields\\\": [ "+
			"      {\\\"name\\\": \\\"fName\\\", \\\"type\\\": \\\"string\\\"}, "+
			"      {\\\"name\\\": \\\"lName\\\", \\\"type\\\": \\\"string\\\"}, "+
			"      {\\\"name\\\": \\\"phoneNumber\\\",  \\\"type\\\": \\\"string\\\"} "+
			"  ] "+
			"}\"" +
			"}";

	final OkHttpClient client = new OkHttpClient();

	@Test
	public void a_showTopLevelConfig() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + TOP_LEVEL_CONFIG)
				.build();
		String output = client.newCall(request).execute().body().string();
		System.out.println("Top Level config: \n" + output);
	}

	@Test
	public void b_setTopLevelConfig() throws Exception {
		Request request = new Request.Builder()
				.put(RequestBody.create(SCHEMA_CONTENT, "{\"compatibility\": \"backward\"}"))
				.url(SCHEMA_REGISTRY_BASE + TOP_LEVEL_CONFIG)
				.build();
		String output = client.newCall(request).execute().body().string();
		System.out.println("Setting Top Level config to 'backward', set: \n" + output);
	}

	@Test
	public void c_listAllSchemas() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST)
				.build();
		String output = client.newCall(request).execute().body().string();
		System.out.println("All schemas in registry: \n" + output);
	}

	@Test
	public void d_postVersion_1_OfEmployeeSchema() throws Exception {
		System.out.println("Schema being registered: \n" + EMPLOYEE_SCHEMA_V1);
		Request request = new Request.Builder()
				.post(RequestBody.create(SCHEMA_CONTENT, EMPLOYEE_SCHEMA_V1))
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME + "/versions")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	public void e_showAllVersionsOfEmployee() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME + "/versions/")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	public void f_showVersion2OfEmployee() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME + "/versions/4")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	public void g_showSchemaWithId3() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + SCHEMAS_LIST + "ids/3")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	public void h_showSchemaWithId1() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + SCHEMAS_LIST + "ids/1")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	public void i_showLatestVersionOfEmployee() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME + "/versions/latest")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	public void j_checkIfEmployeeSchemaIsRegistered() throws Exception {
		Request request = new Request.Builder()
				.post(RequestBody.create(SCHEMA_CONTENT, EMPLOYEE_SCHEMA_V1))
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME)
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	public void k_showEmployeeConfigLevel() throws Exception {
		Request request = new Request.Builder()
				.url(SCHEMA_REGISTRY_BASE + TOP_LEVEL_CONFIG + SCHEMA_NAME)
				.build();
		String output = client.newCall(request).execute().body().string();
		System.out.println("Employee Level config: \n" + output);
	}

	@Test
	public void l_setEmployeeConfigLevel() throws Exception {
		Request request = new Request.Builder()
				.put(RequestBody.create(SCHEMA_CONTENT, "{\"compatibility\": \"backward\"}"))
				.url(SCHEMA_REGISTRY_BASE + TOP_LEVEL_CONFIG + SCHEMA_NAME)
				.build();
		String output = client.newCall(request).execute().body().string();
		System.out.println("Employee Level config set: \n" + output);
	}

	@Test
	public void m_postVersion_2_OfEmployeeSchema() throws Exception {
		Request request = new Request.Builder()
				.post(RequestBody.create(SCHEMA_CONTENT, EMPLOYEE_SCHEMA_V2))
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME + "/versions")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	@Ignore
	public void deleteLatestVersionOfEmployeeSchema() throws Exception {
		Request request = new Request.Builder()
				.delete()
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME + "/versions/latest")
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}

	@Test
	@Ignore
	public void deleteAllVersionsOfEmployeeSchema() throws Exception {
		Request request = new Request.Builder()
				.delete()
				.url(SCHEMA_REGISTRY_BASE + SUBJECTS_LIST + SCHEMA_NAME)
				.build();

		String output = client.newCall(request).execute().body().string();
		System.out.println(output);
	}
}
