package br.com.zup.edu.marketplace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.marketplace.model.NotaFiscal;
import br.com.zup.edu.marketplace.repository.NotaFiscalRepository;

@RestController
@RequestMapping("/notasfiscais")
public class NotaFiscalController {
	
	private final NotaFiscalRepository repository;

	public NotaFiscalController(NotaFiscalRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotaFiscalResponseDTO> detalhar(@PathVariable("id") Long idNotaFiscal){
		
		NotaFiscal notafiscal = repository.findById(idNotaFiscal).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota fiscal não encontrada"));
		
		return ResponseEntity.ok(new NotaFiscalResponseDTO(notafiscal));
	}
	
}
