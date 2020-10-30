int SENSOR_A0; 
int SENSOR_A1; 
int sensorValInit = 0;
int our_delay = 300;
int sensorValCurrA0 = 0;
int sensorValCurrA1 = 0;
int lightGr = 0;
int lightRd = 0;
bool pirmas;
int input = 0;


void setup()
{
  pirmas = true;
  lightGr = 12;
  lightRd = 13;
  SENSOR_A0 = A0;
  SENSOR_A1 = A1;
  pinMode(lightGr, OUTPUT);
  pinMode(lightRd, OUTPUT);
  
  pinMode(SENSOR_A0, INPUT);
  pinMode(SENSOR_A1, INPUT);
 
  Serial.begin(9600);

  
  sensorValInit = analogRead(SENSOR_A0);
 
  digitalWrite(lightRd, HIGH);
  digitalWrite(lightGr, HIGH);



}
void loop()
{
  sensorValCurrA0 = analogRead(A0);  
  sensorValCurrA1 = analogRead(A1);
  
  if (Serial.available() > 0) {
    input = Serial.parseInt();
    
  }
  
  if(sensorValInit * 0.35 > sensorValCurrA0 || sensorValInit * 0.35 > sensorValCurrA1)
  {
    if(input > 0){
      delay(input);
    }
    sensorValCurrA0 = analogRead(A0);  
    sensorValCurrA1 = analogRead(A1);
    
    if(sensorValInit * 0.35 > sensorValCurrA0 || sensorValInit * 0.35 > sensorValCurrA1)
    {
      Serial.println("1");
      
      digitalWrite(lightGr, LOW);
     
      for(int i = 0; i < 5; i++)
      {
      digitalWrite(lightRd, HIGH);
      delay(our_delay);
      digitalWrite(lightRd, LOW);
      delay(our_delay);
      }
      
      digitalWrite(lightRd, HIGH);
      digitalWrite(lightGr, HIGH);   
      delay(2000);
    }
    
  }



 

  
}
