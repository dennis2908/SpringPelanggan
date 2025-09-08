package com.example.live.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import com.example.live.function.ResponseHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

// import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.live.auth.JwtUtil;
// import com.example.live.user.User;
// import com.example.live.user.UserLoginReq;
// import com.example.live.user.UserErrorRes;
// import com.example.live.user.UserLoginRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")

public class UserController {

  private final AuthenticationManager authenticationManager;


    private JwtUtil jwtUtil;
    private final String secret_key = "mysecretkey";
    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

  @Autowired
  private UserRepository userRepository;

  private ResponseHandler responseHandler;
  

  @GetMapping
  // public List<User> getAllUsers() {
  //   return userRepository.findAll();
  // }
  ResponseEntity<Object> getAllCountry() {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      userRepository.findAll()
    );
  }

  @GetMapping("/{id}")
  // public User getUserById(@PathVariable Long id) {
  //   return userRepository.findById(id).get();
  // }
  ResponseEntity<Object> getUserById(@PathVariable Long id) {
    // User getUser = userRepository.findUserByName("dennis");
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      userRepository.findById(id).get()
    );
  }

  @GetMapping("/findUserByName")
  // public User getUserById(@PathVariable Long id) {
  //   return userRepository.findById(id).get();
  // }
  ResponseEntity<Object> findUserByName(@RequestParam String name) {
    // System.out.println("ddddd",userRepository.findUserByName(name));
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      userRepository.findUserByName(name)
    );
  }


  @PostMapping("/refresh/token")
  public ResponseEntity getCookie(@CookieValue(value = "token", defaultValue = "tkn") String cookie) {
    
         try {
          Claims claims = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(cookie).getBody();

         if(claims != null & jwtUtil.validateClaims(claims)){

          String email =  claims.getSubject();

          User user = new User(email,"");

          String token = jwtUtil.createToken(user);

          return ResponseEntity.status(HttpStatus.OK).body(Map.of(
            "new token", token,
            "email", email));
          
         };

         UserErrorRes errorResponse = new UserErrorRes(HttpStatus.BAD_REQUEST,"Can not refresh token");
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  
      }catch (BadCredentialsException e){
        UserErrorRes errorResponse = new UserErrorRes(HttpStatus.BAD_REQUEST,"Can not refresh token");
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
      }catch (Exception e){
        UserErrorRes errorResponse = new UserErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
      }
    }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserLoginReq userLoginReq, HttpServletResponse response)  {
    System.out.println(userLoginReq.getEmail());  
    try {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginReq.getEmail(), userLoginReq.getPassword()));
        String email = authentication.getName();
        System.out.println(email);  
        User user = new User(email,"");
        String token = jwtUtil.createToken(user);
        UserLoginRes userloginRes = new UserLoginRes(email,token);

        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        return ResponseEntity.ok(userloginRes);

    }catch (BadCredentialsException e){
      UserErrorRes errorResponse = new UserErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }catch (Exception e){
      UserErrorRes errorResponse = new UserErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

  @PostMapping
  // public User createUser(@RequestBody User user) {
  //   return userRepository.save(user);
  // }
  ResponseEntity<Object> createUser(@RequestBody User user) {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      userRepository.save(user)
    );
  }

  @PutMapping("/{id}")
  // public User updateUser(@PathVariable Long id, @RequestBody User user) {
  //   User existingUser = userRepository.findById(id).get();
  //   existingUser.setName(user.getName());
  //   existingUser.setEmail(user.getEmail());
  //   existingUser.setAddress(user.getAddress());
  //   return userRepository.save(existingUser);
  // }
  ResponseEntity<Object> updateUser(
    @PathVariable Long id,
    @RequestBody User user
  ) {
    User existingUser = userRepository.findById(id).get();
    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    existingUser.setAddress(user.getAddress());
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      userRepository.save(existingUser)
    );
  }

  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable Long id) {
    try {
      userRepository.findById(id).get();
      userRepository.deleteById(id);
      return "User deleted successfully";
    } catch (Exception e) {
      return "User not found";
    }
  }
}
