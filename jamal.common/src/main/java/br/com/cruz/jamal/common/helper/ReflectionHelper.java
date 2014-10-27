package br.com.cruz.jamal.common.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;

public final class ReflectionHelper extends JamalHelper {

	
	private static final long serialVersionUID = 4184413189222960213L;
	
	// get
	
	public static final <K, V> V get(K object, String fieldName, Class<V> fieldType) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(fieldName);
			ValidationHelper.notNull(fieldType);
			
			Field field = ReflectionHelper.getPublicField(object.getClass(), fieldName);
			
			if (field == null) {
				return null;
			}
			
			return ReflectionHelper.get(object, field, fieldType);
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("get", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static final <K, V> V get(K object, Field field, Class<V> fieldType) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(field);
			ValidationHelper.notNull(fieldType);
			
			return (V) ReflectionHelper.executeMethod(object, ReflectionHelper.getGetterMethod(object.getClass(), field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("get", e);
		}
		
	}
	
	
	// set
	
	public static final <K, V> void set(K object, String fieldName, V fieldValue) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(fieldName);
			
			Field field = ReflectionHelper.getPublicField(object.getClass(), fieldName);
			
			if (field == null) {
				return;
			}
			
			ReflectionHelper.set(object, field, fieldValue);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
		
	}
	
	public static final <K, V> void set(K object, Field field, V fieldValue) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(field);
			ValidationHelper.notNull(fieldValue);
			
			ReflectionHelper.executeMethod(object, ReflectionHelper.getSetterMethod(object.getClass(), field), fieldValue);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
		
	}
	
	
	// executeMethod
	
	public static final Object executeMethod(Object object, Method method, Object... parameterArgumentArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(method);
			
			return method.invoke(object, parameterArgumentArray);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeMethod", e);
		}

	}
	
	
	// getPublicFieldList
	
	public static final List<Field> getPublicFieldList(Class<?> clazz) throws JamalException {
		
		try {
			
			return ReflectionHelper.getPublicFieldList(clazz, null);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
		
	}
	
	private static final List<Field> getPublicFieldList(Class<?> clazz, String fieldName) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			List<Field> publicFieldList = new ArrayList<Field>();
			
			do {
				
				for (Field field : clazz.getDeclaredFields()) {
					
					if (ReflectionHelper.isPublicField(clazz, field)) {
						
						if (StringHelper.isNullOrEmpty(fieldName)) {
							publicFieldList.add(field);
							continue;
						}
						
						if (field.getName().equals(fieldName)) {
							publicFieldList.add(field);
							return publicFieldList;
						}
						
					}
					
				}
				
				clazz = clazz.getSuperclass();
				
			} while(clazz != null);
			
			return publicFieldList;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
		
	}
	
	
	// getPublicField
	
	public static final Field getPublicField(Class<?> clazz, String fieldName) throws JamalException {
		 
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(fieldName);
			
			List<Field> publicFieldList = ReflectionHelper.getPublicFieldList(clazz, fieldName);
			
			if (publicFieldList == null) {
				return null;
			}
			
			return publicFieldList.get(0);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicField", e);
		}
		
	}
	
	
	// isPublicField
	
	public static final <T> boolean isPublicField(Class<T> clazz, Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.getGetterMethod(clazz, field) != null;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isPublicField", e);
		}
	}
	
	
	// getGetterMethodName
	
	public static final String getGetterMethodName(Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.getGetterMethodName(field.getName());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethodName", e);
		}
	}
	
	public static final String getGetterMethodName(String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(fieldName);
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return null;
			}
			
			return String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethodName", e);
		}
	}
	
	
	// getGetterMethod
	
	public static final <T> Method getGetterMethod(Class<T> clazz, Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getGetterMethodName(field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethod", e);
		}
	}
	
	public static final <T> Method getGetterMethod(Class<T> clazz, String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(fieldName);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getGetterMethodName(fieldName));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethod", e);
		}
	}
	
	
	// getSetterMethodName
	
	public static final String getSetterMethodName(Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.getSetterMethodName(field.getName());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethodName", e);
		}
	}
	
	public static final String getSetterMethodName(String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(fieldName);
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return null;
			}
			
			return String.format("set%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethodName", e);
		}
	}
	
	
	// getSetterMethod
	
	public static final <T> Method getSetterMethod(Class<T> clazz, Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getSetterMethodName(field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethod", e);
		}
		
	}
	
	public static final <T> Method getSetterMethod(Class<T> clazz, String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(fieldName);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getSetterMethodName(fieldName));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethod", e);
		}
		
	}
	
	
	// findMethod
	
	public static final <T> Method findMethod(Class<T> clazz, String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(methodName);
			
			List<Method> foundMethodList = ReflectionHelper.findMethodList(clazz, 1, methodName, parameterTypeArray);
			
			if (CollectionHelper.isNullOrEmpty(foundMethodList)) {
				return null;
			}
			
			return foundMethodList.get(0);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethod", e);
		}
		
	}


	// findMethodList
	
	public static final <T> List<Method> findMethodList(Class<T> clazz, String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(methodName);
		
			return ReflectionHelper.findMethodList(clazz, -1, methodName, parameterTypeArray);
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethodList", e);
		}
		
	}
	
	
	private static final <T> List<Method> findMethodList(Class<T> clazz, int resultSize, String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		List<Method> foundMethodList = new ArrayList<Method>();
		
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(methodName);
			
			boolean isSearchByParameterType = !CollectionHelper.isNullOrEmpty(parameterTypeArray);
			
			for (Method method : clazz.getMethods()) {
				
				boolean isFindMethod = false;
				
				if (resultSize < 0) {
					isFindMethod = true;
				} else {
					isFindMethod = foundMethodList.size() < resultSize;
				}
				
				if (!isFindMethod) {
					return foundMethodList;
				}
				
				if (methodName.equals(method.getName())) {
					
					if (!isSearchByParameterType) {
						foundMethodList.add(method);
						continue;
					}
					
					if (method.getParameterTypes().length != parameterTypeArray.length) {
						continue;
					}
					
					for (int i = 0; i < method.getParameterTypes().length; i++) {
						if (parameterTypeArray[i] != method.getParameterTypes()[i]) {
							continue;
						}
					}
					
					foundMethodList.add(method);
					
				}
				
			}
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethodList", e);
		}
		
		return foundMethodList;
		
	}
}
