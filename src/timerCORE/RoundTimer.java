/*
 * Copyright (c) 2010, Marvin S. Mueller All rights reserved. 
 */


package timerCORE;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import timerGUI.TimerFrame;


/**
 * 
 * These class represents the core of a timer
 * to switch between rounds and breaks.
 * @author Marvin Mueller
 * 
 */
public class RoundTimer {
	private TimerFrame tFrame;

	private ClockThread clock;

	protected int roundTime; // in seconds
	protected int breakTime; // in seconds

	public boolean isBreak;

	private AudioClip sound;

	protected int currTime;
	public int currRound;

	/**
	 * Starts the GUI of the timer
	 * @param args
	 */
	public static void main(String[] args) {
		TimerFrame startFrame = new TimerFrame();
		startFrame.setSize(400, 700);
	}

	/**
	 * creates a new round timer with a given GUI
	 * @param tFrame the GUI
	 */
	public RoundTimer(TimerFrame tFrame) {
		super();
		this.tFrame = tFrame;
		this.isBreak = true;
		clock = new ClockThread(this);
		initSound();
	}

	/**
	 * starts the timer
	 */
	public void start() {
		clock.interrupt();
		clock = new ClockThread(this);
		clock.start();
	}

	/**
	 * stops the timer
	 */
	public void stop() {
		clock.interrupt();
	}

	/**
	 * resets the current time and current round on 0.
	 */
	public void reset() {
		currRound = 0;
		currTime = 0;
		isBreak = true;
		clock = new ClockThread(this);
		tFrame.newRound();
	}

	/**
	 * increases the current time
	 */
	protected void increaseTime() {
		if (currTime < (isBreak ? breakTime : roundTime)) {
			currTime++;
		} else {
			this.newRound();
		}
		tFrame.refreshTime();
	}

	/**
	 * switches between round and break
	 */
	private void newRound() {
		sound.play();
		if (!isBreak) {
			currTime = 0;
			tFrame.newRound();
		} else {
			currRound++;
			currTime = 0;
			tFrame.startRound();
		}
		isBreak = !isBreak;
	}

	/**
	 * initialize the soundfile
	 */
	private void initSound() {
		File f = new File("./sound/alarm_buzzer.wav");
		try {
			sound = Applet.newAudioClip(f.toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the isBreak
	 */
	public boolean isBreak() {
		return isBreak;
	}

	/**
	 * @param isBreak
	 *            the isBreak to set
	 */
	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	/**
	 * @return the roundTime
	 */
	public int getRoundTime() {
		return roundTime;
	}

	/**
	 * @param roundTime
	 *            the roundTime to set
	 */
	public void setRoundTime(int roundTime) {
		this.roundTime = roundTime;
	}

	/**
	 * @return the breakTime
	 */
	public int getBreakTime() {
		return breakTime;
	}

	/**
	 * @param breakTime
	 *            the breakTime to set
	 */
	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}

	/**
	 * returns the current time
	 * 
	 * @return current time
	 */
	public int getCurrTime() {
		return currTime;
	}
}
