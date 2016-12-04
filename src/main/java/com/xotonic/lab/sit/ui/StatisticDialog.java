package com.xotonic.lab.sit.ui;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class StatisticDialog {

    private static  final Logger log = LogManager.getLogger(StatisticDialog.class.getName());

    private Statistic statistic;
    private OnConfirmListener onConfirmListener;
    private OnCancelListener onCancelListener;

    private JDialog dialog;
    private JTextPane area;
    private JButton ok;
    private JButton cancel;
    private Frame parent;

    public StatisticDialog(Frame parent)
    {
        this.parent = parent;
        onConfirmListener = () -> log.debug("Confirmed");
        onCancelListener = () -> log.debug("Canceled");

        setupUI();
    }

    private void setupUI() {
        dialog = new JDialog(parent);
        dialog.setLocationRelativeTo(parent);
        dialog.setTitle("Simulation statistic");
        dialog.pack();
        dialog.setModal(false);
        dialog.setSize(300, 300);
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        area = new JTextPane();
        area.setEnabled(false);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        rootPanel.add(area, c);

        ok = new JButton("Stop");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        rootPanel.add(ok, c);
        ok.addActionListener( e ->  { onConfirmListener.OnConfirm(); close(); });

        cancel = new JButton("Cancel");
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        rootPanel.add(cancel, c);
        cancel.addActionListener( e -> { onCancelListener.OnCancel(); close(); });

        dialog.setContentPane(rootPanel);
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public void setStatistic(Statistic statistic)
    {
        this.statistic = statistic;
    }

    void show()
    {
        log.debug("o/");
        area.setContentType("text/html");
        String text = String.format("<b><font size=\"5\" face=\"Arial\">Total cars: %s</font><br></b>"+
                "<font size=\"5\"><u>Total bikes: %s</u></font><br>"+
                "<font size=\"5\"><i>Total time:%s</i></font>",
                statistic.getTotalCarsCreated(),
                statistic.getTotalBikesCreated(),
                statistic.getTotalTime());
        area.setText(text);
        dialog.setVisible(true);
    }

    void close()
    {
        dialog.setVisible(false);
    }

    public interface OnConfirmListener
    {
        void OnConfirm();
    }

    public interface OnCancelListener
    {
        void OnCancel();
    }
}
