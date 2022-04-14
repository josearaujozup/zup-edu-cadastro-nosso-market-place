package br.com.zup.edu.marketplace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.marketplace.ProdutoRepository;
import br.com.zup.edu.marketplace.model.Produto;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	private final ProdutoRepository repository;

	public ProdutoController(ProdutoRepository repository) {
		this.repository = repository;
	}
	
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> remove(@PathVariable("id") Long idProduto){
		
		Produto produto = repository.findById(idProduto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"produto nao cadastrado"));
		
		if(produto.isAtivo()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"impossivel deletar produto ativo");
		}
		
		repository.delete(produto);
		
		return ResponseEntity.noContent().build();
	}
	
}
