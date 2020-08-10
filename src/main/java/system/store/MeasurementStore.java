package system.store;

import org.apache.log4j.Logger;
import system.exceptions.EmptyMeasurementException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeasurementStore {

    //private static final Logger LOGGER = Logger.getLogger(MeasurementStore.class.getName());

    private List<Double> measurements = new ArrayList<Double>();
    private Boolean key = true; //it allows the user to turn on and off all sensors
    private Boolean difMaxMinOFR = false; //it allows us to see if the monitor detect an out of range dif from max and min
    private Boolean avgOFR = false; //it allows us to see if the monitor detect an out of range avg

    public synchronized void measurementListener(Double measurement, String sensorName) {

        measurements.add(measurement);
        //LOGGER.info(String.format("Measurement with value: %f, has been added from sensor: %s", measurement, sensorName));
        System.out.println(String.format("Measurement with value: %f, has been added from sensor: %s", measurement, sensorName));
    }

    public synchronized Double measurementProvider() { // i decided to make the system wait another cycle to process instead of wait here

        if(!measurements.isEmpty())
            return measurements.remove(0);
        else
            throw new EmptyMeasurementException("There are no measurements to process, reprocessing");
    }


    public List<Double> getMeasurements() {
        return measurements;
    }

    public void turnOnSystem() {

        this.key = true;
        //LOGGER.info(String.format("The system has been turn on"));
        System.out.println(String.format("The system has been turn on"));
    }

    public void turnOffSystem() {

        this.key = false;
        //LOGGER.info(String.format("The system has been turn off"));
        System.out.println(String.format("The system has been turn off"));
    }

    public Boolean getKey() {
        return key;
    }

    public void OFRdifDetected(){
        if(!difMaxMinOFR)
            difMaxMinOFR = true;
    }

    public void OFRavgDetected(){
        if(!avgOFR)
            avgOFR = true;
    }

    public void rightAvgDetected(){
        if(!avgOFR)
            avgOFR = false;
    }

    public Boolean getDifMaxMinOFR() {
        return difMaxMinOFR;
    }

    public Boolean getAvgOFR() {
        return avgOFR;
    }
}
