package com.suporte.demo.LAYERS.services;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suporte.demo.LAYERS.entities.Usuario;
import com.suporte.demo.LAYERS.entities.enums.Papel;
import com.suporte.demo.LAYERS.repositories.UsuarioRepository;
import com.suporte.demo.utils.ValidadorTelefone;

@Service
public class UsuarioService {

    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z]).{6,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario getUsuario(Integer idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        return usuario.get();
    }

    public Usuario salvar(Usuario usuario) {
        String senhaNormal = usuario.getSenha();

        // Validações antes de salvar
        Matcher matcher = pattern.matcher(senhaNormal);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres e conter letras.");
        }

        Usuario usuarioExistente = usuarioRepository.getUsuarioPorLogin(usuario.getLogin());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("Login já existe.");
        }
        if(usuario.getNome() == null || usuario.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("O nome do Usuario não pode ser nulo ou vazio.");
        }
        if(usuario.getTelefone() == null || usuario.getTelefone().trim().isEmpty()){
            throw new IllegalArgumentException("O Telefone do Usuario não pode ser nulo ou vazio.");
        }
        if(ValidadorTelefone.validarTelefone(usuario.getTelefone())){
            throw new IllegalArgumentException("O telefone do Usuario não está no formato valido");
        }

        // Criptografa e salva o usuário
        String senhaCriptografada = bCryptPasswordEncoder.encode(senhaNormal);
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Integer idUsuario, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(idUsuario).map(usuario -> {
            usuario.setLogin(usuarioAtualizado.getLogin());
            if (usuarioAtualizado.getPapel() != null) {
                usuario.setPapel(usuarioAtualizado.getPapel());
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void remover(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public Usuario getUsuarioPorLogin(String login) {
      return usuarioRepository.getUsuarioPorLogin(login);
    }

    public Usuario getUsuarioPorLogin2(String login, String senha){

        Usuario usuario = usuarioRepository.getUsuarioPorLogin(login);
        if (senha == null){
            throw new IllegalArgumentException("senha nula!");
        }

         if (!bCryptPasswordEncoder.matches(senha, usuario.getSenha())) {
        throw new RuntimeException("Senha incorreta.");
    }

        return usuario ;
    }



    public Usuario cadastrar(Usuario usuario) {
        // Definir o papel fixo como ROLE_USER, sem considerar o papel enviado
        usuario.setPapel(Papel.ROLE_CLIENTE);

        // Validação da senha
        String senhaNormal = usuario.getSenha();
        Matcher matcher = pattern.matcher(senhaNormal);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Senha não atende os requisitos.");
        }

        // Criptografar a senha
        String senhaCriptografada = bCryptPasswordEncoder.encode(senhaNormal);
        usuario.setSenha(senhaCriptografada);

        // Verificar se o login já existe
        if (usuarioRepository.getUsuarioPorLogin(usuario.getLogin()) != null) {
            throw new IllegalArgumentException("Login já existe.");
        }
        

        // Salvar o usuário no banco de dados
        return usuarioRepository.save(usuario);
    }
}








