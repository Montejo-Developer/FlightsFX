/**
 * This class has some auxiliar methods to help manage the files in our program
 */
package flightsfx.utils;

import flightsfx.model.Flight;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Montejeitor
 */
public class FileUtils {
    
    /**
     * This method loads the flights from the text file to a list
     * @return 
     */
    public static List<Flight> loadFlights() {
        
        List<Flight> flightList = new ArrayList<>();
        
        DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        
        if (! (new File("flights.txt")).exists() ) {
            System.out.println("flights.txt not found");
            return null;
        }

        try {
            BufferedReader fileIN = new BufferedReader(
                    new FileReader(new File("flights.txt")));

            String linea = "";
            System.out.println("Reading file...");
            do {
                linea = fileIN.readLine();
                
                if ( linea != null ) {
                    
                    String[] aux = linea.split(";");
                    
                    LocalDateTime dateTime = LocalDateTime.parse
                        (aux[2],formatter);
                    
                    LocalTime localTime = LocalTime.parse(aux[3],dtf);
                    
                    flightList.add( new Flight(aux[0], aux[1], dateTime, 
                            localTime));
                }
            }
            while ( linea != null );

            fileIN.close();
            System.out.println("File Readed");
        }
        catch (IOException errorDeFichero) {
            System.out.println(
                "Error: " +
                errorDeFichero.getMessage() );
        }
        return flightList;
    }
    
    /**
     * This method saves a list to a text file
     * @param flights 
     */
    public static void saveFlights (List<Flight> flights) {
    
        File fileName = new File ("flights.txt");

       try {
            PrintWriter ouputFile = new PrintWriter(fileName);
            
            for (Flight fl : flights) {
                ouputFile.println(fl.getFlightNumber() + ";" + 
                        fl.getDestination() + ";" + fl.getFormat()
                        + ";" + fl.getFlightDuration());
            }
            ouputFile.close();
        } catch (IOException e) {
            System.out.println("Writting Error.");
        }
    }
   }           
