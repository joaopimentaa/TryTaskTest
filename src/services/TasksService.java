package services;

import data.DbManager;
import models.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TasksService {
    private Task task;
    private final JPanel jPanel;
    private DbManager dbManager;

    public TasksService(JPanel jPanel) {
        this.jPanel = jPanel;
        this.dbManager = new DbManager();
    }
    
    // salvar no banco e adicionar na 
    public void createTask(String description) {
        dbManager.insertTasks(description);
    }

    public void addTask(int key, String description, boolean status) {
        try {
            // Criação da JCheckBox
            JCheckBox cb = new JCheckBox(description);
            int W = 340;
            int H = cb.getPreferredSize().height;
            Dimension dim = new Dimension(W, H);

            // Seleciona as tarefas com base no status
            Map<Integer, String> checkboxes = dbManager.selectTasks(status);
            List<Integer> keys = new ArrayList<>(checkboxes.keySet());

            // Crie um mapa de tarefas para associar o ID à checkbox
            int taskId = keys.get(key);
            String taskDescription = checkboxes.get(taskId);

            // Criar uma JCheckBox com a descrição da tarefa
            JCheckBox taskCheckBox = new JCheckBox(taskDescription);

            // Verificar o status da tarefa (is_completed) e marcar a checkbox se a tarefa for concluída
            boolean isCompleted = status; // Usar o status passado para determinar se a tarefa está concluída
            taskCheckBox.setSelected(isCompleted);  // Se isCompleted for true, a checkbox será marcada

            // Definindo um cliente property com o ID da tarefa
            taskCheckBox.putClientProperty("taskId", taskId);

            // Adicionando o ItemListener para cada checkbox
            taskCheckBox.addItemListener(e -> {
                JCheckBox source = (JCheckBox) e.getItemSelectable();
                Integer taskIdFromCheckBox = (Integer) source.getClientProperty("taskId"); // Pegando o ID da tarefa

                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    dbManager.updateStats(taskIdFromCheckBox, false);
                } else {
                    dbManager.updateStats(taskIdFromCheckBox, true);
                }

                reloadTasksList(status);
            });

            // Definir o tamanho e layout da checkbox
            taskCheckBox.setPreferredSize(dim);
            taskCheckBox.setMaximumSize(dim);
            taskCheckBox.setMinimumSize(dim);
            taskCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            taskCheckBox.setOpaque(false); // Deixa a checkbox transparente

            jPanel.add(taskCheckBox);
            jPanel.add(Box.createVerticalStrut(6)); // Espaçamento

            // Atualiza a interface gráfica
            jPanel.revalidate();
            jPanel.repaint();

        } catch (Exception e) {
            System.out.println("Não foi possível adicionar a tarefa à GUI.");
        }
    }

    public void reloadTasksList(boolean status) {
        // Limpa a lista de tarefas do painel antes de recarregar
        jPanel.removeAll();   // Remove todos os componentes (checkBoxes)
        jPanel.revalidate();   // Recalcula o layout
        jPanel.repaint();      // Redesenha a área vazia


        // Carrega as novas tarefas do banco de dados
        Map<Integer, String> taskList = dbManager.selectTasks(status);

        // Lista para fazer busca por indexes no Map.
        List<Integer> keys = new ArrayList<>(taskList.keySet());
        for (int i = 0; i < taskList.size(); i++) {
            addTask(i, taskList.get(keys.get(i)), status);
        }
    }
}
