package app.connector;

import app.Test;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

public class ArduinoConnector implements Runnable{

    private Test app;

    public ArduinoConnector(Test app) {
        this.app = app;
    }

    public String reciveMessage() throws IOException {
        SerialPort sp = app.getSerialPort();
        byte[] bytes = new byte[100];
        int i=0;
        while(sp.getInputStream().available()>0) {
            int tmp = sp.getInputStream().read();
            if(tmp==10)
                break;
            bytes[i++] = (byte) tmp;
        }

        return new String(bytes);
    }

    @Override
    public void run() {
        while(true) {
            //app.newFill(0);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            String msg = "";
            try {
                msg = reciveMessage();
            } catch (IOException e) {
                //e.printStackTrace();
                continue;
            }

            System.out.println(msg);

            if(msg.startsWith("SYS")){

                String[] arr = msg.split(" ");
                int runTask = getInteger(arr[1]);
                int servCap = getInteger(arr[2]);
                int currTick = getInteger(arr[3]);

                app.getControler().addToList(runTask);
                app.getControler().setCapacity(servCap);
                app.setCurrTick(currTick);

            }else if(msg.startsWith("TMA")){

                String[] arr = msg.split(" ");

                int task = getInteger(arr[1]);
                int flag = getInteger(arr[2]);

                if(flag == 1){
                    app.getControler().addToArrival(task);
                }else{
                    System.err.println("IDK what this mead: "+flag);
                }

            }else if(msg.startsWith("MSG")){
                String[] arr = msg.split(" ");
                int ind = getInteger(arr[1]);
                if(ind == 1){
                    app.getControler().openWarningWindow("This batch isn't schedulable");
                }else{
                    System.err.println("IDK what this mead: "+ind);
                }

            }else if(msg.startsWith("MSC")){

                String[] arr = msg.split(" ");
                int max = getInteger(arr[1]);

                app.getControler().setMaxCap(max);
            }

        }
    }

    int getInteger(String str){
        byte[] arr = str.getBytes();

        return ((arr[0] & 0xff)) << 8 | (arr[1] & 0xff) ;
    }
}
