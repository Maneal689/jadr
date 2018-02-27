import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.BorderFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddProjectDialog extends JDialog{
    private JPanel pan = new JPanel();
    private AddProjectDialog self;
    private ProjectInfo pInfo;

    public AddProjectDialog(JFrame parent, ProjectInfo pInfo){
        super(parent, "Project", true);
        this.setContentPane(pan);
        this.setSize(375, 250);
        this.setResizable(true);
        this.self = this;
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        this.pInfo = pInfo;


        initComponent();
    }

    private void initComponent(){
        JPanel projectNamePan = new JPanel();
        projectNamePan.setLayout(new BoxLayout(projectNamePan, BoxLayout.LINE_AXIS));
        JLabel labName = new JLabel("Name: ");
        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension((int)nameField.getMaximumSize().getWidth(), (int)nameField.getMinimumSize().getHeight()));
        projectNamePan.add(labName);
        projectNamePan.add(Box.createHorizontalGlue());
        projectNamePan.add(nameField);
        projectNamePan.repaint();

        JPanel descriptionPan = new JPanel();
        descriptionPan.setLayout(new BoxLayout(descriptionPan, BoxLayout.PAGE_AXIS));
        JTextField descriptionField = new JTextField();
        descriptionPan.add(descriptionField);
        descriptionPan.setBorder(BorderFactory.createTitledBorder("Description"));

        JPanel sliderPan = new JPanel();
        sliderPan.setLayout(new BoxLayout(sliderPan, BoxLayout.LINE_AXIS));
        sliderPan.setBorder(BorderFactory.createTitledBorder("Work progress"));
        JSlider workSlider = new JSlider();
        workSlider.setMaximum(100);
        workSlider.setMinimum(0);
        workSlider.setValue(0);
        workSlider.setPaintTicks(true);
        workSlider.setPaintLabels(true);
        workSlider.setMinorTickSpacing(5);
        workSlider.setMajorTickSpacing(10);
        sliderPan.add(workSlider);

        //--- DATES ---//
        Box datesBeginPan = Box.createHorizontalBox();
        JLabel beginLab = new JLabel("Begin date\n(yyyy-MM-dd HH:mm): ");
        JTextField beginField = new JTextField();
        datesBeginPan.add(beginLab);
        datesBeginPan.add(beginField);
        beginField.setMaximumSize(new Dimension((int)beginField.getMaximumSize().getWidth(), (int)beginField.getMinimumSize().getHeight()));

        Box datesEndPan = Box.createHorizontalBox();
        JLabel endLab = new JLabel("End date: ");
        JTextField endField = new JTextField();
        datesEndPan.add(endLab);
        datesEndPan.add(endField);
        endField.setMaximumSize(new Dimension((int)endField.getMaximumSize().getWidth(), (int)endField.getMinimumSize().getHeight()));

        //--- Buttons ---//
        Box buttonsPan = Box.createHorizontalBox();
        buttonsPan.add(Box.createHorizontalGlue());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                self.setVisible(false);
            }
        });
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                boolean good = false;
                try{
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime.parse(beginField.getText(), dateFormat);
                    LocalDateTime.parse(endField.getText(), dateFormat);
                    good = true;
                }
                catch(DateTimeParseException e){}
                if (good && !((nameField.getText()).equals(""))){
                    self.pInfo.name = nameField.getText();
                    self.pInfo.description = descriptionField.getText();
                    self.pInfo.progress = (int)workSlider.getValue();
                    self.pInfo.dateBegin = beginField.getText();
                    self.pInfo.dateEnd = endField.getText();
                    self.setVisible(false);
                    self.dispose();
                }
            }
        });
        buttonsPan.add(cancelButton);
        buttonsPan.add(okButton);

        pan.add(projectNamePan);
        pan.add(descriptionPan);
        pan.add(sliderPan);
        pan.add(datesBeginPan);
        pan.add(datesEndPan);
        pan.add(buttonsPan);
        this.setVisible(true);
    }
}
