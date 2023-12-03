package controlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Homecontrolers implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
     private Parent fxml;
     @FXML
     private AnchorPane root;

     @FXML
     private ImageView image_user;

     @FXML
     private Label lab_userName;

    @FXML
    void Bus_Voyage(MouseEvent event) {
    	try {
			fxml= FXMLLoader.load(getClass().getResource("/Views/BusDeVoyage.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void Chauffeur(MouseEvent event) {
    	try {
			fxml= FXMLLoader.load(getClass().getResource("/Views/Chauffeurview.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void Clients(MouseEvent event) {
    	try {
			fxml= FXMLLoader.load(getClass().getResource("/Views/Clientsview.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    

    @FXML
    void Les_Trajets_Voyage(MouseEvent event) {
    	try {
			fxml= FXMLLoader.load(getClass().getResource("/Views/LesTrajetVoyages.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

   
    @FXML
    void Tickets_de_bus(MouseEvent event) {
    	try {
			fxml= FXMLLoader.load(getClass().getResource("/Views/Tickets_de bus.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConnexionDB();
		String spl="select userName,photo from userconnect where id=(select MAX(id) from userconnect)";
		byte byteimg[];
		Blob blob;
		try {
			st= cnx.prepareStatement(spl);
			result= st.executeQuery();
			if(result.next()) {
				lab_userName.setText(result.getString("userName"));
				blob=result.getBlob("photo");
				 byteimg=blob.getBytes(1, (int) blob.length());
				    Image img=new Image(new ByteArrayInputStream(byteimg), image_user.getFitWidth(), image_user.getFitHeight(), true, true);
				    image_user.setImage(img);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			fxml= FXMLLoader.load(getClass().getResource("/Views/Clientsview.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
		
	}

