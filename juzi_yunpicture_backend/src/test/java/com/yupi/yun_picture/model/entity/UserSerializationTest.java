package com.yupi.yun_picture.model.entity;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserSerializationTest {

    @Test
    void userCanBeSerializedForRedisSession() {
        User user = new User();
        user.setId(1L);
        user.setUserAccount("testUser");
        user.setUserPassword("encryptedPassword");

        assertDoesNotThrow(() -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        });
    }
}
