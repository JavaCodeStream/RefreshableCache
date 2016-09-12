package edu.javacodestream.web.cache.dao.impl;

import edu.javacodestream.web.cache.dao.UserDao;
import edu.javacodestream.web.cache.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sandip on 15-08-2016.
 */
@Component
public class UserDaoImpl implements UserDao {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(UserDaoImpl.class);

    public Map<String, User> retrieveAllUsers() {

        slf4jLogger.debug("UserDao is retrieving users..");

        Map<String, User> alUsers = new HashMap<String, User>();
        alUsers.put("user1", new User("user1", "firmwide/user1", 26));
        alUsers.put("user2", new User("user1", "firmwide/user2", 26));
        alUsers.put("user3", new User("user1", "firmwide/user3", 28));
        alUsers.put("user4", new User("user1", "firmwide/user4", 30));
        alUsers.put("user5", new User("user1", "firmwide/user5", 21));
        alUsers.put("user6", new User("user1", "firmwide/user6", 34));
        return alUsers;
    }
}
