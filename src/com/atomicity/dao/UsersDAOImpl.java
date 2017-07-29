package com.atomicity.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.atomicity.customExceptions.*;
import com.atomicity.domain.Users;
import com.atomicity.util.Debug;

public class UsersDAOImpl implements UsersDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionfactory) {
		this.sessionFactory = sessionfactory;
	}

	/**
	 * Adds a new user to the database
	 * 
	 * @param newUser
	 *            The user to be added
	 * @throws UserNameTakenException
	 *             In the case a username is taken
	 * @throws InvalidNameException
	 *             If a field is left blank, but front end should prevent that
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void push(Users newUser) throws UserNameTakenException, InvalidNameException {
		// For debugging purposes:
		Debug.printMessage(this.getClass(), "push()", "invoked");
		// Check if there are any empty Strings
		if (newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty() || newUser.getFirstName().isEmpty()
				|| newUser.getLastName().isEmpty()) {
			throw new InvalidNameException("There is an empty String");
		}
		// Get the session
		Session sess = sessionFactory.getCurrentSession();
		// Check to see if the username is taken
		Users users = getUserByName(newUser.getUsername());
		// If the list is not empty, a user with the name was found
		if (users != null) {
			sess.close();
			throw new UserNameTakenException("The username was found in the database");
		} else {
			// Otherwise, add the new user
			// Debug
			Debug.printMessage(this.getClass(), "push()", "username available.");
			Debug.printErrorMessage(this.getClass(), "push()", "saving " + newUser.getUsername());
			sess.save(newUser);
		}

	}

	/**
	 * Updates the User's password in the database
	 * 
	 * @param user
	 *            The user to change
	 * @param newVal
	 *            The new password
	 */
	@Override
	@Transactional
	public void updatePassword(Users user, String newVal) {
		Debug.printMessage(this.getClass(), "updatePassword()", "invoked");
		Session sess = sessionFactory.getCurrentSession();
		user.setPassword(newVal);
		sess.update(user);
	}

	/**
	 * Updates the User's first name in the database
	 * 
	 * @param user
	 *            The user to change
	 * @param newVal
	 *            The new first name
	 */
	@Override
	@Transactional
	public void updateFirstName(Users user, String newVal) {
		// For debugging purposes:
		Debug.printMessage(this.getClass(), "updateFirstName()", "invoked");
		Session sess = sessionFactory.getCurrentSession();
		user.setFirstName(newVal);
		sess.update(user);
	}

	/**
	 * Updates the User's last name in the database
	 * 
	 * @param user
	 *            The user to change
	 * @param newVal
	 *            The new last name
	 */
	@Override
	@Transactional
	public void updateLastName(Users user, String newVal) {
		// For debugging purposes:
		Debug.printMessage(this.getClass(), "updateLastName()", "invoked");
		Session sess = sessionFactory.getCurrentSession();
		user.setLastName(newVal);
		sess.update(user);
	}

	@Override
	@Transactional
	public void setBlocked(Users user, boolean blocked) {
		// For debugging purposes:
		Debug.printMessage(this.getClass(), "updateLastName()", "invoked");
		Session sess = sessionFactory.getCurrentSession();
		user.setBlocked(blocked);
		sess.update(user);
	}

	/**
	 * Returns the user with the given username
	 * 
	 * @param username
	 *            The username to find
	 */
	@Override
	@Transactional
	public Users getUserByName(String username) {
		// For debugging purposes:
		Debug.printMessage(this.getClass(), "getUserByName()", "invoked");
		Session sess = sessionFactory.getCurrentSession();
		Users user = (Users) sess.get(Users.class, username);
		return user;
	}

	/**
	 * Returns a list of all users from A_USERS
	 */
	@Override
	@Transactional
	public List<Users> getAllUsers() {
		// For debugging purposes:
		Debug.printMessage(this.getClass(), "getAllUsers()", "invoked");
		Session sess = sessionFactory.getCurrentSession();
		Query query = sess.getNamedQuery("getAllUsers");
		List<Users> users = query.list();
		return users;
	}

}
