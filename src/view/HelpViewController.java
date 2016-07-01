package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import tools.IO;

public class HelpViewController {

	@FXML
	private TextArea textarea;
	
	public HelpViewController() {
	}
	
	public void setText() {
		StringBuffer buf = new StringBuffer();
		try (InputStream fis = IO.getIS("/view/help.txt")) {
			BufferedReader in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		textarea.setText(buf.toString());
	}

}
