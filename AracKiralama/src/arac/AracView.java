package arac;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.file.UploadedFile;

import entityUtil.EntityUtil;

public class AracView {
	
	private FacesMessage message;
	private Arac arac = new Arac();

	private UploadedFile resim;

	public void handleFileUpload(FileUploadEvent event) {
		System.out.println("fotograf yüklemesi deneniyor.......");
		try {
			resim = event.getFile();
			byte[] fileInBytes = new byte[(int) resim.getSize()];
			InputStream inputStream = resim.getInputStream();
			inputStream.read(fileInBytes);
			inputStream.close();
			arac.setResimByte(fileInBytes);

		} catch (Exception e) {
			basarisizFotografYukleme();
			System.out.println("hata------");
			e.printStackTrace();
		}
		basariliFotografYukleme();

	}

	public void basariliFotografYukleme() {
		message = new FacesMessage("Baþarýlý", "Fotoðrafýnýz yüklendi");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void basarisizFotografYukleme() {
		message = new FacesMessage("Baþarýsýz", "Fotoðraf yükleme sýrasýnda hata oluþtu");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onRowEdit(RowEditEvent<Arac> event) {
		guncelle();
		message = new FacesMessage("Araç Düzenlendi", String.valueOf(event.getObject().getMarka()));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onRowCancel(RowEditEvent<Arac> event) {
		message = new FacesMessage("Düzenleme Ýptal Edildi", String.valueOf(event.getObject().getMarka()));
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void kaydet() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(arac);
		entityManager.getTransaction().commit();
		arac = new Arac();
		
		message = new FacesMessage("Baþarýlý", "Araç Kaydedildi");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void sil() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(arac);
		entityManager.getTransaction().commit();
		arac = new Arac();
	}

	public void guncelle() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(arac);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Arac> getAracListesi() {
		EntityManager entityManager = EntityUtil.getEntityManager();
		List<Arac> list1 = entityManager.createQuery("from Arac where marka!=''").getResultList();

		Arac arac1 = new Arac();

		for (int i = 0; i < list1.size(); i++) {
			arac1 = list1.get(i);
			// Path target = Paths.get("c:\\dev\\carResim" + i + ".png");
			Path target = Paths
					.get("C:\\dev\\eclipse-workspace\\AracKiralama\\WebContent\\cars\\carResim" + i + ".png");
			if (list1.get(i).getResimByte() != null) {
				try {
					InputStream in = new ByteArrayInputStream(list1.get(i).getResimByte());
					BufferedImage bufferedImage = ImageIO.read(in);
					ImageIO.write(bufferedImage, "png", target.toFile());
					arac1.setAracResimUzantisi("/cars/carResim" + i + ".png");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				list1.set(i, arac1);
			}
		}
		return list1;
	}

	public Arac getArac() {
		return arac;
	}

	public void setArac(Arac arac) {
		this.arac = arac;
	}

	public UploadedFile getResim() {
		return resim;
	}

	public void setResim(UploadedFile resim) {
		this.resim = resim;
	}
}
