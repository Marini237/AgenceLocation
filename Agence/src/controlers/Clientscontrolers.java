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

import Models.Clients;
import application.ConnexionMysql;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Clientscontrolers implements Initializable{
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
    @FXML
    private JFXTextField txt_searchCNI;

    @FXML
    private JFXTextField txt_nom;

    @FXML
    private TableView<Clients> table_clients;

    @FXML
    private TableColumn<Clients, Integer> cln_id;

    @FXML
    private TableColumn<Clients, String> cln_nom;

    @FXML
    private TableColumn<Clients, String> cln_cni;

    @FXML
    private TableColumn<Clients, Date> cln_date;

    @FXML
    private TableColumn<Clients, String> cln_tele;

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
    
    public ObservableList<Clients> data = FXCollections.observableArrayList();
    @FXML
    void addclients() {
    	String nom=txt_nom.getText();
    	String tele=txt_tele.getText();
    	String cni=txt_CNI.getText();
    	
    	String sql3="insert into clients(nomprenomC2,dateNaissC2,telepC2,CNI2) values(?,?,?,?)";
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
    			Alert alert = new Alert(AlertType.CONFIRMATION, "client ajouté avec succes!",javafx.scene.control.ButtonType.OK);
            	alert.showAndWait();
            	showClients();
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}

    	}else {
    		Alert alert = new Alert(AlertType.WARNING, "veuillez remplir tout les champs!",javafx.scene.control.ButtonType.OK);
        	alert.showAndWait();
    	}
    	
    }

    @FXML
    void deleteclients() {
    	String sql5= "delete from clients where CNI2= '"+txt_searchCNI.getText()+"'";
    	try {
			st=cnx.prepareStatement(sql5);
			st.executeUpdate();
			txt_CNI.setText("");
			txt_nom.setText("");
			txt_searchCNI.setText("");
			txt_tele.setText("");
			datePicker.setValue(null);
			Alert alert = new Alert(AlertType.CONFIRMATION, "client suprimé avec succes!",javafx.scene.control.ButtonType.OK);
        	alert.showAndWait();
        	showClients();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    @FXML
    void table_clientsEvent() {
    	Clients clients=table_clients.getSelectionModel().getSelectedItem();
    	String sql6="select * from clients where idC2 = ?";
    	try {
			st=cnx.prepareStatement(sql6);
			st.setInt(1, clients.getId());
			result=st.executeQuery();
			if(result.next()) {
				txt_CNI.setText(result.getString("CNI2"));
				txt_tele.setText(result.getString("telepC2"));
				txt_nom.setText(result.getString("nomprenomC2"));
				Date date=result.getDate("dateNaissC2");
				datePicker.setValue(date.toLocalDate());
				txt_searchCNI.setText(result.getString("CNI2"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }


    @FXML
    void editclients() {
    	String nom=txt_nom.getText();
    	String tele=txt_tele.getText();
    	String cni=txt_CNI.getText();
    	String sql4="update clients set nomprenomC2=?,dateNaissC2=?,telepC2=?,CNI2=? where CNI2 = '"+txt_searchCNI.getText()+"'";
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
			Alert alert = new Alert(AlertType.CONFIRMATION, "client modifié avec succes!",javafx.scene.control.ButtonType.OK);
        	alert.showAndWait();
        	showClients();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	}else {
    		Alert alert = new Alert(AlertType.WARNING, "veuillez remplir tout les champs!",javafx.scene.control.ButtonType.OK);
        	alert.showAndWait();
    	}
    }
 
    @FXML
    void searchclients() {
    	String sql1= "select nomprenomC2, CNI2, dateNaissC2,telepC2 from clients where CNI2 = '"+txt_searchCNI.getText()+"'";
    	int m=0;
        try {
			st= cnx.prepareStatement(sql1);
			result=st.executeQuery();
			if( result.next()) {
				txt_CNI.setText(result.getString("CNI2"));
				txt_tele.setText(result.getString("telepC2"));
				txt_nom.setText(result.getString("nomprenomC2"));
				Date date=result.getDate("dateNaissC2");
				datePicker.setValue(date.toLocalDate());
				m=1;
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
        if(m==0) {
        	Alert alert = new Alert(AlertType.ERROR, "Aucun clients trouvé avec CNI="+txt_searchCNI.getText()+"",javafx.scene.control.ButtonType.OK);
        	alert.showAndWait();
        }
    }
  
    public void showClients() {
    	table_clients.getItems().clear();
    	String sql= "select * from clients";
    	try {
			st= cnx.prepareStatement(sql);
			result=st.executeQuery();
			while(result.next()) {
				data.add(new Clients(result.getInt("idC2"),result.getString("nomprenomC2"),result.getDate("dateNaissC2"),result.getString("telepC2"),result.getString("CNI2")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	cln_cni.setCellValueFactory(new PropertyValueFactory<Clients, String>("cni"));
    	cln_date.setCellValueFactory(new PropertyValueFactory<Clients, Date>("dateNaiss"));
    	cln_id.setCellValueFactory(new PropertyValueFactory<Clients, Integer>("id"));
    	cln_nom.setCellValueFactory(new PropertyValueFactory<Clients, String>("nomprenom"));
    	cln_tele.setCellValueFactory(new PropertyValueFactory<Clients, String>("tele"));
    	table_clients.setItems(data);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConnexionDB();
		showClients();
		
		
	}

}
