package system;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import system.monitoringSys.Monitor;
import system.sensor.Sensor;
import system.store.MeasurementStore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SystemMain {

    private static Boolean case1Running = false;
    private static MeasurementStore store = new MeasurementStore();

    public static void main(String[] args)  {

        //LOG
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        //INPUT
        int selection;
        Scanner input = new Scanner(System.in);

        //MONITOR
        Thread monitor = new Thread(new Monitor(store));
        monitor.start();

        //MENU
        while(true) {

            System.out.println("\n/***************************************************/\n");

            System.out.println("Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - Run BaseCase (4 sensors with random measurements)");
            System.out.println("2 - Stop Sensors (The monitor won't stop)");
            System.out.println("3 - Configure Monitor DifMaxMin Const Value (If max-min is bigger than this Const " +
                    "the system throws a console error) | Default 40.0");
            System.out.println("4 - Configure Monitor avg Const Value (If average is bigger than this Const " +
                    "the system throws a console error) | Default: 70.0");
            System.out.println("5 - Stop System / Quit");

            System.out.println("\nInsert numeric choice:");

            try{

                selection = input.nextInt();
                menuSelector(selection);
            }
            catch (InputMismatchException e){

                System.err.println("The input doesn't match with the format required, only numbers are available");
                System.exit(1);
            }
        }
    }

    private static void menuSelector(int choice){ //TODO... extract menu and cases' methods in another class

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
                input = new Scanner(System.in); //TODO... extract inputs logic to another method
                System.out.println("Option Three has been selected");
                System.out.println("Entre maxMin Const value | Between 0 - 100 : ");
                value = input.nextDouble();
                changeMonitorConst("maxMin", value);
                break;

            case 4:
                input = new Scanner(System.in);
                System.out.println("Option Four has been selected");
                System.out.println("Entre avg Const value | Between 0 - 100: ");
                value = input.nextDouble();
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
