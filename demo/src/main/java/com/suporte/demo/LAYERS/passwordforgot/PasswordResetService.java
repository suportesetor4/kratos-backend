package com.suporte.demo.LAYERS.passwordforgot;

import java.util.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suporte.demo.LAYERS.entities.Usuario;
import com.suporte.demo.LAYERS.repositories.UsuarioRepository;
import com.suporte.demo.utils.EmailService;

@Service
public class PasswordResetService {

@Autowired
private UsuarioRepository usuarioRepository;

@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;

@Autowired
private EmailService emailService;



public void CreatePasswordResetToken(String email) throws Exception{
    Usuario usuario = usuarioRepository.findByEmail(email)
    .orElseThrow(() -> new RuntimeException("usuário não encontrado"));

    //jwt teste.
    KeyBasedPersistenceTokenService tokenService = getInstanceFor(usuario);

	Token allocateToken = tokenService.allocateToken(usuario.getEmail());
    System.out.println(allocateToken.getKey());


































































     
     // Monta o link com o token
     String resetLink = "http://localhost:3000/esqueceu-senha/status?token=" + allocateToken.getKey();

     // Cria o conteúdo HTML do e-mail
     String html = "<!DOCTYPE html>" +
             "<html>" +
             "<body style='font-family: Arial, sans-serif;'>" +
             "<h2>Redefinição de Senha</h2>" +
             "<p>Olá " + usuario.getNome() + ",</p>" +
             "<p>Clique no botão abaixo para redefinir sua senha. Esse link expira em 30 minutos:</p>" +
             "<a href='" + resetLink + "' style='display: inline-block; padding: 10px 20px; font-size: 16px; " +
             "color: white; background-color: #007BFF; text-decoration: none; border-radius: 5px;'>Redefinir Senha</a>" +
             "<p>Se você não solicitou isso, ignore este e-mail.</p>" +
             "</body></html>";


    try {
        emailService.sendHtmlMessage(usuario.getEmail(), "Redefinição de Senha", html);
    } catch (Exception e) {
        throw new RuntimeException("Erro ao enviar e-mail de redefinição de senha: " + e.getMessage());
    }
}


public boolean validatePasswordResetToken(String token) {

    try {
        // Método JWT para obter os dados do token
        PasswordTokenPublicData publicdata = readPublicData(token);

        // Verifica se o token expirou
        if (isExpired(publicdata)) {
            return false; // Token expirado
        }

        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findByEmail(publicdata.getEmail())
                .orElse(null); // Retorna null caso o usuário não seja encontrado

        if (usuario == null) {
            return false; // Usuário não encontrado
        }

        // Verifica o token com base no usuário
        KeyBasedPersistenceTokenService tokenService = this.getInstanceFor(usuario);
        tokenService.verifyToken(token); // Se falhar, uma exceção pode ser lançada
        return true; // Token válido e verificado

    } catch (Exception e) {
        // Se algum erro ocorrer, retornamos false
        return false;
    }

}
public void resetPassoword(String token, String newpassword) throws Exception{

//metodo jwt;
    PasswordTokenPublicData publicdata = readPublicData(token);

    if(isExpired(publicdata)){
        throw new RuntimeException("token expirado!");

    }
    Usuario usuario = usuarioRepository.findByEmail(publicdata.getEmail())
        .orElseThrow(UserEntityNotFoundException::new);

    KeyBasedPersistenceTokenService tokenService = this.getInstanceFor(usuario);
    tokenService.verifyToken(token);
    String novasenha = bCryptPasswordEncoder.encode(newpassword);
    usuario.setSenha(novasenha);
    usuarioRepository.save(usuario);
}


private boolean isExpired(PasswordTokenPublicData publicdata) {
    Instant createdAt = new Date(publicdata.getCreateAtTimestamp()).toInstant();
    Instant now = new Date().toInstant();
    return createdAt.plus(Duration.ofMinutes(30)).isBefore(now);
}


private PasswordTokenPublicData readPublicData(String token) {

    byte[]  bytes = Base64.getDecoder().decode(token);
    String rawTokenDecoded = new String(bytes);
    String[] tokenParts = rawTokenDecoded.split(":");

    Long timestamp = Long.parseLong(tokenParts[0]);
    String email = tokenParts[2];  
    return new PasswordTokenPublicData(email,timestamp);
}
private KeyBasedPersistenceTokenService getInstanceFor(Usuario usuario) throws Exception {
    KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();

    tokenService.setServerSecret(usuario.getSenha());
    tokenService.setServerInteger(16);
    tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());
    return tokenService;
}

}
