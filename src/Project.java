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

public class Project extends JPanel{
    String name;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateNow = LocalDateTime.now();
    LocalDateTime dateBeg;
    LocalDateTime dateEnd;
    Fenetre parent;
    Project self;
    LinkedList<Project> projects;
    // Description dans le "?"
    // workProgress avancement

    public Project(String name, String startDate, String endDate, Fenetre parent){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.parent = parent;
        this.self = this;
        dateBeg = LocalDateTime.parse(startDate, dateFormat);
        dateEnd = LocalDateTime.parse(endDate, dateFormat);
        JPanel component = new JPanel();
        component.setLayout(new BoxLayout(component, BoxLayout.PAGE_AXIS));

        //--- NAME && BUTTONS --//
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(new JLabel(name));
        p1.add(Box.createHorizontalGlue());
        p1.add(new JButton("?"));
        p1.add(getDelButtons());

        //--- timeProgressBar ---//
        Duration totalTime = Duration.between(dateBeg, dateEnd);
        Duration passedTime = Duration.between(dateBeg, dateNow);
        int percent = (int)((double)passedTime.getSeconds() / (double)totalTime.getSeconds() * 100);
        JProgressBar timeProgress = new JProgressBar();
        timeProgress.setStringPainted(true);
        timeProgress.setMinimum(0);
        timeProgress.setMaximum(100);
        timeProgress.setValue(percent);
        timeProgress.setBorderPainted(true);

        //--- workProgressBar ---//
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
        JProgressBar workProgress = new JProgressBar();
        workProgress.setStringPainted(true);
        workProgress.setMinimum(0);
        workProgress.setMaximum(100);
        workProgress.setBorderPainted(true);
        workProgress.setValue(0);
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
        String state = dateNow.isBefore(dateEnd) ? (dateNow.isBefore(dateBeg) ? new String("Not started yet") : new String("In progress")) : new String("Finished");
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
        p3.add(new JLabel(dateBeg.format(dateFormat)));
        p3.add(Box.createHorizontalGlue());
        p3.add(new JLabel(state));
        p3.add(Box.createHorizontalGlue());
        p3.add(new JLabel(dateEnd.format(dateFormat)));


        component.add(p1);
        component.add(timeProgress);
        component.add(p2);
        component.add(p3);
        component.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(component);
        this.add(Box.createVerticalStrut(5));
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
}
