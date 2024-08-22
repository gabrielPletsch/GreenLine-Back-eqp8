package app.auth;

import app.auditing.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import app.config.JwtServiceGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class LoginService {

    private static final Logger logger = LogManager.getLogger(LoginService.class);

    @Autowired
    private Audit audit;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtServiceGenerator jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String logar(Usuario login) {
        // Autentica o usuário
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );

        // Log de login
        logger.info("Usuário autenticado: {}", login.getUsername());
        audit.registrarLogin(login.getUsername());

        // Busca o usuário no repositório
        Usuario user = repository.findByEmailUsuario(login.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gera o token JWT
        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }
}
