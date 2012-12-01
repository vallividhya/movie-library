package edu.sjsu.videolibrary.db;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.sjsu.videolibrary.util.Config;

public class Cache {

	ICacheStorage cacheStorage = null;
	static Cache INSTANCE = null;
	static boolean cacheEnabled = edu.sjsu.videolibrary.util.Config.cacheEnabled;

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

	public void invalidatePrefix(String keyPrefix) {
		cacheStorage.invalidatePrefix(keyPrefix);
	}

	static private interface ICacheStorage {
		public void put( String key, Object value);
		public Object get(String key);
		public Object invalidate(String key);
		public void invalidatePrefix(String key);
	};

	static private class DisabledCacheStorage implements ICacheStorage {
		public void put( String key, Object value) {}
		public Object get(String key){ return null; }
		public Object invalidate(String key) {return null;}
		public void invalidatePrefix(String key) {}
	}

	static private class LRUCacheStorage implements ICacheStorage {
		static Map<String, Object> cacheStorage =
				new CacheMap();

		public void put( String key, Object value ) {
			synchronized (cacheStorage) {
				cacheStorage.put(key, value);
			}
		}

		public Object get(String key) {
			synchronized (cacheStorage) {
				if( cacheStorage.containsKey(key) ) {
					Object value = cacheStorage.remove(key);
					cacheStorage.put(key, value);
					return value;
				}
			}
			return null;
		}


		public Object invalidate(String key) {
			synchronized (cacheStorage) {
				return cacheStorage.remove(key);
			}
		}


		public void invalidatePrefix(String keyPrefix) {
			synchronized (cacheStorage) {
				Set<String> keySet = cacheStorage.keySet();
				for( String key : keySet ) {
					if( key.startsWith(keyPrefix)) {
						cacheStorage.remove(key);
					}
				}
			}
		}
	}

	static private class CacheMap extends LinkedHashMap<String,Object> {	
		private static final long serialVersionUID = -5238266812387935265L;
		static final int MAX_SIZE = Config.cacheSize;
		protected boolean removeEldestEntry(Map.Entry<String,Object> eldest) {
			return size() > MAX_SIZE;
		}
	};
}
