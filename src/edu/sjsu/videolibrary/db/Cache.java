package edu.sjsu.videolibrary.db;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cache {

	ICacheStorage cacheStorage = null;
	static Cache INSTANCE = null;
	static boolean cacheEnabled = true;
	
	private Cache(){
		if( cacheEnabled ) {
			cacheStorage = new LRUCacheStorage();
		} else {
			cacheStorage = new DisabledCacheStorage();
		}
	}
	
	synchronized static public Cache getInstance() {
		if( INSTANCE == null ) {
			INSTANCE = new Cache();
		}
		return INSTANCE;
	}
	
	public void put( String key, Object value) {
		cacheStorage.put(key, value);
	}
	public Object get(String key){ 
		return cacheStorage.get(key);
	}
	
	public Object invalidate(String key) {
		return cacheStorage.invalidate(key);
	}
	
	static private interface ICacheStorage {
		public void put( String key, Object value);
		public Object get(String key);
		public Object invalidate(String key);
	};
	
	static private class DisabledCacheStorage implements ICacheStorage {
		public void put( String key, Object value) {}
		public Object get(String key){ return null; }
		public Object invalidate(String key) {return null;}
	}
	
	static private class LRUCacheStorage implements ICacheStorage {
		static Map<String, Object> cacheStorage =
				Collections.synchronizedMap(new CacheMap());
		
		public void put( String key, Object value ) {
			cacheStorage.put(key, value);
		}
		
		public Object get(String key) {
			if( cacheStorage.containsKey(key) ) {
				Object value = cacheStorage.remove(key);
				cacheStorage.put(key, value);
				return value;
			}
			return null;
		}

		@Override
		public Object invalidate(String key) {
			return cacheStorage.remove(key);
		}
	}
	
	static private class CacheMap extends LinkedHashMap<String,Object> {	
		private static final long serialVersionUID = -5238266812387935265L;
		static final int MAX_SIZE = 100;
		protected boolean removeEldestEntry(Map.Entry<String,Object> eldest) {
	        return size() > MAX_SIZE;
	     }
	};
}
