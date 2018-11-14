package service;

import java.util.Date;
import java.util.List;

import dao.UserDaoImpl;
import entity.User;

public class UserService {

    private UserDaoImpl userDao = new UserDaoImpl();

    public void insert(User newUser) {
        userDao.insert(newUser);
    }

    public void update(User updatedUser) {
        userDao.update(updatedUser);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUser(int id) {
        return userDao.getUser(id);
    }

    public List<User> getUsersFiltered(String filter) {
        return userDao.getUsersFiltered(filter);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public User getUserByEmailAddress(String emailAddress) {
        return userDao.getUserByEmailAddress(emailAddress);
    }

    public int getDateDifference(Date d1, Date d2) {
        return userDao.getDateDifference(d1, d2);
    }

    public User getUserByDNI(int dni) {

        try {

            return userDao.getUsersFiltered("identification = '" + dni + "'").get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
