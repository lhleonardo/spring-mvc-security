package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Repository
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;

	public void grava(Produto produto) {
		if (produto.getId() == null)
			manager.persist(produto);
		else
			manager.merge(produto);
	}

	public List<Produto> listaTodos() {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos", Produto.class)
				.getResultList();
	}

	public Produto buscaPeloId(Long id) {
		TypedQuery<Produto> query = this.manager
				.createQuery("select distinct(p) from Produto p join fetch p.precos where p.id = :pId", Produto.class);
		query.setParameter("pId", id);
		return query.getSingleResult();

	}

	public BigDecimal somaProdutoPeloTipo(TipoPreco tipo) {
		return this.manager
				.createQuery("select sum(preco.valor) from Produto p join p.precos "
						+ "preco where preco.tipo = :pTipoPreco", BigDecimal.class)
				.setParameter("pTipoPreco", tipo).getSingleResult();
	}

}
