package br.com.alura.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "preco_unitario")
	private BigDecimal preco;
	private Integer quantidade;
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;

	public ItemPedido() {
	}

	public ItemPedido(Integer quantidade, Produto produto, Pedido pedido) {
		this.quantidade = quantidade;
		this.produto = produto;
		this.preco = produto.getPreco();
		this.pedido = pedido;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produtoId) {
		this.produto = produtoId;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedidoId) {
		this.pedido = pedidoId;
	}
	
	public BigDecimal getValorTotal() {
		return preco.multiply(new BigDecimal(quantidade));
	}

}
