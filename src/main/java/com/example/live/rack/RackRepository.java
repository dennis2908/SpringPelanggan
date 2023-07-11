package com.example.live.rack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RackRepository extends JpaRepository<Rack, Long> {
    public Rack findById(int rackId);
}