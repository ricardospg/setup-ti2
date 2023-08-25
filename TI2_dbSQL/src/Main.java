
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/TI2";
        String username = "ricardospgc";
        String password = "12345";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            ClienteDAO clientedao = new ClienteDAO(connection);

            Scanner scanner = new Scanner(System.in);
            int opcao = 0;

            while (opcao != 5) {
                System.out.println("1) Listar");
                System.out.println("2) Inserir");
                System.out.println("3) Excluir");
                System.out.println("4) Atualizar");
                System.out.println("5) Sair");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = scanner.nextInt();
                    scanner.nextLine();  // Consumir a quebra de linha após o número
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida. Escolha um número de 1 a 5.");
                    scanner.nextLine();  // Limpar o buffer de entrada
                    continue;
                }

                switch (opcao) {
                    case 1:
                        listarRegistros(clientedao);
                        break;
                    case 2:
                        inserirRegistro(clientedao, scanner);
                        break;
                    case 3:
                        excluirRegistro(clientedao, scanner);
                        break;
                    case 4:
                        atualizarRegistro(clientedao, scanner);
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Escolha um número de 1 a 5.");
                        break;
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarRegistros(ClienteDAO clienteDao) throws SQLException {
        List<Cliente> clienteList = clienteDao.getAllCliente();

        System.out.println("Registros da tabela Cliente:");
        for (Cliente cliente : clienteList) {
            System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome() + ", Idade: " + cliente.getIdade());
        }
    }

    private static void inserirRegistro(ClienteDAO clienteDao, Scanner scanner) throws SQLException {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o telefone: ");
        int telefone = scanner.nextInt();

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setTelefone(telefone);

        clienteDao.inserirCliente(novoCliente);

        System.out.println("Registro inserido com sucesso!");
    }

    private static void excluirRegistro(ClienteDAO clienteDao, Scanner scanner) throws SQLException {
        System.out.print("Digite o ID do registro a ser excluído: ");
        int id = scanner.nextInt();

        clienteDao.excluirCliente(id);

        System.out.println("Registro excluído com sucesso!");
    }

    private static void atualizarRegistro(ClienteDAO clienteDao, Scanner scanner) throws SQLException {
        System.out.print("Digite o ID do registro a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir a quebra de linha

        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo telefone: ");
        int telefone = scanner.nextInt();

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(id);
        clienteAtualizado.setNome(nome);
        clienteAtualizado.setTelefone(telefone);

        clienteDao.atualizarCliente(clienteAtualizado);

        System.out.println("Registro atualizado com sucesso!");
    }
}
