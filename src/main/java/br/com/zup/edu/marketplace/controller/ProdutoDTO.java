package br.com.zup.edu.marketplace.controller;

import javax.validation.constraints.NotBlank;

public class ProdutoDTO {
	
	@NotBlank
	private String descricao;

	public ProdutoDTO(@NotBlank String descricao) {
		this.descricao = descricao;
	}
	
	public ProdutoDTO() {
		
	}

	public String getDescricao() {
		return descricao;
	}
	

}
