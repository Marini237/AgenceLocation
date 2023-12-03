package controlers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.ConnexionMysql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Signlncontrolers implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	 @FXML
	    private VBox vBox;
        private Parent fxml;

        @FXML
        private JFXTextField txt_userName;

        @FXML
        private JFXPasswordField txt_password;
	    @FXML
	    private JFXButton btn_passwordForgote;

	    @FXML
	    private JFXButton btn_seconnecter;

	    @FXML
	    void openHome() {
	    	String nom= txt_userName.getText();
	    	String pass= txt_password.getText();
	    	String sql="select userName, password,CNI,adrmail,photo from admin";
	    	int nb=0;
	    	try {
				st=cnx.prepareStatement(sql);
				result=st.executeQuery();
				while(result.next()) {
		             if(nom.equals(result.getString("userName"))&&pass.equals(result.getString("password"))) { 
		            	 nb=1;
		            	 String spl2="insert into userconnect(userName,password,CNI,adrmail,photo) values(?,?,?,?,?)";
		            	 st=cnx.prepareStatement(spl2);
		            	 st.setString(1, result.getString("userName"));
		            	 st.setString(2, result.getString("password"));
		            	 st.setString(3, result.getString("CNI"));
		            	 st.setString(4, result.getString("adrmail"));
		            	 st.setBlob(5, result.getBlob("photo"));
		            	 st.executeUpdate();	            	 
		            	 vBox.getScene().getWindow().hide();
		            	 Stage home= new Stage();
		            	 try {
		            	 fxml= FXMLLoader.load(getClass().getResource("/Views/Homeview.fxml"));
		            	 Scene scene= new Scene(fxml);
		            	 home.setScene(scene);
		            	 home.show();
		            	 }catch(IOException e){
		            		 e.printStackTrace();
		            	 }
		            	 
		             }
		            
			        }	
			 } catch (SQLException e1) {
				e1.printStackTrace();
			}
	    	
	    	 if(nb==0) {
        	     Alert alert= new Alert(AlertType.ERROR,"nom de l'utilisateur ou mot de passe Incorrect!",javafx.scene.control.ButtonType.OK);
        	     alert.showAndWait();
        	     txt_password.setText("");
        	     txt_userName.setText("");
         }
	    	
	    	
	    	
	    	
	    	
	    	
	    }
	    
	    @FXML
	    void sendPassWord() {
	    	String spl="select * from admin where userName ='"+txt_userName.getText()+"'";
	    	String email="empty";
	    	try {
				st=cnx.prepareStatement(spl);
				result=st.executeQuery();
				if(result.next()) {
					email=result.getString("adrmail");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	sendMail(email);

	    }
	    public void sendMail (String recepient) {
	    	System.out.println("preparation pour envoi de Mail...");
	    	Properties properties = new Properties();
	    	
	    	properties.put("mail.smtp.auth", "true");
	    	
	    	properties.put("mail.smtp.starttls.enable", "true");
	    	
	    	properties.put("mail.smtp.host", "smtp.gmail.com");
	    	
	    	properties.put("mail.smtp.port", "587");
	    	
	    	final String MyAccoutEmail="busdevoyage.trajetdevoyagejava@gmail.com";
	    	
	    	final String password ="123456azer";
	    	
	    	Session session = Session.getInstance(properties, new Authenticator(){
	    			protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
						return new javax.mail.PasswordAuthentication(MyAccoutEmail, password);
	    				
	    			}
	    	});
	    	
	    	Message message =preparedMessage(session,MyAccoutEmail, recepient );
	    	
	    	try {
				Transport.send(message);
				Alert alert= new Alert(AlertType.CONFIRMATION,"consulter votre boite mail pour recuperer votre mot de pass",javafx.scene.control.ButtonType.OK);
       	     alert.showAndWait();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	    	
	    }
	    @SuppressWarnings("unused")
		private Message preparedMessage(Session session,String MyAccountEmail, String recepient) {
	    	String spl="select * from admin where userName ='"+txt_userName.getText()+"'";
	    	String pass="empty";
	    	try {
				st=cnx.prepareStatement(spl);
				result=st.executeQuery();
				if(result.next()) {
					pass=result.getString("password");
			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	String text="votre mot de pass est : "+pass+"";
	    	String objet="Recuperation de mot de pass";
	    	Message message = new MimeMessage(session);
	    	try {
				message.setFrom(new InternetAddress(MyAccountEmail));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
				message.setSubject(objet);
				String htmlcode = " <h1> "+text+" </h1> <h2><b> </b></h2>";
				message.setContent(htmlcode, "text/html");
				return message;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	    	
	    return null;	
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx= ConnexionMysql.ConnexionDB();
		

	}

}
