package model;

public class Auto {
	private String rekNo, merkki, malli;
	private int vuosi;
	public Auto() {
		super();		
	}
	public Auto(String rekNo, String merkki, String malli, int vuosi) {
		super();
		this.rekNo = rekNo;
		this.merkki = merkki;
		this.malli = malli;
		this.vuosi = vuosi;
	}
	public String getRekNo() {
		return rekNo;
	}
	public void setRekNo(String rekNo) {
		this.rekNo = rekNo;
	}
	public String getMerkki() {
		return merkki;
	}
	public void setMerkki(String merkki) {
		this.merkki = merkki;
	}
	public String getMalli() {
		return malli;
	}
	public void setMalli(String malli) {
		this.malli = malli;
	}
	public int getVuosi() {
		return vuosi;
	}
	public void setVuosi(int vuosi) {
		this.vuosi = vuosi;
	}
	@Override
	public String toString() {
		return "Auto [rekNo=" + rekNo + ", merkki=" + merkki + ", malli=" + malli + ", vuosi=" + vuosi + "]";
	}
	
	
	

}
