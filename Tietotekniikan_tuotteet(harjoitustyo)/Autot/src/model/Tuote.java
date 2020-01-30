package model;

public class Tuote {
	private String tuote_id_nimi;
	private String tuote_malli; 
	private int tuote_julkaisuvuosi;
	private int tuote_id;
	private String tuote_kuvaus;
	private double tuote_hinta;

	public Tuote() {
		super();
	}
	
	public Tuote(String tuote_id_nimi, String tuote_malli, int tuote_julkaisuvuosi, int tuote_id, String tuote_kuvaus, double tuote_hinta) {
		super();
		this.tuote_id_nimi = tuote_id_nimi;
		this.tuote_malli = tuote_malli;
		this.tuote_julkaisuvuosi = tuote_julkaisuvuosi;
		this.tuote_id = tuote_id;
		this.tuote_kuvaus = tuote_kuvaus;
		this.tuote_hinta = tuote_hinta;
	}

	public String getTuote_id_nimi() {
		return tuote_id_nimi;
	}

	public void setTuote_id_nimi(String tuote_id_nimi) {
		this.tuote_id_nimi = tuote_id_nimi;
	}

	public String getTuote_malli() {
		return tuote_malli;
	}

	public void setTuote_malli(String tuote_malli) {
		this.tuote_malli = tuote_malli;
	}

	public int getTuote_julkaisuvuosi() {
		return tuote_julkaisuvuosi;
	}

	public void setTuote_julkaisuvuosi(int tuote_julkaisuvuosi) {
		this.tuote_julkaisuvuosi = tuote_julkaisuvuosi;
	}

	public int getTuote_id() {
		return tuote_id;
	}

	public void setTuote_id(int tuote_id) {
		this.tuote_id = tuote_id;
	}

	public String getTuote_kuvaus() {
		return tuote_kuvaus;
	}

	public void setTuote_kuvaus(String tuote_kuvaus) {
		this.tuote_kuvaus = tuote_kuvaus;
	}

	public double getTuote_hinta() {
		return tuote_hinta;
	}

	public void setTuote_hinta(double tuote_hinta) {
		this.tuote_hinta = tuote_hinta;
	}

	@Override
	public String toString() {
		return "Tuote [tuote_id_nimi=" + tuote_id_nimi + ", tuote_malli=" + tuote_malli + ", tuote_julkaisuvuosi="
				+ tuote_julkaisuvuosi + ", tuote_id=" + tuote_id + ", tuote_kuvaus=" + tuote_kuvaus + ", tuote_hinta="
				+ tuote_hinta + "]";
	}
	
	

}
