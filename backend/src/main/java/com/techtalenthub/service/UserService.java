package com.techtalenthub.service;

import com.techtalenthub.model.User;
import com.techtalenthub.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  /**
   * Retorna todos os usuários.
   * 
   * @return lista de todos os usuários
   */
  public List<User> findAll() {
    return userRepository.findAll();
  }

  /**
   * Encontra um usuário pelo ID.
   * 
   * @param id o ID do usuário
   * @return um Optional contendo o usuário, se encontrado
   */
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  /**
   * Encontra um usuário pelo email.
   * 
   * @param email o email do usuário
   * @return um Optional contendo o usuário encontrado ou vazio se não encontrado
   */
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * Salva um novo usuário.
   * 
   * @param newUser o usuário a ser salvo
   * @return o usuário salvo
   */
  public User save(User newUser) {
    return userRepository.save(newUser);
  }

  /**
   * Atualiza um usuário existente.
   * 
   * @param user    o usuário a ser atualizado
   * @param newUser os dados de atualização do usuário
   * @return o usuário atualizado
   */
  public User update(User user, User newUser) {
    user.setEmail(newUser.getEmail());
    user.setPassword(newUser.getPassword());
    user.setName(newUser.getName());
    user.setRole(newUser.getRole());

    return userRepository.save(user);
  }

  /**
   * Deleta um usuário pelo ID.
   * 
   * @param id o ID do usuário a ser deletado
   */
  public void delete(Long id) {
    userRepository.deleteById(id);
  }

  /**
   * Verifica se um usuário existe pelo ID.
   * 
   * @param id o ID do usuário
   * @return true se o usuário existir, false caso contrário
   */
  public boolean existsById(Long id) {
    return userRepository.existsById(id);
  }
}
