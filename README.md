# Arduino_FreeRTOS_SporadicServer

# If-you-look-you-die
Bunch of smaller project I needed to transfer to more computers:

## What hava I done so far:

1. Promenio sam strukturu TCB_t da poseduje periodu( funkcionalno deadline za schedularae) i pri xTaskCreate ta perioda je na max, koristiće se druge funkcije za pravljenje korisničkih taskova

2. Kreirana struktura za periodični task, poseduje sve što joj treba da u određeno vreme napravi mini task, koje će raditi posao ( taskHandle, Periodu, Vreme izvršavanja, kod funkcije za izvršavanje, parametri, veličina steka i ime periodičnog taska)

3. Napravljena statički niz koji će sadržati sve periodične taskove

4. Kreiranje aperiodičnih taskova, efektivno će da zameni xTaskCreate ali još nije gotov... Treba da se zameni kada se doda sporadik server

5. Kreiranje periodičnih taskova i napravljena statički niz (koji će možda biti promenjen u ulantačnu listu iz FreeRTOS-a) koji će sadržati sve instance periodničnih taskova. 

6. Kreiranje periodičnih taskova je sada u **prvIdleTask** funkciji, možda treba zamenti kasnije **vApplicationIdleHook**, jer mi neće trebati vrv;

7. Urađen **Rate monotinic** i trebalo bi da radi na svim periodičnim taskovima.

8. Urađena struktura za Server i njeno pravljenje. Provera rasporedivosti taskova.

9. Početak integracije Servera u raspoređivač, jedino je ostalo da se odradi održavanje kapaciteta servera. Napravljena list aperiodičnih taskova na čekanju i raspoređivanje na Server ( jedan po jedan ) slično kao i mehanizam za odlaganje taskova ( xTaskDelay )

10. Popunjavanje servera urađeno, Izračunavanje perioda popunjavanja ( kada se task završi ili kada se kapacitet spusti na nulu )


## TO DO:
1. ~~Popraviti tako da radi Rate Monotonic (RT)~~

2. ~~Napraviti strukture za Server i placeholdere za aperiodične taskove~~
 
   2.1. ~~Napisati funkcije za inicijalizaciju Servera ( treba da bude obavezna u korisničkom prostoru pri korišćenju aperiodičnih taskova)~~
 
   2.2. ~~Napistati funkcije za pravljenje aperiodničnih tasakova( ili zameniti već postojeće) i za skladištenje dok se čekaju na izvršavanje ( pogledati liste iz list.h)~~
   
      2.2.1. ~~Ispravka cilja: ne treba kreirati placeholder za task, već se može direktno praviti TCB_t struktura, jer je **xTaskCreate preskupa funkcija**, posebno za IncreentTick. Samo će se kreirani taskovi čuvati u posobnu listu ( pogledati **xDelayedTaskList** ) i samo u praviom trenutku ubacati ih u pxReadyTasksList.~~

3. Odraditi integraciju Servera u raspoređivač

   3.1. ~~Raspoređivanje servera i aperiodičnih taskova~~
   
   3.2. ~~Održavanje kapaciteta, i pravila vezano za to~~

4. ~~Provera da li su taskovi rasporedivi~~


## Problemi:

 1. Problem svi taskovi kreirani sa **xTaskCreate** će biti na istom novou kao i IDLE task i vrlo verovatno se neće izvršiti **NE KORISTITI!**
 
 2. Problem nastaje ako se Idle task ne nalazi na dugo na raspoređivač, neće se kreirati periodični taskovi ili može da preskoči kreiranje nekog
 
 3. Taskovi kreirani sa xTaskCreate funkcijom će biti Periodični, jer ako im je isPeriodic False ( tj. Apriodični su ) to će da pravi prbolem za Server i za "prave" aperiodične taskove 
