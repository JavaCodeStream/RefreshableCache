package edu.javacodestream.cache.common;

import net.sf.ehcache.*;

/**
 * Created by Sandip on 14-08-2016.
 */
public class HelloEhCache {

    public static void main(String[] args) {
        //1. Create a cache manager
        CacheManager cm = CacheManager.newInstance();

        //cm.addCache("cache1");

        //2. Get a cache called "cache1", declared in ehcache.xml
        net.sf.ehcache.Cache cache = cm.getCache("userCache");

        //3. Put few elements in cache
//        cache.put(new Element("1","Jan"));
//        cache.put(new Element("2","Feb"));
//        cache.put(new Element("3","Mar"));

        //4. Get element from cache
        Element ele = cache.get("allUsers");
        Object obj = ele.getObjectValue();
        //5. Print out the element
//        String output = (ele == null ? null : ele.getObjectValue().toString());
//        System.out.println(output);

        //6. Is key in cache?
//        System.out.println(cache.isKeyInCache("3"));
//        System.out.println(cache.isKeyInCache("10"));

        //7. shut down the cache manager
        cm.shutdown();
    }

}
