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
import br.com.alura.vo.RelatorioDeVendasVO;

public class CadastroPedido {
	public static void main(String[] args) {
		PopularBanco();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		
		Produto produto = produtoDAO.buscarPorId(1l);
		Produto produto2 = produtoDAO.buscarPorId(2l);
		Produto produto3 = produtoDAO.buscarPorId(3l);
		Cliente cliente = clienteDAO.buscarPorId(1l);
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, produto, pedido));
		pedido.adicionarItem(new ItemPedido(4, produto2, pedido));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(5, produto3, pedido));
		
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		pedidoDAO.cadastrar(pedido);
		pedidoDAO.cadastrar(pedido2);
		
		em.getTransaction().commit();	
		
		BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
		System.out.println(totalVendido);
		List<RelatorioDeVendasVO> resultado = pedidoDAO.relatorioDeVendas();
		for (RelatorioDeVendasVO relatorio : resultado) {
			System.out.println(" " +relatorio.getNomeProduto() +" - " + relatorio.getQuantidadeVendida() + " - " +relatorio.getDataUltimaVenda());
		}

	}

	private static void PopularBanco() {
		Categoria catcelular = new Categoria("CELULAR");
		Categoria catvideogame = new Categoria("VIDEOGAME");
		Categoria catinformatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("PocoPhone F1","Xiaomi 6gb ram",new BigDecimal("1700"),catcelular);
		Produto videogame = new Produto("PS5","PPlayStation 5",new BigDecimal("5000"),catvideogame);
		Produto pc = new Produto("MacBookk","Apple 6gb ram",new BigDecimal("8000"),catinformatica);
		
		Cliente cliente = new Cliente("Vitor","123123");
		
//		Faz a coneccao com o banco via codigo;
		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		
		em.getTransaction().begin(); //inicia a transacao
		
		categoriaDAO.cadastrar(catcelular);//muda de transiente para managed(estado necessario para cconseguir atualizar o db)
		categoriaDAO.cadastrar(catvideogame);
		categoriaDAO.cadastrar(catinformatica);
		
		produtoDAO.cadastrar(celular);  //adiciona no banco
		produtoDAO.cadastrar(videogame);
		produtoDAO.cadastrar(pc);
		celular.setNome("Iphone"); //altera no banco de dados
		clienteDAO.cadastrar(cliente);
		
//		em.getTransaction().commit(); //comita no banco
		em.flush(); //comita no banco de dados
		
		em.clear(); //ou em.close() vai para o estado detached(estado fechado, nao altera o banco)
		//para voltar para o estado managed, precisamos usar o:
		celular = em.merge(celular); //nao muda o objeto que se esta utilizando para managed, ele devolve um novo para o uso;
		
		
		celular.setNome("Sangung S20");//gera outro upade no db
//		em.flush(); //faz o uptade de cima ser commitado no banco
		em.getTransaction().commit();
		em.close();
//		em.remove(celular);//remove do banco
//		em.flush();//commita no banco
	}
}
