package com.api.produtos.repositories;

import com.api.produtos.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Queries Derivadas são as queries que já vem automaticas do jpa

    List<Produto> findByNomeContainingIgnoreCase(String nome);  // Busca todo que contém a palavra passada e ignorando
    // maiusculas e minusculas
    List<Produto> findByPreco(BigDecimal preco);



    // HQL => Utiliza o hibernate puro, é utilizado no hibernate persistence, exemplo no arquivo ProdutoDAO
    // JPQL => É o SQL com JPA

    // JPQL
    // Lembrar que o precisa ser o mesmo nome da entidade
    @Query("""
            select p from produto p where p.nome = :nome
    """)
    List<Produto> buscarPorNomeJPQL(String nome);

    @Modifying  // Quer dizer que essa query vai fazer alteração e não buscar no banco de dados
    @Transactional // Não permite operação pela metade, se alguma coisa der errado retorna para o estado atual do banco
    @Query("update produto p set p.preco = :preco where p.id = :id")
    void atualizarPreco(Long id, BigDecimal preco);



    // Query Nativa

    @Query(value = "select * from produto where nome = :nome", nativeQuery = true)
    List<Produto> buscarPorNomeSQLNativo(String nome);
}
