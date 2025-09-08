package com.example.live.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE u.name = ?1")
  List<User> findUserByName(String name);

  @Query("SELECT u FROM User u WHERE u.email = ?1")
  User findUserByEmail(String email);
}
