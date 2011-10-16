package com.usemodj.forum.service;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserMetaServiceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUpdateUserMetaSqlSessionLongStringMapOfStringObject() {
		Map metaValue = new HashMap();
		metaValue.put("member", true	);
		
		Gson gson = new Gson();
		Type listType = new TypeToken<Map<String, Boolean>>() {}.getType();
		String json = gson.toJson(metaValue, listType);
		System.out.println("-- json: "+ json);
		// Deserialize : 
		Map metaValue2 = gson.fromJson(json, listType);
		System.out.println("-- Deserialize: "+ metaValue2.toString());
	}
	@Test
	public void testGson(){
		Map metaValue = new HashMap();
		metaValue.put("member", "true"	);
		
		Gson gson = new Gson();
		Type listType = new TypeToken<Map<String, String>>() {}.getType();
		String json = gson.toJson(metaValue);
		System.out.println("-- json: "+ json);
		// Deserialize : 
		Map metaValue2 = gson.fromJson(json, listType);
		System.out.println("-- Deserialize: "+ metaValue2.toString());
		System.out.println("-- map member: "+ metaValue2.get("member"));
	}
}
