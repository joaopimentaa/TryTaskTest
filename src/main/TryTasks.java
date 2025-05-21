package main;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import interfaces.*;

public class TryTasks {
    public static void main(String[] args) {
        FlatOneDarkIJTheme.setup(); 

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
