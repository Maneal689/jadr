import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;


public class Project extends JPanel implements Serializable{
    private Fenetre parent;
    private Project self;
    public ProjectInfo pInfo;
    
    private JLabel nameLabel;
    private JProgressBar workProgress;
    private JProgressBar timeProgress;
    private JLabel dateBegLabel;
    private JLabel dateEndLabel;
    private JLabel projectStateLabel;

    public Project(ProjectInfo pInfo, Fenetre parent){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.parent = parent;
        this.self = this;
        this.pInfo = pInfo;

        nameLabel = new JLabel();
        dateBegLabel = new JLabel();
        dateEndLabel = new JLabel();
        projectStateLabel = new JLabel();

        workProgress = new JProgressBar();
        timeProgress = new JProgressBar();

        this.update();

        JPanel component = new JPanel();
        component.setLayout(new BoxLayout(component, BoxLayout.PAGE_AXIS));

        //--- NAME && BUTTONS --//
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(nameLabel);
        p1.add(Box.createHorizontalGlue());

        JButton editButton = new JButton("?");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                AddProjectDialog editor = new AddProjectDialog(parent, self.pInfo);
                editor.dispose();
                self.update();
            }
        });
        p1.add(editButton);
        p1.add(getDelButtons());

        timeProgress.setStringPainted(true);
        timeProgress.setMinimum(0);
        timeProgress.setMaximum(100);
        timeProgress.setBorderPainted(true);
        //--- timeProgressBar ---//

        //--- workProgressBar ---//
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
        JProgressBar workProgress = new JProgressBar();
        workProgress.setStringPainted(true);
        workProgress.setMinimum(0);
        workProgress.setMaximum(100);
        workProgress.setBorderPainted(true);
        workProgress.setValue(pInfo.progress);
        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        plusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                workProgress.setValue(workProgress.getValue() + 1);
            }
        });
        minusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                workProgress.setValue(workProgress.getValue() - 1);
            }
        });
        p2.add(minusButton);
        p2.add(workProgress);
        p2.add(plusButton);

        //--- DATE STATEMENT ---//
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
        p3.add(dateBegLabel);
        p3.add(Box.createHorizontalGlue());
        p3.add(projectStateLabel);
        p3.add(Box.createHorizontalGlue());
        p3.add(dateEndLabel);


        component.add(p1);
        component.add(timeProgress);
        component.add(p2);
        component.add(p3);
        component.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(component);
        this.add(Box.createVerticalStrut(5));
    }
    
    void update(){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime dateBeg;
        LocalDateTime dateEnd;
        dateBeg = LocalDateTime.parse(pInfo.dateBegin, dateFormat);
        dateEnd = LocalDateTime.parse(pInfo.dateEnd, dateFormat);

        Duration totalTime = Duration.between(dateBeg, dateEnd);
        Duration passedTime = Duration.between(dateBeg, dateNow);
        int percent = (int)((double)passedTime.getSeconds() / (double)totalTime.getSeconds() * 100);
        String state = dateNow.isBefore(dateEnd) ? (dateNow.isBefore(dateBeg) ? new String("Not started yet") : new String("In progress")) : new String("Finished");

        timeProgress.setValue(percent);
        workProgress.setValue(self.pInfo.progress);
        nameLabel.setText(self.pInfo.name);
        dateBegLabel.setText(self.pInfo.dateBegin);
        dateEndLabel.setText(self.pInfo.dateEnd);
        projectStateLabel.setText(state);
    }

    JButton getDelButtons(){
        JButton delButton = new JButton("x");
        delButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                ListIterator it = parent.projects.listIterator();
                int cpt = 0;
                if (parent.projects.contains(self)){
                    while (it.hasNext() && it.next() != self)
                        cpt++;
                    parent.projects.remove(cpt);
                    parent.addProjectsToPan();
                }
            }
        });
        return (delButton);
    }

    public String toString(){
        return ("Name: " + this.pInfo.name + "\nProgress: " + this.pInfo.progress + "\nDate: " + this.pInfo.dateBegin);
    }
}
