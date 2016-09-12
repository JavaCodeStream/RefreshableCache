package edu.javacodestream.web.cache.loader;

import edu.javacodestream.cache.common.Loader;
import edu.javacodestream.web.cache.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Sandip
 */
@Component(value = "userLoader")
public class UserLoader implements Loader {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(UserLoader.class);

    @Autowired
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public Object load(Map params) {
        slf4jLogger.debug("UserLoader is now loading the users..");
        return userDao.retrieveAllUsers();
    }
}
