package br.com.zup.edu.marketplace.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import br.com.zup.edu.marketplace.controller.ProdutoNaoEncontradoException;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String cpf;

	@Column(nullable = false)
	private String endereco;

	@Column(nullable = false)
	private String telefone;

	@ManyToMany
	@JoinTable(name = "usuario_produto", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
	private Set<Produto> listaDeDesejos = new HashSet<>();

	public Usuario(String nome, String cpf, String endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
	}

	/**
	 * @deprecated construtor de uso exclusivo do Hibernate
	 */
	@Deprecated
	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
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

	public void adiciona(Produto produto) {
		this.listaDeDesejos.add(produto);
	}

	public void remove(Produto produto) {

		if (!this.listaDeDesejos.remove(produto)) {
			throw new ProdutoNaoEncontradoException("Não existe um produto com o id fornecido.");
		}

	}

}
