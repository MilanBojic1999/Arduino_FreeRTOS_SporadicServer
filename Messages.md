
## JAVA to Arduino

  ISS - Initialize Sporadic Server *( int period, int capacity )*
  
  APT - Add Aperiodic task *( int task_ind, int arrivel )*
  
  PET - Add Periodic task *( string task_name,int task_ind,int period )*
  
  SPT - Stop Periodic task *( string task_name )*
  
  ABT - Add Batch of tasks *( int num_periodic, int num_aperiodic )*
  
  MSC - Get Servers max capacity  *( void )*

## Arduino to JAVA
  
  SYS - Systems status ->*(int runningTask,int serverCapacity,int currTick )*
  
  TMA - Tasks markers ->*(int task,int marker)*
  
  MSG - Arduino String Message ->*( int msg_ind )*
  
