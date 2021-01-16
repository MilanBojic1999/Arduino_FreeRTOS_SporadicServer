#include <Arduino_FreeRTOS.h>


void setup()
{
  
  Serial.begin(9600);
  Serial.println(F("In Setup function"));

  /* Create two tasks with priorities 1 and 2. An idle task is also created, 
     which will be run when there are no tasks in RUN state */

  //xTaskCreate(MyTask1, "Task1", 100, NULL, 2, NULL);
  //xTaskCreate(MyTask2, "Task2", 100, NULL, 1, NULL);

  xSporadicServerInit((TickType_t)40,(TickType_t)1);
  
  xAperiodicTaskCreate(MyTask1APer, "Task3", 65, NULL, NULL,(TickType_t)0);
  xAperiodicTaskCreate(MyTask2APer, "Task4", 65, NULL, NULL,(TickType_t)21);
  xAperiodicTaskCreate(MyTask3APer, "Task5", 65, NULL, NULL,(TickType_t)37);
  //xAperiodicTaskCreate(MyTask1APer, "Task31", 65, NULL, NULL,(TickType_t)200);
  /*xAperiodicTaskCreate(MyTask2APer, "Task41", 75, NULL, NULL,(TickType_t)22);
  xAperiodicTaskCreate(MyTask3APer, "Task51", 75, NULL, NULL,(TickType_t)0);
  xAperiodicTaskCreate(MyTask1APer, "Task32", 75, NULL, NULL,(TickType_t)20);*/
 
  //xTaskCreate(MyIdleTask, "IdleTask", 100, NULL, 0, NULL);
  xPeriodicTaskCreate(MyTask1Per,"Per1",65,NULL,NULL,(TickType_t)28,(TickType_t)3);
  xPeriodicTaskCreate(MyTask2Per,"Per2",65,NULL,NULL,(TickType_t)32,(TickType_t)3);
  xPeriodicTaskCreate(MyTask3Per,"Per3",75,NULL,NULL,(TickType_t)46,(TickType_t)3);
  /*xPeriodicTaskCreate(MyTask1Per,"Per4",75,NULL,NULL,(TickType_t)39,(TickType_t)3);
  xPeriodicTaskCreate(MyTask2Per,"Per5",75,NULL,NULL,(TickType_t)32,(TickType_t)3);
  /*xPeriodicTaskCreate(MyTask3Per,"Per3",75,NULL,NULL,(TickType_t)27,(TickType_t)3);
  xPeriodicTaskCreate(MyTask1Per,"Per1",75,NULL,NULL,(TickType_t)49,(TickType_t)3);
  xPeriodicTaskCreate(MyTask2Per,"Per2",75,NULL,NULL,(TickType_t)33,(TickType_t)3);
  xPeriodicTaskCreate(MyTask3Per,"Per3",75,NULL,NULL,(TickType_t)96,(TickType_t)3);*/
  
  Serial.println(F("Finished Setup function"));
}

unsigned int cnt = 0;
char timer = 1;
void loop()
{
  //Serial.println(F("Task1A\t\t"));
  /*Serial.print((xGetCurrTask()));
  Serial.print(F("   "));*/
  /*Serial.print(xTaskGetTickCount());
  Serial.print(F("  ---->> "));*/
  /*Serial.print(xGetSystemStatus());
  Serial.print(F("  ----  )) "));
  Serial.print(xGetNextTimetoFill());
  Serial.println(F("   "));*/
  //cnt = (cnt+1);
  if(timer == 1){
    timer = 0;
    startSchedluer();
    //vTaskStartScheduler();
  }
  if(cnt == 0 && xTaskGetTickCount() > 300)
  {
    vPeriodicTaskDelete("Per1");
    cnt = 1;
  }
}

void fun()
{
  Serial.print(F("F is for fun\n\t"));
  Serial.flush();
}

/* Task1 with priority 1 */
static void MyTask1(void* pvParameters)
{
  while(1)
  {
    //if(cnt%100 == 0){
      Serial.print(F("Task1A\t\t"));
      Serial.println(cnt);
    //}
    vTaskDelay(100);
  }
}

/* Task2 with priority 2 */
static void MyTask2(void* pvParameters)
{
  while(1)
  {    
    Serial.println(F("Task2A |||||||||||"));
    vTaskDelay(10);
  }
}

static void MyTask3(void* pvParameters)
{
  while(1)
  {
    Serial.print(F("Task3A\t\t"));
    Serial.println(cnt);
    vTaskDelay(10);
  }
}

/* Idle Task with priority Zero */ 
static void MyIdleTask(void* pvParameters)
{
  while(1)
  {
    Serial.println(F("Idle state"));
    vTaskDelay(200/portTICK_PERIOD_MS);
  }
}

static void MyTask1Per(void* pvParameters)
{
  int i;
  for(i = 0;i<2;++i){
    Serial.println(F("Task1P ///"));
  }
  vTaskDelete(0);
}

/* Task2 with priority 2 */
static void MyTask2Per(void* pvParameters)
{    
   int i;
  for(i = 0;i<2;++i){
    Serial.println(F("Task2P  ________________"));
  }
   vTaskDelete(0);
}

static void MyTask3Per(void* pvParameters)
{    
   int i;
  for(i = 0;i<2;++i){
    Serial.println(F("Task3P  ********"));
  }
   vTaskDelete(0);
}

static void MyTask1APer(void* pvParameters)
{
  int i;
  for(i = 0;i<3;++i){
    Serial.println(F("Task1AP ~~~~******~~~~"));
    Serial.flush();
  }
  vTaskDelete(0);
}

static void MyTask2APer(void* pvParameters)
{
  int i;
  for(i = 0;i<3;++i){
    Serial.println(F("Task2AP +++++++++"));
    Serial.flush();
  }
  vTaskDelete(0);
}

static void MyTask3APer(void* pvParameters)
{
  int i;
  for(i = 0;i<3;++i){
    Serial.println(F("Task3AP -\\/-\\/-\\/-"));
    Serial.flush();
  }
  vTaskDelete(0);
}

void systemInfo(int runningTask,int serverCapacity,int currTick){
  
}


void taskMarker(int task,int marker){

  
}


void custMsg(int flag){

  
}
