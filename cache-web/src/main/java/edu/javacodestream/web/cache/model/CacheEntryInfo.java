package edu.javacodestream.web.cache.model;

import java.util.Date;

/**
 * Created by Sandip on 02-09-2016.
 */
public class CacheEntryInfo {

    private String entryKey;

    private String creationTime;

    public String getEntryKey() {
        return entryKey;
    }

    public void setEntryKey(String entryKey) {
        this.entryKey = entryKey;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
