package test;

import data.DbManager;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SelecionarTarefaConcluidaTest {

    @Test
    void selecionarTarefasConcluidasComSucesso() throws SQLException {
        DbManager dbManager = new DbManager();

        String tarefaConcluida1 = "Passear com cachorro";
        String tarefaNaoConcluida1 = "Pagar cartão dia 08/06";
        String tarefaConcluida2 = "Pagar internet";

        dbManager.insertTasks(tarefaConcluida1);
        int idConcluida1 = obterUltimoIdInserido();
        dbManager.updateStats(idConcluida1, true);

        dbManager.insertTasks(tarefaNaoConcluida1);

        dbManager.insertTasks(tarefaConcluida2);
        int idConcluida2 = obterUltimoIdInserido();
        dbManager.updateStats(idConcluida2, true);

        Map<Integer, String> tarefasConcluidas = dbManager.selectTasks(true);

        System.out.println("Teste selecionarTarefasConcluidasComSucesso:");
        System.out.println("  Esperado que tarefasConcluidas contenha '" + tarefaConcluida1 + "': true");
        System.out.println("  Encontrado que tarefasConcluidas contém '" + tarefaConcluida1 + "': " + tarefasConcluidas.containsValue(tarefaConcluida1));
        assertTrue(tarefasConcluidas.containsValue(tarefaConcluida1));

        System.out.println("  Esperado que tarefasConcluidas contenha '" + tarefaConcluida2 + "': true");
        System.out.println("  Encontrado que tarefasConcluidas contém '" + tarefaConcluida2 + "': " + tarefasConcluidas.containsValue(tarefaConcluida2));
        assertTrue(tarefasConcluidas.containsValue(tarefaConcluida2));

        System.out.println("  Esperado que tarefasConcluidas NÃO contenha '" + tarefaNaoConcluida1 + "': false");
        System.out.println("  Encontrado que tarefasConcluidas NÃO contém '" + tarefaNaoConcluida1 + "': " + !tarefasConcluidas.containsValue(tarefaNaoConcluida1));
        assertFalse(tarefasConcluidas.containsValue(tarefaNaoConcluida1));

        System.out.println("  Esperado que tarefasConcluidas tenha tamanho: 2");
        System.out.println("  Encontrado que tarefasConcluidas tem tamanho: " + tarefasConcluidas.size());
        assertEquals(2, tarefasConcluidas.size()); // Corrigi a asserção para o valor correto

        //limparTarefasPorDescricao(tarefaConcluida1);
        //limparTarefasPorDescricao(tarefaNaoConcluida1);
        //limparTarefasPorDescricao(tarefaConcluida2);
    }

    private Map<Integer, String> buscarTarefasPorDescricao(String description) throws SQLException {
        Map<Integer, String> tasksEncontradas = new HashMap<>();
        String url = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres"
                + "?user=postgres.hpwsyhmalwkmeuqdqcpm"
                + "&password=FasoftTryTasks"
                + "&sslmode=require";

        try (Connection connection = DriverManager.getConnection(url);
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
        String url = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres"
                + "?user=postgres.hpwsyhmalwkmeuqdqcpm"
                + "&password=FasoftTryTasks"
                + "&sslmode=require";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement("DELETE FROM tasks WHERE description = ?")) {
            ps.setString(1, description);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao limpar tarefas de teste:");
            e.printStackTrace();
            throw e;
        }
    }
    private int obterUltimoIdInserido() throws SQLException {
        int ultimoId = -1;
        String url = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres"
                + "?user=postgres.hpwsyhmalwkmeuqdqcpm"
                + "&password=FasoftTryTasks"
                + "&sslmode=require";
        try (Connection connection = DriverManager.getConnection(url);
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
}