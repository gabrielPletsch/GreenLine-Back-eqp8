package app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.auditing.Audit;
import app.repository.AuditRepository;
import app.auth.Usuario;
import app.entity.Cliente;
import app.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private AuditRepository auditRepository;


	@Autowired
	private ClienteRepository clienteRepository;
	
	public String save (Cliente cliente) {
		this.clienteRepository.save(cliente);
		Audit audit = new Audit("CLIENTE ADICIONADO", cliente.getIdCliente());
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return cliente.getNomeCliente() + " Foi registrado";
	}
	
	public List <Cliente> findAll () {
	return this.clienteRepository.findAll();
		
	}
	
	
	
	public Cliente findById(long idCliente) {

		Cliente cliente = this.clienteRepository.findById(idCliente).get();
		return cliente;

	}
	
	public Cliente findByUsuarioId(long idUsuario) {
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		Optional<Cliente> cliente = this.clienteRepository.findByUsuario(usuario);
		if(cliente.isPresent())
		return cliente.get();
		else return null;

	}
	
	public String update (Cliente cliente, long idCliente) {
		cliente.setIdCliente(idCliente);
		this.clienteRepository.save(cliente);

		Audit audit = new Audit("ALTERAÇÂO CADASTRAL REALIZADA", cliente.getIdCliente());
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return "O " + cliente.getNomeCliente() + " Foi atualizado";
		
	}
	
	public String delete (long idCliente) {
		this.clienteRepository.deleteById(idCliente);
		Audit audit = new Audit("CLIENTE DELETADO", idCliente);
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return "Cliente deletado";
	}
	
}
