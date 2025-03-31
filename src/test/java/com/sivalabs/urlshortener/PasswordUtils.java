package com.sivalabs.urlshortener;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String adminPwd = encoder.encode("admin");
        System.out.println("adminPwd = " + adminPwd);

        String secretPwd = encoder.encode("secret");
        System.out.println("secretPwd = " + secretPwd);
    }
}
