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


    // JOIN => É uma operação em SQL que permite combinar dados de duas ou mais tabelas
    // com base em uma condição de relacionamento entre elas, através de uma chave primaria
    // em uma tabela e uma chave estrangeira em outra. O relacionamento geralmente é feito
    // por PK/FK, mas que pode ser por qualquer condição lógica válida

    // Exemplo de relacionamento de tabelas
    //   Tabela: clientes            Tabela: pedidos
    //  +------------+-------+       +----+-------+------------+
    //  | cliente_id | nome  |       | id | valor | cliente_id |
    //  +------------+-------+       +----+-------+------------+
    //  | 1          | Ana   |       | 10 | 150.0 | 1          |
    //  | 2          | Bob   |       | 11 | 200.0 | 1          |
    //  | 3          | Carlos|       +----+-------+------------+
//      +------------+-------+

    // INNER JOIN => Retorna somente as linha que possuem relação direta, pega apenas os
    // registros que tem dados correspondentes entre as tabelas
    // EX: Retorna apenas clientes que possuem pelo menos um pedido registrado
    //     SELECT c.nome, p.id
    //     FROM clientes c
    //     INNER JOIN pedidos p ON c.cliente_id = p.cliente_id;


    // LEFT JOIN => Retorna todas as linhas da tabela da esquerda mesmo que não haja relação
    // No exemplo a baixo é para garantir que venham todos os clientes mesmo que não
    // tenham relação com pedidos. Quando não houver correspondência, os campos da direita
    // virão como NULL
    // EX: Garante que todos os clientes apareçam, mesmo os que nunca fizeram pedidos
    //     SELECT c.nome, p.id
    //     FROM clientes c
    //     LEFT JOIN pedidos p ON c.cliente_id = p.cliente_id;


    // RIGHT JOIN => Oposto do LEFT JOIN retorna todas as linhas da tabela da direita mesmo que
    // não haja relação, no exemplo a baixo é para garantir que venham todos os pedidos mesmo
    // que não tenham relação com clientes
    // EX: Garante que todos os pedidos apareçam, mesmo que o cliente tenha sido deletado
    //     SELECT c.nome, p.id
    //     FROM clientes c
    //     RIGHT JOIN pedidos p ON c.cliente_id = p.cliente_id;


    // GROUP BY => Agrupa linhas com os mesmos valores nas colunas especificadas.
    // É utilizado em conjunto com funções de agregação (SUM, AVG, COUNT, MAX, MIN).
    // Regra importante: Todas as colunas presentes no SELECT que não estejam dentro de
    // uma função de agregação devem estar listadas no GROUP BY.
    // EX: Retorna o maior e o menor valor de produto para cada categoria
    //     SELECT
    //          p.categoria_id,
    //          MAX(p.valor),
    //          MIN(p.valor)
    //     FROM produtos p
    //     GROUP BY p.categoria_id;


    // ORDER BY => Ordena o conjunto de resultados com base em uma ou mais colunas.
    //ASC: Ordem crescente (padrão)
    //DESC: Ordem decrescente

    // CRESCENTE
    // Ordem Crescente (A-Z ou 0-9)
    // EX: SELECT
    //          p.nome, p.valor
    //     FROM produtos p
    //     ORDER BY p.nome ASC

    // DECRECENTE
    // Ordem Decrescente (Z-A ou 9-0)
    // EX: SELECT
    //          p.nome, p.valor
    //     FROM produtos p
    //     ORDER BY p.nome DESC

    // Filtrando multiplas linhas
    // caso haja nomes iguais, desempata pelo valor em ordem crescente
    // EX: SELECT
    //          p.nome, p.valor
    //     FROM produtos p
    //     ORDER BY p.nome DESC, p.valor ASC


}
