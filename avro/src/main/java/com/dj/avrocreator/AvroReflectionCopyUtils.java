package com.dj.avrocreator;

import com.dj.model.Actual;
import com.dj.model.ModelCreator;
import com.dj.model.avro.ActualAvro;
import org.apache.avro.util.Utf8;
import org.joda.time.DateTime;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class AvroReflectionCopyUtils {
	public static final Map<String, Map<String, Method>> classToGetterMethodMapping = new HashMap<>();
	public static final Map<String, Map<String, Method>> classToSetterMethodMapping = new HashMap<>();

	public static void main(String[] args) throws Exception {
		new AvroReflectionCopyUtils().startProcessing();
	}

	public void startProcessing() throws Exception {
		Actual actual = ModelCreator.createActual();
		ActualAvro actualAvro = deepCopy(actual, ActualAvro.class);
		System.out.println(actual);
		System.out.println(actualAvro);
	}

	public <T> T deepCopy(Object copyFrom, Class<T> clazz) throws Exception {
		Collection<Method> getterMethods = retrieveGetMethodsMap(copyFrom);
		if (getterMethods.isEmpty()) {
			System.err.println("Object of type: " + copyFrom.getClass().getName() + " does not contain any GETTER methods");
			return null;
		}
		T targetObject = clazz.newInstance();
		Map<String, Method> targetFieldMap = getTargetClassSetterMap(clazz);
		for (Method getMethod : getterMethods) {
			Object value = getMethod.invoke(copyFrom);
			String setterMethodName = getSetterMethodNameFromGetterMethod(getMethod);
			Method setterMethod = targetFieldMap.get(setterMethodName);
			if (setterMethod != null) {
				setValue(targetObject, setterMethod, value);
			} else {
				System.out.println("Corresponding setter not found in Target class for getter: " + getMethod.toString());
			}
		}
		return targetObject;
	}

	private String getSetterMethodNameFromGetterMethod(Method getMethod) {
		String methodName = getMethod.getName();
		if (methodName.startsWith("get")) return "set" + methodName.substring(3);
		if (methodName.startsWith("is")) return "set" + methodName.substring(2);
		return methodName;
	}

	private Collection<Method> retrieveGetMethodsMap(Object copyFrom) throws Exception {
		if (!classToGetterMethodMapping.containsKey(copyFrom.getClass().getName())) {
			Map<String, Method> getMethodMap = new HashMap<>();
			Method[] methods = copyFrom.getClass().getMethods();
			for (Method getMethod : methods) {
				if (isGetter(getMethod)) {
					getMethodMap.put(getMethod.getName(), getMethod);
				}
			}
			classToGetterMethodMapping.put(copyFrom.getClass().getName(), getMethodMap);
		}
		return classToGetterMethodMapping.get(copyFrom.getClass().getName()).values();
	}

	public Map<String, Method> getTargetClassSetterMap(Class targetClass) throws Exception {
		String targetClassName = targetClass.getName();
		if (!classToSetterMethodMapping.containsKey(targetClassName)) {
			Map<String, Method> setMethodMap = new HashMap<>();
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (isSetter(method)) {
					setMethodMap.put(method.getName(), method);
				}
			}
			classToSetterMethodMapping.put(targetClassName, setMethodMap);
		}
		if (classToSetterMethodMapping.get(targetClassName).isEmpty()) {
			throw new Exception("Target class: " + targetClassName + " does not contain any setter methods");
		}
		return classToSetterMethodMapping.get(targetClassName);
	}

	private <T> void setValue(T targetObject, Method setterMethod, Object value) throws Exception {
		Class<?>[] setterMethodParameterTypes = setterMethod.getParameterTypes();
		if (setterMethodParameterTypes == null) {
			System.out.println("Skipping setter method since it has no parameters: " + setterMethod);
			return;
		}
		if (setterMethodParameterTypes.length > 1) {
			System.out.println("Setter method contains more than params: " + setterMethod.toString());
			return;
		}
		Class<?> aClass = setterMethodParameterTypes[0];
		if (aClass.isAssignableFrom(CharSequence.class)
				|| aClass.isAssignableFrom(String.class)
				|| aClass.isAssignableFrom(Integer.class)
				|| aClass.isAssignableFrom(Long.class)
				|| aClass.isAssignableFrom(Float.class)
				|| aClass.isAssignableFrom(Double.class)
				|| aClass.isAssignableFrom(Boolean.class)
				|| aClass.getName().equals("boolean")
				|| aClass.getName().equals("float")
				|| aClass.getName().equals("double")
				|| aClass.getName().equals("long")
				|| aClass.getName().equals("int")
				) {
			if (value instanceof Utf8) {
				value = new String(((Utf8) value).getBytes());
				setterMethod.invoke(targetObject, value);
			} else {
				setterMethod.invoke(targetObject, value);
			}

		} else if (aClass.isAssignableFrom(DateTime.class)) {
			if (value instanceof Date) {
				setterMethod.invoke(targetObject, new DateTime(value));
			}
		} else if (aClass.isAssignableFrom(Date.class)) {
			if (value instanceof DateTime) {
				System.out.println(value);
				setterMethod.invoke(targetObject, ((DateTime) value).toDate());
			}
		} else if (aClass.isAssignableFrom(List.class)) {
			if (value instanceof List) {
				List listOfValues = (List) value;
				List targetValues = new ArrayList();
				Class targetClass = Object.class;
				Type[] genericInterfaces = setterMethod.getGenericParameterTypes();
				for (Type genericType : genericInterfaces) {
					if (genericType instanceof ParameterizedType) {
						ParameterizedType parameterizedType = (ParameterizedType) genericType;
						Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
						targetClass = (Class) actualTypeArguments[0];
						System.out.println("List's Generic Type: " + targetClass.getName());
					}
				}
				for (Object leftObject : listOfValues) {
					targetValues.add(deepCopy(leftObject, targetClass));
				}
				setterMethod.invoke(targetObject, targetValues);
			} else {
				System.out.println("Getter did not return value as List");
			}
		} else {
			System.out.println("Setting non-standard class: " + aClass.getName());
			Object target = deepCopy(value, aClass);
			setterMethod.invoke(targetObject, target);
		}
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
}
