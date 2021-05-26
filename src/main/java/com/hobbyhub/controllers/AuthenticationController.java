package com.hobbyhub.controllers;

import com.hobbyhub.models.jwt.JwtUtils;
import com.hobbyhub.models.responses.AuthenticationResponse;
import com.hobbyhub.models.users.SignInRequest;
import com.hobbyhub.models.users.SignUpRequest;
import com.hobbyhub.models.users.UserModel;
import com.hobbyhub.models.users.UserService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserService userService;

  @Autowired private JwtUtils jwtUtils;

  @Autowired private PasswordEncoder passwordEncoder;

  @PostMapping(AppUrls.SIGN_IN)
  public ResponseEntity<AuthenticationResponse> signIn(
      @RequestBody SignInRequest signInRequest) {
    String username = signInRequest.getUsername();
    String password = signInRequest.getPassword();
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
      if (userService.isUserSuspended(username)) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
          new AuthenticationResponse("This username is suspended: " + username));
    }
    catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
          new AuthenticationResponse("Error during client authentication " + username));
    }
    UserDetails userDetails = userService.loadUserByUsername(username);
    UserModel userModel = userService.getUserModel(username);
    String token = jwtUtils.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse("Successful user sign in", token, userModel));
  }

  @GetMapping(AppUrls.AUTHENTICATE_CLIENT)
  public ResponseEntity<AuthenticationResponse> authenticateClient() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserDetails userDetails = userService.loadUserByUsername(username);
    UserModel userModel = userService.getUserModel(username);
    String token = jwtUtils.generateToken(userDetails);
    return  ResponseEntity.ok(new AuthenticationResponse("Successful client authentication", token, userModel));
  }

  @PostMapping(AppUrls.SIGN_UP)
  public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
    try {
      UserModel user = populateNewUserInformation(signUpRequest);
      userService.newUser(user);
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              signUpRequest.getUsername(), signUpRequest.getPassword()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
          new AuthenticationResponse(
              String.format("Username[%s] already used", signUpRequest.getUsername())));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthenticationResponse("Error during user creation"));
    }
    UserDetails userDetails = userService.loadUserByUsername(signUpRequest.getUsername());
    UserModel userModel = userService.getUserModel(signUpRequest.getUsername());
    String token = jwtUtils.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse("Successful user sign up", token, userModel));
  }

  private UserModel populateNewUserInformation(SignUpRequest signUpRequest) {
    if (!userService.isNewUsername(signUpRequest.getUsername())) {
      throw new IllegalArgumentException("Username is already used");
    }
    return UserModel.builder()
        .username(signUpRequest.getUsername())
        .email(signUpRequest.getEmail())
        .firstName(signUpRequest.getFirstName())
        .lastName(signUpRequest.getLastName())
        .gender(signUpRequest.getGender())
        .imageUrl(signUpRequest.getImageUrl())
        .password(passwordEncoder.encode(signUpRequest.getPassword()))
        .followers(Collections.emptyList())
        .hobbiesFollowing(Collections.emptyList())
        .usersFollowing(Collections.emptyList())
        .posts(Collections.emptyList())
        .build();
  }
}
