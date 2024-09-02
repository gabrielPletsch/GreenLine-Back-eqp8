package app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.auditing.Audit;
import app.repository.AuditRepository;
import app.entity.Carrinho;
import app.entity.ItemCarrinho;
import app.repository.CarrinhoRepository;
import app.repository.ItemCarrinhoRepository;
import app.repository.ProdutoRepository;
import jakarta.validation.Valid;
import dto.MesValorDTO;

import java.time.LocalDate;

@Service
public class CarrinhoService {

	@Autowired
	private AuditRepository auditRepository;
	@Autowired
	private CarrinhoRepository carrinhoRepository;
	@Autowired
	private ItemCarrinhoRepository itemCarrinhoRepository;
	@Autowired
	private ItemCarrinhoService itemCarrinhoService;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
//	private Date dataCarrinho;
	
	//metodo para calcular o valor final do carrinho automaticamente 
	public double valorTotalCarrinho(@Valid List<ItemCarrinho> itemCarrinho) {
		
		double valorTotal = 0;
		if(itemCarrinho != null) {
			for (@Valid ItemCarrinho itemCar : itemCarrinho) {
				valorTotal += (itemCar.getValorUnitario() * itemCar.getQuantProd());
			}
		}else {
			throw new RuntimeException(" O carrinho nao pode estar vazio");
		}
		return valorTotal;
		
	}
		
	public String save (Carrinho carrinho) {
		// Verifica se o carrinho possui algum item
	    if (carrinho.getItemCarrinho() == null || carrinho.getItemCarrinho().isEmpty()) {
	    	throw new RuntimeException(" Carrinho vazio");
	    }
	    // Testar necessidade apos implementação 
		// Percorre os itens do carrinho e os salva no banco de dados
//        for (ItemCarrinho item : carrinho.getItemCarrinho()) {
//            item.setCarrinho(carrinho);
//          //adiciona o valor do produto a variavel valorUnitario para manter o registro do valor na compra
//    		double valorUnitario = this.itemCarrinhoService.setValor(item.getProduto());
//    		item.setValorUnitario(valorUnitario);
//            // Salva o item do carrinho no banco de dados
//            itemCarrinhoRepository.save(item);
//        }
        //chamada do metodo para fazer o calculo do valor final do carrinho antes de persistir o mesmo
        double valorFinal = this.valorTotalCarrinho(carrinho.getItemCarrinho());
        carrinho.setValorCarrinho(valorFinal);
        carrinho.setStatus("Encerrado");
        carrinho.setDataCarrinho(LocalDate.now());
        //salva o carrinho no banco de dados 
        this.carrinhoRepository.save(carrinho);

		Audit audit = new Audit("CARRINHO CRIADO", carrinho.getIdCarrinho());
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
        return carrinho.getValorCarrinho() + "  registrada";
	}
	
	public List <Carrinho> listAll () {
		return this.carrinhoRepository.findAll();
	}
	

	public Carrinho findById(long idCarrinho) {
		Carrinho carrinho = this.carrinhoRepository.findById(idCarrinho).get();
		return carrinho;
	}
	
	public String update (Carrinho carrinho, long idCarrinho) {
		// Define o ID do carrinho com base no ID fornecido
		carrinho.setIdCarrinho(idCarrinho);
		
		//verifica se o id  mandado pelo usuario e valido, caso nao seja devolve uma exception
		Optional<Carrinho> car = this.carrinhoRepository.findById(idCarrinho);
		if (car.isEmpty()) {
			throw new RuntimeException("Id nao encontrado");
		}
	
		// Percorre os itens do carrinho e os salva no banco de dados
		for (ItemCarrinho item : carrinho.getItemCarrinho()) {
            item.setCarrinho(carrinho);
            
         // Verifica se o item já existe no banco de dados
            if (item.getIdItem() != 0 && itemCarrinhoRepository.existsById(item.getIdItem())) {
                // Se existe, atualiza o item
                itemCarrinhoRepository.save(item);
            } else {
                // Se não existe, salva como um novo item
                itemCarrinhoRepository.save(item);
            }
            //adiciona o valor do produto a variavel valorUnitario para manter o registro do valor na compra
    		double valorUnitario = this.itemCarrinhoService.setValor(item.getProduto());
    		item.setValorUnitario(valorUnitario);
            
        }
		//chamada do metodo para fazer o calculo do valor final do carrinho antes de persistir o mesmo
		double valorFinal = this.valorTotalCarrinho(carrinho.getItemCarrinho());
		carrinho.setValorCarrinho(valorFinal);	
		this.carrinhoRepository.save(carrinho);
		Audit audit = new Audit("CARRINHO ALTERADO", idCarrinho);
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return " Carrinho " + carrinho.getValorCarrinho() + " Foi atualizado";
		
	}
	
	public String delete (long idCarrinho) {
		//verifica se o id  mandado pelo usuario e valido, caso nao seja devolve uma exception
		Optional<Carrinho> car = this.carrinhoRepository.findById(idCarrinho);
		if (car.isEmpty()) {
			throw new RuntimeException("Id nao encontrado");
		}
		Carrinho carrinho = car.get();
		// Percorre os itens do carrinho e os salva no banco de dados
        for (ItemCarrinho item : carrinho.getItemCarrinho()) {
            item.setCarrinho(carrinho);
            // Deleta o item do carrinho no banco de dados
            itemCarrinhoRepository.deleteById(item.getIdItem());
        }

		this.carrinhoRepository.deleteById(idCarrinho);
		Audit audit = new Audit("VENDA DELETADA", idCarrinho);
		audit.setCreateDate(LocalDateTime.now());
		auditRepository.save(audit);
		return " Venda deletada";
	}
	
	//consultas DB
		
	public List<Carrinho> findByItemCarrinhoProdutoNomeProduto(String nomeProduto){
		if (nomeProduto == "") {
			throw new RuntimeException("nome nao pode estar vazio");
		}else {
			return this.carrinhoRepository.findByItemCarrinhoProdutoNomeProduto(nomeProduto);
		}
		
	}
	
	public List<Carrinho> buscarVendaAcimaValor(double valorCarrinho){
		return this.carrinhoRepository.buscarVendaAcimaValor(valorCarrinho);
	}
	
	public List<Carrinho> buscarVendaAbaixoValor(double valorCarrinho){
		return this.carrinhoRepository.buscarVendaAbaixoValor(valorCarrinho);
	}
	
//	public List<MesValorDTO> ListVendasByMonthForLast12Months(){
//		return this.carrinhoRepository.findTotalValorCarrinhoByMonthForLast12Months();
//	}
	
	public List<MesValorDTO> getTotalValorCarrinhoByMonthForLast12Months() {
        List<Object[]> results = carrinhoRepository.findTotalValorCarrinhoByMonthForLast12Months();
        List<MesValorDTO> dtoResults = new ArrayList<>();

        for (Object[] result : results) {
            String mes = (String) result[0];
            Double valorTotal = ((Number) result[1]).doubleValue(); // Cast para Double
            dtoResults.add(new MesValorDTO(mes, valorTotal));
        }

        return dtoResults;
    }
	
	public List<Carrinho> getVendasFinalizadas(){
		return this.carrinhoRepository.getVendasFinalizadas();
	}

}
