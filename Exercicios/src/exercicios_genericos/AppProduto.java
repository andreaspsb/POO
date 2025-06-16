package exercicios_genericos;

import java.util.HashMap;
import java.util.Map;

public class AppProduto {
    public static void main(String[] args) {
        try {
            Produto produto1 = new Produto("Produto A", "001", 10);
            Produto produto2 = new Produto("Produto B", "002", 5);
            Produto produto3 = new Produto("Produto C", "003", 0);

            Map<String, Produto> produtosMap = new HashMap<>();
            
            if (produtosMap.containsKey(produto1.getCodigo())) {
                throw new ProdutoJaCadastradoException("Produto já cadastrado: " + produto1.getNome());
            }
            produtosMap.put(produto1.getCodigo(), produto1);

            if (produtosMap.containsKey(produto2.getCodigo())) {
                throw new ProdutoJaCadastradoException("Produto já cadastrado: " + produto2.getNome());
            }
            produtosMap.put(produto2.getCodigo(), produto2);

            if (produtosMap.containsKey(produto3.getCodigo())) {
                throw new ProdutoJaCadastradoException("Produto já cadastrado: " + produto3.getNome());
            }
            produtosMap.put(produto3.getCodigo(), produto3);

            System.out.println("Produtos cadastrados com sucesso!");

            // Exibir os produtos cadastrados
            System.out.println("Produtos no estoque:");

            for (Map.Entry<String, Produto> entry : produtosMap.entrySet()) {
                System.out.println("Código: " + entry.getKey() + ", Nome: " + entry.getValue().getNome());
            }

            // Tentativa de cadastrar um produto já existente
            // Isso deve lançar a exceção ProdutoJaCadastradoException
            produto1 = new Produto("Produto A", "001", 10);
            if (produtosMap.containsKey(produto1.getCodigo())) {
                throw new ProdutoJaCadastradoException("Produto já cadastrado: " + produto1.getNome());
            }

        } catch (ProdutoJaCadastradoException e) {
            System.err.println(e.getMessage());
        }
    }

}
