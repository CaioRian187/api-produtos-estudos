package com.api.produtos.repositories.dao;

import com.api.produtos.entities.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoDAO {

    // ESTRUTURA UTILIZADA QUANDO NECESSITAR DE CONSULTAS DINÂICAS

    @PersistenceContext
    private EntityManager em;

    public List<Produto> buscarPorFiltro(Produto produto){

        // Está sendo criada uma consulta dinâmica, se não vinher nenhum atributo irá buscar todos os produtos,
        // se vinher o nome busca pelo nome e se vinher o preco busca pelo preco

        StringBuilder sql = new StringBuilder();

        sql.append("select p from Produto p where 1=1");

        if (produto.getNome() != null && !produto.getNome().isEmpty()){
            sql.append(" and p.nome = :nome");
        }

        if (produto.getPreco() != null && !produto.getPreco().toString().isEmpty()){
            sql.append(" and p.preco = :preco");
        }


        // É preciso criar a query utilizando o EntityManager
        TypedQuery<Produto> query = em.createQuery(sql.toString(), Produto.class);

        // É preciso informar os parametros utilizados
        if (produto.getNome() != null && !produto.getNome().isEmpty()){
            query.setParameter("nome", produto.getNome());
        }
        if (produto.getPreco() != null && !produto.getPreco().toString().isEmpty()){
            query.setParameter("preco", produto.getPreco());
        }

        return query.getResultList();
    }
}
