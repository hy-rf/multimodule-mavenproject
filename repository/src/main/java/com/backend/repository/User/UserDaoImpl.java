package com.backend.repository.User;

import com.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<User> getAllUsers() {
    List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    return users;
  }

  @Override
  @Transactional
  public void saveUser(User user) {
    if (user.getId() == null) {
      try {
        entityManager.persist(user);
      } catch (Exception e) {
        // Handle exception, e.g., log it or rethrow it
        throw new RuntimeException("Error saving user: " + e.getMessage(), e);
      }
    } else {
      entityManager.merge(user);
    }
  }

  @Override
  @Transactional
  public void saveExampleUser(String userName, String passwordHash) {
    User user = new User();
    user.setUsername(userName);
    user.setPasswordHash(passwordHash);
    user.setIsActive(true);
    user.setEmailVerified(false);
    user.setTwoFactorEnabled(false);
    user.setTwoFactorSecret(null);
    user.setCreatedAt(java.time.LocalDateTime.now());
    user.setUpdatedAt(java.time.LocalDateTime.now());
    // You can set other fields as needed

    saveUser(user);
  }

  @Override
  public List<User> getUsers(String filterField, String filterValue, String sortField, boolean ascending) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsers'");
  }

  @Override
  public Optional<User> findByUsername(String username) {
    String jpql = "SELECT u FROM User u WHERE u.username = :username";
    TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
    query.setParameter("username", username);
    return query.getResultStream().findFirst();
  }

  @Override
  public User getUserById(Long id) {
    return entityManager.find(User.class, id);
  }

}
