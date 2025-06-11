package com.mysbproject.dao.User;

import com.mysbproject.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public User[] getAllUsers() {
    List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    return users.toArray(new User[0]);
  }

  @Override
  @Transactional
  public void saveUser(User user) {
    if (user.getId() == null) {
      entityManager.persist(user);
    } else {
      entityManager.merge(user);
    }
  }

  @Override
  @Transactional
  public void saveExampleUser() {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    user.setPasswordHash("hashedpassword");
    user.setFullName("Test User");
    user.setIsActive(true);
    user.setEmailVerified(false);
    user.setTwoFactorEnabled(false);
    user.setTwoFactorSecret(null);
    user.setCreatedAt(java.time.LocalDateTime.now());
    user.setUpdatedAt(java.time.LocalDateTime.now());
    // You can set other fields as needed

    saveUser(user);
  }

}
