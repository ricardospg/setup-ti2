
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Cliente> getAllCliente() throws SQLException {
        List<Cliente> clienteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Cliente";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setTelefone(resultSet.getInt("telefone"));
                clienteList.add(cliente);
            }
        }

        return clienteList;
    }

    public void inserirCliente(Cliente cliente) throws SQLException {
        String insertQuery = "INSERT INTO Cliente (nome, telefone) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setInt(2, cliente.getTelefone());

            preparedStatement.executeUpdate();
        }
    }

    public void excluirCliente(int id) throws SQLException {
        String deleteQuery = "DELETE FROM Cliente WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    public void atualizarCliente(Cliente cliente) throws SQLException {
        String updateQuery = "UPDATE Cliente SET nome = ?, telefone = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setInt(2, cliente.getTelefone());
            preparedStatement.setInt(3, cliente.getId());

            preparedStatement.executeUpdate();
        }
    }
}
