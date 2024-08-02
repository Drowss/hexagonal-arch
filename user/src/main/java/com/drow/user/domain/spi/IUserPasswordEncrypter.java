package com.drow.user.domain.spi;

public interface IUserPasswordEncrypter {

    String encryptPassword(String password);

    Boolean checkPassword(String password, String hashedPassword);
}
