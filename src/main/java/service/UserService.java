package service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import dao.UserDaoImpl;
import entity.Audit;
import entity.User;
import main.java.util.Ip;

public class UserService {

	private UserDaoImpl userDao = new UserDaoImpl();

	public void insert(User newUser) {
		userDao.insert(newUser);
	}

	public void update(User updatedUser) {
		userDao.update(updatedUser);
/*		service.AuditService a = new service.AuditService();
		entity.Audit audit = new Audit();
		audit.setTableid(1);
		audit.setTablename("User");
		audit.setOperationcrud("U");
		audit.setCreatedate(new java.sql.Timestamp(System.currentTimeMillis()));
		audit.setUserid(updatedUser.getId());
		audit.setAddressip(Ip.getIp());*/
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
}
