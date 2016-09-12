package edu.javacodestream.cache.common;

import java.util.Map;

/**
 * Created by Sandip on 13-08-2016.
 *
 * Implementor is an object that contains enough info to refresh itself.
 *
 */
public interface Refreshable {

    public void setLoader(Loader loader);

    public Loader getLoader();

    public void setParams(Map map);

    public Map getParams();

}
