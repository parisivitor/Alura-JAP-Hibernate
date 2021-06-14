package br.com.alura.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate dataPedido = LocalDate.now();
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	@OneToMany(mappedBy = "pedido",cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
	
	public Pedido() {
	}
	
	
	public Pedido( Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValorTotal());
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente_id() {
		return cliente;
	}

	public void setCliente_id(Cliente cliente_id) {
		this.cliente = cliente_id;
	}
	
}
