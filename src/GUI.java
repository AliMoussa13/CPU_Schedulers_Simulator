import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Controller.*;
import Model.Process;

public class GUI {
    private JFrame frame;
    private JPanel mainPanel;
    private CustomPanel chartPanel;
    private JScrollPane tablePane;
    private JTable table;

    private JButton computeBtn;
    private JLabel wtLabel;
    private JLabel wtResultLabel;
    private JLabel tatLabel;
    private JLabel tatResultLabel;
    private JComboBox<String> option;
    private DefaultTableModel model;
    private Vector<Process> processes; // Processes for scheduling

    public GUI() {
        processes = new Vector<>();
        model = new DefaultTableModel(new String[]{"Process", "COLOR" , "NAME", "PID", "Priority" }, 0);

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        tablePane = new JScrollPane(table);
        tablePane.setBounds(650, 26, 300, 250);  // Adjusted the bounds to move the table below the chart

        chartPanel = new CustomPanel();
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setPreferredSize(new Dimension(700, 100));
        JScrollPane chartPane = new JScrollPane(chartPanel);
        chartPane.setBounds(25, 26, 600, 300);  // Adjusted the bounds to move the chart above the table

        wtLabel = new JLabel("Average Waiting Time:");
        wtLabel.setBounds(650, 300, 180, 25);
        tatLabel = new JLabel("Average Turn Around Time:");
        tatLabel.setBounds(650, 325, 180, 25);
        wtResultLabel = new JLabel();
        wtResultLabel.setBounds(215, 425, 180, 25);
        tatResultLabel = new JLabel();
        tatResultLabel.setBounds(215, 450, 180, 25);

        option = new JComboBox<>(new String[]{"SRTF", "SJF", "PRIORITY", "AG"});
        option.setBounds(745, 380, 85, 20);

        computeBtn = new JButton("Calculate");
        computeBtn.setBounds(725, 420, 120, 25);
        computeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        computeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller controller = new Controller();
                Vector<Process> processes = new Vector<>();

                processes.add(new Process("p1","Black",0,17,4));
                processes.add(new Process("p2","Black",3,6,9));
                processes.add(new Process("p3","Black",4,10,3));
                processes.add(new Process("p4","Black",29,4,8));
                processes.add(new Process("p5","Black",0,9,1));

                String selected = (String) option.getSelectedItem();


                switch (selected) {
                    case "SRTF":
                        System.out.println("this is SRTF:");
                        controller.setProcessController(new SRTF());
                        controller.perform(processes);
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        break;
                    case "SJF":
                        System.out.println("this is SJF:");
                        controller.setProcessController(new SJF());
                        controller.perform(processes);
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        break;
                    case "Priority":
                        System.out.println("this is Priority:");
                        controller.setProcessController(new Priority());
                        controller.perform(processes);
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        break;
                    case "AG":
                        System.out.println("this is AG:");
                        controller.setProcessController(new AG(4));
                        controller.perform(processes);
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        break;
                    default:
                        return;
                }
                /*
                // Update the GUI with the results
                for (int i = 0; i < processes.size(); i++) {
                    Process process = processes.get(i);
                    model.setValueAt(process.getName(), i, 0);
                    model.setValueAt(process.getColor(), i, 1);
                    model.setValueAt(Integer.toString(process.getArrivalTime()), i, 2);
                    model.setValueAt(Integer.toString(process.getBurstTime()), i, 3);
                    model.setValueAt(Integer.toString(process.getPriority()), i, 4);
                }
                 // update GUI with the results
                for (int i = 0; i < processes.size(); i++) {
                    Process process = processes.get(i);
                    model.setValueAt(process.getWaitingTime(), i, 4);
                    model.setValueAt(process.getTurnaroundTime(), i, 5);
                }

                wtResultLabel.setText(Double.toString(Process.getAverageWaitingTime(processes)));
                tatResultLabel.setText(Double.toString(Process.getAverageTurnaroundTime(processes)));

                chartPanel.setTimeline(processController.getSortedProcesses());*/
            }
        });

        mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(1000, 480));
        mainPanel.add(chartPane);
        mainPanel.add(tablePane);
        mainPanel.add(wtLabel);
        mainPanel.add(tatLabel);
        mainPanel.add(wtResultLabel);
        mainPanel.add(tatResultLabel);
        mainPanel.add(option);
        mainPanel.add(computeBtn);

        frame = new JFrame("CPU Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(mainPanel);
        frame.pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }

    class CustomPanel extends JPanel {
        private Vector<Process> timeline;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (timeline != null) {
                int width = 30;

                for (int i = 0; i < timeline.size(); i++) {
                    Process process = timeline.get(i);
                    int x = 30 * (i + 1);
                    int y = 20;

                    g.drawRect(x, y, 30, 30);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    g.drawString(process.getName(), x + 10, y + 20);
                    g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    g.drawString(Integer.toString(process.getStartingTime()), x - 5, y + 45);

                    if (i == timeline.size() - 1) {
                        g.drawString(Integer.toString(process.getEndTime()), x + 27, y + 45);
                    }

                    width += 30;
                }

                this.setPreferredSize(new Dimension(width, 75));
            }
        }

        public void setTimeline(Vector<Process> timeline) {
            this.timeline = timeline;
            repaint();
        }
    }
}
