package edu.javacodestream.cache.common;

/**
 * Created by Sandip on 13-08-2016.
 *
 * A wrapper class of a regular cache entry. An object wrapped by this class knows more about
 * its cache container, making it a self-contained object that can easily passed around.
 *
 */
public class CacheEntry implements CacheAware {

    // the key for the object being stored in the cache
    private Object key;

    // the object being stored
    private Object content;

    private Cache container;

    // to indicate an entry that is currently being loaded in a background thread
    public static final String STATUS_LOADING = "LOADING";

    // to indicate an entry that is already loaded in the cache
    // NOTE: this does not mean that the data is up-to-date
    public static final String STATUS_LOADED = "LOADED";

    public static final String PARAM_MAP_ID = "CacheEntry";

    public Cache getCache() {
        return container;
    }

    public void setCache(Cache container) {
        this.container = container;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void putInCache() {
        getCache().put(this.key, this);
    }
}
