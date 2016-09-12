package edu.javacodestream.cache.common;

import edu.javacodestream.cache.common.util.LoadingThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Sandip
 *
 * A cache entry that handles both synchronous and asyncronous data refreshing.
 * Note that asynchronous loading will take place if and only if:
 * <p>
 * <ul>
 *     <li>loader ia an <code>AsynchLoader</code></li>
 *     <li>parameter passed to the refresh(boolean synchLoad) method is false</li>
 * </ul>
 *
 * <p>
 * If a regular synch loader is provided and refresh() method is invoked with asynch mode,
 * a warning message is generated and data is still retrieved in synchronous mode.
 *
 */
public class RefreshableCacheEntry extends CacheEntry implements Refreshable {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(RefreshableCacheEntry.class);

    //the loader can be synch or asynch
    private Loader loader;

    //parameters needed to by the loader for data retrieval
    private Map params;

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public Loader getLoader() {
        return this.loader;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public Map getParams() {
        return this.params;
    }

    public void refresh(boolean synchLoad) {

        if(slf4jLogger.isTraceEnabled())
            slf4jLogger.trace("Entering refresh .. synchLoad: " + synchLoad);

        Map loadingStatusMap = getCache().getLoadingStatusMap();
        Object key = getKey();

        // Don't start a new Thread if another thread have already started loading
        synchronized (loadingStatusMap) {
            String loadingStatus = (String) loadingStatusMap.get(key);
            if (loadingStatus != null && loadingStatus.equals(CacheEntry.STATUS_LOADING)) {
                slf4jLogger.debug("Cache entry already in LOADING status. Hence exiting refresh");
                return;
            }

            slf4jLogger.info("Mark the loading status for key: '" + key + "' as '" + CacheEntry.STATUS_LOADING + "'.");
            loadingStatusMap.put(key, CacheEntry.STATUS_LOADING);
        }

        if(synchLoad) {
            if(slf4jLogger.isDebugEnabled())
                slf4jLogger.debug("Start Synchronous Loading...");

            Object content = loader.load(params);
            slf4jLogger.debug("loaded content: " + ((Map)content).keySet());
            setContent(content);

            synchronized (loadingStatusMap) {
                slf4jLogger.info("Mark loading status for key: '" + key + "' as '" + CacheEntry.STATUS_LOADED + "'.");
                loadingStatusMap.put(key, CacheEntry.STATUS_LOADED);
            }
        }
        else {
            if(loader instanceof AsynchLoader) {
                if (slf4jLogger.isTraceEnabled())
                    slf4jLogger.trace("Start Asynchronous Loading...");

                slf4jLogger.debug("Start Loading Thread to load the cache entry asynchronously... ");
                LoadingThread thread = new LoadingThread(this);
                thread.start();
            }
            else {
                slf4jLogger.warn("Asynchronous load mode is require. However, data is still loaded synchronously " +
                        "because '" + loader + "' does not implement AsynchLoader.");
                slf4jLogger.info("Start Synchronous Loading...");
                Object loaded = loader.load(params);
                setContent(loaded);
            }
        }
    }

    public  void putInCache() {

        if(slf4jLogger.isTraceEnabled())
            slf4jLogger.trace("Entering putInCache ... key: " + getKey());

        boolean syncLoad = true;

        refresh(syncLoad);

        super.putInCache();
    }
}
