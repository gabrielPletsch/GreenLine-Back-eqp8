package app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Produto;
import app.repository.ProdutoRepository;

import app.auditing.Audit;
import app.repository.AuditRepository;

@Service
public class ProdutoService {

	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public String save(Produto produto) {
		produtoRepository.save(produto);
		Audit audit = new Audit("PRODUTO CRIADO", produto.getIdProduto());
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return produto.getNomeProduto() + " salvo com sucesso!";
	}
	
	public List<Produto> listAll() {
		return produtoRepository.findAll();
	}
	
	public Produto findById(long idProduto) {
		return produtoRepository.findById(idProduto).get();
	}

	public String update(long idProduto, Produto produto) {
		Produto produtoExistente = produtoRepository.findById(idProduto).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

		// Atualize os campos do produtoExistente com os valores do produto recebido
		produtoExistente.setNomeProduto(produto.getNomeProduto());
		// ... atualize outros campos conforme necessário

		produtoRepository.save(produtoExistente); // Salva a entidade gerenciada

		Audit audit = new Audit("PRODUTO ATUALIZADO", idProduto);
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return "O produto " + produtoExistente.getNomeProduto() + " foi atualizado com sucesso!";
	}
	
	public String delete(long idProduto) {
		produtoRepository.deleteById(idProduto);
		Audit audit = new Audit("PRODUTO DELETADO", idProduto);
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return "Produto deletado com sucesso!";
	}
}
