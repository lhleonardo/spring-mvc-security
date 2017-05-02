package br.com.casadocodigo.loja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;

@Controller
@RequestMapping("/api/produtos")
public class ProdutoServiceRest implements ProdutoService {

	@Autowired
	private ProdutoDAO daoProduto;

	@Override
	@GetMapping
	public Produto findOne(Long id) {
		return daoProduto.buscaPeloId(id);
	}

	@Override
	public List<Produto> findAll() {
		return daoProduto.listaTodos();
	}

	@Override
	public void save(Produto data) {
		this.daoProduto.grava(data);
	}

	@Override
	public void remove(Long id) {
		System.out.printf("%d\n", id);
	}

	@Override
	public List<Produto> pesquisaPeloNome(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produto> pesquisaPeloFornecedor(String fornecedor) {
		// TODO Auto-generated method stub
		return null;
	}

}
