package test;

import data.DbManager;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InserirTarefaTest {

    private final String DB_URL = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres"
            + "?user=postgres.hpwsyhmalwkmeuqdqcpm"
            + "&password=FasoftTryTasks"
            + "&sslmode=require";

    @Test
    void inserirTarefaComSucesso() throws SQLException {
        DbManager dbManager = new DbManager();
        String descricaoTeste = "Prova IHC";

        dbManager.insertTasks(descricaoTeste);

        Map<Integer, String> tarefasInseridas = buscarTarefasPorDescricao(descricaoTeste);

        System.out.println("Teste inserirTarefaComSucesso:");
        System.out.println("  Esperado que tarefasInseridas não esteja vazio: true");
        System.out.println("  Encontrado que tarefasInseridas não está vazio: " + !tarefasInseridas.isEmpty());
        assertFalse(tarefasInseridas.isEmpty());

        System.out.println("  Esperado que tarefasInseridas contenha a descrição '" + descricaoTeste + "': true");
        System.out.println("  Encontrado que tarefasInseridas contém a descrição '" + descricaoTeste + "': " + tarefasInseridas.containsValue(descricaoTeste));
        assertTrue(tarefasInseridas.containsValue(descricaoTeste));

        //limparTarefasPorDescricao(descricaoTeste);
    }

    private Map<Integer, String> buscarTarefasPorDescricao(String description) throws SQLException {
        Map<Integer, String> tasksEncontradas = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = connection.prepareStatement("SELECT id, description FROM tasks WHERE description = ?")) {
            ps.setString(1, description);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasksEncontradas.put(rs.getInt("id"), rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar tarefas por descrição no teste:");
            e.printStackTrace();
            throw e;
        }
        return tasksEncontradas;
    }

    private void limparTarefasPorDescricao(String description) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = connection.prepareStatement("DELETE FROM tasks WHERE description = ?")) {
            ps.setString(1, description);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao limpar tarefas de teste:");
            e.printStackTrace();
            throw e;
        }
    }
}