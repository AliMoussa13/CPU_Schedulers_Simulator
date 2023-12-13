import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Controller.*;
import Model.Process;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.data.xy.XYSeriesCollection;

public class GUI {
    private JFrame frame;
    private JPanel mainPanel;
    private ProcessBarChart chartPanel;
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
        model = new ProcessTableModel();

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Color.class, new ColorRenderer());

        tablePane = new JScrollPane(table);
        tablePane.setBounds(650, 26, 300, 250);  // Adjusted the bounds to move the table below the chart

        chartPanel = new ProcessBarChart();
        chartPanel.setBounds(25, 26, 600, 300);
        // JScrollPane chartPane = new JScrollPane(chartPanel);
        // chartPane.setBounds(25, 26, 600, 300);  // Adjusted the bounds to move the chart above the table

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
                model.setRowCount(0); // Remove all rows from the table

                Controller controller = new Controller();
                Vector<Process> processes = new Vector<>();

                processes.add(new Process("p1",Color.YELLOW,0,17,4));
                processes.add(new Process("p2",Color.RED,3,6,9));
                processes.add(new Process("p3",Color.BLUE,4,10,3));
                processes.add(new Process("p4",Color.BLACK,29,4,8));
                //processes.add(new Process("p5","Black",0,9,1));

                for (int i = 0; i < processes.size(); i++) {
                    Process process = processes.get(i);
                    Color color = i % 2 == 0 ? Color.RED : Color.GREEN;

                    model.addRow(new Object[]{
                            i, // Process
                            color, // Color
                            process.getName(), // NAME
                            process.getName(), // PID
                            process.getPriority() // Priority
                    });
                }

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
                    case "PRIORITY":
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
                chartPanel.setDataset(processes);
                chartPanel.repaint();
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
                tatResultLabel.setText(Double.toString(Process.getAverageTurnaroundTime(processes))); */

                chartPanel.setDataset(processes);
            }
        });

        mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(1000, 480));
        mainPanel.add(chartPanel);
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

    class ProcessTableModel extends DefaultTableModel {
        public ProcessTableModel() {
            super(new String[]{"Process", "COLOR" , "NAME", "PID", "Priority" }, 0);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }
    }

    class ColorRectangle extends JComponent {
        Color color;
        int width = 15;
        int height = 15;

        public ColorRectangle(Color color) {
            this.color = color;

            setToolTipText("RGB value: " + color.getRed() + ", "
                    + color.getGreen() + ", "
                    + color.getBlue());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int horizontalCenter = (this.getWidth() - width) / 2;
            int verticalCenter = (this.getHeight() - height) / 2;

            g.setColor(color);
            g.fillRect(horizontalCenter, verticalCenter, width, height);
        }
    }

    class ColorRenderer implements TableCellRenderer {
        public ColorRenderer() {
            // setOpaque(true);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object color,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            Color newColor = (Color)color;
            /* setBackground(newColor); */

            ColorRectangle rectangle = new ColorRectangle(newColor);

            return rectangle;
        }
    }

    class ProcessBarChart extends JPanel {
        JFreeChart chart;
        ChartPanel chartPanel;

        /**
         * Constructs a new application frame.
         */
        public ProcessBarChart() {
            super();
            createChart();

            chartPanel = new ChartPanel(chart);
            chartPanel.setBackground(Color.white);
            chartPanel.setPreferredSize(new Dimension(600, 300));

            add(chartPanel);
        }

        private void createChart() {
            XYBarRenderer xyRenderer = new XYBarRenderer();

            xyRenderer.setShadowVisible(false);
            xyRenderer.setUseYInterval(true);
            xyRenderer.setBarPainter(new StandardXYBarPainter());

            NumberAxis timeAxis = new NumberAxis();

            timeAxis.setAutoRange(true);

            XYPlot plot = new XYPlot(
                    new XYSeriesCollection(),
                    new SymbolAxis("", new String[]{}),
                    timeAxis,
                    xyRenderer
            );

            plot.setOrientation(PlotOrientation.HORIZONTAL);
            plot.setBackgroundPaint(Color.WHITE);

            chart = new JFreeChart(plot);

            /* dataset = new DefaultCategoryDataset();
            chart = ChartFactory.createBarChart(
                    "Process Bar Chart",
                    "Process",
                    "Time",
                    dataset,
                    PlotOrientation.HORIZONTAL,
                    false,
                    true,
                    false
            );

            final CategoryPlot plot = chart.getCategoryPlot();

            plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT); */
        }

        public void setDataset(Vector<Process> processes) {
            XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();
            XYIntervalSeries[] series = new XYIntervalSeries[processes.size()];
            String[] categories = new String[series.length];

            XYPlot plot = (XYPlot) chart.getPlot();
            XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();

            for (int i = 0; i < series.length; i++) {
                Process process = processes.get(i);

                // renderer.setSeriesPaint(i, some color);

                String seriesKey = String.format("Process %d", i);

                categories[i] = seriesKey;
                series[i] = new XYIntervalSeries(seriesKey);

                int point0 = process.getArrivalTime();
                int point1 = point0 + 5;

                int point2 = point1 + 2;
                int point3 = point2 + 3;

                series[i].add(i, i - 0.2, i + 0.2, point0, point0, point1);
                series[i].add(i, i - 0.2, i + 0.2, point2, point2, point3);

                dataset.addSeries(series[i]);
            }

            plot.setDomainAxis(new SymbolAxis("", categories));
            plot.setDataset(dataset);
        }
    }
}
