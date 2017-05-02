package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Carrinho implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();

	public void add(CarrinhoItem item) {
		this.itens.put(item, getQuantidade(item) + 1);
	}

	public Integer getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			this.itens.put(item, 0);
		}
		return this.itens.get(item);
	}

	public int getQuantidade() {
		return itens.values().stream().reduce(0, (a, b) -> a + b);
	}

	public Collection<CarrinhoItem> getItens() {
		return this.itens.keySet();
	}

	public BigDecimal getValorTotal(CarrinhoItem item) {
		return item.getPreco().multiply(BigDecimal.valueOf(this.itens.get(item)));
	}

	public BigDecimal getValorTotal() {
		BigDecimal valor = BigDecimal.ZERO;

		for (CarrinhoItem item : this.itens.keySet()) {
			valor = valor.add(this.getValorTotal(item));
		}

		return valor;
	}

	public void remove(Long produtoId, TipoPreco tipoPreco) {
		Produto p = new Produto();
		p.setId(produtoId);
		CarrinhoItem item = new CarrinhoItem(p, tipoPreco);
		this.itens.remove(item);
	}

}
