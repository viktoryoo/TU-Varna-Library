package com.example.library.helpers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordHelper {
  public static String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(StandardCharsets.UTF_8.encode(password));

    return String.format("%032x", new BigInteger(1, md5.digest()));
  }
}
