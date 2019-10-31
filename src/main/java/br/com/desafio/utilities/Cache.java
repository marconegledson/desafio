package br.com.desafio.utilities;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("serial")
public class Cache<K, V> extends LinkedHashMap<K, V> {
	
	public Cache() {
		super(10, 0.75f, true);
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return super.size() > 10;
	}

}
