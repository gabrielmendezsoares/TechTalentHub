package com.techtalenthub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techtalenthub.model.User;
import com.techtalenthub.repository.UserRepository;
import com.techtalenthub.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public User update(Long id, User user) {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User not found with id " + id);
    }

    user.setId(id);

    return userRepository.save(user);
  }

  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User not found with id " + id);
    }

    userRepository.deleteById(id);
  }

  public boolean existsById(Long id) {
    return userRepository.existsById(id);
  }
}
