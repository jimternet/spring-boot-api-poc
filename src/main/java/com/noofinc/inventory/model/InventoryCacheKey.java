package com.noofinc.inventory.model;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class InventoryCacheKey implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		
		return null;
	}

}
