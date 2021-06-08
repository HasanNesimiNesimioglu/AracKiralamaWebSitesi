package uye;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Uye {
	@Id
	@GeneratedValue
	private Integer uyeID;
	private String ad;
	private String soyad;
	private String eposta;
	private String telefon;
	private Date dogumtarihi;
	private String sifre;

	public Uye() {
		super();
	}

	public Uye(String ad, String soyad, String eposta, String telefon, Date dogumtarihi, String sifre) {
		super();
		this.ad = ad;
		this.soyad = soyad;
		this.eposta = eposta;
		this.telefon = telefon;
		this.dogumtarihi = dogumtarihi;
		this.sifre = sifre;
	}

	public Integer getUyeID() {
		return uyeID;
	}

	public void setUyeID(Integer uyeID) {
		this.uyeID = uyeID;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public String getEposta() {
		return eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Date getDogumtarihi() {
		return dogumtarihi;
	}

	public void setDogumtarihi(Date dogumtarihi) {
		this.dogumtarihi = dogumtarihi;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

}
