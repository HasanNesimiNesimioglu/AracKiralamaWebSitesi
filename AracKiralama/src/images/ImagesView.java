package images;

import java.util.ArrayList;
import java.util.List;

public class ImagesView {

	private List<String> images = new ArrayList<String>();

	public ImagesView() {

	}

	public List<String> getImages() {
		images.add("arabalar/araba1.jpg");
		images.add("arabalar/araba2.jpg");
		images.add("arabalar/araba3.jpg");
		images.add("arabalar/araba4.jpg");
		images.add("arabalar/araba5.jpg");
		return images;
	}

}
