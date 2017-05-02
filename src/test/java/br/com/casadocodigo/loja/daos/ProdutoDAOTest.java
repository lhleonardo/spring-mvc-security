package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.configuration.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.configuration.JPAConfiguration;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class })
@ActiveProfiles("test")
public class ProdutoDAOTest {

	@Autowired
	private ProdutoDAO daoProduto;

	/**
	 * Inicializar o banco de dados antes de rodar os testes.
	 */
	public void init() {
		// 50 reais
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(4)
				.buildAll();
		// 100 reais
		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN.add(BigDecimal.TEN))
				.more(4).buildAll();

		// 150 reais
		List<Produto> livrosCombo = ProdutoBuilder
				.newProduto(TipoPreco.COMBO, BigDecimal.TEN.add(BigDecimal.TEN.add(BigDecimal.TEN))).more(4).buildAll();

		gravaMuitos(livrosCombo);
		gravaMuitos(livrosEbook);
		gravaMuitos(livrosImpressos);
	}

	/**
	 * Percorre uma lista de produtos e salva eles no banco de dados
	 * 
	 * @param lista
	 */
	private void gravaMuitos(List<Produto> lista) {
		lista.forEach(daoProduto::grava);
	}

	/**
	 * Teste que vai fazer o processo de verificação dos valores com a API de
	 * stream para verificar se o código da consulta no BD esta correto
	 */
	@Test
	@Transactional
	public void deveSomarTodosOsPrecosPeloTipoDoLivroEbook() {
		
		init();
		
		// busco todos os produtos do banco de dados
		List<Produto> todos = daoProduto.listaTodos();

		// pego todos os produtos que tem o tipo de preco EBOOK
		// PS: criei o método na classe produto que verifica se possui tal tipo
		// de preço em seus precos
		List<Produto> ebooks = todos
				.stream()
				.filter(produto -> produto.temTipo(TipoPreco.EBOOK))
				.collect(Collectors.toList());

		// faço a soma dos valores dos preços para cada produto, somando o preço
		// para ebook
		// PS: método getValorParaTipo(TipoPreco.EBOOK) percorre a lista de
		// preços e pega o valor do EBOOK
		BigDecimal valorEbooksStream = ebooks
				.stream()
				.map(p -> p.getValorParaTipo(TipoPreco.EBOOK))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// valor da soma na consulta do BD
		BigDecimal valorEbooksDAO = daoProduto.somaProdutoPeloTipo(TipoPreco.EBOOK);
		System.out.println(valorEbooksDAO);

		Assert.assertEquals(valorEbooksDAO.setScale(2), valorEbooksStream.setScale(2));
	}

}
