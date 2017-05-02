package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.Optional;

public class CarrinhoItem {

	private Produto produto;
	private TipoPreco tipoPreco;

	public CarrinhoItem(Produto p, TipoPreco tipo) {
		this.produto = p;
		this.tipoPreco = tipo;
	}

	public BigDecimal getPreco() {
		Optional<Preco> optional = this.produto
										.getPrecos().stream()
										.filter(p -> p.getTipo().equals(this.tipoPreco))
										.findFirst();

		return optional
				.orElseThrow(() -> new IllegalArgumentException("Nenhum preço informado para o produto."))
				.getValor();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoPreco getTipoPreco() {
		return tipoPreco;
	}

	public void setTipoPreco(TipoPreco tipoPreco) {
		this.tipoPreco = tipoPreco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((tipoPreco == null) ? 0 : tipoPreco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrinhoItem other = (CarrinhoItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (tipoPreco != other.tipoPreco)
			return false;
		return true;
	}

}
