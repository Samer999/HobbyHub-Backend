package com.hobbyhub.models.users;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameToSearch) throws UsernameNotFoundException {
    UserModel userModel = userRepository.findByUsername(usernameToSearch);
    if (userModel == null) return null;
    return new User(userModel.getUsername(), userModel.getPassword(), new ArrayList<>());
  }

  public UserModel save(UserModel userModel) {
    return userRepository.save(userModel);
  }

  public UserModel getUserModel(String username) {
    return userRepository.findByUsername(username);
  }

  public boolean isNewUsername(String username) {
    return userRepository.findByUsername(username) == null;
  }
}
