package com.suporte.demo.LAYERS.passwordforgot;

import org.springframework.stereotype.Component;

@Component
public class Passwordutils {
    public String generatePasswordResetToken() {
        return java.util.UUID.randomUUID().toString();
    }
}

