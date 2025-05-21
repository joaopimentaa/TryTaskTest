package data;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbManager {
    private Connection connection; // Conexão ativa no banco.

    public static void main(String[] args) throws SQLException {
        DbManager dbManager = new DbManager();

        //dbManager.insertTasks("Tarefa teste");
        dbManager.selectTasks(false);
        dbManager.selectTasks(true);
        dbManager.close();
    }

    public DbManager() {
        try {
            String url = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres"
                    + "?user=postgres.hpwsyhmalwkmeuqdqcpm"
                    + "&password=FasoftTryTasks"
                    + "&sslmode=require"; // Remova o sslfactory

            connection = DriverManager.getConnection(url);
            System.out.println("Conectado ao Supabase!");
        } catch (SQLException e) {
            System.err.println("Erro de conexão:");
            e.printStackTrace();
        }
    }

    public Map<Integer, String> selectTasks(boolean is_completed) {
        String sql = "SELECT * FROM tasks WHERE is_completed = ?";
        Map<Integer, String> tasks = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, is_completed);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.put(rs.getInt(1), rs.getString(2));
                    //printTasks(rs);
                }
            } catch (SQLException e) {
            System.err.println("Não foi possível executar o select:");
            e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Não foi possível realizar o prepareStatement:");
            e.printStackTrace();
        }

        return tasks;
    }

    public void insertTasks(String description) {
        String sql = "INSERT INTO tasks(description, is_completed) VALUES(?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, description);
            ps.setBoolean(2, false);
            int linhas = ps.executeUpdate();   // agora grava
            System.out.println("Inseriu " + linhas + " linha(s)");
        } catch(SQLException e) {
            System.err.println("Não foi possível inserir a tarefa:");
            e.printStackTrace();
        }
    }

    public void updateStats(int id, boolean newStats) {
        String sql = "UPDATE tasks SET is_completed = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, newStats);
            ps.setInt(2, id);
            int linhas = ps.executeUpdate();   // linhas alteradas
            System.out.println("Alterou " + linhas + " linha(s)");
        } catch (SQLException e) {
            System.err.println("Não foi possível atualizar os status da tarefa:");
            e.printStackTrace();
        }
    }

    public void updateDescription(int id, String description) {
        String sql = "UPDATE tasks SET description = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, description);
            ps.setInt(2, id);
            int linhas = ps.executeUpdate();   // linhas alteradas
            System.out.println("Alterou " + linhas + " linha(s)");
        } catch (SQLException e) {
            System.err.println("Não foi possível atualizar a descrição da tarefa:");
            e.printStackTrace();
        }
    }
    
    
    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);  // Corrigido o índice para 1
            int linhas = ps.executeUpdate();  // Executa a deleção
            System.out.println("Deletou " + linhas + " linha(s)");
        } catch (SQLException e) {
            System.err.println("Não foi possível deletar a tarefa:");
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) connection.close();
    }

    public static void printTasks(ResultSet rs) throws SQLException {
        System.out.printf("id: %d | description: %s | is_completed: %b%n",
                rs.getInt(1), rs.getString(2), rs.getBoolean(3));
    }
}
