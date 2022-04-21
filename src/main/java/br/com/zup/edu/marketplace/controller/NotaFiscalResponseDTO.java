package br.com.zup.edu.marketplace.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.zup.edu.marketplace.model.NotaFiscal;

public class NotaFiscalResponseDTO {
	
	private String destinatario;
	private String cpf;
	private String endereco;
    private String telefone;
	
    private List<ProdutoResponse> itens;
    private BigDecimal valorFinal;
    
    
	public NotaFiscalResponseDTO(NotaFiscal notafiscal) {
		this.destinatario = notafiscal.getDestinatario().getNome();
		this.cpf = notafiscal.getDestinatario().getCpf();
		this.endereco = notafiscal.getDestinatario().getEndereco();
		this.telefone = notafiscal.getDestinatario().getTelefone();
		this.itens = notafiscal.getItens().stream().map(ProdutoResponse::new).collect(Collectors.toList());
		this.valorFinal = notafiscal.getValorFinal();
	}
	
	public NotaFiscalResponseDTO() {
		
	}

	public String getDestinatario() {
		return destinatario;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public List<ProdutoResponse> getItens() {
		return itens;
	}

	public BigDecimal getValorFinal() {
		return valorFinal;
	}
	
}
