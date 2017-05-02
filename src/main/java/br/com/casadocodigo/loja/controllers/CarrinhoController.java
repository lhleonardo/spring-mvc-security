package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Carrinho;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoController {

	@Autowired
	private ProdutoDAO daoProduto;
	
	@Autowired
	private Carrinho carrinho;

	@RequestMapping(method = RequestMethod.POST, value="/add")
	public ModelAndView add(Long produtoId, TipoPreco tipo) {
		this.carrinho.add(criaItem(produtoId, tipo));
		return new ModelAndView("redirect:/carrinho");
	}
	
	@RequestMapping("/remove")
	public ModelAndView remove(Long produtoId, TipoPreco tipoPreco) {
		
		this.carrinho.remove(produtoId, tipoPreco);
		
		return new ModelAndView("redirect:/carrinho");
	}
	
	@RequestMapping
	public ModelAndView lista() {
		return new ModelAndView("carrinho/itens");
	}

	private CarrinhoItem criaItem(Long p, TipoPreco tipo) {
		return new CarrinhoItem(daoProduto.buscaPeloId(p), tipo);
	}

}
