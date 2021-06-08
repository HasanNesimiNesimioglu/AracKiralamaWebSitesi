package arac;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class Arac {
	@Id
	@GeneratedValue
	private Integer aracID;
	private String marka;
	private String motor;
	private String sanziman;
	private String yakitTuru;
	private String koltukSayisi;
	private Integer ucret;
	@Lob
	private byte[] resimByte;
	@Transient
	private String aracResimUzantisi;

	public Arac() {
	}

	public Arac(String marka, String motor, String sanziman, String yakitTuru, String koltukSayisi, Integer ucret,
			byte[] resimByte, String aracResimUzantisi) {

		this.marka = marka;
		this.motor = motor;
		this.sanziman = sanziman;
		this.yakitTuru = yakitTuru;
		this.koltukSayisi = koltukSayisi;
		this.ucret = ucret;
		this.resimByte = resimByte;
		this.aracResimUzantisi = aracResimUzantisi;
	}

	public Integer getAracID() {
		return aracID;
	}

	public void setAracID(Integer aracID) {
		this.aracID = aracID;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getSanziman() {
		return sanziman;
	}

	public void setSanziman(String sanziman) {
		this.sanziman = sanziman;
	}

	public String getYakitTuru() {
		return yakitTuru;
	}

	public void setYakitTuru(String yakitTuru) {
		this.yakitTuru = yakitTuru;
	}

	public String getKoltukSayisi() {
		return koltukSayisi;
	}

	public void setKoltukSayisi(String koltukSayisi) {
		this.koltukSayisi = koltukSayisi;
	}

	public Integer getUcret() {
		return ucret;
	}

	public void setUcret(Integer ucret) {
		this.ucret = ucret;
	}

	public byte[] getResimByte() {
		return resimByte;
	}

	public void setResimByte(byte[] resimByte) {
		this.resimByte = resimByte;
	}

	public String getAracResimUzantisi() {
		return aracResimUzantisi;
	}

	public void setAracResimUzantisi(String aracResimUzantisi) {
		this.aracResimUzantisi = aracResimUzantisi;
	}

}
