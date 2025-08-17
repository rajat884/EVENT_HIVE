package com.eventhive.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {

    public static void main(String[] args) {
        String plainPassword = "admin123";

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));


    }
}