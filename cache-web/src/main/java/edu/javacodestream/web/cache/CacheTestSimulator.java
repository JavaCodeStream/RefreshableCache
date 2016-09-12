package edu.javacodestream.web.cache;

import edu.javacodestream.cache.common.Cache;
import edu.javacodestream.cache.common.CacheEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Sandip
 */
@Component
@DependsOn("userCacheEntry")
public class CacheTestSimulator extends Thread{

    private static final Logger slf4jLogger = LoggerFactory.getLogger(CacheTestSimulator.class);

    @Autowired
    private Cache container;

    private String cacheKey = "key.allUsers";

    public void run(){
        while(true) {
            if(container == null) {
                slf4jLogger.debug("Cache is NULL");

            }

            slf4jLogger.debug("Thread is going to check Cache....");
            CacheEntry entry = container.get(cacheKey);

            if(entry != null) {
                slf4jLogger.debug("Read from cache: keys - " + ((Map) entry.getContent()).keySet());
                slf4jLogger.debug("Read from cache: value - " + ((Map) entry.getContent()).get("user1"));
            }
            else {
                slf4jLogger.warn("entry null");
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                slf4jLogger.error("Thread exception", e);
            }
        }

    }

    @PostConstruct
    public void init() {
        slf4jLogger.debug("Init Method Called");
        this.start();
    }
}
