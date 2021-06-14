package br.com.alura.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.dao.CategoriaDAO;
import br.com.alura.dao.ProdutoDAO;
import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.Produto;
import br.com.alura.util.JPAUtil;

public class CadastroProduto {
	public static void main(String[] args) {
		CadastrarProdutos();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		
		Produto p = produtoDAO.buscarPorId(1l);
		System.out.println(p.getNome());
		
		List<Produto> listaP = produtoDAO.buscarTodos();
		listaP.forEach(p2 -> System.out.println(p2.getPreco()));
		
		List<Produto> listaPN = produtoDAO.buscarPorNome("Sangung S20");
		listaPN.forEach(p3 -> System.out.println(p3.getNome()));
		
		List<Produto> listaPC = produtoDAO.buscarPorCategoria("CELULAR");
		listaPC.forEach(p4 -> System.out.println(p4.getPreco()));
		
		BigDecimal preco = produtoDAO.buscarPrecoDoProdutoComNome("Sangung S20");
		System.out.println(preco);
		
		

	}

	private static void CadastrarProdutos() {
		Categoria cat = new Categoria("CELULAR");
		Produto celular = new Produto("PocoPhone F1","Xiaomi 6gb ram",new BigDecimal("1700"),cat);
		
//		Faz a coneccao com o banco via codigo;
		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		
		em.getTransaction().begin(); //inicia a transacao
		
		categoriaDAO.cadastrar(cat);//muda de transiente para managed(estado necessario para cconseguir atualizar o db)
		
		produtoDAO.cadastrar(celular);  //adiciona no banco
		celular.setNome("Iphone"); //altera no banco de dados
		
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
