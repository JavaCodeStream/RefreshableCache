package edu.javacodestream.web.cache.rest;

import edu.javacodestream.cache.common.CacheEntry;
import edu.javacodestream.cache.common.RefreshableCacheEntry;
import edu.javacodestream.web.cache.model.CacheEntryInfo;
import edu.javacodestream.web.cache.model.CacheEntryLoadingStatus;
import edu.javacodestream.web.cache.model.User;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Sandip
 */
@RestController
public class CacheManagerRestController {

    Logger slf4jLogger = LoggerFactory.getLogger(CacheManagerRestController.class);

    CacheManager cacheManager = CacheManager.newInstance();

    @Autowired
    private List<RefreshableCacheEntry> cacheEntries;

    public void setCacheEntries(List<RefreshableCacheEntry> cacheEntries) {
        this.cacheEntries = cacheEntries;
    }

    @RequestMapping(value = "/cacheEntries/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<CacheEntryInfo>>> listAllCache() {
        Map<String, List<CacheEntryInfo>> cacheEntryInfoMap = new HashMap<>();
        String[] allCacheNames = cacheManager.getCacheNames();
        for(String cacheName : allCacheNames) {
            Ehcache ehcache = cacheManager.getEhcache(cacheName);
            List<String> cacheKeys = ehcache.getKeys();
            List<CacheEntryInfo> cacheEntryInfoList = new ArrayList<>(cacheKeys.size());
            for(String cacheKey : cacheKeys) {
                CacheEntryInfo entryInfo = new CacheEntryInfo();
                entryInfo.setEntryKey(cacheKey);

                DateTime jodaTime = new DateTime(ehcache.get(cacheKey).getCreationTime());
                DateTimeFormatter parser1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                entryInfo.setCreationTime(parser1.print(ehcache.get(cacheKey).getCreationTime()));

                cacheEntryInfoList.add(entryInfo);
            }
            cacheEntryInfoMap.put(cacheName, cacheEntryInfoList);
        }
        return new ResponseEntity<Map<String, List<CacheEntryInfo>>>(cacheEntryInfoMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/cacheEntries/loadingStatus/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CacheEntryLoadingStatus>> fetchCacheEntriesLoadingStatus() {
        List<CacheEntryLoadingStatus> loadingStatusList = new ArrayList<>();
        for(RefreshableCacheEntry cacheEntry : cacheEntries) {
            cacheEntry.getKey().toString();
            cacheEntry.getCache().getLoadingStatusMap().get(cacheEntry.getKey().toString()).toString();
            loadingStatusList.add(new CacheEntryLoadingStatus(cacheEntry.getKey().toString(),
                    cacheEntry.getCache().getLoadingStatusMap().get(cacheEntry.getKey().toString()).toString()));
        }
        return new ResponseEntity<List<CacheEntryLoadingStatus>>(loadingStatusList, HttpStatus.OK);
    }

    @RequestMapping(value="/reloadCache/{cacheKey:.+}", method = RequestMethod.GET)
    public ResponseEntity<Void> reloadCache(@PathVariable("cacheKey") String cacheKey) {
        slf4jLogger.debug("request received... reloadCache: " + cacheKey);
        boolean triggerReloadCache = false;
        for(RefreshableCacheEntry cacheEntry : cacheEntries) {
            if(cacheEntry.getKey().toString().equalsIgnoreCase(cacheKey)) {
                cacheEntry.refresh(false);
                triggerReloadCache = true;
            }
        }
        if(triggerReloadCache)
            return new ResponseEntity<Void>(HttpStatus.OK);
        else
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
}
