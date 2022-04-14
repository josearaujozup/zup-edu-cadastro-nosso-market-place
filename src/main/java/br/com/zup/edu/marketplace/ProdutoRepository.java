package br.com.zup.edu.marketplace;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.marketplace.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
