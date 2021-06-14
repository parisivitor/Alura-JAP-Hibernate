package br.com.alura.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
//@Inheritance(strategy = InheritanceType.JOINED)  //correlaciona herenca, fazendo em tabelas separadas, menos porformace pois necessita de fazer join em futuros selects, mais organizado
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//correlaciona herenca, fazendo em uma unica tabela, mais performace, tudo misturado em uma unica tabelas
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private LocalDate dataCadastro = LocalDate.now();

	@ManyToOne(fetch = FetchType.LAZY) // relacionamento de entidades, nessee caso muitos pra um
	private Categoria categoria;

//	necessita colocar o construtuor default quando se utilizza JPA
	public Produto() {
	}

	public Produto(String nome, String descricao, BigDecimal preco, Categoria categoria) {
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public Categoria getCategoria() {
		return categoria;
	}

}
