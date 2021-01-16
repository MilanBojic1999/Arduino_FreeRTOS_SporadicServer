#include <Arduino_FreeRTOS.h>


#define YELL_LED 2
#define GREEN_LED 3
#define BLUE_LED 4
#define RED_LED 5

#define TEST1_LED 12
#define TEST2_LED 10

// funkcije veza izmađu arduina i FreeRTOS-a

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  pinMode(RED_LED, OUTPUT);
  pinMode(BLUE_LED, OUTPUT);
  pinMode(GREEN_LED, OUTPUT);
  pinMode(YELL_LED, OUTPUT);

  pinMode(TEST1_LED, OUTPUT);
  pinMode(TEST2_LED, OUTPUT);
  
  while(!Serial)
  {
    ;
  }
}

String msg = "";

char ss = 0;
void loop() {
  if(Serial.available() > 0)
  {
    lightTest2();
    delay(4);
    msg = Serial.readStringUntil(' ');

    if(msg.equals("ISS")){
      int period = Serial.readStringUntil(' ').toInt();
      int cap = Serial.readStringUntil('\n').toInt();
      
      initServer(period,cap);
      
    }else if(msg.equals("APT")){
      int taskInd = Serial.readStringUntil(' ').toInt();
      int arrivel = Serial.readStringUntil('\n').toInt();

      createAperiodicTask(taskInd,arrivel);
      
    }else if(msg.equals("PET")){
      String taskName = Serial.readStringUntil(' ');
      int taskInd = Serial.readStringUntil(' ').toInt();
      int period = Serial.readStringUntil('\n').toInt();

      createPeriodicTask(taskName.c_str(),taskInd,period);
      
    }else if(msg.equals("SPT")){
      String taskName = Serial.readStringUntil('\n');
      stopPeriodic(taskName.c_str());
    }else if(msg.equals("SSC")){
      //delay(10);
      
      char ress = bCheckSchedulability();
      /*Serial.write(ress+'0');
      Serial.flush();*/
      if(ress == 1){
        
        Serial.write("MSC ");
        int res = uxGetMaxServerCapacity();

        Serial.write(highByte(res));
        Serial.write(lowByte(res));
        Serial.write(10);
        //delay(5);
        Serial.flush();
        
        vTaskStartScheduler();
      }else{
        custMsg(1);
        vRestartAllPeriodicTasks();
      }
      
      //startSchedluer();
    }else {
      //Serial.println();
      Serial.write("MIS ");
      Serial.println(msg);
      Serial.flush();
      //delay(10);
    }
    
  }else{
    lightTest1();
    //Serial.write(103);
  }
  msg = "";

}

void systemInfo(int runningTask,int serverCapacity,int currTick){

  Serial.write("SYS ");
  
  Serial.write(highByte(runningTask));
  Serial.write(lowByte(runningTask));

  Serial.write(' ');
  
  Serial.write(highByte(serverCapacity));
  Serial.write(lowByte(serverCapacity));

  Serial.write(' ');
  
  Serial.write(highByte(currTick));
  Serial.write(lowByte(currTick));

  Serial.write(10);
  Serial.flush();
  //delay(10);
}


void taskMarker(int task,int marker){

  Serial.write("TMA ");
  
  Serial.write(highByte(task));
  Serial.write(lowByte(task));

  Serial.write(' ');
  
  Serial.write(highByte(marker));
  Serial.write(lowByte(marker));

  Serial.write(10);
  Serial.flush();
  //delay(10);
}



void custMsg(int flag){

  Serial.write("MSG ");
  
  Serial.write(highByte(flag));
  Serial.write(lowByte(flag));

  Serial.write(10);
  Serial.flush();
  //delay(10);
  
}

void maxServerCap(){
  
  Serial.write("MSC ");
  
  int val = uxGetMaxServerCapacity();
  
  Serial.write(highByte(val));
  Serial.write(lowByte(val));

  Serial.write(10);
  Serial.flush();
  //delay(10);
}

// funkcije koje će opslužiti Java pozive

void initServer(int period, int capacity){
  xSporadicServerInit((TickType_t)period,(TickType_t)capacity);
}

void createPeriodicTask(const char taskName[], int taskInd,const int period){
  
  switch(taskInd){
    case 1:
      xPeriodicTaskCreate(lightRed,taskName,65,NULL,NULL,(TickType_t)period,(TickType_t)4);
      break;
    case 2:
      xPeriodicTaskCreate(lightBlue,taskName,65,NULL,NULL,(TickType_t)period,(TickType_t)4);
      break;
    case 3:
      xPeriodicTaskCreate(lightGreen,taskName,65,NULL,NULL,(TickType_t)period,(TickType_t)4);
      break;
    case 4:
      xPeriodicTaskCreate(lightYellow,taskName,65,NULL,NULL,(TickType_t)period,(TickType_t)4);
      break;
    default:
      xPeriodicTaskCreate(lightRGB,taskName,65,NULL,NULL,(TickType_t)period,(TickType_t)8);
  }
}

void createAperiodicTask(int taskInd,const int arrivel){
  switch(taskInd){
    case 1:
      xAperiodicTaskCreate(lightRed,"AR",65,NULL,NULL,(TickType_t)arrivel);
      break;
    case 2:
      xAperiodicTaskCreate(lightBlue,"AB",65,NULL,NULL,(TickType_t)arrivel);
      break;
    case 3:
      xAperiodicTaskCreate(lightGreen,"AG",65,NULL,NULL,(TickType_t)arrivel);
      break;
    case 4:
      xAperiodicTaskCreate(lightYellow,"AY",65,NULL,NULL,(TickType_t)arrivel);
      break;
    default:
      xAperiodicTaskCreate(lightRGB,"ARGB",65,NULL,NULL,(TickType_t)arrivel);
  }
}

void stopPeriodic(const char taskName[]){
  vPeriodicTaskDelete(taskName);
}

void lightRed(void* pvParameters){
  digitalWrite(RED_LED,HIGH);
  digitalWrite(BLUE_LED,LOW);
  digitalWrite(GREEN_LED,LOW);
  digitalWrite(YELL_LED,LOW);
  delay(5);
  vTaskDelete(0);
}

void lightBlue(void* pvParameters){
  digitalWrite(RED_LED,LOW);
  digitalWrite(BLUE_LED,HIGH);
  digitalWrite(GREEN_LED,LOW);
  digitalWrite(YELL_LED,LOW);
  delay(5);
  vTaskDelete(0);
}

void lightGreen(void* pvParameters){
  digitalWrite(RED_LED,LOW);
  digitalWrite(BLUE_LED,LOW);
  digitalWrite(GREEN_LED,HIGH);
  digitalWrite(YELL_LED,LOW);
  delay(5);
  vTaskDelete(0);
}

void lightYellow(void* pvParameters){
  digitalWrite(RED_LED,LOW);
  digitalWrite(BLUE_LED,LOW);
  digitalWrite(GREEN_LED,LOW);
  digitalWrite(YELL_LED,HIGH);
  delay(5);
  vTaskDelete(0);
}

void lightRGB(void* pvParameters){
  digitalWrite(RED_LED,HIGH);
  vTaskDelay(1);
  digitalWrite(BLUE_LED,HIGH);
  vTaskDelay(1);
  digitalWrite(GREEN_LED,HIGH);
  vTaskDelay(1);
  digitalWrite(YELL_LED,HIGH);
  vTaskDelay(1);

  vTaskDelete(0);
}

void lightTest1(){
  digitalWrite(TEST1_LED,HIGH);
  digitalWrite(TEST2_LED,LOW);
}

void lightTest2(){
  digitalWrite(TEST1_LED,LOW);
  digitalWrite(TEST2_LED,HIGH);
}
