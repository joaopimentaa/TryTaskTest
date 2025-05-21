package interfaces;

import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.extras.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import services.TasksService;


public class MainFrame extends JFrame {
    private TasksService tasksService;
    private String description;

    public MainFrame() {
        FlatOneDarkIJTheme.setup();
       
        initComponents();
        
        // Vincular frame do scroll com o jPanel das tarefas:
        jScrollPane1.setViewportView(jPanel2);
        
        // Tratamento do painel de rolagem
        jScrollPane1.setPreferredSize(new Dimension(350, 260));
        jScrollPane1.setMinimumSize(new Dimension(350, 260));
        jScrollPane1.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        /* -------- jPanel2 vira área da lista -------- */
        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.Y_AXIS));   // empilha verticalmente
        jPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);               // alinha à esquerda
        jPanel2.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 8)); // recuo interno

        /* ------------- cabeçalho ------------ */
        JLabel title = new JLabel("TryTasks", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.PLAIN, 72));  // 72 px como no print
        
        // Já carrega as tarefas
        tasksService = new TasksService(jPanel2);
        tasksService.reloadTasksList(false);

        /* ------------- botão + -------------- */
        int size = 40;
        addButton.setPreferredSize(new Dimension(size, size));
        addButton.setMinimumSize(new Dimension(size, size));
        addButton.setMaximumSize(new Dimension(size, size));
        addButton.setIcon(new FlatSVGIcon("resources/icons/add-one.svg", 60, 60));
        //addButton.addActionListener(e -> addTask("Nova Tarefa"));

        addButton.putClientProperty("JButton.buttonType", "roun1dRect");
        addButton.putClientProperty("Button.arc", 1000); // valor alto = círculo
        addButton.putClientProperty("JComponent.outline", "none");
        
        /* ------ ComboBox (Pendentes x Concluídas) ------ */
        jComboBox1.addActionListener(e -> {
            // Pega o item selecionado no ComboBox
            Object sel = jComboBox1.getSelectedItem();
            description = sel != null ? sel.toString() : "";
            
            jPanel2.removeAll();

            // Verifica a seleção e atualiza as tarefas conforme o status
            if (description.equals("Pendentes")) {
                tasksService.reloadTasksList(false);
                editButton.setVisible(true);
            } else {
                tasksService.reloadTasksList(true);
                editButton.setVisible(false);
            }
            
        });

        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        titleLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TryTasks");
        setResizable(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendentes", "Concluídas" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Unispace", 0, 48)); // NOI18N
        titleLabel.setText("TryTasks");

        addButton.setBackground(new java.awt.Color(33, 37, 43));
        addButton.setForeground(new java.awt.Color(33, 37, 43));
        addButton.setBorderPainted(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        editButton.setText("🖉");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(62, 66, 68));

        jPanel2.setForeground(new java.awt.Color(60, 63, 65));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        deleteButton.setText("DEL");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(titleLabel)
                .addGap(37, 37, 37)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(474, 707));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        CreateTaskFrame createTaskFrame = new CreateTaskFrame();
        createTaskFrame.setVisible(true);
        createTaskFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                tasksService.reloadTasksList(false);
            }
        });
        createTaskFrame.setVisible(true);

    }//GEN-LAST:event_addButtonActionPerformed

    private void jComboBox1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void editButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        UpdateTaskFrame updateTaskFrame = new UpdateTaskFrame();
        updateTaskFrame.setVisible(true);
        updateTaskFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                tasksService.reloadTasksList(false);
            }
        });
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        DeleteTaskFrame deleteTaskFrame = new DeleteTaskFrame();
        deleteTaskFrame.setVisible(true);
        deleteTaskFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                tasksService.reloadTasksList(false);
            }
        });
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        /* -------- aparência FlatLaf -------- */
        FlatOneDarkIJTheme.setup();
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    public JPanel getjPanel2() {
        return jPanel2;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
