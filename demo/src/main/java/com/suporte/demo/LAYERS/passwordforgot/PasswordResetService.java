package com.suporte.demo.LAYERS.passwordforgot;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suporte.demo.LAYERS.entities.Usuario;
import com.suporte.demo.LAYERS.repositories.UsuarioRepository;
import com.suporte.demo.utils.EmailService;

@Service
public class PasswordResetService {
    
@Autowired
private PasswordResetTokenRepository tokenRepository;

@Autowired
private UsuarioRepository usuarioRepository;


@Autowired
private Passwordutils passwordutils;

@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;

@Autowired
private EmailService emailService;



public void CreatePasswordResetToken(String email){
    Usuario usuario = usuarioRepository.findByEmail(email)
    .orElseThrow(() -> new RuntimeException("usuário não encontrado"));

    String token = passwordutils.generatePasswordResetToken();
    PasswordResetToken resetToken = new PasswordResetToken();
    resetToken.setToken(token);
    resetToken.setUsuario(usuario);
    resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
    resetToken.setUsed(false);
    tokenRepository.save(resetToken);

     // Monta o link com o token
     String resetLink = "https://seudominio.com/redefinir-senha?token=" + token;

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
             "<p><small>Token: " + token + "</small></p>" + // se quiser mostrar o token no corpo também
             "</body></html>";


    try {
        emailService.sendHtmlMessage(usuario.getEmail(), "Redefinição de Senha", html);
    } catch (Exception e) {
        throw new RuntimeException("Erro ao enviar e-mail de redefinição de senha: " + e.getMessage());
    }
}


public boolean validatePasswordResetToken(String token){
    PasswordResetToken resetToken = tokenRepository.findByToken(token)
    .orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));

    return !resetToken.getUsed() && resetToken.getExpiryDate().isAfter(LocalDateTime.now());
}
public void resetPassoword(String token, String newpassword){
    PasswordResetToken resetToken = tokenRepository.findByToken(token)
    .orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));
    if(validatePasswordResetToken(token)){
        Usuario usuario = resetToken.getUsuario();
        String senhaCriptografada = bCryptPasswordEncoder.encode(newpassword);
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);

        resetToken.setUsed(true);
        tokenRepository.delete(resetToken);
    }else{
        throw new RuntimeException("Token expirado ou já utilizado");
    }

}

}
