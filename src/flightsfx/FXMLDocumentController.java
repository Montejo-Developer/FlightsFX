/**
 * This class manages the logic in our JavaFX
 */
package flightsfx;

import flightsfx.model.Flight;
import flightsfx.utils.FileUtils;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Montejeitor
 */
public class FXMLDocumentController implements Initializable {
    
    List<Flight> flightList;
    ObservableList<Flight> obList;
    
    @FXML
    private TableView <Flight> tableView;
    
    @FXML
    private TableColumn <Flight,String> tableColumn_flightNumber;
    @FXML
    private TableColumn <Flight,String> tableColumn_destination;
    @FXML
    private TableColumn <Flight,LocalDateTime> tableColumn_departure;
    @FXML
    private TableColumn <Flight,LocalTime> tableColumn_duration;
    
    @FXML
    private TextField textField_flightNumber;
    @FXML
    private TextField textField_destination;
    @FXML
    private TextField textField_departure;
    @FXML
    private TextField textField_duration;
    
    @FXML
    private ComboBox comboBox;
    
    @FXML
    private Label label_warning;
    
    @FXML
    private Button button_delete;
    
    /**
     * This function executes itself when we first launch the program
     * @param url
     * @param rb 
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        flightList = FileUtils.loadFlights();
        obList = FXCollections.observableArrayList();
        
        DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for ( Flight f : flightList ) {
            obList.add(f);
        }
        
        tableColumn_flightNumber.setCellValueFactory(
                new PropertyValueFactory("flightNumber"));
        
        tableColumn_destination.setCellValueFactory
            (new PropertyValueFactory("destination"));
        
        tableColumn_departure.setCellValueFactory
            (new PropertyValueFactory("format"));
        
        tableColumn_duration.setCellValueFactory
            (new PropertyValueFactory("flightDuration"));
                
        tableView.setItems(obList);
        
        button_delete.setDisable(true);
        
        comboBox.setItems(FXCollections.observableArrayList(
                "Show all flights",
                "Show all flights to currently selected city",
                "Show long flights",
                "Show next 5 flights",
                "Show flight duration average"));
   }    
    
    /** 
     * This function adds a flight to the table when we push the add button
     * @param event 
     */
    @FXML
    private void addFlightAction(ActionEvent event) {
        
       DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        
        if ( "".equals(textField_flightNumber.getText()) || 
                "".equals(textField_destination.getText()) ||
                "".equals(textField_departure.getText()) ||
                "".equals(textField_duration.getText()))  {
            
            
            label_warning.setText("None of the fields can be empty");
        }    
        
        else {
            try {
                String auxdeparture = textField_departure.getText();
        
                LocalDateTime dateTime = LocalDateTime.parse
                            (auxdeparture,formatter);

                LocalTime localTime = LocalTime.parse(textField_duration.getText(),dtf);
                flightList.add(new Flight(textField_flightNumber.getText(),
                    textField_destination.getText(),
                    dateTime, localTime));

                label_warning.setText("Flight added");

                FileUtils.saveFlights(flightList);

                flightList = FileUtils.loadFlights();

                obList = FXCollections.observableArrayList();

                for ( Flight f : flightList ) {
                    obList.add(f);
                }
                tableView.setItems(obList);
            }catch (Exception e) {
                label_warning.setText("Make sure that all the fields have the correct format");
            }
            
        }
    }
    
    /**
     * This function deletes a selected item from the table
     * @param event 
     */
    @FXML
    private void removeItem(ActionEvent event) {
        
        int index = tableView.getSelectionModel().getSelectedIndex();
        
        if(index >= 0){
             obList.remove(index);
             label_warning.setText("Flight removed!");
             flightList = obList;
             FileUtils.saveFlights(flightList);
        }
           
    }
    
    /**
     * This method activates or deactivates the delete button
     * @param arg0 
     */
    @FXML
    private void activateDelete(MouseEvent arg0) {
        
        int index = tableView.getSelectionModel().getSelectedIndex();
        
        if(index >= 0){
            button_delete.setDisable(false);
        }
        
        else
            button_delete.setDisable(true);
    }
    
    /**
     * This method applyes the selected filter to the list
     * @param event 
     */
    @FXML
    private void applyFilter(ActionEvent event) {
        
        ObservableList<Flight> aux = FXCollections.observableArrayList();
        int index = comboBox.getSelectionModel().getSelectedIndex();
        
        switch ( index ) {
            
            case 0:
                showAllFlights();
                break;
                
            case 1:
                showSelectedFlights(index,aux);            
                break;
                
            case 2:
                showLongFlights(aux);
                break;
                
            case 3:
                showFiveFlights(aux);
                break;
                
            case 4:
                showAverageFlight();
                break;
                
            default:
                label_warning.setText("You must select a filter!");
                break;
        }
    }
    
    /**
     * This function shows all the flights in the program
     */
    private void showAllFlights() {
        
        tableView.setItems(obList);
    }
    
    /**
     * This function shows all the flights from a selected city
     * @param index
     * @param aux 
     */
    private void showSelectedFlights(int index,ObservableList aux) {
        try {
                    
                index = tableView.getSelectionModel().getSelectedIndex();
                String city = flightList.get(index).getDestination();

                for ( Flight f : flightList ) {
                    if ( f.getDestination().equals(city) ) {
                        aux.add(f);
                    }
                }   
                
                tableView.setItems(aux);
                }catch (Exception e) {
                    label_warning.setText("You must select a flight");
    }
    }
    
    /**
     * This function shows all the flights tha are over 3 hours long
     * @param aux 
     */
    private void showLongFlights(ObservableList aux) {
               
        for ( Flight f : flightList ) {
            if ( f.getFlightDuration().getHour() > 3 ) {
                aux.add(f);
            }
        }
        
        tableView.setItems(aux);
    }
    
    /**
     * This method shows the next five flights to leave the airport 
     * @param aux 
     */
    private void showFiveFlights(ObservableList aux) {
        
        DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        String auxDate = LocalDateTime.now().format(formatter);
        
        LocalDateTime dateTime = LocalDateTime.parse
                            (auxDate,formatter);
        
        int maxFlights = 0;
        
        for ( Flight f : flightList ) {
            
            if ( f.getDepartureDate().toEpochSecond(ZoneOffset.UTC) 
                    > dateTime.toEpochSecond(ZoneOffset.UTC) && maxFlights < 5 ) {
                aux.add(f);
                maxFlights++;
            }
        }
        
        Comparator comparator = new Comparator<Flight>() {
            
            @Override
            public int compare(Flight o1, Flight o2) {
                
                return (int)o1.getDepartureDate().toEpochSecond(ZoneOffset.UTC)- 
                        (int)o2.getDepartureDate().toEpochSecond(ZoneOffset.UTC);
            }
		};
        
        aux.sort(comparator);
        
        tableView.setItems(aux);
    }
    
    /**
     * This method shows the average flight lenght
     * @param aux 
     */
    private void showAverageFlight() {
        
        int total = 0;
        
        for ( Flight f : flightList ) {
            
            total += f.getFlightDuration().toSecondOfDay();
                    
        }
        
        total = total / flightList.size();
        
        int hours = total / 3600;
        int minutes = (total % 3600) / 60;

        String timeString = String.format("%02d:%02d", hours, minutes);
        
        Alert alert = new Alert(AlertType.NONE, timeString + "  Average flight lenght"
                 , ButtonType.CLOSE);
        
        alert.showAndWait();
    }
}
