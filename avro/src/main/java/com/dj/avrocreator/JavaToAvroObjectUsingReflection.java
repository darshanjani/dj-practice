package com.dj.avrocreator;

import com.dj.model.Actual;
import com.dj.model.ModelCreator;
import com.dj.model.avro.ActualAvro;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.joda.time.LocalDate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static com.dj.avrocreator.AvroSchemaCreator.MODEL_PACKAGE;

public class JavaToAvroObjectUsingReflection {
	public static void main(String[] args) throws Exception {
		new JavaToAvroObjectUsingReflection().startProcessing();
	}

	public void startProcessing() throws Exception {
		Actual actual = ModelCreator.createActual();
		ActualAvro actualAvro = deepCopy(actual, ActualAvro.class);
		System.out.println(actual);
		System.out.println(actualAvro);
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
		for (Method getMethod : methods) {
			if (isGetter(getMethod)) {
				Object value = MethodUtils.invokeExactMethod(copyFrom, getMethod.getName());
				String methodName = getMethod.getName();
//				System.out.println(methodName + "~" + value);
				String setterMethod = getSetter(methodName);
				setValue(targetObject, targetFieldMap.get(setterMethod), value);
			}
		}
		return targetObject;
	}

	private <T> void setValue(T targetObject, Method setterMethod, Object value) throws Exception {
		if (setterMethod.getParameterTypes().length > 1) {
			System.out.println("Setter method contains more than params: " + setterMethod.toString());
		}
		Class<?> aClass = setterMethod.getParameterTypes()[0];
		if (aClass.isAssignableFrom(String.class)
				|| aClass.isAssignableFrom(Integer.class)
				|| aClass.isAssignableFrom(Long.class)
				|| aClass.isAssignableFrom(Float.class)
				|| aClass.isAssignableFrom(Double.class)
				|| aClass.isAssignableFrom(Boolean.class)
		) {
			setterMethod.invoke(targetObject, value);
		} else if (aClass.isAssignableFrom(LocalDate.class)) {
			if (value instanceof Date) {
				setterMethod.invoke(targetObject, new LocalDate((Date)value));
			}
		} else if (aClass.isAssignableFrom(List.class)) {
			if (value instanceof List) {
				List listOfValues = (List) value;
				List targetValues = new ArrayList();
				for (Object leftObject : listOfValues) {
					String classOfObject = leftObject.getClass().getName();
					System.out.println("Left object: " + leftObject);
					System.out.println("Class of object: " + classOfObject);
					String avroClassName = MODEL_PACKAGE + "." + classOfObject.substring(classOfObject.lastIndexOf(".") + 1) + "Avro";
					Class<?> targetClass = Class.forName(avroClassName);
//					System.out.println(targetClass.getName());
					Object target = deepCopy(leftObject, targetClass);
					targetValues.add(target);
				}
				setterMethod.invoke(targetObject, targetValues);
			} else {
				System.out.println("Getter did not return value as List");
			}
		} else {
			String classOfObject = value.getClass().getName();
			String avroClassName = MODEL_PACKAGE + "." + classOfObject.substring(classOfObject.lastIndexOf(".") + 1) + "Avro";
			Class<?> targetClass = Class.forName(avroClassName);
			System.out.println(targetClass.getName());
			Object target = deepCopy(value, targetClass);
			setterMethod.invoke(targetObject, target);
			System.out.println("Setting value for " + setterMethod.toString());
		}
//		System.out.println(setterMethod.getParameterTypes() + " ~ " + value);
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
