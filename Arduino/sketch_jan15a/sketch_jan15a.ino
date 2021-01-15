#define RED 13
#define BLUE 12
#define GREEN 11
#define YELLOW 10

void setup() {
  // put your setup code here, to run once:
  pinMode(RED,OUTPUT);
  pinMode(BLUE,OUTPUT);
  pinMode(GREEN,OUTPUT);
  pinMode(YELLOW,OUTPUT);
}

int cnt = 0;
void loop() {
  cnt = random(0,4);
  switch(cnt){
    case 0:
      lightRed();
      break;
    case 1:
      lightBlue();
      break;
    case 2:
      lightGreen();
      break;
    case 3:
      lightYellow();
      break; 
  }

  cnt = (cnt+1)%4;
  delay(150);

}

void lightRed(){
  digitalWrite(RED,HIGH);
  digitalWrite(BLUE,LOW);
  digitalWrite(GREEN,LOW);
  digitalWrite(YELLOW,LOW);
}

void lightBlue(){
  digitalWrite(RED,LOW);
  digitalWrite(BLUE,HIGH);
  digitalWrite(GREEN,LOW);
  digitalWrite(YELLOW,LOW);
}

void lightGreen(){
  digitalWrite(RED,LOW);
  digitalWrite(BLUE,LOW);
  digitalWrite(GREEN,HIGH);
  digitalWrite(YELLOW,LOW);
}

void lightYellow(){
  digitalWrite(RED,LOW);
  digitalWrite(BLUE,LOW);
  digitalWrite(GREEN,LOW);
  digitalWrite(YELLOW,HIGH);
}
