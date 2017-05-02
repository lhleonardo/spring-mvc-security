package br.com.casadocodigo.loja.service;

import java.util.List;

import br.com.casadocodigo.loja.models.Produto;

public interface ProdutoService extends Service<Produto, Long> {
	
	public List<Produto> pesquisaPeloNome(String name);
	
	public List<Produto> pesquisaPeloFornecedor(String fornecedor);
	
}
