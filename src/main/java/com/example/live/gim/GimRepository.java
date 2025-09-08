package com.example.live.gim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GimRepository extends JpaRepository<Gim, Long> {

}