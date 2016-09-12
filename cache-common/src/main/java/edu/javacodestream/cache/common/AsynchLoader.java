package edu.javacodestream.cache.common;

import java.util.Map;

/**
 * Created by Sandip on 13-08-2016.
 *
 * A Loader with Logic for post-load processing, used by a background thread
 * for asynchronous loading
 */
public interface AsynchLoader extends Loader {

    /**
     *
     * @param loadedValue is whatever loaded data
     * @param params whatever info needed for the post load processing. for example, it might be the CacheEntry container
     */
    public void postLoad(Object loadedValue, Map params);
}
