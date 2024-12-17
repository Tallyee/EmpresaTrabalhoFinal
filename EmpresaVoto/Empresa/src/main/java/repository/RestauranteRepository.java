package repository;
import model.Restaurante;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class RestauranteRepository {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("EmpresaDB");
    private EntityManager em;
    /* Fábrica de EntityManager (EntityManagerFactory) que cria os EntityManagers necessários
    para interagir com o banco de dados*/


    public RestauranteRepository() {
        this.em = factory.createEntityManager();
    }

    public RestauranteRepository(EntityManager em) {
        this.em = em;
    }

    public List<Restaurante> listarRestaurantes() {
                                                      /* seleciona um restaurante ordenado por nome*/
        TypedQuery<Restaurante> query = em.createQuery("SELECT r FROM Restaurante  r ORDER BY r.nome", Restaurante.class);
        return query.getResultList();

       /* método para listar restaurantes
        return uma lista de restaurantes*/
    }


    public void cadastrarRestaurante(Restaurante restaurante) {
            em.getTransaction().begin();
            em.persist(restaurante);
            em.getTransaction().commit();

            /*método cadastra um restaurante
            @param do tipo Restaurante restaurante
            cadastra o restaurante o H2*/
    }

    public Restaurante buscarRestaurantePorId(Integer id) {
        return em.find(Restaurante.class, id);

       /* método para buscar Restaurante pelo Id específico
        @param Integer Id
        return o restaurante esperado*/
    }

}
