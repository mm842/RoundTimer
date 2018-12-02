/*
 * Copyright (c) 2010, Marvin S. Mueller All rights reserved. 
 */

package timerCORE;

/**
 * The clock of the Timer.
 * 
 * @author Marvin Mueller
 * 
 */
public class ClockThread extends Thread {

	private RoundTimer tCore;

	/**
	 * the duration of breaks
	 */
	protected int precision = 1000;

	public ClockThread() {
		super();
	}

	/**
	 * @param tCore
	 */
	public ClockThread(RoundTimer tCore) {
		super();
		this.tCore = tCore;
	}

	/**
	 * increases the time with precision saved in object
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Thread.sleep(precision);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			tCore.increaseTime();
			/* get sure the thread terminates */
			if(tCore.currTime >= tCore.breakTime + tCore.roundTime)
				return;
		}
	}

}
