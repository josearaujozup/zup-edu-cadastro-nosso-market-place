package br.com.zup.edu.marketplace.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario destinatario;

    @ManyToMany
    private List<Produto> itens= new ArrayList<>();


    public NotaFiscal(Usuario destinatario, List<Produto> itens) {
        this.destinatario = destinatario;
        this.itens = itens;
    }

    /**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
    @Deprecated
    public NotaFiscal() {
    }

	public Long getId() {
		return id;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public List<Produto> getItens() {
		return itens;
	}

	public BigDecimal getValorFinal() {
		BigDecimal soma = itens.stream().map(produto -> produto.getPreco()).reduce(BigDecimal.ZERO, BigDecimal::add);
		return soma;
	}
}
