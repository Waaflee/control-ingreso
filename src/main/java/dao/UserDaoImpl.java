package dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.User;
import util.HibernateUtil;

public class UserDaoImpl implements UserDAO {

	@Override
	public void insert(User newUser) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(newUser);
		t.commit();
	}

	@Override
	public void update(User updatedUser) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(updatedUser);
		t.commit();
	}

	@Override
	public List<User> getUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<User> list = session.createQuery("from User").list();
		t.commit();
		return list;
	}

	@Override
	public User getUser(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (User) session.load(User.class, id);
	}

	@Override
	public List<User> getUsersFiltered(String filter) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<User> list = session.createQuery("from User where " + filter).list();
		t.commit();
		return list;
	}

	@Override
	public User getUserByUsername(String username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<User> lista = session.createQuery("from User where username = :username")
				.setParameter("username", username).list();
		t.commit();
		session.close();
		if (!lista.isEmpty()) {
			return (User) lista.get(0);
		} else {
			return null;
		}
	}

	@Override
	public User getUserByEmailAddress(String emailAddress) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<User> lista = session.createQuery("from User where emailaddress = :email")
				.setParameter("email", emailAddress).list();
		t.commit();
		session.close();
		if (!lista.isEmpty()) {
			return (User) lista.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int getDateDifference(Date d1, Date d2) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
		@SuppressWarnings("unchecked")
		List<BigInteger> list = session.createNativeQuery("select DATEDIFF('" + d1 + "','" + f.format(d2) + "')")
				.list();
		t.commit();
		session.close();
		return list.get(0).intValue();
	}
}
