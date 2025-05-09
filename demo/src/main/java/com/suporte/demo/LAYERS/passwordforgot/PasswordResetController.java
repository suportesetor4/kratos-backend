package com.suporte.demo.LAYERS.passwordforgot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;


    // 1. Endpoint para solicitar token de redefinição (envia por e-mail)
    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam  String email) throws Exception {
        try {
            passwordResetService.CreatePasswordResetToken(email);
            return ResponseEntity.ok("E-mail de redefinição enviado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Endpoint para validar token (opcional, pode ser feito no frontend)
    @GetMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@RequestParam  String token) {
        try {
            boolean isValid = passwordResetService.validatePasswordResetToken(token);

            if(isValid){
                return ResponseEntity.ok("token válido");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token invalido");

            }
        }catch (UsernameNotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Endpoint para submeter nova senha ---- para requisitar é newPassword json.
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestBody ResetPasswordRequest request) throws Exception { 
        try {
            if(request.getNewPassword() == null || request.getNewPassword().isEmpty() || request.getNewPassword().isBlank()){
                return ResponseEntity.badRequest().body("Senha não pode ser Vazia");
            }
            passwordResetService.resetPassoword(token, request.getNewPassword());
            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}