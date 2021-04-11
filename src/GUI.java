import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Scanner;

public class GUI extends JFrame implements ActionListener {
    private JButton b_countdown, b_start, b_lap, b_stop;
    private JTextField t_countdown, t_timer;
    private TextArea t_laps;
    private Timer countdown, timer;
    private final Container contentPane = this.getContentPane();

    public GUI() {
        //  Set application close
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //  Table name and dimensions
        this.setTitle("Election Data");
        this.setSize(500,300);
        this.setResizable(false);
        //  Setup group layout
        GridBagLayout layout = new GridBagLayout();
        contentPane.setLayout(layout);
        //  Initialize content
        b_countdown = new JButton("Start Countdown");
        b_countdown.addActionListener(this);
        t_countdown = new JTextField(18);
        b_start = new JButton("Start");
        b_start.addActionListener(this);
        b_lap = new JButton("Lap");
        b_lap.addActionListener(this);
        b_stop = new JButton("Pause");
        b_stop.addActionListener(this);
        t_timer = new JTextField(14);
        t_laps = new TextArea();

        //  Setup Layout
        JPanel firstRow = new JPanel();
        firstRow.setLayout(new FlowLayout());
        firstRow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        firstRow.add(b_countdown);
        firstRow.add(t_countdown);
        JPanel secondRow = new JPanel();
        secondRow.setLayout(new FlowLayout());
        secondRow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        secondRow.add(b_start);
        secondRow.add(b_lap);
        secondRow.add(b_stop);
        secondRow.add(t_timer);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        contentPane.add(firstRow,c);
        c.gridy = 1;
        contentPane.add(secondRow,c);
        c.weighty = 2;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        contentPane.add(t_laps,c);
        //  Update view
        this.setVisible(true);
    }

    public void timer(String time) {
        t_timer.setText(time);
    }

    public void countdown(String time) {
        t_countdown.setText(time);
    }

    private void countdownStop() {
        countdown.setRunning(false);
        countdown = null;
        b_countdown.setText("Start Countdown");
        t_countdown.setEditable(true);
    }

    public void countdownFinish() {
        JOptionPane.showMessageDialog(this, "Countdown has ended.");
        countdownStop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //  Countdown button
        if (e.getSource() == b_countdown) {
            if (countdown == null) {
                Scanner sc = new Scanner(t_countdown.getText());
                countdown = new Timer(scanInput(sc), true, this);
                b_countdown.setText("Stop Countdown");
                t_countdown.setEditable(false);
            } else {
                countdownStop();
            }
        //  Start timer
        } else if (e.getSource() == b_start) {
            if (timer == null) {
                Scanner sc = new Scanner(t_timer.getText());
                timer = new Timer(scanInput(sc), false, this);
                b_start.setText("Reset");
                t_timer.setEditable(false);
            } else {
                timer.setRunning(false);
                timer = null;
                b_start.setText("Start");
                t_timer.setText("");
                t_laps.setText("");
                t_timer.setEditable(true);
            }
        //  Get Lap
        } else if (e.getSource() == b_lap) {
            if (timer != null && timer.isRunning()) {
                t_laps.setText(t_laps.getText()+"\nLap: "+t_timer.getText());
            }
        //  Pause timer
        } else if (e.getSource() == b_stop) {
            if (timer != null && timer.isRunning()) {
                timer.setRunning(false);
                timer = null;
                t_timer.setEditable(true);
                b_stop.setText("Continue");
            } else if (timer == null) {
                Scanner sc = new Scanner(t_timer.getText());
                timer = new Timer(scanInput(sc), false, this);
                t_timer.setEditable(false);
                b_stop.setText("Pause");
            }
        }
    }

    private int scanInput(Scanner sc) {
        sc.useDelimiter(":");
        int[] nums = new int[0];
        while (sc.hasNextInt()) {
            nums = Arrays.copyOf(nums, nums.length + 1);
            nums[nums.length - 1] = sc.nextInt();
        }
        if (nums.length == 1) {
            return nums[0] * 1000;
        } else if (nums.length == 2) {
            return nums[0] * 1000 + nums[1];
        } else if (nums.length == 3) {
            return nums[0] * 60000 + nums[1] * 1000 + nums[2];
        } else {
            return 0;
        }
    }
}
