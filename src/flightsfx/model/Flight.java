/**
 * This class will generate the flight objects required
 */
package flightsfx.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Montejeitor
 */
public class Flight {

    private String flightNumber,destination;
    private LocalDateTime departureDate;
    private LocalTime flightDuration;
    
    /**
     * Constructor with only the flight number
     * @param flightNumber 
     */
    public Flight(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    /**
     * Constructor with all the parameters
     * @param flightNumber
     * @param destination
     * @param departureDate
     * @param flightDuration 
     */
    public Flight(String flightNumber, String destination,
            LocalDateTime departureDate, LocalTime flightDuration) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureDate = departureDate;
        this.flightDuration = flightDuration;
    }
    
    /**
     * This formats the dates in a way that works properly
     * @return 
     */
    public String getFormat () {
        
        DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        return departureDate.format(formatter);
    }
    
    /**
     * @return the flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * @param flightNumber the flightNumber to set
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the departureDate
     */
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    /**
     * @param departureDate the departureDate to set
     */
    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * @return the flightDuration
     */
    public LocalTime getFlightDuration() {
        return flightDuration;
    }

    /**
     * @param flightDuration the flightDuration to set
     */
    public void setFlightDuration(LocalTime flightDuration) {
        this.flightDuration = flightDuration;
    }
    
    
    
}
