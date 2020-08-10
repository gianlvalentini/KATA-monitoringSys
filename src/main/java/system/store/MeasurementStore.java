package system.store;

import org.apache.log4j.Logger;
import system.exceptions.EmptyMeasurementException;

import java.util.ArrayList;
import java.util.List;

public class MeasurementStore {

    private static final Logger LOGGER = Logger.getLogger(MeasurementStore.class.getName());

    private List<Double> measurements = new ArrayList<>();
    private List<Double> measurementsProcessed = new ArrayList<>();
    private Boolean sensorKey = true; //it allows the user to turn on and off all sensors
    private Boolean difMaxMinOFR = false; //it allows us to see if the monitor detect an out of range dif from max and min
    private Boolean avgOFR = false; //it allows us to see if the monitor detect an out of range avg
    private Double avgConst = 70.0;
    private Double difMaxMinConst = 50.0;

    public synchronized void measurementListener(Double measurement, String sensorName) {

        measurements.add(measurement);
        LOGGER.info(String.format("Measurement with value: %f, has been added from sensor: %s", measurement, sensorName));
    }

    public synchronized Double measurementProvider() {

        if(!measurements.isEmpty())
            return measurements.remove(0);
        else
            throw new EmptyMeasurementException("There are no measurements to process, reprocessing");
    }

    public synchronized void newMeasurementsProcessed(Double measurement){

        measurementsProcessed.add(measurement);
    }

    public void turnOnSensors() {

        this.sensorKey = true;
        LOGGER.info(String.format("Sensors have been turn on"));
    }

    public void turnOffSensors() {

        this.sensorKey = false;
        LOGGER.info(String.format("Sensor have been turn off"));
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

    public Boolean getSensorKey() {
        return sensorKey;
    }

    public List<Double> getMeasurements() {
        return measurements;
    }

    public List<Double> getMeasurementsProcessed() {
        return measurementsProcessed;
    }

    public Double getAvgConst() {
        return avgConst;
    }

    public Double getDifMaxMinConst() {
        return difMaxMinConst;
    }

    public void setDifMaxMinConst(Double difMaxMinConst) {
        this.difMaxMinConst = difMaxMinConst;
    }

    public void setAvgConst(Double avgConst) {
        this.avgConst = avgConst;
    }

}
