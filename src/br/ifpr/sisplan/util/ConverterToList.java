package br.ifpr.sisplan.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ConverterToList {
	
	public static <ClazzTarget> List<ClazzTarget> convertListMappedToList(List<Map> source, Class clazzTarget) {
		List<ClazzTarget> listCasted = new ArrayList<ClazzTarget>();
		for(Map map: threatKeysOnListMapped(source)) {
			listCasted.add((ClazzTarget) instantiateClass(map, clazzTarget));
		}
		
		return listCasted;
	}
	
	//Remove '_' from database column names
	private static List<Map> threatKeysOnListMapped(List<Map> source) {
		List<Map> newList = new ArrayList<Map>();
		Map newMap;
		for(Map<String, Object> map: source) {
			newMap = new HashMap();
			for(Map.Entry<String, Object> pair: map.entrySet()) {
				String key = pair.getKey().replace("_", "");
				newMap.put(key.toLowerCase(), pair.getValue());
			}
			newList.add(newMap);
		}
		return newList;
	}
	
	private static<ClazzTarget> ClazzTarget instantiateClass(Map mapValues, Class clazzTarget) {
		ClazzTarget instance = createInstance(clazzTarget);
		
		Field[] fields;
		
		while (!clazzTarget.isInstance(new Object())) {
			fields = clazzTarget.getDeclaredFields();
			for (Field field : fields) {
			    if (!Modifier.isStatic(field.getModifiers())) {
			    	Object value = mapValues.get(field.getName().toLowerCase());
			    	if(value != null)
			    		setField(field, value, instance, clazzTarget);
			    }
			}
			clazzTarget = clazzTarget.getSuperclass();
		}
		return instance;
	}
	
	private static void setField(Field field, Object value, Object instance, Class clazz) {
		Pattern regexp = Pattern.compile("set"+field.getName().toLowerCase() +"$", Pattern.CASE_INSENSITIVE);
    	for(Method m: clazz.getDeclaredMethods()) {
    		if(regexp.matcher(m.getName().toLowerCase()).find())
				try {
					m.invoke(instance, value);
					break;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	}
	}

	private static<ClazzTarget> ClazzTarget createInstance(Class clazzTarget) {
		ClazzTarget instance = null;
		try {
			Constructor constructor = clazzTarget.getConstructor();
			instance = (ClazzTarget)constructor.newInstance();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
}
