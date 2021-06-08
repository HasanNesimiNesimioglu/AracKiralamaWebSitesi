package yonetici;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Yonetici {
	@Id
	@GeneratedValue
	private Integer yoneticiID;
	private String ad;
	private String soyad;
	private String telefon;
	private String eposta;
	private String sifre;
	@Transient
	private Boolean mailKontrol;

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

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEposta() {
		return eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public Integer getYoneticiID() {
		return yoneticiID;
	}

	public void setYoneticiID(Integer yoneticiID) {
		this.yoneticiID = yoneticiID;
	}

	public Boolean getMailKontrol() {
		return mailKontrol;
	}

	public void setMailKontrol(Boolean mailKontrol) {
		this.mailKontrol = mailKontrol;
	}
}
