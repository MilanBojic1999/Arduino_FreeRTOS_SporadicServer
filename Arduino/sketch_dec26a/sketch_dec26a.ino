#define YELL_LED 2
#define GREEN_LED 3
#define BLUE_LED 4
#define RED_LED 5

#define TEST1_LED 12
#define TEST2_LED 10

void setup() {
  Serial.begin(9600);
  
  pinMode(RED_LED, OUTPUT);
  pinMode(BLUE_LED, OUTPUT);
  pinMode(GREEN_LED, OUTPUT);
  pinMode(YELL_LED, OUTPUT);

  pinMode(TEST1_LED, OUTPUT);
  pinMode(TEST2_LED, OUTPUT);
  
  unsigned long timeBegin = micros();
  
  /*for (int i = 0; i < 500; i++)
  {
    digitalWrite(TEST_PIN, HIGH);
    digitalWrite(TEST_PIN, LOW);
  }*/

  lightRGB(NULL);
  
  unsigned long timeEnd = micros();
  unsigned long duration = timeEnd - timeBegin;
  double averageDuration = (double)duration / 1000.0;
  Serial.println(averageDuration);
}
void loop() {}

void lightRed(void* pvParameters){
  digitalWrite(RED_LED,HIGH);
  digitalWrite(BLUE_LED,LOW);
  digitalWrite(GREEN_LED,LOW);
  digitalWrite(YELL_LED,LOW);
  delay(10);
}

void lightBlue(void* pvParameters){
  digitalWrite(RED_LED,LOW);
  digitalWrite(BLUE_LED,HIGH);
  digitalWrite(GREEN_LED,LOW);
  digitalWrite(YELL_LED,LOW);
  delay(10);
}

void lightGreen(void* pvParameters){
  digitalWrite(RED_LED,LOW);
  digitalWrite(BLUE_LED,LOW);
  digitalWrite(GREEN_LED,HIGH);
  digitalWrite(YELL_LED,LOW);
  delay(10);
}

void lightYellow(void* pvParameters){
  digitalWrite(RED_LED,LOW);
  digitalWrite(BLUE_LED,LOW);
  digitalWrite(GREEN_LED,LOW);
  digitalWrite(YELL_LED,HIGH);
  delay(10);
}

void lightRGB(void* pvParameters){
  digitalWrite(RED_LED,HIGH);
  delay(2);
  digitalWrite(BLUE_LED,HIGH);
  delay(2);
  digitalWrite(GREEN_LED,HIGH);
  delay(2);
  digitalWrite(YELL_LED,HIGH);
  delay(2);
}
