package com.example.jimi.mystroke;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordSalting {

    public static String[] hashNew(char[] pass) {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return hash(pass, salt);
    }

    public static String[] hash(char[] pass, byte[] salt) {
        String[] res = new String[2];
        res[0] = Base64.encodeToString(salt, Base64.DEFAULT);
        try {
            KeySpec pbeKeySpec = new PBEKeySpec(pass, salt, 2^16, 128);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            res[1] = Base64.encodeToString(hash, Base64.DEFAULT);
            return res;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
