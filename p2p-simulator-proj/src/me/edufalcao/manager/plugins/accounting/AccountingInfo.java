package me.edufalcao.manager.plugins.accounting;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import me.edufalcao.manager.model.Peer;

public class AccountingInfo {
	
	private Peer peer;
	private double consumed = 0;	//this is what this.peer consumed
	private double donated = 0;		//this is what this.peer donated
	private int lastUpdated = 0;
	
	public AccountingInfo(Peer peer, int currentTime){
		this.peer = peer;
		consumed = donated = 0;
		lastUpdated = currentTime;
	}
	
	public String toString() {
		return "Peer(id)=" + peer.getId() + ", consumed=" + formatDouble(consumed) + ", donated="
				+ formatDouble(donated);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AccountingInfo) {
			AccountingInfo other = (AccountingInfo) obj;
			return other.getPeer().equals(peer);
		}
		return false;
	}
	
	public void addDonation(double donation) {
		this.donated += donation;
	}
	
	public void addConsumption(double consumption) {
		this.consumed += consumption;
	}
	
	public double getConsumed() {
		return consumed;
	}

	public double getDonated() {
		return donated;
	}
	
	public Peer getPeer() {
		return peer;
	}
	
	public int getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(int lastUpdated) {
		this.lastUpdated = lastUpdated;
	}	
	
	public static double formatDouble(double doubleValue) {
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.US);
		formatSymbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.000000", formatSymbols);
		return Double.valueOf(df.format(doubleValue));
	}

}
