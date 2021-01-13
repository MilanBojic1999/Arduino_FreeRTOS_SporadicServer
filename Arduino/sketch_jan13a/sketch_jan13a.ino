void setup() {
  Serial.begin(9600);
  while(!Serial)
  {
    ;
  }
}

void loop() {
  if(Serial.available()>0){
    byte incByte = Serial.read();
    if(incByte != -1)
    {
      Serial.write("I recive: ");
      Serial.write(incByte+'0');
      Serial.flush();
    }
  }

}
