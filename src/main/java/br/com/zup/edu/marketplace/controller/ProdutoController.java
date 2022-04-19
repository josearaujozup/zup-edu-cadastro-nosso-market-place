package br.com.zup.edu.marketplace.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;

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
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long idProduto, @RequestBody @Valid ProdutoDTO request){
		
		Produto produto = repository.findById(idProduto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"produto nao cadastrado"));
		
		produto.atualizar(request.getDescricao());
		
		repository.save(produto);
		
		return ResponseEntity.noContent().build();
	}
	
}
