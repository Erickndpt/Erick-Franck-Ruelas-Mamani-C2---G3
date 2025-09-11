package pe.edu.upeu.asistencia.control;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Map;


@Controller
public class MainguiController {

    @FXML
    private MenuItem menuItem1, menuItem2, menuItem3, menuItem4;
    @FXML
    private MenuBar menuBar;

    private ComboBox<String> comboBox=new ComboBox<>();
    private CustomMenuItem customMenuEstilo=new CustomMenuItem(comboBox);;
    private Menu menuEstilo=new Menu("Cambiar estilo");
    @FXML
    private TabPane tabPane;
    @FXML
    BorderPane bp;

    @Autowired
    private ApplicationContext context;
    @FXML
    public void initialize() {
        comboBox.getItems().addAll("Estilo por defecto", "Estilo Oscuro"
                , "Estilo Azul", "Estilo Verde", "Estilo Rosado");
        comboBox.setOnAction(e->cambiarEstilo());
        customMenuEstilo.setHideOnClick(false);
        menuEstilo.getItems().add(customMenuEstilo);
        menuBar.getMenus().add(menuEstilo);
        MenuItemListener miL=new MenuItemListener();
        menuItem1.setOnAction(miL::handle);
        menuItem2.setOnAction(miL::handle);
        menuItem3.setOnAction(miL::handle);
        menuItem4.setOnAction(miL::handle);
    }
    @FXML
    public void cambiarEstilo(){
        String estiloSeleccionado=comboBox.getSelectionModel().getSelectedItem();
        Scene scene=bp.getScene();
        scene.getStylesheets().clear();
        switch (estiloSeleccionado){
            case "Estilo Oscuro":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-oscuro.css").toExternalForm()); break;
            case "Estilo Azul":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm()); break;
            case "Estilo Verde":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-verde.css").toExternalForm()); break;
            case "Estilo Rosado":
                scene.getStylesheets().add(getClass().getResource("/css/estilo-rosado.css").toExternalForm()); break;
            default:  break;
        }
    }

    class MenuItemListener{
        Map<String, String[]> menuConfig=Map.of(
                            "menuItem1",new String[]{"/fxml/main_participante.fxml","Participantes","T"},
                            "menuItem2",new String[]{"/fxml/login.fxml","Salir","C"},
                            "menuItem3",new String[]{"/fxml/main_asistencia.fxml","Asistencia","T"},
                            "menuItem4",new String[]{"/fxml/main_asistencia.fxml","Asistencia","T"}
                                );

        public void handle(ActionEvent e) {
            String id= ( (MenuItem) e.getSource() ).getId();
            if(menuConfig.containsKey(id)){
                String[] mi=menuConfig.get(id);
                if(mi[2].equals("C")){
                    Platform.exit();
                    System.exit(0);
                }else{
                    abrirArchivoFXML(mi[0],mi[1]);
                }
            }
        }

        public void abrirArchivoFXML(String filename, String tittle){
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            Tab newTab = new  Tab(tittle, scrollPane);
            tabPane.getTabs().clear();
            tabPane.getTabs().add(newTab);

            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class MenuListener{
        public void handle(Event e) {

        }
    }

}
