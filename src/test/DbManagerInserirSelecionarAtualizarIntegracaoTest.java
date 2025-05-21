package test;

import data.DbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DbManagerInserirSelecionarAtualizarIntegracaoTest {

    private DbManager dbManager;
    private final String descricaoTeste = "Tarefa de integração teste";
    private int idTarefaTeste;
    private final String dbUrl = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres"
            + "?user=postgres.hpwsyhmalwkmeuqdqcpm"
            + "&password=FasoftTryTasks"
            + "&sslmode=require";

    @BeforeEach
    void setUp() throws SQLException {
        dbManager = new DbManager();
        // Limpar qualquer vestígio da tarefa de teste antes de cada teste
        //limparTarefasPorDescricao(descricaoTeste);

        dbManager.insertTasks(descricaoTeste);
        idTarefaTeste = obterUltimoIdInserido();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Limpar a tarefa de teste após cada teste
        //limparTarefasPorDescricao(descricaoTeste);
        dbManager.close();
    }

    @Test
    void inserirTarefaComSucessoEVerificarSelecao() throws SQLException {
        String novaDescricao = "Nova tarefa de integração";
        dbManager.insertTasks(novaDescricao);
        Map<Integer, String> tarefasInseridas = buscarTarefasPorDescricao(novaDescricao);

        System.out.println("Teste inserirTarefaComSucessoEVerificarSelecao:");
        System.out.println("  Esperado que tarefasInseridas não seja nulo: true");
        System.out.println("  Encontrado que tarefasInseridas não é nulo: " + (tarefasInseridas != null));
        assertNotNull(tarefasInseridas);

        System.out.println("  Esperado que tarefasInseridas não esteja vazio: true");
        System.out.println("  Encontrado que tarefasInseridas não está vazio: " + !tarefasInseridas.isEmpty());
        assertFalse(tarefasInseridas.isEmpty());

        System.out.println("  Esperado que tarefasInseridas contenha a descrição '" + novaDescricao + "': true");
        System.out.println("  Encontrado que tarefasInseridas contém a descrição '" + novaDescricao + "': " + tarefasInseridas.containsValue(novaDescricao));
        assertTrue(tarefasInseridas.containsValue(novaDescricao));
        //limparTarefasPorDescricao(novaDescricao);
    }

    @Test
    void atualizarStatusDaTarefaEVerificar() throws SQLException {
        dbManager.updateStats(idTarefaTeste, true);
        Map<Integer, String> tarefasConcluidas = dbManager.selectTasks(true);

        System.out.println("Teste atualizarStatusDaTarefaEVerificar:");
        System.out.println("  Esperado que tarefasConcluidas não seja nulo: true");
        System.out.println("  Encontrado que tarefasConcluidas não é nulo: " + (tarefasConcluidas != null));
        assertNotNull(tarefasConcluidas); // Garante que o mapa não é nulo

        System.out.println("  Esperado que tarefasConcluidas contenha a chave '" + idTarefaTeste + "': true");
        System.out.println("  Encontrado que tarefasConcluidas contém a chave '" + idTarefaTeste + "': " + tarefasConcluidas.containsKey(idTarefaTeste));
        assertTrue(tarefasConcluidas.containsKey(idTarefaTeste));
    }

    private Map<Integer, String> buscarTarefasPorDescricao(String description) throws SQLException {
        Map<Integer, String> tasksEncontradas = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement ps = connection.prepareStatement("SELECT id, description FROM tasks WHERE description = ?")) {
            ps.setString(1, description);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasksEncontradas.put(rs.getInt("id"), rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar tarefas por descrição no teste de integração:");
            e.printStackTrace();
            throw e;
        }
        return tasksEncontradas;
    }

    private int obterUltimoIdInserido() throws SQLException {
        int ultimoId = -1;
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT MAX(id) FROM tasks")) {
            if (rs.next()) {
                ultimoId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter o último ID inserido:");
            e.printStackTrace();
            throw e;
        }
        return ultimoId;
    }
    private void limparTarefasPorDescricao(String description) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl);
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