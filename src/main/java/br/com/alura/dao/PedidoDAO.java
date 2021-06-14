package br.com.alura.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.modelo.Pedido;
import br.com.alura.vo.RelatorioDeVendasVO;

public class PedidoDAO {
	private EntityManager em;

	public PedidoDAO(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	public void atualizar(Pedido pedido) {
		this.em.merge(pedido);
	}
	
	public void remover(Pedido pedido) {
		pedido = this.em.merge(pedido);
		this.em.remove(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	
	public List<RelatorioDeVendasVO> relatorioDeVendas(){
		String jpql = "SELECT new br.com.alura.vo.RelatorioDeVendasVO( produto.nome, SUM(item.quantidade), MAX(pedido.dataPedido)) "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.itens item JOIN item.produto produto "
				+ "GROUP BY produto.nome "
				+ "ORDER BY item.quantidade DESC";
		return em.createQuery(jpql,RelatorioDeVendasVO.class).getResultList();
	}

	public Pedido buscarPedidoComClinte(Long id) {
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id",Pedido.class)
				.setParameter("id", id).getSingleResult();
	}
}
