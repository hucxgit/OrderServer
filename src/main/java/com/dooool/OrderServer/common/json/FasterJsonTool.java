package com.dooool.OrderServer.common.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import java.io.IOException;
import java.util.*;

/**
 * @author chen
 * 
 */
public class FasterJsonTool {
	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.registerModule(new AfterburnerModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	}

	public static String writeListValueAsString(Collection<? extends com.dooool.OrderServer.common.json.Jsonable> jsonableList) {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		for (com.dooool.OrderServer.common.json.Jsonable jsonable : jsonableList) {
			list.add(jsonable.getMap4Json());
		}
		return writeValueAsString(list);
	}

	public static String writeValueAsString(com.dooool.OrderServer.common.json.Jsonable jsonable) {
		return writeValueAsString(jsonable.getMap4Json());
	}

	/** JSON序列化 */
	public static String writeValueAsString(Object obj) {
		if (obj instanceof com.dooool.OrderServer.common.json.Jsonable) {
			return writeValueAsString((Jsonable) obj);
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException ex) {
			throw new IllegalArgumentException(ex);
		} catch (JsonMappingException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	/** JSON反序列化 */
	public static <T> T readValue(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException ex) {
			throw new IllegalArgumentException(ex);
		} catch (JsonMappingException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	/** JSON反序列化 */
	public static ArrayList<Map<String, Object>> readValue2List(String json) {
		ArrayList<Map<String, Object>> list = null;
		try {
			list = mapper.readValue(json,
					TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Map.class));
		} catch (JsonParseException ex) {
			throw new IllegalArgumentException(ex);
		} catch (JsonMappingException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
		return list;
	}

	/** JSON反序列化 */
	public static <T> ArrayList<T> readValue2List(String json, Class<T> clazz) {
		try {
			return mapper
					.readValue(json, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz));
		} catch (JsonParseException ex) {
			throw new IllegalArgumentException(ex);
		} catch (JsonMappingException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	/** JSON反序列化 */
	public static <T> ArrayList<T> readValue2List(String json, TypeReference<List<T>> typeReference) {
		try {
			return mapper.readValue(json, typeReference);
		} catch (JsonParseException ex) {
			throw new IllegalArgumentException(ex);
		} catch (JsonMappingException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	public static <K, V> Map<K, V> readValue2Map(String json, Class<K> keyClazz, Class<V> valueClazz) {
		try {
			return mapper.readValue(json,
					TypeFactory.defaultInstance().constructMapType(Map.class, keyClazz, valueClazz));
		} catch (JsonParseException ex) {
			throw new IllegalArgumentException(ex);
		} catch (JsonMappingException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@SuppressWarnings("deprecation")
	public static <T, K> T readValue(String json, Class<T> clazz, Class<K> parameterClasses) {
		try {
			return mapper.readValue(json, TypeFactory.defaultInstance()
					.constructParametricType(clazz, parameterClasses));
		} catch (JsonParseException ex) {
			throw new IllegalArgumentException(ex);
		} catch (JsonMappingException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
}