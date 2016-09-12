package edu.javacodestream.cache.common.impl;

import edu.javacodestream.cache.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Sandip
 *
 */
public class AsynchCacheAwareLoader implements CacheAware, AsynchLoader {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(AsynchCacheAwareLoader.class);

    // Cache container where to store the loaded content
    private Cache cache;

    // key for which to store the loaded content
    private Object key;

    // an asynchronous loader
    private Loader loader;

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        if(cache == null)
            throw new IllegalArgumentException("Cache can not be NULL");
        this.cache = cache;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        if(key == null)
            throw new IllegalArgumentException("Cache key can not be NULL");
        this.key = key;
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        if(loader == null)
            throw new IllegalArgumentException("loader can not be NULL");
        this.loader = loader;
    }

    public Object load(Map params) {
        return loader.load(params);
    }

    public void postLoad(Object loadedValue, Map postLoadParams) {

        if(slf4jLogger.isTraceEnabled())
            slf4jLogger.trace("Start Post-load Processing... for key: '" + key + "'.");

        if(postLoadParams == null)
            throw new IllegalArgumentException("CacheEntry Object is expected in PostLoadParam, However the Map is NULL");

        CacheEntry entry = (CacheEntry) postLoadParams.get(CacheEntry.PARAM_MAP_ID);

        if(entry == null)
            throw new IllegalArgumentException("CacheEntry can not be NULL at this position.");

        entry.setContent(loadedValue);

        // entry needs to be re-put/replaced into the cache to reset the refresh period.
        cache.put(key, entry);

        if(slf4jLogger.isTraceEnabled())
            slf4jLogger.trace("End of post-loaf processing .. for key: '" + key + "'");
    }

}
