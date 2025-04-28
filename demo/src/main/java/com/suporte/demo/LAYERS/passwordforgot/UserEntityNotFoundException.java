package com.suporte.demo.LAYERS.passwordforgot;

public class UserEntityNotFoundException extends RuntimeException {

    public UserEntityNotFoundException() {
        super("Usuário não encontrado.");
    }

    public UserEntityNotFoundException(String message) {
        super(message);
    }

}
