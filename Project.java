import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class Project extends JPanel{
    String name;
    // Date depart
    // Date fin
    // Description dans le "?"

    public Project(String name){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.add(new JLabel(name), BorderLayout.WEST);
        p1.add(new JButton("?"), BorderLayout.EAST);

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(new JLabel("PROGRESS BAR"), BorderLayout.CENTER);

        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        p3.add(new JLabel("date depart"), BorderLayout.WEST);
        p3.add(new JLabel("date fin"), BorderLayout.EAST);
        //p3.setBackground(Color.RED);

        JPanel component = new JPanel();
        component.setLayout(new BoxLayout(component, BoxLayout.PAGE_AXIS));

        component.add(p1);
        component.add(p2);
        component.add(p3);
        component.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(component);
        this.add(Box.createVerticalStrut(5));
    }
}
