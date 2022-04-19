package br.com.zup.edu.marketplace.model;

import javax.persistence.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProduto status=StatusProduto.PENDENTE;

    @Column(nullable = false)
    private BigDecimal preco;

    public Produto(String titulo, String descricao, BigDecimal preco) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
    }

    /**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
    @Deprecated
    public Produto() {
    }

    public Long getId() {
        return id;
    }

	public boolean isAtivo() {
		boolean retorno = false;
				
		if(this.status == StatusProduto.PENDENTE || this.status == StatusProduto.CADASTRADO) {
			retorno = true;
		}
		
		System.out.println("teste: "+retorno);
		return retorno;
	}

	private boolean isPedente() {
		return this.status == StatusProduto.PENDENTE;
	}

	public void atualizar(String descricao) {
		
		if(!isPedente()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"produto cadastrado ou inativo");
		}
		
		this.descricao = descricao;
	}
}
