package edu.javacodestream.cache.common;

/**
 * Created by Sandip on 13-08-2016.
 *
 * The implementor knows to which cache it belongs to and under which key.
 */
public interface CacheAware {

    public Cache getCache();

    public void setCache(Cache container);

    public Object getKey();

    public void setKey(Object key);
}
