package controller;
import model.Funcionario;
import model.Restaurante;
import model.Voto;
import repository.FuncionarioRepository;
import repository.RestauranteRepository;
import repository.VotoRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.List;

public class EmpresaController {

    private FuncionarioRepository funcionarioRepository;
    private RestauranteRepository restauranteRepository;
    private VotoRepository votoRepository;
    private EntityManager em;
    private EntityManagerFactory factory;

    public EmpresaController() {
        /**
         * Construtor padrão da classe  EmpresaController
         * Inicializa a fábrica de gerenciadores de entidades, cria um  EntityManager
         */
        this.factory = Persistence.createEntityManagerFactory("EmpresaDB");
        this.em = factory.createEntityManager();
        this.funcionarioRepository = new FuncionarioRepository(em);
        this.restauranteRepository = new RestauranteRepository(em);
        this.votoRepository = new VotoRepository(em);
    }

    public String apurarVotos(Calendar data) {
        if (data == null) {
            return "Data não pode ser nula.";
        }
        return votoRepository.apurarVotosDoDia(data);
    }

    public String cadastrarVoto(Voto voto, Calendar dataVoto) {
        if (voto == null || dataVoto == null) {
            return "Voto e data não podem ser nulos.";
        }
        /**
         * Cadastra um voto no sistema com as regras necessárias
         * Este método verifica se o voto e a data são válidos, se o funcionário e
         * o restaurante existem no sistema e se o funcionário ainda não votou no mesmo dia
         * @param voto  contendo os dados do voto a ser cadastrado
         * @param dataVoto Data em que o voto está sendo registrado
         * @return Uma mensagem informando o resultado da operação
         */

        Funcionario funcionario = funcionarioRepository.buscarFuncionariorPorId(voto.getFuncionario().getId());
        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(voto.getRestaurante().getId());

        if (funcionario == null || restaurante == null) {
            return "Funcionário ou restaurante não encontrados.";
        }
       /* Verificar se o Funcionário Já Votou Hoje*/
        String jpql = "SELECT COUNT(v) FROM Voto v WHERE v.funcionario = :funcionario AND v.dataVoto = :dataVoto";
        long count = (long) em.createQuery(jpql)
                .setParameter("funcionario", funcionario)
                .setParameter("dataVoto", dataVoto)
                .getSingleResult();
        if (count > 0) {
            return "Funcionário já votou hoje.";
        }
       /* Verificar Voto no Mesmo Restaurante Hoje*/
        jpql = "SELECT COUNT(v) FROM Voto v WHERE v.funcionario = :funcionario AND v.restaurante = :restaurante AND v.dataVoto = :dataVoto";
        count = (long) em.createQuery(jpql)
                .setParameter("funcionario", funcionario)
                .setParameter("restaurante", restaurante)
                .setParameter("dataVoto", dataVoto)
                .getSingleResult();
        if (count > 0) {
            return "Funcionário já votou neste restaurante hoje.";
        }

        try {
            votoRepository.cadastrarVoto(voto, dataVoto);
            return "Voto cadastrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar voto: " + e.getMessage();
        }
    }

    public List<Funcionario> listarFuncionarios() {
        String jpql = "SELECT f FROM Funcionario f";
        return em.createQuery(jpql, Funcionario.class).getResultList();
    }

    public List<Restaurante> listarRestaurantes() {
        String jpql = "SELECT r FROM Restaurante r";
        return em.createQuery(jpql, Restaurante.class).getResultList();
    }

    public String cadastrarFuncionario(Funcionario  funcionario) {
        if (funcionario == null) {
            return "Funcionário não pode ser nulo.";
        }

        try {
            funcionarioRepository.cadastrarFuncionario(funcionario);
            return "Funcionário cadastrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar funcionário: " + e.getMessage();
        }
    }

    public String cadastrarRestaurante(Restaurante restaurante) {
        if (restaurante == null) {
            return "Restaurante não pode ser nulo.";
        }

        try {
            restauranteRepository.cadastrarRestaurante(restaurante);
            return "Restaurante cadastrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar restaurante: " + e.getMessage();
        }
    }

    }

