package repository;

import model.Voto;
import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

public class VotoRepository {

    private final EntityManager em;

    public VotoRepository(EntityManager em) {
        this.em = em;
    }

    public void cadastrarVoto(Voto voto, Calendar dataVoto) {
        try {
            TypedQuery<Long> query = this.em.createQuery(
                    /*query jpql que conta o número de votos de um funcionário em uma data específica.*/
                    "SELECT COUNT(v) FROM Voto v WHERE v.funcionario.id = :funcionarioId AND v.dataVoto = :data", // "dataVoto" é a coluna correta
                    Long.class
            );
            query.setParameter("funcionarioId", voto.getFuncionario().getId());
            query.setParameter("data", dataVoto);

            if (query.getSingleResult() > 0) {
                throw new IllegalArgumentException("O funcionário já votou hoje!");
            }
            this.em.getTransaction().begin();
            voto.setDataVoto(dataVoto);
            this.em.persist(voto);
            this.em.getTransaction().commit();
            System.out.println("Voto cadastrado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao tentar cadastrar o voto: " + e.getMessage());
        }

        /*método para cadastrar voto, com a lógica que se o funcionario ja votou
        lança uma excessao com mensagem, se não votou ainda cadastra o voto.
        @param do tipo Voto voto, Calendar dataVoto*/
    }

    public String apurarVotosDoDia(Calendar data) {
        TypedQuery<Object[]> query = em.createQuery(
                /*query jpql busca restaurante associado pelo voto, conta os votos de cada restaurante
                filtra eles também ordenado por data e agrupa os resultados pelo nome*/
                "SELECT v.restaurante.nome, COUNT(v) FROM Voto v WHERE v.dataVoto = :data GROUP BY v.restaurante.nome",
                Object[].class);

        query.setParameter("data", data);
        List<Object[]> resultados = query.getResultList();
    /*    executa a query acima e retorna uma lista de objetos*/

        String restauranteVencedor = null;
        Long maiorQuantidadeVotos = 0L;
        for (Object[] resultado : resultados) {
            String nomeRestaurante = (String) resultado[0];
           /* objeto convertido para String para aceitar um restaurante*/
            Long quantidadeVotos = (Long) resultado[1];
            /*objeto convertido para Long para aceitar os resultados*/

            if (quantidadeVotos > maiorQuantidadeVotos) {
                maiorQuantidadeVotos = quantidadeVotos;
                restauranteVencedor = nomeRestaurante;
            }
           /* há muitos valores nessa query, cada posição do
                Array de Object é a ordem dos campos da query*/
        }
        System.out.println("Restaurante vencedor: " + restauranteVencedor + " com " + maiorQuantidadeVotos + " votos.");
        return restauranteVencedor;
    }

    public Voto buscarVotoPorFuncionarioData(Long idFuncionario, Calendar data) {
         /* Busca um voto específico de um funcionário em uma data específica
                o método utiliza uma query jpql para procurar um registro no Voto
          com base no Id do funcionário e na data do voto
          @param idFuncionario Identificador único do funcionário
          @param data  Data em que o voto foi registrado
          @return O objeto  Voto encontrado ou  null caso nenhum resultado seja encontrado*/

        String query = "SELECT v FROM Voto v WHERE v.funcionario.id = :idFuncionario AND v.dataVoto = :data"; // "dataVoto" é a coluna correta
        TypedQuery<Voto> typedQuery = em.createQuery(query, Voto.class);
        typedQuery.setParameter("idFuncionario", idFuncionario);
        typedQuery.setParameter("data", data);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
