package app;

import app.connector.ArduinoConnector;
import app.controler.mWindowControler;
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Test extends Application {

    private mWindowControler controler;
    private LinkedList<Integer> list;

    private int currTick;

    private static Test instance;
    private ArduinoConnector connector;
    private Thread connectioThread;

    private SerialPort serialPort;

    public static void main(String[] args) {
        launch(args);
    }


    public static Test getInstance() {
        if(instance==null)
            instance = new Test();
        return instance;
    }

    @Override
    public void stop() throws Exception {
        connectioThread.stop();
        if(!serialPort.closePort()) {
            System.out.println("There is a misteg");
        }
        super.stop();
    }

    @Override
    public void init() throws Exception {
        instance = this;
        this.currTick = 0;
        connector = new ArduinoConnector(instance);
        connectioThread = new Thread(connector);
        list = new LinkedList<>();
        serialPort = SerialPort.getCommPort("COM3");
        serialPort.setComPortParameters(9600,8, 1, 0);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        if(serialPort.openPort()) {
            System.out.println("Port is open:");
        }else {
            System.err.println("WRong");
        }

        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root;
        try{
            root = fxmlLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindow.fxml").openStream()));
            controler = fxmlLoader.getController();
            //instance.setControler(controler);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        /*Random r = new Random();

        for(int i=0;i<20;i++){
            list.add(r.nextInt(4));
        }

        controler.fillTaskCanves(list);*/

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Ovo je samo test");
        stage.show();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        connectioThread.start();

    }

    public ArduinoConnector getConnector() {
        return connector;
    }

    public mWindowControler getControler() {
        return controler;
    }

    public void setCurrTick(int currTick) {
        this.currTick = currTick;
    }

    public int addToList(int val){
        int ret = -1;
        if(list.size()>=20){
            ret = list.poll();
        }

        list.add(val);

        return ret;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public boolean sendSporadicServerInit(int period,int capacity) {
        String msg = "ISS "+period+" "+capacity+"\n";
        System.out.println(msg);
        return sendMassageToArduino(msg);
    }

    public boolean sendAddAperiodicTask(int task_ind,int arrival){
        String msg = "APT "+task_ind+" "+arrival+"\n";
        System.out.println(msg);
        return sendMassageToArduino(msg);
    }

    public boolean sendAddAperiodicTask(int task_ind){
        int arrival = currTick + 5;
        String msg = "APT "+task_ind+" "+arrival+"\n";
        System.out.println(msg);
        return sendMassageToArduino(msg);
    }

    public boolean sendAddPeriodicTask(String taskName,int task_ind,int period){
        String msg = "PET "+taskName+" "+task_ind+" "+period+"\n";
        System.out.println(msg);
        return sendMassageToArduino(msg);
    }

    public boolean sendStopPeriodicTask(String taskName){
        String msg = "SPT "+taskName+"\n";
        System.out.println(msg);

        return sendMassageToArduino(msg);
    }

    public boolean sendStartServer(){
        String msg = "SSC ";
        System.out.println(msg);
        return sendMassageToArduino(msg);
    }

    private boolean sendMassageToArduino(String msg){
        try {
            serialPort.getOutputStream().write(msg.getBytes());
            serialPort.getOutputStream().flush();
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }

        return true;
    }


}
