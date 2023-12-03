package controlers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Models.Trajetdevoyage;
import application.ConnexionMysql;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class LesTrajetVoyagescontrolers implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	 @FXML
	    private JFXTextField txt_searchid;

	    @FXML
	    private JFXTextField txt_lieuD;
	    
	    @FXML
	    private JFXTextField txt_trajet;
	    
	    @FXML
	    private JFXTextField txt_destination;
	    
	    @FXML
	    private JFXComboBox<String> cb_destination;

	    @FXML
	    private JFXComboBox<String> cb_lieudedepart;
	    
	    @FXML
	    private JFXComboBox<String> cb_categoriebus;

	    @FXML
	    private Label tab_url;

	    @FXML
	    private TableView<Trajetdevoyage> table_trajetdevoyage;

	    @FXML
	    private TableColumn<Trajetdevoyage, Integer> cln_id;
	    
	    @FXML
	    private TableColumn<Trajetdevoyage, String> cln_nomdetrajetvoyage;
	   
	    @FXML
	    private TableColumn<Trajetdevoyage, String> cln_destination;

	    @FXML
	    private TableColumn<Trajetdevoyage, Integer> cln_numerobus;

	    @FXML
	    private TableColumn<Trajetdevoyage, String> cln_categoriebus;
	    
	    @FXML
	    private TableColumn<Trajetdevoyage, String> cln_lieudedepart;
	    
	    @FXML
	    private ImageView image_trajetdevoyage;

	    @FXML
	    private FontAwesomeIconView icon_importer;

	    @FXML
	    private JFXButton btn_add;

	    @FXML
	    private JFXButton btn_edit;
	    
        private FileInputStream fs;
	    @FXML
	    private JFXButton btn_delete;

	    @FXML
	    private JFXComboBox<Integer> cb_numerobus;

	    @FXML
	    void ajoutertrajetdevoyage() {
	    	String nomtrajetdevoyage=txt_trajet.getText();
	    	String categorie= cb_categoriebus.getValue();
	    	String spl4=" select  idcategoriebus from categoriebus where nomcategorieBus='"+categorie+"'" ;
	    	int categoriebus=0;
            try {
				st= cnx.prepareStatement(spl4);
				result=st.executeQuery();
				if(result.next()) {
				categoriebus=result.getInt("idcategoriebus");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            String dest= cb_destination.getValue();
	    	String spl8=" select  iddestination from destination where Nomdestination='"+dest+"'" ;
	    	int destination=0;
            try {
				st= cnx.prepareStatement(spl8);
				result=st.executeQuery();
				if(result.next()) {
				destination=result.getInt("iddestination");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

            
            String lieuD= cb_lieudedepart.getValue();
	    	String spl9=" select  idlieudedepart from lieudedepart where Nomlieudedepart='"+lieuD+"'" ;
	    	int lieudedepart=0;
            try {
				st= cnx.prepareStatement(spl9);
				result=st.executeQuery();
				if(result.next()) {
				lieudedepart=result.getInt("idlieudedepart");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

            
            Integer numeros=cb_numerobus.getValue();
			String sql5=" select idNumeroBUS from numerobus where NomnumeroBus='"+numeros+"'";
			int numerobus=0;
            try {
				st=cnx.prepareStatement(sql5);
				result=st.executeQuery();
				if(result.next()) {
					numerobus=result.getInt("idNumeroBUS");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            
            File image= new File(tab_url.getText());
            
            String spl0="insert into trajetdevoyage(Nomtrajetvevoyage,lieudedepart,categoriebus,destination,numerobus,image)  values(?,?,?,?,?,?)";
            try {
				st=cnx.prepareStatement(spl0);
				st.setString(1, nomtrajetdevoyage);
				st.setInt(2, lieudedepart);
				st.setInt(3, categoriebus);
				st.setInt(4, destination);
				st.setInt(5, numerobus);
				fs= new FileInputStream(image);
				st.setBinaryStream(6, fs, image.length());
				st.executeUpdate();
				showTrajetdevoyage();
				tab_url.setText("aucune selectionée");
				txt_trajet.setText("");
				cb_lieudedepart.setValue("lieudedepart");
				cb_categoriebus.setValue("categoriebus");
				cb_destination.setValue("destination");
				cb_numerobus.setValue(java.lang.Integer.valueOf("numerobus"));
				image_trajetdevoyage.setImage(null);
				Alert alert= new Alert(AlertType.CONFIRMATION, " trajet de voyage ajouté",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    }

	    @FXML
	    void importerImage() {
	    	FileChooser fc=new FileChooser();
	    	fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png","*.jpg"));
	    	File f=fc.showOpenDialog(null);
	    	if(f != null) {
	    		tab_url.setText(f.getAbsolutePath());
	    		Image image= new Image(f.toURI().toString(),image_trajetdevoyage.getFitWidth(),image_trajetdevoyage.getFitHeight(),true,true);
	    		image_trajetdevoyage.setImage(image);
	    		
	    	}

	    }

	    @FXML
	    void modifiertrajetdevoyage() {
	    	String nomtrajetdevoyage=txt_trajet.getText();
	    	String categorie= cb_categoriebus.getValue();
	    	String spl4=" select  idcategoriebus from categoriebus where nomcategorieBus='"+categorie+"'" ;
	    	int categoriebus=0;
            try {
				st= cnx.prepareStatement(spl4);
				result=st.executeQuery();
				if(result.next()) {
				categoriebus=result.getInt("idcategoriebus");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            String dest= cb_destination.getValue();
	    	String spl8=" select  iddestination from destination where Nomdestination='"+dest+"'" ;
	    	int destination=0;
            try {
				st= cnx.prepareStatement(spl8);
				result=st.executeQuery();
				if(result.next()) {
				destination=result.getInt("iddestination");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

            
            String lieuD= cb_lieudedepart.getValue();
	    	String spl9=" select  idlieudedepart from lieudedepart where Nomlieudedepart='"+lieuD+"'" ;
	    	int lieudedepart=0;
            try {
				st= cnx.prepareStatement(spl9);
				result=st.executeQuery();
				if(result.next()) {
				lieudedepart=result.getInt("idlieudedepart");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

            
            Integer numeros=cb_numerobus.getValue();
			String sql5=" select idNumeroBUS from numerobus where NomnumeroBus='"+numeros+"'";
			int numerobus=0;
            try {
				st=cnx.prepareStatement(sql5);
				result=st.executeQuery();
				if(result.next()) {
					numerobus=result.getInt("idNumeroBUS");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            
            File image= new File(tab_url.getText());
            
            String spl0="Update  trajetdevoyage set Nomtrajetvevoyage=?,lieudedepart=?,categoriebus=?,destination=?,numerobus=?,image=? where idtrajetdevoyage ='"+txt_searchid.getText()+"'";
            try {
				st=cnx.prepareStatement(spl0);
				st.setString(1, nomtrajetdevoyage);
				st.setInt(2, lieudedepart);
				st.setInt(3, categoriebus);
				st.setInt(4, destination);
				st.setInt(5, numerobus);
				fs= new FileInputStream(image);
				st.setBinaryStream(6, fs, image.length());
				st.executeUpdate();
				showTrajetdevoyage();
				tab_url.setText("aucune selectionée");
				txt_trajet.setText("");
				cb_lieudedepart.setValue("lieudedepart");
				cb_categoriebus.setValue("categoriebus");
				cb_destination.setValue("destination");
				cb_numerobus.setValue(java.lang.Integer.valueOf("numerobus"));
				image_trajetdevoyage.setImage(null);
				Alert alert= new Alert(AlertType.CONFIRMATION, " trajet de voyage modifier",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

	    }

	    @FXML
	    void remplircategoriebus() {

	    }

	    @FXML
	    void remplirnumerobus() {

	    }

	    @FXML
	    void searchtrajetdevoyage() {
	    	Trajetdevoyage trajetdevoyage= table_trajetdevoyage.getSelectionModel().getSelectedItem();
	    	String sql3= " select idtrajetdevoyage,Nomtrajetvevoyage,Nomlieudedepart,nomcategorieBus,Nomdestination,NomnumeroBus,image from trajetdevoyage,lieudedepart,categoriebus,destination,numerobus where trajetdevoyage.lieudedepart=lieudedepart.idlieudedepart and trajetdevoyage.categoriebus=categoriebus.idcategoriebus and trajetdevoyage.destination=destination.iddestination and trajetdevoyage.numerobus=numerobus.idNumeroBUS and idtrajetdevoyage=? ";
            try {
				st =cnx.prepareStatement(sql3);
				st.setString(1, txt_searchid.getText());
				result=st.executeQuery();
				byte byteImage[];
				Blob blob;
				while(result.next()) {
					int id=result.getInt("idtrajetdevoyage");
					txt_searchid.setText(String.valueOf(id));
					txt_trajet.setText(result.getString("Nomtrajetvevoyage"));
				    cb_categoriebus.setValue(result.getString("nomcategorieBus"));
				    cb_lieudedepart.setValue(result.getString("Nomlieudedepart"));
				    cb_destination.setValue(result.getString("Nomdestination"));
				    cb_numerobus.setValue(result.getInt("NomnumeroBus"));
				    blob=result.getBlob("image");
				    byteImage=blob.getBytes(1, (int) blob.length());
				    Image img=new Image(new ByteArrayInputStream(byteImage), image_trajetdevoyage.getFitWidth(), image_trajetdevoyage.getFitHeight(), true, true);
				    image_trajetdevoyage.setImage(img);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }

	    @FXML
	    void supprimertrajetdevoyage() {
	    	String sql = "delete from trajetdevoyage where idtrajetdevoyage='"+txt_searchid.getText()+"'";
	    	try {
				st= cnx.prepareStatement(sql);
				st.executeUpdate();
				showTrajetdevoyage();
				tab_url.setText("aucune selectionée");
				txt_trajet.setText("");
				cb_lieudedepart.setValue("lieudedepart");
				cb_categoriebus.setValue("categoriebus");
				cb_destination.setValue("destination");
				cb_numerobus.setValue(java.lang.Integer.valueOf("numerobus"));
				image_trajetdevoyage.setImage(null);
				Alert alert= new Alert(AlertType.CONFIRMATION, " trajetdevoyage supprimé!",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }
	    
	    ObservableList<Trajetdevoyage> ListTra= FXCollections.observableArrayList();
	    public void showTrajetdevoyage() {
	    	table_trajetdevoyage.getItems().clear();
	    	String sql=" select idtrajetdevoyage,Nomtrajetvevoyage,Nomlieudedepart,nomcategorieBus,Nomdestination,NomnumeroBus from trajetdevoyage,lieudedepart,categoriebus,destination,numerobus where trajetdevoyage.lieudedepart=lieudedepart.idlieudedepart and trajetdevoyage.categoriebus=categoriebus.idcategoriebus and trajetdevoyage.destination=destination.iddestination and trajetdevoyage.numerobus=numerobus.idNumeroBUS ";
	    	try {
				st= cnx.prepareStatement(sql);
				result=st.executeQuery();
				while(result.next()) {
					ListTra.add(new Trajetdevoyage(result.getInt(1),result.getString(3),result.getString(2),result.getString(5),result.getString(4),result.getInt(6)));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	       cln_id.setCellValueFactory(new PropertyValueFactory<Trajetdevoyage, Integer>("id"));
	       cln_nomdetrajetvoyage.setCellValueFactory(new PropertyValueFactory<Trajetdevoyage,  String>("nomtrajetdevoyage"));
	       cln_categoriebus.setCellValueFactory(new PropertyValueFactory<Trajetdevoyage,  String>("categoriebus"));
	       cln_destination.setCellValueFactory(new PropertyValueFactory<Trajetdevoyage,  String>("destination"));
	       cln_numerobus.setCellValueFactory(new PropertyValueFactory<Trajetdevoyage, Integer>("id"));
	       cln_lieudedepart.setCellValueFactory(new PropertyValueFactory<Trajetdevoyage, String>("lieudedepart"));
	       table_trajetdevoyage.setItems(ListTra);
	       
	    }
	    public void remplircategoriebus1() {
	    	String sql1="select nomcategorieBus from categoriebus";
	    	List<String> categoriebus = new ArrayList<String>();
	    	try {
				st=cnx.prepareStatement(sql1);
				result=st.executeQuery();
				while(result.next()) {
					categoriebus.add(result.getString("nomcategorieBus"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	cb_categoriebus.setItems(FXCollections.observableArrayList(categoriebus));
	    }
	    public void remplirlieudedepar1() {
	 	    	String sql6="select Nomlieudedepart from lieudedepart";
	 	    	List<String> lieudedepart = new ArrayList<String>();
	 	    	try {
	 				st=cnx.prepareStatement(sql6);
	 				result=st.executeQuery();
	 				while(result.next()) {
	 					lieudedepart.add(result.getString("Nomlieudedepart"));
	 				}
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 			}
	 	    	cb_lieudedepart.setItems(FXCollections.observableArrayList(lieudedepart));
	    }
	 	   public void remplirdestination1() {
	 		    	String sql7="select Nomdestination from destination";
	 		    	List<String> destination = new ArrayList<String>();
	 		    	try {
	 					st=cnx.prepareStatement(sql7);
	 					result=st.executeQuery();
	 					while(result.next()) {
	 						destination.add(result.getString("Nomdestination"));
	 					}
	 				} catch (SQLException e) {
	 					e.printStackTrace();
	 				}
	 		    	cb_destination.setItems(FXCollections.observableArrayList(destination));
	    }
	    @FXML
	    void tabletrajetdevoyageEvent() {
	    	Trajetdevoyage trajetdevoyage= table_trajetdevoyage.getSelectionModel().getSelectedItem();
	    	String sql3= " select idtrajetdevoyage,Nomtrajetvevoyage,Nomlieudedepart,nomcategorieBus,Nomdestination,NomnumeroBus,image from trajetdevoyage,lieudedepart,categoriebus,destination,numerobus where trajetdevoyage.lieudedepart=lieudedepart.idlieudedepart and trajetdevoyage.categoriebus=categoriebus.idcategoriebus and trajetdevoyage.destination=destination.iddestination and trajetdevoyage.numerobus=numerobus.idNumeroBUS and idtrajetdevoyage=? ";
            try {
				st =cnx.prepareStatement(sql3);
				st.setInt(1, trajetdevoyage.getId());
				result=st.executeQuery();
				byte byteImage[];
				Blob blob;
				while(result.next()) {
					int id=result.getInt("idtrajetdevoyage");
					txt_searchid.setText(String.valueOf(id));
					txt_trajet.setText(result.getString("Nomtrajetvevoyage"));
				    cb_categoriebus.setValue(result.getString("nomcategorieBus"));
				    cb_lieudedepart.setValue(result.getString("Nomlieudedepart"));
				    cb_destination.setValue(result.getString("Nomdestination"));
				    cb_numerobus.setValue(result.getInt("NomnumeroBus"));
				    blob=result.getBlob("image");
				    byteImage=blob.getBytes(1, (int) blob.length());
				    Image img=new Image(new ByteArrayInputStream(byteImage), image_trajetdevoyage.getFitWidth(), image_trajetdevoyage.getFitHeight(), true, true);
				    image_trajetdevoyage.setImage(img);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }

	    public void remplirnumerobus1() {
	    	String sql2="select NomnumeroBus from numerobus";
	    	List<Integer> numero = new ArrayList<Integer>();
	    	try {
				st=cnx.prepareStatement(sql2);
				result=st.executeQuery();
				while(result.next()) {
					numero.add(result.getInt("NomnumeroBus"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	cb_numerobus.setItems(FXCollections.observableArrayList(numero));
	    	
	    }
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConnexionDB();
		showTrajetdevoyage();
		remplircategoriebus1();
		remplirnumerobus1();
		remplirdestination1();
		remplirlieudedepar1();
	}

}
