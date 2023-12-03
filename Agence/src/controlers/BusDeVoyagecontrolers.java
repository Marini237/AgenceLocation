package controlers;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import application.ConnexionMysql;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BusDeVoyagecontrolers implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	 @FXML
	    private JFXTextField txt_searchtrajetdevoyageid;

	    @FXML
	    private JFXTextField txt_lieudedepart;
	    
	    @FXML
	    private JFXDatePicker datedefin;

	    @FXML
	    private JFXTextField txt_categoriebus;

	    @FXML
	    private JFXTextField txt_numerobus;

	    @FXML
	    private JFXTextField txt_destination;

	    @FXML
	    private JFXTextField txt_cnisearch;

	    @FXML
	    private JFXTextField txt_nomprenom;

	    @FXML
	    private JFXTextField txt_telephone;

	    @FXML
	    private JFXTextField txt_CNI;

	    @FXML
	    private JFXDatePicker datedepart;

	    @FXML
	    private JFXTimePicker tempdedepart;

	    @FXML
	    private JFXTextField txt_periode;
	    
	    @FXML
	    private JFXTextField txt_nomdetrajet;
	    
	    @FXML
	    private ImageView imagetrajet;

	    public boolean isBetween(java.sql.Date my_date, java.sql.Date my_depart,java.sql.Date my_fin) {
	    	return(my_date.equals(my_depart) || my_date.after(my_depart)) && (my_date.equals(my_fin) || my_date.before(my_fin));
	    }
	    
	    public boolean isOut(java.sql.Date datedepart,java.sql.Date datedefin, java.sql.Date my_depart,java.sql.Date my_fin) {
	    	return(datedepart.before(my_depart) && datedefin.after(my_fin));
	    }
	    
	    @FXML
	    void addbusdevoyage() {
	    	String sql2= " select idC1 from chauffeur where CNI='"+txt_CNI.getText()+"'";
	    	int chauffeur=0;
	    	try {
				st=cnx.prepareStatement(sql2);
				result= st.executeQuery();
				if(result.next()) {
				  chauffeur=result.getInt("idC1");	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	String sql3= " select idtrajetdevoyage from trajetdevoyage where Nomtrajetvevoyage='"+txt_nomdetrajet.getText()+"'";
	    	int trajetdevoyage=0;
	    	try {
				st=cnx.prepareStatement(sql3);
				result= st.executeQuery();
				if(result.next()) {
				  trajetdevoyage=result.getInt("idtrajetdevoyage");	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	Date datedd= Date.from(datedepart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    	java.sql.Date datedepart = new java.sql.Date(datedd.getTime());
	    	Date dateff= Date.from(datedefin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    	java.sql.Date datedefin = new java.sql.Date(dateff.getTime());
		      
		      String spl4="select datedefin, dateDeDepart from busdevoyages where trajetdevoyage='"+trajetdevoyage+"'";
		      boolean depart=false;
		      boolean fin=false;
		      java.sql.Date dated=null;
		      java.sql.Date datef=null;
		      Date d=null;
		      Date f=null;
		      try {
				st=cnx.prepareStatement(spl4);
				result=st.executeQuery();
				while(result.next()) {
				dated=result.getDate("dateDeDepart");
				datef= result.getDate("datedefin");
				if(isBetween(datedefin, dated,datef)) {
					fin=true;
				}
				if(isBetween(datedepart, dated,datef )==true) {
					depart=true;
				}
				if(isOut(datedepart, datedefin, dated, datef)==true) {
					fin=true;
					depart=true;
				}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
          if(fin==true || depart==true) {
        	  Alert alert= new Alert(AlertType.WARNING, "ce busdevoyage est occupé pendant la periode entre "+dated+" et "+datef+"",ButtonType.OK);
       	      alert.showAndWait();  
          }else {
        	  String sql5="insert into busdevoyages(trajetdevoyage,chauffeur,datedefin,dateDeDepart) values(?,?,?,?)";
        	  try {
				st=cnx.prepareStatement(sql5);
				st.setInt(1, trajetdevoyage);
				st.setInt(2, chauffeur);
				st.setDate(4, datedepart);
				st.setDate(3, datedefin);
				txt_nomdetrajet.setText("");
				txt_CNI.setText("");
				txt_nomprenom.setText("");
				txt_lieudedepart.setText("");
				txt_categoriebus.setText("");
				txt_destination.setText("");
				txt_numerobus.setText("");
				txt_telephone.setText("");
				this.datedepart.setValue(null);
				this.datedefin.setValue(null);
				imagetrajet.setImage(null);
				Alert alert= new Alert(AlertType.CONFIRMATION, "busdevoyage ajouter avec succes!",ButtonType.OK);
	       	      alert.showAndWait(); 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
          }
	    }

	   

		@FXML
	    void saerchchauffeur() {
	    	String spl="select nomprenomC1, CNI,telepC1 from chauffeur where CNI ='"+txt_cnisearch.getText()+"'";
	    	int nbr=0;
	    	try {
				st=cnx.prepareStatement(spl);
				result=st.executeQuery();
				if(result.next()) {
					txt_CNI.setText(result.getString("CNI"));
					txt_nomprenom.setText(result.getString("nomprenomC1"));
					txt_telephone.setText(result.getString("telepC1"));
					txt_cnisearch.setText("");
					nbr=1;
					
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
               if(nbr==0) {
            	   Alert alert= new Alert(AlertType.ERROR, " aucun chauffeur trouvé avec CNI= '"+txt_cnisearch.getText()+"",ButtonType.OK);
            	   alert.showAndWait();
               }
	    }

	    @FXML
	    void periode() {
	    	Date dated= Date.from(datedepart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    	java.sql.Date datedepart = new java.sql.Date(dated.getTime());
	    	Date datef= Date.from(datedefin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    	java.sql.Date datedefin = new java.sql.Date(datef.getTime());
	    	int days= daysBetwee(datedepart, datedefin);
	    	int mois= days/30;
	    	txt_periode.setText(String.valueOf(mois));
	    	
	    	
	    }
	    public int daysBetwee(java.sql.Date d1, java.sql.Date d2) {
	    	return (int) ((d2.getTime()-d1.getTime())/(1000*60*60*24));
	    }
	    

	    @FXML
	    void searchtrajetdevoyage() {
	    	String sql1="select Nomtrajetvevoyage,Nomlieudedepart,nomcategorieBus,Nomdestination,NomnumeroBus,image from trajetdevoyage,lieudedepart,categoriebus,destination,numerobus where trajetdevoyage.lieudedepart=lieudedepart.idlieudedepart and trajetdevoyage.categoriebus=categoriebus.idcategoriebus and trajetdevoyage.destination=destination.iddestination and trajetdevoyage.numerobus=numerobus.idNumeroBUS and trajetdevoyage.idtrajetdevoyage='"+txt_searchtrajetdevoyageid.getText()+"'";
	    	int nb=0;
	    	try {
				st=cnx.prepareStatement(sql1);
				result=st.executeQuery();
				byte ByteImg[];
			    Blob blob;
				if(result.next()) {
					txt_nomdetrajet.setText(result.getString("Nomtrajetvevoyage"));
					txt_lieudedepart.setText(result.getString("Nomlieudedepart"));
					txt_categoriebus.setText(result.getString("nomcategorieBus"));
					txt_destination.setText(result.getString("Nomdestination"));
					txt_numerobus.setText(result.getString("NomnumeroBus"));
					blob = result.getBlob("image");
					ByteImg=blob.getBytes(1, (int) blob.length());
					Image img=new Image(new ByteArrayInputStream(ByteImg),imagetrajet.getFitWidth(),imagetrajet.getFitHeight(),true,true);
					imagetrajet.setImage(img);
					txt_searchtrajetdevoyageid.setText("");
					nb=1;
					
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	    	if(nb==0) {
	    		Alert alert= new Alert(AlertType.ERROR, " aucun trajetdevoyage trouvé avec identifiant= '"+txt_searchtrajetdevoyageid.getText()+"",ButtonType.OK);
         	   alert.showAndWait();
	    	}

	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConnexionDB();
		
	}

}
