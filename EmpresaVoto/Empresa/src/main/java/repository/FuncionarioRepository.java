package repository;

import model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class FuncionarioRepository {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("EmpresaDB");
    private final  EntityManager em;
   /* Fábrica de EntityManager (EntityManagerFactory) que cria os EntityManagers necessários
    para interagir com o banco de dados*/

    public FuncionarioRepository() {
        this.em = factory.createEntityManager();
    }

    public FuncionarioRepository(EntityManager em) {
        this.em = em;
    }


    public List<Funcionario> listarFuncionarios() {
        TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f ORDER BY f.nome", Funcionario.class);
        return query.getResultList();

       /* método para retornar uma lista de funcionários
        do tipo List<Funcionario>*/
    }

    public Funcionario buscarFuncionariorPorId(Integer id) {
        return em.find(Funcionario.class, id);

        /*método para buscar um funcionario por um específico Id
        @param Integer Id
        return o funcionario buscado*/
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
            em.getTransaction().begin();
            em.persist(funcionario);
            em.getTransaction().commit();

           /* método para cadastrar funcionário
            @param do tipo Funcionario funcionario
            cadastra um funcionário no banco H2*/
    }



}
