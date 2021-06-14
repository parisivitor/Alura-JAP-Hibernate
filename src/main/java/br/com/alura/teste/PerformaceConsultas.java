package br.com.alura.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.dao.CategoriaDAO;
import br.com.alura.dao.ClienteDAO;
import br.com.alura.dao.PedidoDAO;
import br.com.alura.dao.ProdutoDAO;
import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.Cliente;
import br.com.alura.modelo.ItemPedido;
import br.com.alura.modelo.Pedido;
import br.com.alura.modelo.Produto;
import br.com.alura.util.JPAUtil;

public class PerformaceConsultas {
	public static void main(String[] args) {
		PopularBanco();
		EntityManager em = JPAUtil.getEntityManager();
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		Pedido pedido = em.find(Pedido.class, 2l);
		Pedido pedido2 = pedidoDAO.buscarPedidoComClinte(1l);
		em.close();
		
		System.out.println(pedido2.getValorTotal());
		System.out.println(pedido2.getCliente_id().getNome());
		
		EntityManager em1 = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em1);
		List<Produto> listaProduto = produtoDAO.buscarPorParametros("PS5", null, null);
		listaProduto.forEach(s -> System.out.println(s.getNome()));
	}

	private static void PopularBanco() {
		Categoria catcelular = new Categoria("CELULAR");
		Categoria catvideogame = new Categoria("VIDEOGAME");
		Categoria catinformatica = new Categoria("INFORMATICA");

		Produto celular = new Produto("PocoPhone F1", "Xiaomi 6gb ram", new BigDecimal("1700"), catcelular);
		Produto videogame = new Produto("PS5", "PPlayStation 5", new BigDecimal("5000"), catvideogame);
		Produto pc = new Produto("MacBookk", "Apple 6gb ram", new BigDecimal("8000"), catinformatica);

		Cliente cliente = new Cliente("Vitor", "123123");
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, celular, pedido));
		pedido.adicionarItem(new ItemPedido(4, videogame, pedido));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(5, pc, pedido2));

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		
		em.getTransaction().begin();
		
		categoriaDAO.cadastrar(catcelular);
		categoriaDAO.cadastrar(catvideogame);
		categoriaDAO.cadastrar(catinformatica);
		
		produtoDAO.cadastrar(celular);  
		produtoDAO.cadastrar(videogame);
		produtoDAO.cadastrar(pc);
		
		clienteDAO.cadastrar(cliente);
		
		pedidoDAO.cadastrar(pedido);
		pedidoDAO.cadastrar(pedido2);
		
		em.getTransaction().commit();	
		
	}

}
