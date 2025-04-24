package com.suporte.demo.LAYERS.passwordforgot;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer>{
    
    Optional <PasswordResetToken> findByToken(String PasswordResetToken);
    
}
