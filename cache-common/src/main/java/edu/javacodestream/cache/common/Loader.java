package edu.javacodestream.cache.common;

import java.util.Map;

/**
 * Created by Sandip on 13-08-2016.
 *
 * The implementor know how to load a specific set of data
 */
public interface Loader {

    public Object load(Map params);

}
