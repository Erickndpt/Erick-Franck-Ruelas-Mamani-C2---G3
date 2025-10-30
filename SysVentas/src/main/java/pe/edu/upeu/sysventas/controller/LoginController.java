package pe.edu.upeu.sysventas.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sysventas.components.StageManager;
import pe.edu.upeu.sysventas.components.Toast;
import pe.edu.upeu.sysventas.dto.SessionManager;
import pe.edu.upeu.sysventas.model.Usuario;
import pe.edu.upeu.sysventas.service.IUsuarioService;

import java.io.IOException;
import javafx.scene.input.KeyCode; //  Librería agregada para detectar tecla ENTER

@Controller
public class LoginController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    IUsuarioService us;
    @FXML
    TextField txtUsuario;
    @FXML
    PasswordField txtClave;
    @FXML
    Button btnIngresar;

    @FXML
    public void initialize() {
        //  Evento ENTER para ejecutar el botón ingresar
        txtUsuario.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnIngresar.fire();
            }
        });

        txtClave.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnIngresar.fire();
            }
        });
    }

    @FXML
    public void cerrar(ActionEvent event) { //este metodo se encarga de cerrar la ventana del fxml
        Stage stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        try {
            Usuario usu = us.loginUsuario(txtUsuario.getText(), new String(txtClave.getText()));
            //Usuario usu = us.loginUsuario(txtUsuario.getText(), encriptarClave(txtClave.getText()));
            if (usu != null) {
                SessionManager.getInstance().setUserId(usu.getIdUsuario());
                SessionManager.getInstance().setUserName(usu.getUser());
                SessionManager.getInstance().setUserPerfil(usu.getIdPerfil().getNombre());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/maingui.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent mainRoot = loader.load();
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getBounds();
                Scene mainScene = new Scene(mainRoot, bounds.getWidth(), bounds.getHeight() - 30);
                mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResource("/img/store.png").toExternalForm()));
                stage.setScene(mainScene);
                stage.setTitle("SysVentas SysCenterLife");
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setResizable(true);
                StageManager.setPrimaryStage(stage);
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                stage.show();
            } else {
                Stage stage = (Stage) ((Node)
                        event.getSource()).getScene().getWindow();
                double with = stage.getWidth() * 2;
                double h = stage.getHeight() / 2;
                System.out.println(with + " h:" + h);
                Toast.showToast(stage, "Credencial invalido!! intente nuevamente", 2000, with, h);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String encriptarClave(String clave) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(clave.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
