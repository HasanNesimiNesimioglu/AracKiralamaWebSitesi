package rezervasyon;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import arac.Arac;
import uye.Uye;

@Entity
public class Rezervasyon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer satisID;
	private Date baslangicTarihi;
	private Date bitisTarihi;
	private String ucret;
	@OneToOne
	private Uye uye;
	@OneToOne
	private Arac arac;

	public Rezervasyon() {

	}

	public Rezervasyon(Date baslangicTarihi, Date bitisTarihi, String ucret, Uye uye, Arac arac) {

		this.baslangicTarihi = baslangicTarihi;
		this.bitisTarihi = bitisTarihi;
		this.ucret = ucret;
		this.uye = uye;
		this.arac = arac;
	}

	public Integer getSatisID() {
		return satisID;
	}

	public Date getBaslangicTarihi() {
		return baslangicTarihi;
	}

	public void setBaslangicTarihi(Date baslangicTarihi) {
		this.baslangicTarihi = baslangicTarihi;
	}

	public Date getBitisTarihi() {
		return bitisTarihi;
	}

	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}

	public String getUcret() {
		return ucret;
	}

	public void setUcret(String ucret) {
		this.ucret = ucret;
	}

	public Uye getUye() {
		return uye;
	}

	public void setUye(Uye uye) {
		this.uye = uye;
	}

	public Arac getArac() {
		return arac;
	}

	public void setArac(Arac arac) {
		this.arac = arac;
	}

	@Override
	public String toString() {
		return "Rezervasyon [satisID=" + satisID + ", baslangicTarihi=" + baslangicTarihi + ", bitisTarihi="
				+ bitisTarihi + ", ucret=" + ucret + ", uye=" + uye + ", arac=" + arac + "]";
	}

}
