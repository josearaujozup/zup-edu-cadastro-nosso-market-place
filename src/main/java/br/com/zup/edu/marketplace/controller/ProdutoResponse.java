package br.com.zup.edu.marketplace.controller;

import java.math.BigDecimal;

import br.com.zup.edu.marketplace.model.Produto;

public class ProdutoResponse {
	
	private String titulo;
	private String descricao;
	private String status;
	private BigDecimal preco;
	
	public ProdutoResponse(Produto produto) {
		this.titulo = produto.getTitulo();
		this.descricao = produto.getDescricao();
		this.status = produto.getStatus().name();
		this.preco = produto.getPreco();
	}
	
	
	public ProdutoResponse() {
		
	}


	public String getTitulo() {
		return titulo;
	}


	public String getDescricao() {
		return descricao;
	}


	public String getStatus() {
		return status;
	}


	public BigDecimal getPreco() {
		return preco;
	}
	
}
