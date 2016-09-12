package edu.javacodestream.cache.common.impl;

import edu.javacodestream.cache.common.Cache;
import edu.javacodestream.cache.common.CacheEntry;
import edu.javacodestream.cache.common.RefreshableCacheEntry;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandip
 * Cache implementation backed by EHCache.
 *
 */
public class EHCache implements Cache{

    private static final Logger slf4jLogger = LoggerFactory.getLogger(EHCache.class);

    // name of the cache
    private String cacheName;

    private int timeToLiveSeconds;

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    CacheManager cacheManager = CacheManager.newInstance();

    private Map<String, String> loadingStatusMap = new HashMap<>();

    public String toString() {
        return "CacheName: " + this.cacheName;
    }

    public void put(Object key, CacheEntry entry) {
        slf4jLogger.debug("About to store the entry in the cache - key '" + toString() + "' value: " + entry);
        cacheManager.getCache(this.cacheName).put(new Element(key, entry));

        Element element = (Element)cacheManager.getCache(this.cacheName).get(key);
    }

    public CacheEntry get(Object key) {
        slf4jLogger.debug("About to retrieve the entry in the cache - key '" + toString() + "'");

        CacheEntry content = null;

        Element element = (Element)cacheManager.getCache(this.cacheName).get(key);
        slf4jLogger.debug("element: " + element);
        if(element == null) {
            return null;
        }

        content = (CacheEntry) element.getObjectValue();

        slf4jLogger.debug("isElementInCacheExpired: "+ isElementInCacheExpired(element));

        if(isElementInCacheExpired(element)) {
            slf4jLogger.debug("Got back Expired content for key: " + key + "'");

            if(content instanceof RefreshableCacheEntry) {

                RefreshableCacheEntry refreshableCacheEntry = (RefreshableCacheEntry) content;
                boolean synchLoad = false;

                refreshableCacheEntry.refresh(synchLoad);
            }
        }
        return content;
    }

    public void remove(Object key) {
        slf4jLogger.debug("About to remove the entry from the cache - key '" + toString() + "'");
        cacheManager.getCache(this.cacheName).remove(key);
    }

    public void clear() {
        slf4jLogger.debug("About to remove all cached items");
        cacheManager.getCache(this.cacheName).removeAll();
    }

    public Map getLoadingStatusMap() {
        return this.loadingStatusMap;
    }

    private boolean isElementInCacheExpired(Element element) {
        // The synchronized block prevents multiple threads from checking the expiration condition at the same time.
        synchronized (element) {
            long now = System.currentTimeMillis();
            return (now > element.getLatestOfCreationAndUpdateTime() + TimeUtil.toMillis((int) this.timeToLiveSeconds));
        }

    }
}
