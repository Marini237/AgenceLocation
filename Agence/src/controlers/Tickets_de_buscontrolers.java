package controlers;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.ConnexionMysql;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public  class Tickets_de_buscontrolers implements Initializable {
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;


	 @FXML
	    private JFXTextField txt_destination;

	    @FXML
	    private JFXTextField txt_nomdetrajet;

	    @FXML
	    private JFXComboBox<Integer> cb_trajetdebus;

	    @FXML
	    private ImageView imageTra;

	    @FXML
	    private JFXTextField txt_categoriebus;

	    @FXML
	    private JFXTextField txt_client;

	    @FXML
	    private JFXTextField txt_chauffeur;

	    @FXML
	    private JFXTextField txt_lieudedepart;
	    
	    @FXML
	    private JFXTextField txt_clients;

	    @FXML
	    private JFXTextField txt_NAP;

	    @FXML
	    private JFXComboBox<String> cb_chauffeur;
	    
	    @FXML
	    private JFXComboBox<String> cb_clients;
	    
	    @FXML
	    private JFXTextField txt_nombre;



	    @FXML
	    void imprimer() {
	    	Document docu = new Document();
	    	try {
				PdfWriter.getInstance(docu, new FileOutputStream("facture.pdf"));
				docu.open();
				String format="dd/MM/yy hh:mm";
				
				SimpleDateFormat formater= new SimpleDateFormat(format);
				java.util.Date date = new java.util.Date();
				com.itextpdf.text.Image img=com.itextpdf.text.Image.getInstance("C:\\Users\\KMAX\\Desktop\\doc de travaux java\\Agence\\src\\images\\Capture.PNG");
			    img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
			    docu.add(img);
			    docu.add(new Paragraph("Facture á: "+txt_clients.getText()+""
			    		+ "\nNomtrajet de trajet de voyage  : "+txt_nomdetrajet.getText()+""
			    		+ "\ndestination de trajet de voyage  : "+txt_destination.getText()+""
			    		+"\ncategoriebusd de trajet de voyage  : "+txt_categoriebus.getText()+""
			    		+"\nlieudedepart de trajet de voyage  : "+txt_lieudedepart.getText()+""
			    		+"\nchauffeur de trajet de voyage  : "+txt_chauffeur.getText()+""
			    		+"\nmontant a payer de trajet de voyage : "+txt_NAP.getText()+""
			    	    +"\n\nfait a Douala le "+formater.format(date)+""
			    		+"\nsignature ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLACK)));
			    docu.close();
			    Desktop.getDesktop().open(new File("facture.pdf"));
	    	} catch (FileNotFoundException  | DocumentException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    public void remplirtrajetdevoyage(){
	         String spl=" select DISTINCT Trajetdevoyage from busdevoyages";	
	         List<Integer> ids=new ArrayList<Integer>();
	         try {
				st=cnx.prepareStatement(spl);
				result=st.executeQuery();
				while(result.next()) {
					 ids.add(result.getInt("trajetdevoyage"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	       cb_trajetdebus.setItems(FXCollections.observableArrayList(ids));  
	    }

	    @FXML
	    void remplirChauffeur() {
	    	String sql1="select nomprenomC1 from chauffeur,busdevoyages where chauffeur.idC1=busdevoyages.chauffeur";
	    	List<String> names=new ArrayList<String>();
	    	try {
				st= cnx.prepareStatement(sql1);
				result=st.executeQuery();
				while(result.next()) {
					names.add(result.getString("nomprenomC1"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	cb_chauffeur.setItems(FXCollections.observableArrayList(names));

	    }
	    
	    public void remplirclient() {
	    	String sql3="select nomprenomC2 from clients,busdevoyages where clients.idC2=busdevoyages.clients";
	    	List<String> name=new ArrayList<String>();
	    	try {
				st= cnx.prepareStatement(sql3);
				result=st.executeQuery();
				while(result.next()) {
					name.add(result.getString("nomprenomC2"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	cb_clients.setItems(FXCollections.observableArrayList(name));

	    }
	    	
	    

	    @FXML
	    void search() {
	    	Integer id=cb_trajetdebus.getValue();
	    	String spl2=" select Nomtrajetvevoyage,Nomlieudedepart,nomcategorieBus,Nomdestination,image from trajetdevoyage,lieudedepart,categoriebus,destination where trajetdevoyage.lieudedepart=lieudedepart.idlieudedepart and trajetdevoyage.categoriebus=categoriebus.idcategoriebus and trajetdevoyage.destination=destination.iddestination and trajetdevoyage.idtrajetdevoyage ='"+id+"'";
            int nap; 
            int n;
	    	try {
				st=cnx.prepareStatement(spl2);
				result=st.executeQuery();
				byte byteimg[];
				Blob blob;
				if(result.next()) {
					txt_nomdetrajet.setText(result.getString("Nomtrajetvevoyage"));
					txt_destination.setText(result.getString("Nomdestination"));
					txt_categoriebus.setText(result.getString("nomcategoriebus"));
					txt_lieudedepart.setText(result.getString("Nomlieudedepart"));
					txt_chauffeur.setText(cb_chauffeur.getValue());
					txt_clients.setText(cb_clients.getValue());
					blob=result.getBlob("image");
					byteimg=blob.getBytes(1, (int) blob.length());
					Image img=new Image(new ByteArrayInputStream(byteimg),imageTra.getFitWidth(), imageTra.getFitHeight(), true,true);
					imageTra.setImage(img);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConnexionDB();
		remplirtrajetdevoyage();
		remplirclient() ;
	}

}
