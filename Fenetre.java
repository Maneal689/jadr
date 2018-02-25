import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.Color;

public class Fenetre extends JFrame{

    private JPanel pan = new JPanel();
    private Box projectsPan = Box.createVerticalBox();
    private JScrollPane scroll = new JScrollPane(projectsPan);
    private LinkedList<Project> projects; 

    public Fenetre(){
        //--- PARAMETRAGE DE LA FENETRE ---//
        super();
        this.setTitle("Project_manager");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(pan);

        JButton plusButton = new JButton("+");
        JPanel buttonPan = new JPanel();

        //--- CREATING BUTTON ---//
        buttonPan.setLayout(new BorderLayout());
        buttonPan.add(plusButton, BorderLayout.EAST);

        //--- MISE EN PAGE ---//
        //scroll.setViewportView(projectsPan);
        pan.setLayout(new BorderLayout());
        pan.add(scroll, BorderLayout.CENTER);
        pan.add(buttonPan, BorderLayout.SOUTH);

        //--- ADDING PROJECTS ---//
        projects = getProjects();
        ListIterator it = projects.listIterator();
        while (it.hasNext()){
            projectsPan.add((Project)it.next());
        }

        plusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                projectsPan.add(new Project("test"));
                setVisible(true);
            }
        });
    }

    private LinkedList<Project> getProjects(){
        LinkedList<Project> res = new LinkedList<Project>();
        res.add(new Project("Projet 1"));
        res.add(new Project("Projet 2"));
        return (res);
    }

    public static void main(String[] args)
    {
        Fenetre fen = new Fenetre();
    }
}
