package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;
	@Lob
	private String descricao;
	private int paginas;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataLancamento;

	private String sumarioPath;

	@ElementCollection
	private List<Preco> precos = new ArrayList<>();

	public Produto() {
		super();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Preco> getPrecos() {
		return precos;
	}

	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	

	public String getSumarioPath() {
		return sumarioPath;
	}

	public void setSumarioPath(String sumarioPath) {
		this.sumarioPath = sumarioPath;
	}
	
	public BigDecimal getValorTotalTipos() {
		BigDecimal valor = this.precos
				.stream()
				.map(preco -> preco.getValor() == null ? BigDecimal.ZERO : preco.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("Produto " + this.titulo + " [" + this.id + "] com total de " + valor);
		return valor;
	}

	public boolean temTipo(TipoPreco tipo) {
		return this.precos.stream().filter(preco -> preco.getTipo().equals(tipo)).findFirst().isPresent();
	}
	
	public BigDecimal getValorParaTipo(TipoPreco tipo) {
		 Optional<Preco> optional = this.precos.stream().filter(p -> p.getTipo().equals(tipo)).findFirst();
		 
		 if (optional.isPresent()) {
			 return optional.get().getValor();
		 }
		 
		 return BigDecimal.ZERO;
		 
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [titulo=" + titulo + ", descricao=" + descricao + ", paginas=" + paginas + "]";
	}

}
