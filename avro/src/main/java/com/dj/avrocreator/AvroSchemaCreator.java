package com.dj.avrocreator;

import com.dj.model.Actual;
import com.dj.model.Composition;
import com.dj.model.LargeObject;
import com.dj.model.SmallConsumerObject;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AvroSchemaCreator {

	public static final String MODEL_PACKAGE = "com.dj.model.avro";

	public static void main(String[] args) throws Exception {
		new AvroSchemaCreator().startProcessing();
	}

	public void startProcessing() throws Exception {
		Class classToInspect = SmallConsumerObject.class;

		File newAvroSchemaFile = createNewAvroSchemaFile(classToInspect);
		JSONObject jsonObject = new JSONObject();
		String passedClass = classToInspect.getName();
		jsonObject.put("namespace", MODEL_PACKAGE);
		jsonObject.put("type", "record");
		jsonObject.put("name", passedClass.substring(passedClass.lastIndexOf(".")+1) + "Avro");
		List<JSONObject> fields = new ArrayList<>();

		List<Field> actualFields = FieldUtils.getAllFieldsList(classToInspect);
		for (int fieldIndex = 0; fieldIndex < actualFields.size(); fieldIndex++) {
			Field field = actualFields.get(fieldIndex);
			if (Modifier.isFinal(field.getModifiers())) {
				System.out.println("Skipping final field: " + field.getName());
				continue;
			}
			fields.add(getFieldType(field));
		}
		jsonObject.put("fields", fields);
		String avroSchemaJson = jsonObject.toString(2);
		FileUtils.write(newAvroSchemaFile, avroSchemaJson, StandardCharsets.UTF_8.name());
		System.out.println("Refer generated schema at: " + newAvroSchemaFile.getAbsolutePath());
//		System.out.println(avroSchemaJson);
	}

	private JSONObject getFieldType(Field field) {
		JSONObject eachField = new JSONObject();
		eachField.put("name", field.getName());

		String fieldName = field.getType().getName();
		Object type = null;
		switch (fieldName) {
			case "long":
			case "double":
			case "float":
			case "boolean":
			case "int":
				type = fieldName; break;
			case "java.lang.String":
				type = "string"; break;
			case "java.lang.Integer":
				type = "int"; break;
			case "java.lang.Long":
				type = "long"; break;
			case "java.lang.Float":
				type = "float"; break;
			case "java.lang.Double":
				type = "double"; break;
			case "java.util.Date":
				JSONObject dateType = new JSONObject();
				dateType.put("type", "long");
				dateType.put("logicalType", "timestamp-millis");
				type = dateType;
				break;
			case "java.util.List":
				JSONObject arrayType = new JSONObject();
				arrayType.put("type", "array");
				Type genericType = field.getGenericType();
				if (genericType instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType)genericType;
					Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
					Class actualTypeArgument = (Class) actualTypeArguments[0];
					arrayType.put("items", actualTypeArgument.getSimpleName() + "Avro");
				} else {
					arrayType.put("items", genericType.getTypeName());
				}
				type = arrayType;
				break;
			default:
				String clazz = field.getType().getName();
				type = clazz.substring(clazz.lastIndexOf(".")+1) + "Avro";
				System.out.println("Default type being converted to Avro, please generate avsc manually: " + type);
		}

		eachField.put("type", type);
		return eachField;
	}

	private File createNewAvroSchemaFile(Class clazz) throws Exception {
		String filename = "src/main/avro/" + (clazz.getName().replaceAll("\\.", "_")) + ".avsc";
		File f = new File(filename);
		if (f.exists()) {
			System.out.println(filename + " already exists... Skipping");
		} else {
			f.createNewFile();
		}
		return f;
	}

}
