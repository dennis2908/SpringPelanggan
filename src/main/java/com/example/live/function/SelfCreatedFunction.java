package com.example.live.function;
import java.lang.StringBuilder;
import java.util.Random;
import org.springframework.context.annotation.Bean;

public class SelfCreatedFunction {
    public static String generatedRandomStr(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
   
  }   

}