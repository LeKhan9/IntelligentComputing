
/*--------------------------------------------------------------------------------
					Mohammad Khan 
				Expert_System, Fall 2014
   Utilizes a rule based expert system that prioritizes based on importance of 
   certain situations that are hard-coded in. Not every rule will fire, but  
   rules are strategically placed in ordering from top to bottom of the 
   ones that tackle the highest level of immediate danger.
   ---------------------------------------------------------------------------------*/
class MKhan extends javaAI
 {
        @Override
        public void AI_loop() 
        {

                setTurnSpeed(20); 
                setPower(5.0);

                double shipTrackDeg=  selfTrackingDeg();//deg of track movement
                double shipHeadDeg=  selfHeadingDeg(); //deg of head movement 
                double enemyDist= enemyDistanceId(closestShipId()); //distance from enemy
                double enemyHead= enemyHeadingDeg(closestShipId()); //deg heading of enemy
                double lockedEnemy= lockHeadingDeg(); //direction track locked onto enemy 
                double enemT = enemyTrackingDegId(closestShipId()); //enemy tracking 
                                
                int turnShot= aimdir(closestShipId()); //direction needed to turn to shoot enemy
                int enemX= screenEnemyX(closestShipId());  //enemy X coordinate
                int enemY= screenEnemyY(closestShipId());  //enemy Y coordinate 
                int posX= selfX();  //self X coordinate 
                int posY= selfY(); //self Y coordinate
                int enemyInt= (int)enemyHead; //cast into int of enemy heading 
                int shipTrackInt = (int)shipTrackDeg; //cast into int of self tracking
                int shipHeadInt= (int)shipHeadDeg; //cast into into of self heading
                int lockedEnemInt= (int)lockedEnemy; //cast into int of direction track locked onto enemy
                int enemTrack = (int) enemT;

                int dist=150; //distance variable for feelers
                int wallBack= wallFeeler(dist-50, shipHeadInt+180); //back of ship -tail
                int wallForward= wallFeeler(dist,shipHeadInt); //front of ship -head
                int wall90= wallFeeler(dist,shipHeadInt + 90); //side of ship
                int wall270= wallFeeler(dist,shipHeadInt +270); //side of ship
                int wallPerif1= wallFeeler(dist+50, shipHeadInt+30); //30 degrees away from front
                int wallPerif2= wallFeeler(dist+50, shipHeadInt-30); //30 degrees away from front 
                int wallBlind1= wallFeeler(dist+50, shipHeadInt+150); //30 degrees away from back
                int wallBlind2= wallFeeler(dist+50, shipHeadInt+210); //30 degrees away from back   


                int wall= wallBetween(posX, posY, enemX, enemY, 1, 1); //checks for wall between enemy and ship
                int xSpeed= selfVelX(); //speed in x direction 
                int ySpeed= selfVelY(); //speed in y direction


                System.out.println("Ship Track:" + shipTrackInt);
                System.out.println("Ship Head:" + shipHeadDeg);
                System.out.println("posX:" + posX);
                System.out.println("posY:" + posY);
                System.out.println("Speed:" + selfSpeed()+ ", X, " + xSpeed+ " , Y, " + ySpeed);
                System.out.println("wallBack: " + wallBack);
                System.out.println("wallForward: " + wallForward);
                System.out.println("wall90: " + wall90);
                System.out.println("wall270: " + wall270);
                System.out.println("turnShot: "+ turnShot);
                System.out.println("wall: " + wall);
                System.out.println("degree to turn to enem: " +lockedEnemInt);
                
                //inverse tangent taking XY Radar of enemy and own XY Radar to find angle needed to turn to enemy
                double turnCalc= Math.toDegrees(Math.atan((closestRadarY()-selfRadarY())/(closestRadarX()-selfRadarX())));
                int turnDeg= (int)turnCalc;

                System.out.println("turnDeg needed:" + turnDeg);
               
                thrust(0);
                turnLeft(0);
                turnRight(0);
                
                //if negative value returned for inverse tangent, add to unit circle
                if(turnDeg<0)
                {
                    turnDeg= 360+turnDeg;
                }
                //if enemy is approaching from left and head is in general direction
                else if(enemX<posX && wall<0)
                {
                    //setPower(20);
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else if(enemX>posX && wall<0)
                {
                    //setPower(20);
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else if(enemY>posY && wall<0)
                {
                    //setPower(20);
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else if(enemY<posY && wall<0)
                {
                    //setPower(20);
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }

                else if(wallBack<dist-100)
                {
                        setPower(11);
                        thrust(1);
                }

                else if(wallForward<dist && wallPerif1<dist+50)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wallForward<dist &&wallPerif2<dist+50)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else if(wallForward<dist && wall90<dist)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wallForward<dist && wall270<dist)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else if(wallBack<dist && wall90<dist)
                {
                        turnRight(1);
                        thrust(1);    
                }
                else if(wallBack<dist && wall270<dist)
                {
                        turnLeft(1);
                        thrust(1);
                }

                else if(wallBlind1< dist-50)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wallBlind2<dist-50)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else if(wall90<dist-50)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wall270<dist-50)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else 
                {
                    lockClose();
                    setPower(20);
                    turnToDeg(turnDeg);
                    thrust(1);
                    fireShot();
                }
        }
        public MKhan(String args[]) 
        {

                super(args);
        }
        public static void main(String args[])
        {
                String[] new_args = {"-name","IamGonnaDie"};
                MKhan spinner = new MKhan(new_args);
        }
}
