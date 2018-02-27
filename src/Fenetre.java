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

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.File;

public class Fenetre extends JFrame{

    private JPanel pan = new JPanel();
    private JPanel projectsPan = new JPanel();
    private JScrollPane scroll = new JScrollPane(projectsPan, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public LinkedList<Project> projects; 
    private Fenetre self = this;
    private AddProjectDialog projectDialog;

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
                ProjectInfo pInfo = new ProjectInfo();
                projectDialog = new AddProjectDialog(self, pInfo);
                projectDialog.dispose();
                if (pInfo.isInitiate()){
                    projects.add(new Project(pInfo, self));
                    addProjectsToPan();
                }
            }
        });

        //--- MISE EN PAGE ---//
        pan.add(scroll, BorderLayout.CENTER);
        pan.add(buttonPan, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void addProjectsToPan(){
        self.projectsPan.removeAll();
        ListIterator it = projects.listIterator();
        while (it.hasNext())
            self.projectsPan.add((Project)it.next());
        self.repaint();
        self.setVisible(true);
        saveProjects();
    }

    private void saveProjects(){
        ObjectOutputStream oos;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(new File("save.prj")));
            ListIterator it = projects.listIterator();
            while (it.hasNext())
                oos.writeObject(((Project)it.next()).pInfo);
            oos.close();
        }
        catch(FileNotFoundException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
    }

    private LinkedList<Project> getProjects(){
        LinkedList<Project> res = new LinkedList<Project>();
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(new FileInputStream("save.prj"));
            Project temp;
            while(true)
                res.add(new Project((ProjectInfo)ois.readObject(), self));
        }
        catch(FileNotFoundException e){e.printStackTrace();}
        catch(ClassNotFoundException e){e.printStackTrace();}
        catch(EOFException e){
            if (ois != null)
                ois.close();
        }
        finally{
            return (res);
        }
    }

    public static void main(String[] args)
    {
        Fenetre fen = new Fenetre();
    }
}
