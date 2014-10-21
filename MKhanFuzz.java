
/*--------------------------------------------------------------------------------
					Mohammad Khan 
				 Fuzzy_System, Fall 2014
   Utilizes a fuzzy (Sugeno approach) to determine the angle needed to turn at the 
   live moment depending on two feelers that utilize distance, tracking, and speed 
   factors to determine singletons, the singletons are averaged to compute the "best" 
   decision. 
   ---------------------------------------------------------------------------------*/
class MKhanFuzz extends javaAI
 {
        //determines which mu value will be used for average 
        public double funky(String x, double y, double z)
        {
            //when the conditional is AND operator, return smaller
            if(x=="and")
            {
                if(y<z)
                {
                    return y;
                }
                else
                {
                    return z;
                }
            }
            //when the conditional is OR operator, return larger
            else if(x=="or")
            {
                if(y<z)
                {
                    return z;
                }
                else 
                {
                    return y;
                }
            }
            //when the degrees of membership are the same
            else 
            {
                return y;
            }
        }

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
                int enemTrack = (int) enemT; //enemy tracking cast into int

                int dist=410; //distance variable for feeler
                int feelAngle1= shipHeadInt +30;
                int feelAngle2= shipHeadInt -30;
                int feel1=  wallFeeler(dist,feelAngle1); //restricted feeler for fuzzy 1, left of head
                int feel2=  wallFeeler(dist,feelAngle2); //restricted feeler for fuzzy 2, right of head
                int wall= wallBetween(posX, posY, enemX, enemY, 1, 1);    

                int dist2=150; //distance variable for feelers
                int wallBack= wallFeeler(dist2-50, shipHeadInt+180); //back of ship -tail
                int wallForward= wallFeeler(dist2,shipHeadInt); //front of ship -head
                int wall90= wallFeeler(dist2,shipHeadInt + 90); //side of ship
                int wall270= wallFeeler(dist2,shipHeadInt +270); //side of ship
                int wallPerif1= wallFeeler(dist2+50, shipHeadInt+30); //30 degrees away from front
                int wallPerif2= wallFeeler(dist2+50, shipHeadInt-30); //30 degrees away from front 
                int wallBlind1= wallFeeler(dist2+50, shipHeadInt+150); //30 degrees away from back
                int wallBlind2= wallFeeler(dist2+50, shipHeadInt+210); //30 degrees away from back 

                int xSpeed= selfVelX(); //speed in x direction 
                int ySpeed= selfVelY(); //speed in y direction
                double zSpeed= Math.sqrt(xSpeed^2 + ySpeed^2);//pythagoras to find diagonal speed

                //inverse tangent taking XY Radar of enemy and own XY Radar to find angle needed to turn to enemy
                double turnCalc= Math.toDegrees(Math.atan((closestRadarY()-selfRadarY())/(closestRadarX()-selfRadarX())));
                int turnDeg= (int)turnCalc;
                //if negative value returned for inverse tangent, add to unit circle
                if(turnDeg<0)
                {
                    turnDeg= 360+turnDeg;
                }


                System.out.println("Ship Track:" + shipTrackInt);
                System.out.println("Ship Head:" + shipHeadDeg);
                System.out.println("posX:" + posX);
                System.out.println("posY:" + posY);
                System.out.println("Speed:" + selfSpeed()+ ", X, " + xSpeed+ " , Y, " + ySpeed);
                System.out.println("Actual Speed:" + zSpeed);
                System.out.println("degree to turn to enem: " +lockedEnemInt);
                
                //sets for Closeness linguistic variable and Closure_Rate linguistic variable
                //initially set to false for both feelers, and will turn true once fired
                boolean really_closeL=false;
                boolean really_closeL2=false;
                boolean closeL=false;
                boolean closeL2=false;
                boolean not_too_closeL=false;
                boolean not_too_closeL2=false;

                boolean lowL=false;
                boolean lowL2=false;
                boolean mediumL=false;
                boolean mediumL2=false;
                boolean highL=false;
                boolean highL2=false;

               
                boolean really_closeR=false;
                boolean really_closeR2=false;
                boolean closeR=false;
                boolean closeR2=false;
                boolean not_too_closeR=false;
                boolean not_too_closeR2=false;

                
                boolean lowR=false;
                boolean lowR2=false;
                boolean mediumR=false;
                boolean mediumR2=false;
                boolean highR=false;
                boolean highR2=false;

                //linguistic variables set to true, to be compared to what sets fired-or turned true
                boolean closeness=true;
                boolean closure_rate=true;

                //gets the difference between tracking and angle to wall
                double diff1= Math.abs(angleDiff(shipHeadInt,feelAngle1));
                double diff2= Math.abs(angleDiff(shipHeadInt,feelAngle2));

                /* 
                   variables for degree of membership values after function, 
                   flat lines on graph are automatically 1, and the rest will be changed from 0
                   by being fed into equations depending on shapes of graphs (see attached)
                */
                 
                //u is for Closeness 
                double u1=1; 
                double u2=0;
                double u3=0; 
                double u4=0; 
                double u5=0; 
                double u6=1; 
                double u7=1; 
                double u8=0;
                double u9=0; 
                double u10=0; 
                double u11=0; 
                double u12=1; 

                //y is for Closure_Rate
                double y1=1; 
                double y2=0;
                double y3=0; 
                double y4=0; 
                double y5=0; 
                double y6=1; 
                double y7=1; 
                double y8=0;
                double y9=0; 
                double y10=0; 
                double y11=0; 
                double y12=1; 

                
                //Deciding which regimes the closeness values fall in
                if(feel1 >0.0 && feel1 <=50)
                {
                    really_closeL=true;
                    u1= 1;
                }
                if(feel1>50 && feel1<=160)
                {
                    really_closeL2=true;
                    u2= ((50-feel1)/(110) +1);
                }
                if(feel1>50 && feel1<=240)
                {
                    closeL=true;
                    u3= ((feel1-50)/(190));
                }
                if(feel1>=240 && feel1<=360)
                {
                    closeL2=true;
                    u4= ((240-feel1)/(120) +1);
                }
                if(feel1>=280 && feel1<=360)
                {
                    not_too_closeL=true;
                    u5= ((feel1-280)/(80));
                }
                if(feel1>=360)
                {
                    not_too_closeL2=true;
                    u6= 1;
                }
                if(feel2 >0.0 && feel2 <=50)
                {
                    really_closeR=true;
                    u7=1;
                }
                if(feel2>50 && feel2<=160)
                {
                    really_closeR2=true;
                    u8= ((50-feel1)/(110) +1);
                }
                if(feel2>50 && feel2<=240)
                {
                    closeR=true;
                    u9=((feel1-50)/(190));
                }
                if(feel2>=240 && feel2<=360)
                {
                    closeR2=true;
                    u10= ((240-feel1)/(120) +1);
                }
                if(feel2>=280 && feel2<=360)
                {
                    not_too_closeR=true;
                    u11= ((feel1-280)/(80));
                }
                if(feel2>=360)
                {
                    not_too_closeR2=true;
                    u12=1;
                }

                //Deciding which regimes the closure_rate values fall in
                if(diff1<=15 && zSpeed>0.0 && zSpeed<=2.5)
                {
                    lowL=true;
                    y1=1;
                }
                if(diff1<=15 && zSpeed>=2.5 && zSpeed<=3.5)
                {
                    lowL2=true;
                    y2= (3.5-zSpeed);
                }
                if(diff1<=15 && zSpeed>=2.5 && zSpeed<=4.0)
                {
                    mediumL=true;
                    y3= ((zSpeed-2.5)/(1.5));
                }
                if(diff1<=15 && zSpeed>=4.0 && zSpeed<=5.0)
                {
                    mediumL2=true;
                    y4= (5-zSpeed);
                }
                if(diff1<=15 && zSpeed>=4.0 && zSpeed<=6.0)
                {
                    highL=true;
                    y5= ((zSpeed-4)/(2));
                }
                if(diff1<=15 && zSpeed>=6.0)
                {
                    highL2=true;
                    y6=1;
                }
                if(diff2<=15 && zSpeed>0.0 && zSpeed<=2.5)
                {
                    lowR=true;
                    y7=1;
                }
                if(diff2<=15 && zSpeed>=2.5 && zSpeed<=3.5)
                {
                    lowR2=true;
                    y8=(3.5-zSpeed);
                }
                if(diff2<=15 && zSpeed>=2.5 && zSpeed<=4.0)
                {
                    mediumR=true;
                    y9=((zSpeed-2.5)/(1.5));
                }
                if(diff2<=15 && zSpeed>=4.0 && zSpeed<=5.0)
                {
                    mediumR2=true;
                    y10= (5-zSpeed);
                }
                if(diff2<=15 && zSpeed>=4.0 && zSpeed<=6.0)
                {
                    highR=true;
                    y11=((zSpeed-4)/(2));
                }
                if(diff2<=15 && zSpeed>=6.0)
                {
                    highR2=true;
                    y12=1;
                }

                //these will be added to and divided in order to determine final defuzzified average
                double numerator=0; 
                double denominator=0;

                /*-------------------------------------------------------------
                            Start of Actual Rules for Fuzzy System
                -------------------------------------------------------------*/

                //aggregation happens when each rule is fired, numerator and denominator values increase
                // "k" is the output singleton, and t is the mu value depending on operators- AND, OR
                if(closeness==really_closeL  && closure_rate==highL2)
                {
                    double k= -80;
                    double t= funky("and",u1,y6);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==really_closeL2 && closure_rate==highL)
                {
                    double k=-80;
                    double t= funky("and",u2,y5);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==really_closeL || closeness== really_closeL2)
                {
                    double k=-80;
                    double t=1;
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closure_rate==highL || closure_rate== highL2)
                {
                    double k=-80;
                    double t=1;
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==closeL && closure_rate==highL)
                {
                    double k=-70;
                    double t=funky("and",u3,y5);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==not_too_closeL2 && closure_rate==lowL)
                {
                    double k=-20;
                    double t=funky("and",u6,y1);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==not_too_closeL2 && closure_rate==highL2)
                {
                    double k=-40;
                    double t= funky("and",u6,y6);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==really_closeR  && closure_rate==highR2)
                {
                    double k= 80;
                    double t= funky("and",u7,y12);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==really_closeR2 && closure_rate==highR)
                {
                    double k=80;
                    double t= funky("and",u8,y11);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==really_closeR || closeness== really_closeR2)
                {
                    double k=80;
                    double t=1;
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closure_rate==highR || closure_rate== highR2)
                {
                    double k=80;
                    double t=1;
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==closeR && closure_rate==highR)
                {
                    double k=70;
                    double t=funky("and",u9,y11);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==not_too_closeR2 && closure_rate==lowR)
                {
                    double k=20;
                    double t=funky("and",u10,y7);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==not_too_closeR2 && closure_rate==highR2)
                {
                    double k=40;
                    double t= funky("and",u10,y12);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==really_closeL && closeness==really_closeR)
                {
                    double k;
                    if(u1<u7)
                    {
                        k= -80;
                    }
                    else 
                    {
                        k=80;
                    }
                    double t= funky("and",u1,u7);
                    numerator+=(k*t);
                    denominator+=t;
                }
                if(closeness==closeL && closeness==closeR)
                {
                    double k;
                    if(u1<u7)
                    {
                        k= -80;
                    }
                    else 
                    {
                        k=80;
                    }
                    double t= funky("and",u4,u10);
                    numerator+=(k*t);
                    denominator+=t; 
                }

                
                double average1= (numerator/denominator);//defuzzified crisp value
                int average= (int) average1;
                turnToDeg(shipHeadInt+average);//Decision after defuzzification
                /*-------------------------------------------------------------
                                    Start of Rule Based System
                -------------------------------------------------------------*/
                //if enemy is approaching from left and head is in general direction
                thrust(0);
                turnLeft(0);
                turnRight(0);

                //turnToDeg is already used in fuzzy system, however overwriting may 
                //not be a problem here because the fuzzy will make a decision based 
                //on the rules above (which already have more priority)
                if(enemX<posX && wall<0)
                {
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else if(enemX>posX && wall<0)
                {
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else if(enemY>posY && wall<0)
                {
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else if(enemY<posY && wall<0)
                {
                    thrust(1);
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else if(wallBack<dist2-100)
                {
                        setPower(15);
                        thrust(1);
                }
                else if(wallForward<dist2+75 && wallPerif1<dist2+50)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wallForward<dist2+75 &&wallPerif2<dist2+50)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else if(wallForward<dist2+75 && wall90<dist2)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wallForward<dist2+75 && wall270<dist2)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else if(wallBack<dist2 && wall90<dist2)
                {
                        turnRight(1);
                        thrust(1);    
                }
                else if(wallBack<dist2 && wall270<dist2)
                {
                        turnLeft(1);
                        thrust(1);
                }

                else if(wallBlind1< dist2)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wallBlind2<dist2)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else if(wall90<dist2)
                {
                        turnRight(1);
                        thrust(1);
                }
                else if(wall270<dist2)
                {
                        turnLeft(1);
                        thrust(1);
                }
                else if(zSpeed>4)
                {
                    thrust(0);
                }
                else 
                {
                    turnToDeg(turnDeg);
                    thrust(1);
                    fireShot();
                }
        }
        public MKhanFuzz(String args[]) 
        {

                super(args);
        }
        public static void main(String args[])
         {
                String[] new_args = {"-name","TheBoss"};
                MKhanFuzz spinner = new MKhanFuzz(new_args);
        }
}
