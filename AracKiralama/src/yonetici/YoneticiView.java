package yonetici;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.RowEditEvent;

import entityUtil.EntityUtil;

public class YoneticiView {
	private Yonetici yonetici = new Yonetici();
	private Boolean girisKontrol = false;

	private FacesMessage message;

	@SuppressWarnings("unchecked")
	public String girisYap() {
		List<Yonetici> liste;
		try {
			EntityManager entityManager = EntityUtil.getEntityManager();
			liste = entityManager.createQuery(
					"from Yonetici where eposta='" + yonetici.getEposta() + "' and sifre='" + yonetici.getSifre() + "'")
					.getResultList();
			if (liste.get(0).getEposta().equals(yonetici.getEposta())
					&& liste.get(0).getSifre().equals(yonetici.getSifre())) {

				yonetici.setYoneticiID(liste.get(0).getYoneticiID());
				yonetici.setAd(liste.get(0).getAd());
				yonetici.setSoyad(liste.get(0).getSoyad());
				yonetici.setTelefon(liste.get(0).getTelefon());
				yonetici.setEposta(liste.get(0).getEposta());
				yonetici.setSifre(liste.get(0).getSifre());
				setGirisKontrol(false);
				return "yoneticipanel";
			} else {
				setGirisKontrol(true);
				return null;
			}
		} catch (Exception e) {
			setGirisKontrol(true);
			return null;
		}
	}

	public void guncelle() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(yonetici);
		entityManager.getTransaction().commit();
	}

	public String cikis() {
		yonetici = new Yonetici();
		return "yoneticigiris";
	}

	@SuppressWarnings("unchecked")
	public List<Yonetici> getYoneticiListesi() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		return entityManager.createQuery("from Yonetici where ad!=''").getResultList();
	}

	public void onRowEdit(RowEditEvent<Yonetici> event) {
		guncelle();
		message = new FacesMessage("Yönetici Bilgileriniz Düzenlendi",
				String.valueOf(event.getObject().getAd() + " " + event.getObject().getSoyad()));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onRowCancel(RowEditEvent<Yonetici> event) {
		message = new FacesMessage("Düzenleme Ýptal Edildi",
				String.valueOf(event.getObject().getAd() + " " + event.getObject().getSoyad()));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Yonetici getYonetici() {
		return yonetici;
	}

	public void setYonetici(Yonetici yonetici) {
		this.yonetici = yonetici;
	}

	public Boolean getGirisKontrol() {
		return girisKontrol;
	}

	public void setGirisKontrol(Boolean girisKontrol) {
		this.girisKontrol = girisKontrol;
	}
}
