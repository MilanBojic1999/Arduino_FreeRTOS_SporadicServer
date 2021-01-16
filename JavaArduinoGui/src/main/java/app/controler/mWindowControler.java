package app.controler;

import java.net.URL;
import java.util.*;

import app.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class mWindowControler {

    private LinkedList<Integer> taskList;
    private List<Integer> arrivalList;

    private int currMaxPixel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField serverTF1;

    @FXML
    private TextField serverTF2;

    @FXML
    private TextField periodic1TF1;

    @FXML
    private TextField periodic1TF2;

    @FXML
    private TextField periodic1TF3;

    @FXML
    private TextField periodic2TF1;

    @FXML
    private TextField periodic2TF2;

    @FXML
    private TextField periodic2TF3;

    @FXML
    private TextField periodic3TF1;

    @FXML
    private TextField periodic3TF2;

    @FXML
    private TextField periodic3TF3;

    @FXML
    private TextField aperiodic1TF1;

    @FXML
    private TextField aperiodic1TF2;

    @FXML
    private TextField aperiodic2TF1;

    @FXML
    private TextField aperiodic2TF2;

    @FXML
    private TextField aperiodic3TF1;

    @FXML
    private TextField aperiodic3TF2;

    @FXML
    private Button startButton;

    @FXML
    private Canvas periodicCanvas1;

    @FXML
    private Canvas periodicCanvas2;

    @FXML
    private Canvas periodicCanvas3;

    @FXML
    private Canvas serverCanves;

    @FXML
    private Button redButton;

    @FXML
    private Button blueButton;

    @FXML
    private Button greenButton;

    @FXML
    private TextField deleteTF;

    @FXML
    private Button delButt;

    @FXML
    private Text maxCapT;

    @FXML
    private Canvas serverCapCanves;

    @FXML
    void blueLED(ActionEvent event) {
        System.out.println("Blue water");
        Test.getInstance().sendAddAperiodicTask(2);
    }

    @FXML
    void deletePeriodic(ActionEvent event) {
        if(deleteTF.getText().isEmpty())
            return;
        Test.getInstance().sendStopPeriodicTask(deleteTF.getText());
    }

    @FXML
    void greenLED(ActionEvent event) {
        System.out.println("Green water");
        Test.getInstance().sendAddAperiodicTask(3);
    }

    @FXML
    void redLed(ActionEvent event) {
        System.out.println("Red water");
        Test.getInstance().sendAddAperiodicTask(1);
    }

    @FXML
    public void startScheduler(ActionEvent event) {
        if(!(serverTF1.getText().isEmpty() || serverTF2.getText().isEmpty()))
            Test.getInstance().sendSporadicServerInit(
                    Integer.parseInt(serverTF1.getText()),Integer.parseInt(serverTF2.getText()));

        if(!(periodic1TF1.getText().isEmpty() || periodic1TF2.getText().isEmpty() || periodic1TF3.getText().isEmpty()))
            Test.getInstance().sendAddPeriodicTask(
                    periodic1TF1.getText(),Integer.parseInt(periodic1TF2.getText()),Integer.parseInt(periodic1TF3.getText()));
        if(!(periodic2TF1.getText().isEmpty() || periodic2TF2.getText().isEmpty() || periodic2TF3.getText().isEmpty()))
            Test.getInstance().sendAddPeriodicTask(
                    periodic2TF1.getText(),Integer.parseInt(periodic2TF2.getText()),Integer.parseInt(periodic2TF3.getText()));
        if(!(periodic3TF1.getText().isEmpty() || periodic3TF2.getText().isEmpty() || periodic3TF3.getText().isEmpty()))
            Test.getInstance().sendAddPeriodicTask(
                    periodic3TF1.getText(),Integer.parseInt(periodic3TF2.getText()),Integer.parseInt(periodic3TF3.getText()));

        if(!(aperiodic1TF1.getText().isEmpty() || aperiodic1TF2.getText().isEmpty()))
            Test.getInstance().sendAddAperiodicTask(
                    Integer.parseInt(aperiodic1TF1.getText()),Integer.parseInt(aperiodic1TF2.getText()));
        if(!(aperiodic2TF1.getText().isEmpty() || aperiodic2TF2.getText().isEmpty()))
            Test.getInstance().sendAddAperiodicTask(
                    Integer.parseInt(aperiodic2TF1.getText()),Integer.parseInt(aperiodic2TF2.getText()));
        if(!(aperiodic3TF1.getText().isEmpty() || aperiodic3TF2.getText().isEmpty()))
            Test.getInstance().sendAddAperiodicTask(
                    Integer.parseInt(aperiodic3TF1.getText()),Integer.parseInt(aperiodic3TF2.getText()));

        Test.getInstance().sendStartServer();
    }

    public int addToList(int val){
        int ret = -1;
        if(taskList.size()>=20){
            ret = taskList.poll();
        }

        currMaxPixel = taskList.size();

        taskList.add(val);
        fillTaskCanves(taskList);
        fillArrivalCanves(arrivalList);
        return ret;
    }

    public void addToArrival(int task){
        arrivalList.add(currMaxPixel*10+task);
        fillTaskCanves(taskList);
        fillArrivalCanves(arrivalList);
    }

    public void fillArrivalCanves(List<Integer> arrivalList){
        GraphicsContext gc0 = serverCanves.getGraphicsContext2D();
        GraphicsContext gc1 = periodicCanvas1.getGraphicsContext2D();
        GraphicsContext gc2 = periodicCanvas2.getGraphicsContext2D();
        GraphicsContext gc3 = periodicCanvas3.getGraphicsContext2D();

        gc0.setFill(Color.RED);
        gc1.setFill(Color.BLUE);
        gc2.setFill(Color.GREEN);
        gc3.setFill(Color.CYAN);

        for(int i=0;i<arrivalList.size();++i){
            int task = arrivalList.get(i)%10;
            int pixel = arrivalList.get(i)/10;

            //System.out.println("/-\\: "+task);
            if(task==0){
                gc0.fillRect((pixel+1)*20,10,10,10);
            }else if(task==1){
                gc1.fillRect((pixel+1)*20,10,10,10);
            }else if(task == 2){
                gc2.fillRect((pixel+1)*20,10,10,10);
            }else if(task==3){
                gc3.fillRect((pixel+1)*20,10,10,10);
            }

            pixel = pixel - 1;
            if(pixel<0){
                arrivalList.remove(i);
            }else{
                arrivalList.set(i,pixel*10+task);
            }
        }
    }

    public void fillTaskCanves(List<Integer> list){
        GraphicsContext gc0 = serverCanves.getGraphicsContext2D();
        GraphicsContext gc1 = periodicCanvas1.getGraphicsContext2D();
        GraphicsContext gc2 = periodicCanvas2.getGraphicsContext2D();
        GraphicsContext gc3 = periodicCanvas3.getGraphicsContext2D();

        gc0.setFill(Color.WHITE);
        gc0.fillRect(0,0,serverCanves.getWidth(),serverCanves.getHeight());

        gc1.setFill(Color.WHITE);
        gc1.fillRect(0,0,serverCanves.getWidth(),serverCanves.getHeight());

        gc2.setFill(Color.WHITE);
        gc2.fillRect(0,0,serverCanves.getWidth(),serverCanves.getHeight());

        gc3.setFill(Color.WHITE);
        gc3.fillRect(0,0,serverCanves.getWidth(),serverCanves.getHeight());

        gc0.setFill(Color.RED);
        gc1.setFill(Color.BLUE);
        gc2.setFill(Color.GREEN);
        gc3.setFill(Color.CYAN);

        for(int i=0;i<list.size();++i){
            int val = list.get(i);
            //System.out.println("->->: "+val);
            if(val==0){
                gc0.fillRect((i+1)*20,30,20,20);
            }else if(val==1){
                gc1.fillRect((i+1)*20,30,20,20);
            }else if(val == 2){
                gc2.fillRect((i+1)*20,30,20,20);
            }else if(val==3){
                gc3.fillRect((i+1)*20,30,20,20);
            }else{
                //System.err.println("Wrong val: "+val);
            }
        }
    }

    public void setCapacity(int val){
        GraphicsContext gc = serverCapCanves.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,serverCapCanves.getWidth(),serverCapCanves.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillText(Integer.toString(val),50,50,20);
    }

    public void setMaxCap(int val){
        //System.out.println(val);
        maxCapT.setText(String.valueOf(val));
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void openWarningWindow(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("There was a problem");
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    void initialize() {
        assert serverTF1 != null : "fx:id=\"serverTF1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert serverTF2 != null : "fx:id=\"serverTF2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic1TF1 != null : "fx:id=\"periodic1TF1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic1TF2 != null : "fx:id=\"periodic1TF2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic1TF3 != null : "fx:id=\"periodic1TF3\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic2TF1 != null : "fx:id=\"periodic2TF1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic2TF2 != null : "fx:id=\"periodic2TF2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic2TF3 != null : "fx:id=\"periodic2TF3\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic3TF1 != null : "fx:id=\"periodic3TF1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic3TF2 != null : "fx:id=\"periodic3TF2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodic3TF3 != null : "fx:id=\"periodic3TF3\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aperiodic1TF1 != null : "fx:id=\"aperiodic1TF1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aperiodic1TF2 != null : "fx:id=\"aperiodic1TF2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aperiodic2TF1 != null : "fx:id=\"aperiodic2TF1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aperiodic2TF2 != null : "fx:id=\"aperiodic2TF2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aperiodic3TF1 != null : "fx:id=\"aperiodic3TF1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert aperiodic3TF2 != null : "fx:id=\"aperiodic3TF2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodicCanvas1 != null : "fx:id=\"periodicCanvas1\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodicCanvas2 != null : "fx:id=\"periodicCanvas2\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert periodicCanvas3 != null : "fx:id=\"periodicCanvas3\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert serverCanves != null : "fx:id=\"serverCanves\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert redButton != null : "fx:id=\"redButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert blueButton != null : "fx:id=\"blueButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert greenButton != null : "fx:id=\"greenButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert deleteTF != null : "fx:id=\"deleteTF\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert delButt != null : "fx:id=\"delButt\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert serverCapCanves != null : "fx:id=\"serverCapCanves\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert maxCapT != null : "fx:id=\"maxCapT\" was not injected: check your FXML file 'MainWindow.fxml'.";


        init();
    }


    void init(){
        serverCapCanves.getGraphicsContext2D().setFont(Font.font("Verdana",20));
        taskList = new LinkedList<>();
        arrivalList = new ArrayList<>();
    }
}
