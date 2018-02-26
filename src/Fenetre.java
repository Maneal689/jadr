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
import javax.swing.BoxLayout;
import java.awt.BorderLayout;

import java.awt.Color;

public class Fenetre extends JFrame{

    private JPanel pan = new JPanel();
    private JPanel projectsPan = new JPanel();
    private JScrollPane scroll = new JScrollPane(projectsPan, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public LinkedList<Project> projects; 
    Fenetre self = this;

    public Fenetre(){
        //--- PARAMETRAGE DE LA FENETRE ---//
        super();
        this.setTitle("Project_manager");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(pan);

        this.pan.setLayout(new BorderLayout());

        //--- PROJETS ---//
        this.projectsPan.setLayout(new BoxLayout(projectsPan, BoxLayout.PAGE_AXIS));
        this.projects = getProjects();
        addProjectsToPan();

        //--- BOUTON ---//
        JButton plusButton = new JButton("+");
        JPanel buttonPan = new JPanel();
        buttonPan.setLayout(new BorderLayout());
        buttonPan.add(plusButton, BorderLayout.EAST);

        plusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                projects.add(new Project("test", "2018-09-23 21:56", "2019-01-01 23:42", self));
                addProjectsToPan();
            }
        });

        //--- MISE EN PAGE ---//
        pan.add(scroll, BorderLayout.CENTER);
        pan.add(buttonPan, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void addProjectsToPan(){
        this.projectsPan.removeAll();
        ListIterator it = projects.listIterator();
        while (it.hasNext()){
           this.projectsPan.add((Project)it.next());
        }
        projectsPan.revalidate();
        repaint();
    }

    private LinkedList<Project> getProjects(){
        LinkedList<Project> res = new LinkedList<Project>();
        res.add(new Project("Projet 1", "2017-12-18 14:23", "2018-04-08 23:42", self));
        res.add(new Project("Projet 2", "2017-02-18 14:23", "2017-05-08 23:42", self));
        return (res);
    }

    public static void main(String[] args)
    {
        Fenetre fen = new Fenetre();
    }
}
