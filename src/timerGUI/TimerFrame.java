/*
 * Copyright (c) 2010 Marvin S. Mueller All rights reserved. 
 */

package timerGUI;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import info.clearthought.layout.TableLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import timerCORE.RoundTimer;

/**
 * The graphical user interface for the timer.
 * 
 * @author Marvin Mueller
 */
public class TimerFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7329515489702993397L;

	private RoundTimer tCore;

	private JComponent valueComp;
	private JLabel textRoundTime;
	private JLabel textBreakTime;
	private JTextField fieldRoundTime;
	private JTextField fieldBreakTime;
	private JLabel textCurrRound;

	private JComponent clockComp;
	private JProgressBar clock;

	private JButton starter;
	private JButton reset;

	private int currRound;

	public TimerFrame() {
		super("RoundTimer by Marvin Mueller");
		tCore = new RoundTimer(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.currRound = 0;
		/* init */
		double[][] table = {
				{ 10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10 },
				{ 10, TableLayout.PREFERRED, 10, 30, 10, 30, 10 } };
		this.setLayout(new TableLayout(table));
		this.createFields();
		this.createClock();
		/* create buttons */
		starter = new JButton("START");
		reset = new JButton("RESET");
		starter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (starter.getText().equals("STOP")) {
					tCore.stop();
					starter.setText("CONTINUE");
					reset.setEnabled(true);
				} else {
					if(!setFieldsEnabled(false))
						return;
					tCore.start();
					starter.setText("STOP");
					reset.setEnabled(false);
				}
			}
		});
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tCore.reset();
				starter.setText("START");
				setFieldsEnabled(true);
				reset.setEnabled(false);
			}
		});
		starter.setEnabled(true);
		reset.setEnabled(false);
		this.add(starter, "1,3");
		this.add(reset, "1,5");

		this.setVisible(true);
	}

	protected boolean setFieldsEnabled(boolean enable) {
		if (enable) {
			this.fieldRoundTime.setEditable(true);
			this.fieldBreakTime.setEditable(true);
		} else {
			int b, r;
			try {
				b = Integer.parseInt(this.fieldBreakTime.getText());
				r = Integer.parseInt(this.fieldRoundTime.getText());
			} catch (NumberFormatException e1) {
				r = 180;
				this.fieldRoundTime.setText("180");
				b = 60;
				this.fieldBreakTime.setText("60");
				JFrame eFrame1 = new JFrame("Error E1");
				eFrame1.setSize(300, 100);
				eFrame1.add(new JLabel(
						"Bitte nur positive ganze Zahlen eingeben."));
				eFrame1.setVisible(true);
				return false;
			}
			this.fieldRoundTime.setEditable(false);
			this.fieldBreakTime.setEditable(false);
			this.tCore.setBreakTime(b);
			this.tCore.setRoundTime(r);
			clock.setMaximum(tCore.isBreak ? b : r);
			clock.setMinimum(0);
		}
		return true;
	}

	private void createClock() {
		double[][] clockTable = { { 100 }, { 500 } };
		clock = new JProgressBar(JProgressBar.VERTICAL);
		clockComp = new JPanel(new TableLayout(clockTable));
		clock.setForeground(Color.RED);
		clockComp.add(clock, "0,0");
		this.add(clockComp, "1,1");
	}

	private void createFields() {
		double[][] valueTable = { { TableLayout.PREFERRED, 20, 50 },
				{ 20, 20, 20, 10, 20, TableLayout.FILL } };
		valueComp = new JPanel(new TableLayout(valueTable));
		textRoundTime = new JLabel("Dauer der Runde (in sec.): ");
		textBreakTime = new JLabel("Dauer der Pausen (in sec.): ");
		fieldRoundTime = new JTextField("180");
		fieldBreakTime = new JTextField("60");
		textCurrRound = new JLabel(tCore.currRound + ". Runde");
		valueComp.add(textRoundTime, "0,0");
		valueComp.add(fieldRoundTime, "2,0");
		valueComp.add(textBreakTime, "0,2");
		valueComp.add(fieldBreakTime, "2,2");
		valueComp.add(textCurrRound, "0,4,2,4");
		this.add(valueComp, "3,1");
	}

	/**
	 * @return the rounds
	 */
	public int getRounds() {
		return currRound;
	}

	/**
	 * @param rounds
	 *            the rounds to set
	 */
	public void setRounds(int rounds) {
		this.currRound = rounds;
	}

	/**
	 * starts a new round with a break
	 */
	public void newRound() {
		clock.setForeground(Color.RED);
		clock.setValue(0);
		clock.setMaximum(tCore.getBreakTime());
		clockComp.validate();
		textCurrRound.setText(tCore.currRound + ". Runde");
		valueComp.validate();
	}

	/**
	 * starts the Round after a break
	 */
	public void startRound() {
		clock.setForeground(Color.GREEN);
		clock.setValue(0);
		clock.setMaximum(tCore.getRoundTime());
		clockComp.validate();
		textCurrRound.setText(tCore.currRound + ". Runde");
		valueComp.validate();
	}

	/**
	 * increases the time on the clock
	 */
	public void refreshTime() {
		clock.setValue(tCore.getCurrTime());
		clockComp.validate();
	}
}
