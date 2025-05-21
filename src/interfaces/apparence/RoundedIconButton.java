package interfaces.apparence;

import java.awt.*;
import javax.swing.*;

public class RoundedIconButton extends JButton {

    private int arcSize = 100; // define o quão arredondado é o botão

    public RoundedIconButton(Icon icon) {
        super(icon);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(50, 50));
        setBackground(new Color(60, 120, 180)); // cor do fundo
        setForeground(Color.WHITE); // cor do ícone
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // fundo arredondado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);

        super.paintComponent(g);
        g2.dispose();
    }

    public void setArcSize(int arc) {
        this.arcSize = arc;
        repaint();
    }
}
