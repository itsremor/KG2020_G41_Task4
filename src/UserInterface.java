import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.cs.vsu.DrawPanel;

public class UserInterface extends JFrame{
    private JPanel mainPanel;
    private JPanel secondPanel;
    private DrawPanel drawPanel1;

    public UserInterface() {

        setContentPane(mainPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(1000, 800);

    }
}
