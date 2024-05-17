package com.example.live.function;
import org.springframework.amqp.core.Binding; 
import org.springframework.amqp.core.BindingBuilder; 
import org.springframework.amqp.core.Queue; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MyAppConfiguration {

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
