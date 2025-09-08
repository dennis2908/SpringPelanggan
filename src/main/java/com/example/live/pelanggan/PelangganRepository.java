package com.example.live.pelanggan;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {
  @Query("SELECT u FROM Pelanggan u WHERE u.name = ?1")
  List<Pelanggan> findPelangganByName(String name);
}
