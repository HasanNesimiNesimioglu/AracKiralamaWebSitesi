
package uye;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.RowEditEvent;

import entityUtil.EntityUtil;
import rezervasyon.Rezervasyon;

public class UyeView {
	private Uye uye = new Uye();
	private Rezervasyon rezervasyon = new Rezervasyon();
	private String sifreKontrol;
	private FacesMessage message;

	@SuppressWarnings("unchecked")
	public String girisYap() {

		List<Uye> liste;
		try {
			EntityManager entityManager = EntityUtil.getEntityManager();
			liste = entityManager
					.createQuery("from Uye where eposta='" + uye.getEposta() + "' and sifre='" + uye.getSifre() + "'")
					.getResultList();

			if (liste.get(0).getEposta().equals(uye.getEposta()) && liste.get(0).getSifre().equals(uye.getSifre())) {
				uye.setUyeID(liste.get(0).getUyeID());
				uye.setAd(liste.get(0).getAd());
				uye.setSoyad(liste.get(0).getSoyad());
				uye.setEposta(liste.get(0).getEposta());
				uye.setTelefon(liste.get(0).getTelefon());
				uye.setDogumtarihi(liste.get(0).getDogumtarihi());
				uye.setSifre(liste.get(0).getSifre());

				message = new FacesMessage("Baþarýlý", "Giriþ yaptýnýz");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return "index";
			} else {
				message = new FacesMessage("Uyarý", "Kullanýcý adý veya þifre hatalý");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		} catch (Exception e) {
			message = new FacesMessage("Uyarý", "Kullanýcý adý veya þifre hatalý");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}

	}

	public void cikis() {
		uye = new Uye();
		System.out.println(uye.getAd() + "laan beees " + uye.getSoyad());
	}

	@SuppressWarnings("unchecked")
	public boolean mailKontrol() {
		List<Uye> liste;
		try {
			EntityManager entityManager = EntityUtil.getEntityManager();
			liste = entityManager.createQuery("from Uye where eposta='" + uye.getEposta() + "'").getResultList();
			if (liste.get(0).getEposta().equals(uye.getEposta())) {
				message = new FacesMessage("Uyarý", "Mail sistemde kayýtlý");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

	}

	public void kayitOl() {
		if (!mailKontrol()) {
			if (uye.getSifre().equals(getSifreKontrol())) {
				try {
					EntityManager entityManager = EntityUtil.getEntityManager();
					entityManager.getTransaction().begin();
					entityManager.persist(getUye());
					entityManager.getTransaction().commit();
					uye = new Uye();

					message = new FacesMessage("Baþarýlý", "Kayýt tamamlandý, giriþ yapabilirsiniz");
					FacesContext.getCurrentInstance().addMessage(null, message);

				} catch (Exception e) {
					// TODO: handle exception
					uye = new Uye();
					message = new FacesMessage("Uyarý", "Kayýt yapýlamadý");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			} else {
				message = new FacesMessage("Uyarý", "Þifreler eþleþmiyor");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Uye> getUyeListesi() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		return entityManager.createQuery("from Uye where ad!=''").getResultList();
	}

	public void kaydet() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(uye);
		entityManager.getTransaction().commit();
		uye = new Uye();
	}

	public void sil() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(uye);
		entityManager.getTransaction().commit();
		uye = new Uye();
	}

	public void guncelle() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(uye);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Rezervasyon> randevuListeGuncel() {
		Date today = new Date();
		String tarih = (today.getYear() + 1900) + "/" + (today.getMonth() + 1) + "/" + today.getDate();
		EntityManager entityManager = EntityUtil.getEntityManager();
		return entityManager
				.createQuery("from Rezervasyon where bitisTarihi>'" + tarih + "' and uye_uyeID=" + uye.getUyeID())
				.getResultList();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Rezervasyon> randevuListeGecmis() {
		Date today = new Date();
		String tarih = (today.getYear() + 1900) + "/" + (today.getMonth() + 1) + "/" + today.getDate();
		EntityManager entityManager = EntityUtil.getEntityManager();
		return entityManager
				.createQuery("from Rezervasyon where bitisTarihi<='" + tarih + "' and uye_uyeID=" + uye.getUyeID())
				.getResultList();
	}

	public void randevuSil() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(getRezervasyon());
		entityManager.getTransaction().commit();
		setRezervasyon(new Rezervasyon());
	}

	public void onRowEdit(RowEditEvent<Uye> event) {
		guncelle();
		message = new FacesMessage("Üye Düzenlendi",
				String.valueOf(event.getObject().getAd() + " " + event.getObject().getSoyad()));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onRowCancel(RowEditEvent<Uye> event) {
		message = new FacesMessage("Düzenleme Ýptal Edildi",
				String.valueOf(event.getObject().getAd() + " " + event.getObject().getSoyad()));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Uye getUye() {
		return uye;
	}

	public void setUye(Uye uye) {
		this.uye = uye;
	}

	public String getSifreKontrol() {
		return sifreKontrol;
	}

	public void setSifreKontrol(String sifreKontrol) {
		this.sifreKontrol = sifreKontrol;
	}

	public Rezervasyon getRezervasyon() {
		return rezervasyon;
	}

	public void setRezervasyon(Rezervasyon rezervasyon) {
		this.rezervasyon = rezervasyon;
	}
}
