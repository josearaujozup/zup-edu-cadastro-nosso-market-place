package br.com.zup.edu.marketplace.controller;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.marketplace.model.Produto;
import br.com.zup.edu.marketplace.model.Usuario;
import br.com.zup.edu.marketplace.repository.ProdutoRepository;
import br.com.zup.edu.marketplace.repository.UsuarioRepository;

@RestController
public class UsuarioController {
	
	private final UsuarioRepository repository;
	private final ProdutoRepository produtoRepository;
	
	public UsuarioController(UsuarioRepository repository, ProdutoRepository produtoRepository) {
		this.repository = repository;
		this.produtoRepository = produtoRepository;
	}
	
	
	@Transactional
	@PostMapping("/usuarios/{usuarioId}/produtos/{produtoId}")
	public ResponseEntity<?> adicionaMusica(@PathVariable("usuarioId") Long usuarioId, @PathVariable("produtoId") Long produtoId){
		
		Usuario usuario = repository.findById(usuarioId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"usuario nao cadastrado"));
		
		System.out.println("usuario: " + usuario.getNome());
		
		Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"produto nao cadastrado"));
		
		usuario.adiciona(produto);
		
		repository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	@Transactional
	@DeleteMapping("/usuarios/{usuarioId}/produtos/{produtoId}")
	public ResponseEntity<?> removeMusica(@PathVariable("usuarioId") Long usuarioId, @PathVariable("produtoId") Long produtoId){
		
		Usuario usuario = repository.findById(usuarioId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"usuario nao cadastrado"));
		
//		System.out.println("usuario: " + usuario.getNome());
		
		Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"produto nao cadastrado"));
		
		usuario.remove(produto);
		
		repository.save(usuario);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
}
