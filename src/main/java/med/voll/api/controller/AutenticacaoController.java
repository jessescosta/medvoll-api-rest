package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.DadosCadastroUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    UsuarioRepository repository;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        //System.out.println(dados.login() +" " +dados.senha()+" " +tokenJWT);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/usuario")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados){
        String login = dados.login();
        if (repository.findAll().stream().anyMatch(usuario -> usuario.getLogin().equalsIgnoreCase(login))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um usuário com este login.");
        }
        // Codificar a senha antes de salvar
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCodificada = passwordEncoder.encode(dados.senha());
        dados = new DadosCadastroUsuario(dados.login().toUpperCase(), senhaCodificada);
        var usuario = new Usuario(dados);

        var dto = repository.save(usuario);
        return ResponseEntity.ok(dto);
    }

}
