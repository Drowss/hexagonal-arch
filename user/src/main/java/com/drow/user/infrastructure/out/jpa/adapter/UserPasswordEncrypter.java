package com.drow.user.infrastructure.out.jpa.adapter;

import com.drow.user.domain.spi.IUserPasswordEncrypter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class UserPasswordEncrypter implements IUserPasswordEncrypter {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean checkPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
