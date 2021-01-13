const int led1 = 5;
const int led2 = 4;
const int led3 = 3;

int  switchPin;

void setup() {
  pinMode(led1,OUTPUT);
  pinMode(led2,OUTPUT);
  pinMode(led3,INPUT);
  
  switchPin = LOW;

}

void loop() {
  // put your main code here, to run repeatedly:

  switchPin = digitalRead(led3);

  if(switchPin == HIGH){
    digitalWrite(led1,HIGH);
    digitalWrite(led2,LOW);
  }else{
    digitalWrite(led2,HIGH);
    digitalWrite(led1,LOW);
  }

  delay(200);
}
