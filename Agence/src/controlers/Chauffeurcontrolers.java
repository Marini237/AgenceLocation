package controlers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import Models.Chauffeur;
import Models.Clients;
import application.ConnexionMysql;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Chauffeurcontrolers implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	  @FXML
	    private JFXTextField txt_searchCNI;

	    @FXML
	    private JFXTextField txt_nom;

	    @FXML
	    private TableView<Chauffeur> table_chauffeur;

	    @FXML
	    private TableColumn<Chauffeur, Integer> cln_id;

	    @FXML
	    private TableColumn<Chauffeur, String> cln_nom;

	    @FXML
	    private TableColumn<Chauffeur, String> cln_cni;

	    @FXML
	    private TableColumn<Chauffeur, Date> cln_date;

	    @FXML
	    private TableColumn<Chauffeur, String> cln_tele;

	    @FXML
	    private JFXButton btn_add;

	    @FXML
	    private JFXButton btn_edit;

	    @FXML
	    private JFXButton btn_delete;

	    @FXML
	    private JFXTextField txt_CNI;

	    @FXML
	    private JFXTextField txt_tele;

	    @FXML
	    private JFXDatePicker datePicker;
	    
	    public ObservableList<Chauffeur> dati = FXCollections.observableArrayList();
	    @FXML
	    void addchauffeur() {
	    	String nom=txt_nom.getText();
	    	String tele=txt_tele.getText();
	    	String cni=txt_CNI.getText();
	    	
	    	String sql3="insert into chauffeur(nomprenomC1,DateNaissC1,telepC1,CNI) value(?,?,?,?)";
	    	if(!nom.equals("") && !tele.equals("") && !cni.equals("") && !datePicker.getValue().equals(null)) {
	    		try {
	    			st=cnx.prepareStatement(sql3);
	    			st.setString(1, nom);
	    			java.util.Date date= java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    			Date sqlDate = new Date(date.getTime());
	    			st.setDate(2, sqlDate);
	    			st.setString(3, tele);
	    			st.setString(4, cni);
	    			st.execute();
	    			txt_CNI.setText("");
	    			txt_nom.setText("");
	    			txt_searchCNI.setText("");
	    			txt_tele.setText("");
	    			datePicker.setValue(null);
	    			Alert alert = new Alert(AlertType.CONFIRMATION, "chauffeur ajouté avec succes!",javafx.scene.control.ButtonType.OK);
	            	alert.showAndWait();
	            	showChauffeur();
	    			
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    		}

	    	}else {
	    		Alert alert = new Alert(AlertType.WARNING, "veuillez remplir tout les champs!",javafx.scene.control.ButtonType.OK);
	        	alert.showAndWait();
	    	}
	    	

	    }

	    @FXML
	    void deletechauffeur() {
	    	String sql5= "delete from chauffeur where CNI= '"+txt_searchCNI.getText()+"'";
	    	try {
				st=cnx.prepareStatement(sql5);
				st.executeUpdate();
				txt_CNI.setText("");
				txt_nom.setText("");
				txt_searchCNI.setText("");
				txt_tele.setText("");
				datePicker.setValue(null);
				Alert alert = new Alert(AlertType.CONFIRMATION, "chauffeur suprimé avec succes!",javafx.scene.control.ButtonType.OK);
	        	alert.showAndWait();
	        	showChauffeur();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }
	    @FXML
	    void table_chauffeurEvent() {
	    	Chauffeur chauffeur =table_chauffeur.getSelectionModel().getSelectedItem();
	    	String sql6="select * from chauffeur where idC1 = ?";
	    	try {
				st=cnx.prepareStatement(sql6);
				st.setInt(1, chauffeur.getId());
				result=st.executeQuery();
				if(result.next()) {
					txt_CNI.setText(result.getString("CNI"));
					txt_tele.setText(result.getString("telepC1"));
					txt_nom.setText(result.getString("nomprenomC1"));
					Date date=result.getDate("DateNaissC1");
					datePicker.setValue(date.toLocalDate());
					txt_searchCNI.setText(result.getString("CNI"));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }

	    @FXML
	    void editchauffeur() {
	    	String nom=txt_nom.getText();
	    	String tele=txt_tele.getText();
	    	String cni=txt_CNI.getText();
	    	String sql4="update chauffeur set nomprenomC1=?,DateNaissC1=?,telepC1=?,CNI=? where CNI = '"+txt_searchCNI.getText()+"'";
	    	if(!nom.equals("") && !tele.equals("") && !cni.equals("") && !datePicker.getValue().equals(null)) {
	    	try {
				st=cnx.prepareStatement(sql4);
				st.setString(1, nom);
				java.util.Date date= java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date sqlDate = new Date(date.getTime());
				st.setDate(2, sqlDate);
				st.setString(3, tele);
				st.setString(4, cni);
				st.executeUpdate();
				txt_CNI.setText("");
				txt_nom.setText("");
				txt_searchCNI.setText("");
				txt_tele.setText("");
				datePicker.setValue(null);
				Alert alert = new Alert(AlertType.CONFIRMATION, "chauffeur modifié avec succes!",javafx.scene.control.ButtonType.OK);
	        	alert.showAndWait();
	        	showChauffeur();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	}else {
	    		Alert alert = new Alert(AlertType.WARNING, "veuillez remplir tout les champs!",javafx.scene.control.ButtonType.OK);
	        	alert.showAndWait();
	    	}

	    }

	    @FXML
	    void searchchauffeur() {
	    	String sql1= "select nomprenomC1, CNI, DateNaissC1,telepC1 from chauffeur where CNI = '"+txt_searchCNI.getText()+"'";
	    	int m=0;
	        try {
				st= cnx.prepareStatement(sql1);
				result=st.executeQuery();
				if( result.next()) {
					txt_CNI.setText(result.getString("CNI"));
					txt_tele.setText(result.getString("telepC1"));
					txt_nom.setText(result.getString("nomprenomC1"));
					Date date=result.getDate("DateNaissC1");
					datePicker.setValue(date.toLocalDate());
					m=1;
					
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	        if(m==0) {
	        	Alert alert = new Alert(AlertType.ERROR, "Aucun chauffeur trouvé avec CNI="+txt_searchCNI.getText()+"",javafx.scene.control.ButtonType.OK);
	        	alert.showAndWait();
	        }

	    }
	    public void showChauffeur() {
	    	table_chauffeur.getItems().clear();
	    	String sql1= "select * from chauffeur";
	    	try {
				st= cnx.prepareStatement(sql1);
				result=st.executeQuery();
				while(result.next()) {
					dati.add(new Chauffeur(result.getInt("idC1"),result.getString("nomprenomC1"),result.getDate("DateNaissC1"),result.getString("telepC1"),result.getString("CNI")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	cln_cni.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("cni"));
	    	cln_date.setCellValueFactory(new PropertyValueFactory<Chauffeur, Date>("dateNaiss"));
	    	cln_id.setCellValueFactory(new PropertyValueFactory<Chauffeur, Integer>("id"));
	    	cln_nom.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("nomprenom"));
	    	cln_tele.setCellValueFactory(new PropertyValueFactory<Chauffeur, String>("tele"));
	    	table_chauffeur.setItems(dati);
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConnexionDB();
		showChauffeur();
		
		
	}

}
