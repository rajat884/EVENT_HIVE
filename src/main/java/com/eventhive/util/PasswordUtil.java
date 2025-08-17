package com.eventhive.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String plainTextPassword) {

        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword) {

        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}