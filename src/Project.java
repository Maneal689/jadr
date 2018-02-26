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

public class Project extends JPanel{
    String name;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateNow = LocalDateTime.now();
    LocalDateTime dateBeg;
    LocalDateTime dateEnd;
    // Date depart
    // Date fin
    // Description dans le "?"

    public Project(String name, String startDate, String endDate){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        dateBeg = LocalDateTime.parse(startDate, dateFormat);
        dateEnd = LocalDateTime.parse(endDate, dateFormat);

        JPanel component = new JPanel();
        component.setLayout(new BoxLayout(component, BoxLayout.PAGE_AXIS));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(new JLabel(name));
        p1.add(Box.createHorizontalGlue());
        p1.add(new JButton("?"));

        component.add(p1);

        Duration totalTime = Duration.between(dateBeg, dateEnd);
        Duration passedTime = Duration.between(dateBeg, dateNow);
        int percent = (int)((double)passedTime.getSeconds() / (double)totalTime.getSeconds() * 100);

        JProgressBar timeProgress = new JProgressBar();
        timeProgress.setStringPainted(true);
        timeProgress.setMinimum(0);
        timeProgress.setMaximum(100);
        timeProgress.setValue(percent);
        timeProgress.setBorderPainted(true);
        //timeProgress.setMinimumSize(new Dimension((int)timeProgress.getMaximumSize().getWidth(), (int)timeProgress.getMaximumSize().getHeight()));
        component.add(timeProgress);

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
        component.add(p2);

        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));

        p3.add(new JLabel(dateBeg.format(dateFormat)));
        p3.add(Box.createHorizontalGlue());

        String state = dateNow.isBefore(dateEnd) ? (dateNow.isBefore(dateBeg) ? new String("Not started yet") : new String("In progress")) : new String("Finished");
        p3.add(new JLabel(state));
        p3.add(Box.createHorizontalGlue());

        p3.add(new JLabel(dateEnd.format(dateFormat)));
        //p3.setBackground(Color.RED);

        component.add(p3);

        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4, BoxLayout.LINE_AXIS));

        JButton underButton = new JButton("v");

        p4.add(Box.createHorizontalGlue());
        p4.add(underButton);
        p4.add(Box.createHorizontalGlue());
        component.add(p4);

        component.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Dimension minHeightDim = component.getMinimumSize();
        int minHeight = (int)minHeightDim.getHeight();
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, minHeight));

        this.add(component);
        this.add(Box.createVerticalStrut(5));
    }
}
