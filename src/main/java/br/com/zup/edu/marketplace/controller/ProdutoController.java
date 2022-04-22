package br.com.zup.edu.marketplace.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	@GetMapping
	public ResponseEntity<?> listar(@PageableDefault(size = 2, page = 1, sort = "id", direction = Direction.ASC) Pageable paginacao){
		
		Page<Produto> produtos = repository.findAll(paginacao);
		
		List<ProdutoResponse> produtoResponses = produtos.stream().map(ProdutoResponse::new).collect(Collectors.toList());
		
		PageImpl<ProdutoResponse> responses = new PageImpl<>(
				produtoResponses, paginacao, produtoResponses.size()
	        );
		
		return ResponseEntity.ok(responses);
	}
	
	
}
