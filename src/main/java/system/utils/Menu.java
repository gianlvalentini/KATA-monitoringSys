package system.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import system.sensor.Sensor;
import system.store.MeasurementStore;

import java.util.Scanner;

public class Menu {

    private static MeasurementStore store;
    private static Boolean case1Running = false;

    public Menu(MeasurementStore measurementStore) {
        store = measurementStore;
    }

    public static void menuSelector(int choice, Double... newValue){

        Scanner input;
        Double value;

        switch (choice) {
            case 0:
                break;

            case 1:
                baseCase();
                break;

            case 2:
                stopSensors();
                break;

            case 3:
                System.out.println("Option Three has been selected");

                if(newValue == null) {
                    input = new Scanner(System.in); //TODO... extract inputs logic to another method
                    System.out.println("Entre maxMin Const value | Between 0 - 100 : ");
                    value = input.nextDouble();
                }else {
                    value = newValue[0];
                }

                changeMonitorConst("maxMin", value);
                break;

            case 4:
                System.out.println("Option Four has been selected");

                if(newValue == null) {
                    input = new Scanner(System.in);
                    System.out.println("Entre avg Const value | Between 0 - 100: ");
                    value = input.nextDouble();
                } else {
                        value = newValue[0];
                }

                changeMonitorConst("avg", value);
                break;

            case 5:
                System.out.println("Option Five has been selected");
                System.out.println("Stoping System");
                System.exit(0);
                break;

            default:
                System.out.println("The choice you choose it's not available, please try again");
        }
    }

    private static void baseCase(){

        System.out.println("Option One has been selected");

        if(!case1Running) {
            System.out.println("activating sensors");

            Thread sensorOne = new Thread(new Sensor("One", store));
            Thread sensorTwo = new Thread(new Sensor("Two", store));
            Thread sensorThree = new Thread(new Sensor("Three", store));
            Thread sensorFour = new Thread(new Sensor("Four", store));

            store.turnOnSensors();

            sensorOne.start();
            sensorTwo.start();
            sensorThree.start();
            sensorFour.start();
        }else {
            System.out.println("Sensors are already in activity");
        }
    }

    private static void stopSensors() {

        System.out.println("Option Two has been selected");
        System.out.println("deactivating sensors");

        store.turnOffSensors();
        case1Running = false;
    }

    private static void changeMonitorConst(String constType, double newValue) {

        if(newValue > 100 || newValue < 0){
            System.err.println("The const number is out of the limits (0 - 100), please try again");
        } else {

            switch (constType) {
                case "maxMin":
                    store.setDifMaxMinConst(newValue);
                    break;

                case "avg":
                    store.setAvgConst(newValue);
                    break;

                default:
                    break;
            }
        }
    }
}
