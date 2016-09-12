package edu.javacodestream.web.cache.model;

/**
 * @author Sandip
 */
public class CacheEntryLoadingStatus {

    private String cacheEntryKey;

    private String cacheEntryLoadingStatus;

    private boolean reloadable = false;

    public boolean isReloadable() {
        return reloadable;
    }

    public void setReloadable(boolean reloadable) {
        this.reloadable = reloadable;
    }

    public CacheEntryLoadingStatus(String cacheEntryKey, String cacheEntryLoadingStatus) {
        this.cacheEntryKey = cacheEntryKey;
        this.cacheEntryLoadingStatus = cacheEntryLoadingStatus;
        if(this.cacheEntryLoadingStatus.equalsIgnoreCase("LOADED"))
            this.reloadable = true;
    }

    public String getCacheEntryKey() {
        return cacheEntryKey;
    }

    public void setCacheEntryKey(String cacheEntryKey) {
        this.cacheEntryKey = cacheEntryKey;
    }

    public String getCacheEntryLoadingStatus() {
        return cacheEntryLoadingStatus;
    }

    public void setCacheEntryLoadingStatus(String cacheEntryLoadingStatus) {
        this.cacheEntryLoadingStatus = cacheEntryLoadingStatus;
        if(this.cacheEntryLoadingStatus.equalsIgnoreCase("LOADED"))
            this.reloadable = true;
    }
}
