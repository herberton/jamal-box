package br.com.cruz.jamal.common.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;
import br.com.cruz.jamal.common.to.Teste1;
import br.com.cruz.jamal.common.to.Teste2;

public class ReflectionHelper extends JamalHelper {

	
	private static final long serialVersionUID = 4184413189222960213L;
	
	public static void main(String[] args) {
		try {
			
			Teste1 teste = new Teste2();
			
			System.out.println(teste.getPublicFieldList());
			
			teste.set("t1", "teste1");
			
			teste.set("t5", "teste5");
			
			System.out.println(teste.get("t1", String.class));
			
			System.out.println(teste.get("t5", String.class));
			
		} catch (JamalException e) {
			e.printStackTrace();
		}
	}
	
	
	// get
	
	public static final <K, V> V get(@NotNull K object, @NotNull String fieldName, @NotNull Class<V> fieldType) throws JamalException {
		
		try {
			
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
	public static final <K, V> V get(@NotNull K object, @NotNull Field field, @NotNull Class<V> fieldType) throws JamalException {
		
		try {
			return (V) ReflectionHelper.executeMethod(object, ReflectionHelper.getGetterMethod(object.getClass(), field));
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("get", e);
		}
		
	}
	
	
	// set
	
	public static final <K, V> void set(@NotNull K object, @NotNull String fieldName, V fieldValue) throws JamalException {
		
		try {
			
			Field field = ReflectionHelper.getPublicField(object.getClass(), fieldName);
			
			if (field == null) {
				return;
			}
			
			ReflectionHelper.set(object, field, fieldValue);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
		
	}
	
	public static final <K, V> void set(@NotNull K object, @NotNull Field field, @NotNull V fieldValue) throws JamalException {
		
		try {
			ReflectionHelper.executeMethod(object, ReflectionHelper.getSetterMethod(object.getClass(), field), fieldValue);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
		
	}
	
	
	// executeMethod
	
	public static final Object executeMethod(@NotNull Object object, @NotNull Method method, Object... parameterArgumentArray) throws JamalException {
		
		try {
			return method.invoke(object, parameterArgumentArray);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeMethod", e);
		}

	}
	
	
	// getPublicFieldList
	
	public static final List<Field> getPublicFieldList(@NotNull Class<?> clazz) throws JamalException {
		
		try {
			
			List<Field> publicFieldList = new ArrayList<Field>();
			
			do {
				
				for (Field field : clazz.getDeclaredFields()) {
					
					if (ReflectionHelper.isPublicField(clazz, field)) {
						
						publicFieldList.add(field);
						
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
	
	public static final Field getPublicField(@NotNull Class<?> clazz, @NotNull String fieldName) throws JamalException {
		 try {
			
			for (Field publicField : ReflectionHelper.getPublicFieldList(clazz)) {
				if (publicField.getName().equals(fieldName)) {
					return publicField;
				}
			}
			 
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicField", e);
		}
			
		return null;
	}
	
	
	// isPublicField
	
	public static final <T> boolean isPublicField(@NotNull Class<T> clazz, @NotNull Field field) throws JamalException {

		try {
			
			return ReflectionHelper.getGetterMethod(clazz, field) != null;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isPublicField", e);
		}
	}
	
	
	// getGetterMethodName
	
	public static final String getGetterMethodName(@NotNull Field field) throws JamalException {

		try {
			
			return ReflectionHelper.getGetterMethodName(field.getName());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethodName", e);
		}
	}
	
	public static final String getGetterMethodName(@NotNull String fieldName) throws JamalException {

		try {
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return null;
			}
			
			return String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethodName", e);
		}
	}
	
	
	// getGetterMethod
	
	public static final <T> Method getGetterMethod(@NotNull Class<T> clazz, @NotNull Field field) throws JamalException {

		try {
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getGetterMethodName(field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethod", e);
		}
	}
	
	public static final <T> Method getGetterMethod(@NotNull Class<T> clazz, @NotNull String fieldName) throws JamalException {

		try {
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getGetterMethodName(fieldName));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethod", e);
		}
	}
	
	
	// getSetterMethodName
	
	public static final String getSetterMethodName(@NotNull Field field) throws JamalException {

		try {
			
			return ReflectionHelper.getSetterMethodName(field.getName());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethodName", e);
		}
	}
	
	public static final String getSetterMethodName(@NotNull String fieldName) throws JamalException {

		try {
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return null;
			}
			
			return String.format("set%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethodName", e);
		}
	}
	
	
	// getSetterMethod
	
	public static final <T> Method getSetterMethod(@NotNull Class<T> clazz, @NotNull Field field) throws JamalException {

		try {
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getSetterMethodName(field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethod", e);
		}
	}
	
	public static final <T> Method getSetterMethod(@NotNull Class<T> clazz, @NotNull String fieldName) throws JamalException {

		try {
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getSetterMethodName(fieldName));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethod", e);
		}
	}
	
	
	// findMethod
	
	public static final <T> Method findMethod(@NotNull Class<T> clazz, @NotNull String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		List<Method> foundMethodList = ReflectionHelper.findMethodList(clazz, methodName, parameterTypeArray);
		
		if (CollectionHelper.isNullOrEmpty(foundMethodList)) {
			return null;
		}
		
		return foundMethodList.get(0);
	}


	// findMethodList
	
	public static final <T> List<Method> findMethodList(@NotNull Class<T> clazz, @NotNull String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		List<Method> foundMethodList = new ArrayList<Method>();
		
		boolean isSearchByParameterType = !CollectionHelper.isNullOrEmpty(parameterTypeArray);
		
		try {
			
			for (Method method : clazz.getMethods()) {
				if (methodName.equals(method.getName())) {
					foundMethodList.add(method);
				}
			}
			
			
			if (isSearchByParameterType) {
				
				Iterator<Method> iterator = foundMethodList.iterator();
				while (iterator.hasNext()) {
					
					Method method = iterator.next();
					
					if (method.getParameterTypes().length != parameterTypeArray.length) {
						iterator.remove();
						continue;
					}
					
					for (int i = 0; i < method.getParameterTypes().length; i++) {
						if (parameterTypeArray[i] != method.getParameterTypes()[i]) {
							iterator.remove();
							continue;
						}
					}
					
				}
				
			}
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethodList", e);
		}
		
		return foundMethodList;
		
	}
}
