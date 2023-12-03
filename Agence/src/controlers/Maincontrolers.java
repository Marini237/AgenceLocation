package controlers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Maincontrolers implements Initializable{
	

    @FXML
    private JFXButton btn_seconnecter;

    @FXML
    private JFXButton btn_senregistrer;

    @FXML
    private VBox vBox;
    private Parent fxml;

    @FXML
    void openSignln() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(1),vBox);
		t.setToX(vBox.getLayoutX() * 5.5);
		t.play();
		t.setOnFinished(e ->{
			try {
				fxml= FXMLLoader.load(getClass().getResource("/Views/Signlnview.fxml"));
				vBox.getChildren().removeAll();
				vBox.getChildren().setAll(fxml);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
    }

    @FXML
    void qpenSignUp() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(1),vBox);
    	t.setToX(5);
    	t.play();
    	t.setOnFinished(e ->{
			try {
				fxml= FXMLLoader.load(getClass().getResource("/Views/SignUpview.fxml"));
				vBox.getChildren().removeAll();
				vBox.getChildren().setAll(fxml);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1),vBox);
		t.setToX(vBox.getLayoutX() * 5.5);
		t.play();
		
		
	}

}
