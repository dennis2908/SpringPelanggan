package com.example.live.log;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.live.log.LogData;

@Repository
public interface LogDataRepository extends MongoRepository<LogData, String> {
}
