package edu.javacodestream.web.cache.dao;

import edu.javacodestream.web.cache.model.User;

import java.util.Map;

/**
 * @author Sandip
 */
public interface UserDao {
    public Map<String, User> retrieveAllUsers();
}
