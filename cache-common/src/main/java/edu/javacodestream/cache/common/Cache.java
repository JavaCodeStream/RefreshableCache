package edu.javacodestream.cache.common;

import java.util.Map;

/**
 * Created by Sandip on 13-08-2016.
 *
 * An abstraction of cache container. Implementors could back it with EHCache for instance
 */
public interface Cache {

    /**
     * store an object inside the cache container
     * @param key
     * @param value
     */
    public void put(Object key, CacheEntry value);

    /**
     * Retrieves a specific entry from the cache.
     * <p>
     * In case where the entry is expired in the cache, then
     * <p>
     *   If the entry is an object that does not implement the <code>Refreshable</code>
     *   interface, then the original object will returned even it is stale.
     * <p>
     *   If the entry is an <code>RefreshableCacheEntry</code> with a <b>synchronous</b> loader, then
     *   the entry will be refreshed first and then the latest result will be returned.
     * <p>
     *   If the entry is an <code>RefreshableCacheEntry</code> with a <b>Asynchronous</b> loader, then
     *   the stale content will be returned first and a background thread will kicked off to load the data
     *   synchronously using the <code>AsynchLoader</code> specified by the <code>RefreshableCacheEntry</code>
     *
     * @param key
     * @return
     */
    public CacheEntry get(Object key);

    /**
     * Removes a specific entry from the cache
     *
     * @param key
     */
    public void remove(Object key);

    /**
     * wipes out the entire cache
     */
    public void clear();

    /**
     * Returns a Map that keeps track of all the <code>RefreshableCacheEntry</code>
     *
     * @return
     */
    public Map getLoadingStatusMap();
}
