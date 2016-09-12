package edu.javacodestream.cache.common.util;

import edu.javacodestream.cache.common.AsynchLoader;
import edu.javacodestream.cache.common.CacheEntry;
import edu.javacodestream.cache.common.Loader;
import edu.javacodestream.cache.common.RefreshableCacheEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sandip
 *
 * This thread is responsible for refreshing the <code>RefreshableCacheEntry</code> with <code>AsynchLoader</code>
 */
public class LoadingThread extends Thread {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(LoadingThread.class);

    //Cache entry to refresh
    private RefreshableCacheEntry cacheEntry;

    public RefreshableCacheEntry getCacheEntry() {
        return cacheEntry;
    }

    public void setCacheEntry(RefreshableCacheEntry cacheEntry) {
        if(cacheEntry == null)
            throw new IllegalArgumentException("Cache Entry can not be NULL");

        Loader loader = cacheEntry.getLoader();
        if(loader == null)
            throw new IllegalArgumentException("Loader Can not be NULL");

        if(!(loader instanceof AsynchLoader))
            throw new IllegalArgumentException("AsynchLoader is expected, but got " + loader + ".");

        this.cacheEntry = cacheEntry;
    }

    public LoadingThread(RefreshableCacheEntry cacheEntry) {
        setCacheEntry(cacheEntry);
    }

    public LoadingThread(String name, RefreshableCacheEntry cacheEntry) {
        super(name);
        setCacheEntry(cacheEntry);
    }

    /**
     * It is notIf the going the check the status whether it is in LOADING, as it is responsibility of the
     * refresher to check and trigger the thread.
     */
    public void run() {
        if(slf4jLogger.isTraceEnabled())
            slf4jLogger.trace("Entering LoadingThread.run ...");

        AsynchLoader asynchLoader = (AsynchLoader) cacheEntry.getLoader();
        Object key = cacheEntry.getKey();

        Map loadingStatusMap = cacheEntry.getCache().getLoadingStatusMap();
//        synchronized (loadingStatusMap) {
//            slf4jLogger.info("Mark the loading status for key: '" + key + "' as '" + CacheEntry.STATUS_LOADING + "'.");
//            loadingStatusMap.put(key, CacheEntry.STATUS_LOADING);
//        }

        slf4jLogger.info("Loading Thread is now loading the data for key: '" + key + "' asynchronously.");
        try {
            slf4jLogger.info("Sleeping for 20 sec, just to simulate the Loading time.");
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object loadedContent = asynchLoader.load(cacheEntry.getParams());

        Map postLoadParam = new HashMap();
        postLoadParam.put(CacheEntry.PARAM_MAP_ID, cacheEntry);

        asynchLoader.postLoad(loadedContent, postLoadParam);

        synchronized (loadingStatusMap) {
            slf4jLogger.info("Mark loading status for key: '" + key + "' as '" + CacheEntry.STATUS_LOADED + "'.");
            loadingStatusMap.put(key, CacheEntry.STATUS_LOADED);
        }

        if(slf4jLogger.isTraceEnabled())
            slf4jLogger.trace("Exiting LoadingThread.run ...");
    }
}
