package app.auth;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import app.auditing.Audit;
import app.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.config.JwtServiceGenerator;

@Service
public class UsuarioService {
	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtServiceGenerator jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder bCrypt;



	public String login(Autenticador autenticador) {
		// Autentica o usuário
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						autenticador.getUsername(),
						autenticador.getPassword()
				)
		);


		// Busca o usuário no repositório
		Usuario user = usuarioRepository.findByEmailUsuario(autenticador.getUsername())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		Audit audit = new Audit("LOGIN_FEITO", user.getIdUsuario());
		audit.setCreateDate(LocalDateTime.now()); // Use user.getId() or appropriate createdBy value
		auditRepository.save(audit);

		// Gera o token JWT
		String jwtToken = jwtService.generateToken(user);

		return jwtToken;
	}


	public String save (Usuario usuario) {

		String senhaCriptografada = this.bCrypt.encode(usuario.getSenhaUsuario());
		usuario.setSenhaUsuario(senhaCriptografada);

		this.usuarioRepository.save(usuario);
		return usuario.getEmailUsuario() + " Foi registrado";



	}

	public List <Usuario> findAll () {
		return this.usuarioRepository.findAll();

	}



	public Usuario findById(long idUsuario) {

		Usuario usuario= this.usuarioRepository.findById(idUsuario).get();
		return usuario;

	}

	public String update (Usuario usuario, long idUsuario) {
		usuario.setIdUsuario(idUsuario);
		this.usuarioRepository.save(usuario);
		Audit audit = new Audit("UPDATE_USUARIO", idUsuario); // Usar o ID do usuário atualizado como referência
		auditRepository.save(audit);
		return "O " + usuario.getEmailUsuario() + " Foi atualizado";




	}

	public String delete (long idUsuario) {
		this.usuarioRepository.deleteById(idUsuario);
		return "Usuario deletado";

	}
	public Optional<Usuario> findByEmail(String emailUsuario){
		return this.usuarioRepository.findByEmailUsuario(emailUsuario);
	}
	public List<Usuario> findByClienteNome(String clienteNome){
		return null;
		//return this.usuarioRepository.findByClienteNomeCliente(clienteNome);
	}
}
