package rezervasyon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import arac.Arac;
import entityUtil.EntityUtil;
import uye.Uye;

public class RezervasyonView {
	int sayac = 0;
	EntityManager entityManager = EntityUtil.getEntityManager();
	private FacesMessage message;

	private Uye uye = new Uye();
	private Arac arac = new Arac();
	private Rezervasyon rezervasyon = new Rezervasyon();
	private Date baslangicGun = new Date();
	private Date bitisGun = new Date();
	private int toplamKiraUcreti;

	private List<Date> invalidDates = new ArrayList<Date>();;
	private Date today = new Date();

	@SuppressWarnings({ "unchecked" })
	public String kapaliGunler() {
		int kiralananGun, baslangicGunu;
		int artanSayi = 0;
		long fark;
		long oneDay = 24 * 60 * 60 * 1000;// milisaniye cinsinden 1 gün
		List<Rezervasyon> list;
		Date date1 = new Date();

		invalidDates.add(date1);// bugünkü tarih eklenilir datepicker için

		list = entityManager.createQuery("from Rezervasyon where arac_aracID=" + arac.getAracID()).getResultList();

		// tarihlerin list nesnesine sýrayla eklenebilmesi için gerekli
		for (int i = 0; i < list.size(); i++) {

			// else kýsmý ilk baþlangýç tarihi+1 eklenir pc saatinden dolayý,
			// if kýsmý i-1 randevu bitiþi i.randevu baþlangýcý
			if (i != 0) {
				fark = (int) (list.get(i).getBaslangicTarihi().getTime() - list.get(i - 1).getBitisTarihi().getTime());
				baslangicGunu = (int) TimeUnit.DAYS.convert(fark, TimeUnit.MILLISECONDS);
			} else {
				fark = (int) (list.get(i).getBaslangicTarihi().getTime() - date1.getTime());
				baslangicGunu = (int) TimeUnit.DAYS.convert(fark, TimeUnit.MILLISECONDS) + 1;
			}

			// kiralanan gün sayýsý hesaplanýp datepicker kapatýlýr
			fark = list.get(i).getBitisTarihi().getTime() - list.get(i).getBaslangicTarihi().getTime();
			kiralananGun = (int) TimeUnit.DAYS.convert(fark, TimeUnit.MILLISECONDS);

			invalidDates.add(new Date(invalidDates.get(artanSayi).getTime() + (baslangicGunu * oneDay)));
			artanSayi++;

			for (int j = 0; j < kiralananGun; j++) {
				invalidDates.add(new Date(invalidDates.get(artanSayi).getTime() + oneDay));
				artanSayi++;
			}
		}
		return "arackirala.xhtml";

	}

	public void ucretHesapla() {

		if (bitisGun.getTime() - baslangicGun.getTime() <= 0) {

			message = new FacesMessage("Baþarýsýz", "Ýade tarihi, alýþ tarihinden ayný veya küçük olamaz");
			FacesContext.getCurrentInstance().addMessage(null, message);

		} else {
			int kiralananGunSayisiMs = (int) (bitisGun.getTime() - baslangicGun.getTime());
			int kiralananGunSayisi = (int) TimeUnit.DAYS.convert(kiralananGunSayisiMs, TimeUnit.MILLISECONDS);
			System.out.println("kiralanan gün: " + kiralananGunSayisi);
			toplamKiraUcreti = arac.getUcret() * kiralananGunSayisi;
		}

	}

	@SuppressWarnings("unchecked")
	public String RandevuKaydet() {

		List<Rezervasyon> list;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (bitisGun.getTime() - baslangicGun.getTime() <= 0) {

			message = new FacesMessage("Baþarýsýz", "Ýade tarihi, alýþ tarihinden ayný veya küçük olamaz");
			FacesContext.getCurrentInstance().addMessage(null, message);

		} else {

			String baslangicGunu = dateFormat.format(baslangicGun);
			String bitisGunu = dateFormat.format(bitisGun);
			list = entityManager.createQuery("from Rezervasyon where bitisTarihi BETWEEN '" + baslangicGunu + "' and '"
					+ bitisGunu + "' and arac_aracID=" + arac.getAracID()).getResultList();
			System.out.println("sorgu boyutu: " + list.size());

			if (list.size() > 0) {

				message = new FacesMessage("Baþarýsýz",
						"Seçtiðiniz tarih aralýðýnda herhangi bir randevu olmamasýna dikkat ediniz");
				FacesContext.getCurrentInstance().addMessage(null, message);

			} else {
				rezervasyon.setBaslangicTarihi(baslangicGun);
				rezervasyon.setBitisTarihi(bitisGun);
				rezervasyon.setUye(uye);
				rezervasyon.setArac(arac);
				rezervasyon.setUcret(String.valueOf(toplamKiraUcreti));

				entityManager = EntityUtil.getEntityManager();
				entityManager.getTransaction().begin();
				entityManager.persist(rezervasyon);
				entityManager.getTransaction().commit();

				rezervasyon = new Rezervasyon();
				arac = new Arac();
				uye = new Uye();
				toplamKiraUcreti = 0;
				baslangicGun = bitisGun = today;

				return "uyearaclar.xhtml";
			}
		}
		return null;
	}

	public void randevuSil() {
		entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(rezervasyon);
		entityManager.getTransaction().commit();
		rezervasyon = (new Rezervasyon());
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Rezervasyon> randevuListeGuncel() {
		Date today = new Date();
		String tarih = (today.getYear() + 1900) + "/" + (today.getMonth() + 1) + "/" + today.getDate();
		entityManager = EntityUtil.getEntityManager();
		return entityManager.createQuery("from Rezervasyon where bitisTarihi>'" + tarih + "'").getResultList();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Rezervasyon> randevuListeGecmis() {
		Date today = new Date();
		String tarih = (today.getYear() + 1900) + "/" + (today.getMonth() + 1) + "/" + today.getDate();
		entityManager = EntityUtil.getEntityManager();
		return entityManager.createQuery("from Rezervasyon where bitisTarihi<='" + tarih + "'").getResultList();
	}

	public Rezervasyon getRezervasyon() {
		return rezervasyon;
	}

	public void setRezervasyon(Rezervasyon rezervasyon) {
		this.rezervasyon = rezervasyon;
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

	public List<Date> getInvalidDates() {
		return invalidDates;
	}

	public Date getToday() {
		return today;
	}

	public Date getBaslangicGun() {
		return baslangicGun;
	}

	public void setBaslangicGun(Date baslangicGun) {
		this.baslangicGun = baslangicGun;
	}

	public Date getBitisGun() {
		return bitisGun;
	}

	public void setBitisGun(Date bitisGun) {
		this.bitisGun = bitisGun;
	}

	public int getToplamKiraUcreti() {
		return toplamKiraUcreti;
	}

	public void setToplamKiraUcreti(int toplamKiraUcreti) {
		this.toplamKiraUcreti = toplamKiraUcreti;
	}

}
