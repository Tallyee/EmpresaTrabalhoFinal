package view;

import controller.EmpresaController;
import model.Funcionario;
import model.Restaurante;
import model.Voto;
import util.TecladoUtil;
import java.util.Calendar;
import java.util.List;

public class Sistema {

    public static EmpresaController controller = new EmpresaController();

    private static boolean sair = false;


    public static void main(String[] args) {
        while (!sair) {
            mostrarMenu();
            int opcao = TecladoUtil.lerInteiro("Informe uma Opcao:");
            executaAcao(opcao);
        }

    }

    private static void executaAcao(int opcao) {
            switch (opcao) {
                case 1:
                cadastrarVoto(controller)
                    break;
                case 2:
                listarFuncionarios(controller);
                    break;
                case 3:
                listarRestaurantes(controller);
                    break;
                case 4:
                apurarVotacaoDoDia(controller);
                    break;
                case 5:
                cadastrarFuncionario();
                    break;
                case 6:
                cadastrarRestaurante();
                    break;
                case 7:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção invalida!");
                    break;
            }

    }

public static void mostrarMenu(){
    System.out.println("Insira uma opção:");
    System.out.println("1. Cadastrar Voto");
    System.out.println("2. Listar Funcionários");
    System.out.println("3. Listar Restaurantes");
    System.out.println("4. Apurar votação do dia");
    System.out.println("5. Cadastrar Funcionário");
    System.out.println("6. Cadastrar Restaurante");
    System.out.println("7. Sair");
}

public static void cadastrarFuncionario(){
    String nome = TecladoUtil.lerString("Informe o nome do funcionário:");
    Funcionario funcionario = new Funcionario();
    funcionario.setNome(nome);

    String resultado = controller.cadastrarFuncionario(funcionario);
    System.out.println(resultado);
}

public static void cadastrarRestaurante(){
        String nome = TecladoUtil.lerString("Informe o nome do restaurane:");
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(nome);

        String resultado = controller.cadastrarRestaurante(restaurante);
        System.out.println(resultado);
}

    private static void cadastrarVoto(EmpresaController empresaController) {
        listarFuncionarios(empresaController);
        int idFuncionario = TecladoUtil.lerInteiro("Digite o ID do funcionário: ");

        listarRestaurantes(empresaController);
        int idRestaurante = TecladoUtil.lerInteiro("Digite o ID do restaurante: ");

        Calendar dataVoto = Calendar.getInstance();

        Funcionario funcionario = new Funcionario();
        funcionario.setId(idFuncionario);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(idRestaurante);

        Voto voto = new Voto(dataVoto, funcionario, restaurante);
        String resultado = empresaController.cadastrarVoto(voto, dataVoto);
        System.out.println(resultado);
    }

    private static void apurarVotacaoDoDia(EmpresaController empresaController) {
        Calendar data = Calendar.getInstance();
        String restauranteVencedor = empresaController.apurarVotos(data);

        if (restauranteVencedor == null || restauranteVencedor.isEmpty()) {
            System.out.println("Nenhum voto registrado na data.");
        } else {
            System.out.println("Restaurante vencedor: " + restauranteVencedor);
        }
    }

    private static void listarFuncionarios(EmpresaController empresaController) {
        List<Funcionario> funcionarios = empresaController.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            System.out.println("\n=== Lista de Funcionários ===");
            for (Funcionario funcionario : funcionarios) {
                System.out.println("ID: " + funcionario.getId() + " | Nome: " + funcionario.getNome());
            }
        }
    }

    private static void listarRestaurantes(EmpresaController empresaController) {
        List<Restaurante> restaurantes = empresaController.listarRestaurantes();

        if (restaurantes.isEmpty()) {
            System.out.println("Nenhum restaurante cadastrado.");
        } else {
            System.out.println("\n=== Lista de Restaurantes ===");
            for (Restaurante restaurante : restaurantes) {
                System.out.println("ID: " + restaurante.getId() + " | Nome: " + restaurante.getNome());
            }
        }
    }

}
