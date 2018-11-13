package dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import entity.User;

public interface UserDAO {

	public void insert(User newUser);
	
	public void update(User updatedUser);
	
	public List<User> getUsers();
	
	public User getUser(int id);
	
	public List<User> getUsersFiltered(String filter);
	
	public User getUserByUsername(String username);
	
	public User getUserByEmailAddress(String emailAddress);
	
	public int getDateDifference(Date d1, Date d2);
}
