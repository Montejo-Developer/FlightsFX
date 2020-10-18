/**
 *This class has the main method 
 */
package flightsfx;

import flightsfx.utils.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Montejeitor
 */

public class FlightsFX extends Application {
    
    /**
     * This method loads and launches the scene
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method launches the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
    
}
