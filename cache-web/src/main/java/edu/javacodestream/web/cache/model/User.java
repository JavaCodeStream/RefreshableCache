package edu.javacodestream.web.cache.model;

/**
 * @author Sandip
 */
public class User {

    private String username;

    private String userNTAccount;

    private int userAge;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserNTAccount() {
        return userNTAccount;
    }

    public void setUserNTAccount(String userNTAccount) {
        this.userNTAccount = userNTAccount;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public User(String username, String userNTAccount, int userAge) {
        this.username = username;
        this.userNTAccount = userNTAccount;
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userNTAccount='" + userNTAccount + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
