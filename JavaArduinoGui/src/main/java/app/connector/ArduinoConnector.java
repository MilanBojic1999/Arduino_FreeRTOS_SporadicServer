package app.connector;

import app.Test;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.Arrays;

public class ArduinoConnector implements Runnable{

    private Test app;

    public ArduinoConnector(Test app) {
        this.app = app;
    }

    public String reciveMessage() throws IOException {
        SerialPort sp = app.getSerialPort();
        byte[] bytes = new byte[100];
        int i=0;
        int bll = sp.getInputStream().available();
        System.out.println(bll);
        if(bll==0)
            return "";
        while(sp.getInputStream().available()>0) {
            int tmp = sp.getInputStream().read();
            //System.out.println(tmp);
            if(tmp==10)
                break;
            try {
                bytes[i++] = (byte) tmp;
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Overflow: "+tmp);
            }
        }
        System.out.println("AA_:"+Arrays.toString(bytes));
        if(i==0)
            return "";
        return new String(bytes);
    }

    @Override
    public void run() {
        while(true) {
            //app.newFill(0);
            //System.out.println("NE message");
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
            if(msg.equals(""))
                return;
            System.out.println("This: "+msg);

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
