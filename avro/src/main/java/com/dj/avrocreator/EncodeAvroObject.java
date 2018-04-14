package com.dj.avrocreator;

import com.dj.model.Actual;
import com.dj.model.ModelCreator;
import com.dj.model.avro.ActualAvro;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EncodeAvroObject {
	public static void main(String[] args) throws Exception {
		new EncodeAvroObject().startProcessing();
	}

	public void startProcessing() throws Exception {
		Actual actual = ModelCreator.createActual();
		ActualAvro actualAvro = deepCopy(actual, ActualAvro.class);
	}

	public <T> T deepCopy(Object copyFrom, Class<T> clazz) throws Exception {
		T targetObject = clazz.newInstance();
		Map<String, Method> targetFieldMap = getTargetFieldsMap(clazz);
		Class classToInspect = copyFrom.getClass();
		List<Field> actualFields = FieldUtils.getAllFieldsList(classToInspect);
		for (int fieldIndex = 0; fieldIndex < actualFields.size(); fieldIndex++) {
			Field field = actualFields.get(fieldIndex);
			if (Modifier.isFinal(field.getModifiers())) {
				System.out.println("Skipping final field: " + field.getName());
				continue;
			}
//			System.out.println(field.getName() + "~~" + FieldUtils.readField(field, copyFrom, true));
		}
		Method[] methods = copyFrom.getClass().getMethods();
		for (Method method : methods) {
			if (isGetter(method)) {
				Object value = MethodUtils.invokeExactMethod(copyFrom, method.getName());
				String methodName = method.getName();
				System.out.println(methodName + "~" + value);
				String setterMethod = getSetter(methodName);
//				setValue(targetObject, setterMethod, value);
				targetFieldMap.get(setterMethod).invoke(targetObject, value);
			}
		}
		System.out.println(targetObject);
		return targetObject;
	}

	private <T> void setValue(T targetObject, String setterMethod, Object value) throws Exception {
		MethodUtils.invokeExactMethod(targetObject, setterMethod, value);
	}

	public Map<String, Method> getTargetFieldsMap(Class targetClass) throws Exception {
		Map<String, Method> methodMap = new HashMap<>();
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (isSetter(method)) {
				methodMap.put(method.getName(), method);
			}
		}
		return methodMap;
	}

	public static boolean isSetter(Method method) {
		return Modifier.isPublic(method.getModifiers()) &&
				method.getReturnType().equals(void.class) &&
				method.getParameterTypes().length == 1 &&
				method.getName().matches("^set[A-Z].*");
	}

	public static boolean isGetter(Method method) {
		if (Modifier.isPublic(method.getModifiers()) &&
				method.getParameterTypes().length == 0) {
			if (method.getName().equals("getClass"))
				return false;
			if (method.getName().matches("^get[A-Z].*") &&
					!method.getReturnType().equals(void.class))
				return true;
			if (method.getName().matches("^is[A-Z].*") &&
					method.getReturnType().equals(boolean.class))
				return true;
		}
		return false;
	}

	public static String getSetter(String methodName) {
		if (methodName.startsWith("get")) return "set" + methodName.substring(3);
		if (methodName.startsWith("is")) return "set" + methodName.substring(2);
		return methodName;
	}
}
