/*--------------------------------------------------------------------------
					Mohammad Khan 
				NN Controller, Fall 2014
Utilizes pre-learned weights from outside the XPilot program by entering 
them into Nodes of the Network. No classes used here, so strictly propagates
values forward through arrays and calculations with indices. Each frame 
calculates a decision based on predetermined weights from Neural Network
outside the XPilot bot. 
---------------------------------------------------------------------------*/
class MKhanNN extends javaAI
 {

        //squashes the distance between 0 and 1, making it so that the bigger
        //the value the closer it will be to zero and the smaller to 1
        //these values are then the input for the nodes (into the arrays below)
        public double zeroOne(double x)
        {
            double j;
            j= (550-x)/(550);
            return j;
        }

        //summation to be inputted into sigmoid
        public double summation(double[] array1, double[] array2)
        {
            double k=0;
            for(int i=0; i<=4;i++)
            {
                k+= array1[i]*array2[i];
            }
            return k;
        }

        //summation for final output layer
        public double summation2(double[] array1, double[] array2)
        {
            double k=0;
            for(int i=0; i<=3;i++)
            {
                k+= array1[i]*array2[i];
            }
            return k;
        }

        //sigmoid to output (0,1)
        public double calcOutput(double x)
        {
            return 1 / (1 + Math.exp(-x));
        }

        @Override
        public void AI_loop() 
        {

                setTurnSpeed(20); 
                setPower(5.0);

                turnLeft(0);
                turnRight(0);
                thrust(0);
                
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

                double turnCalc= Math.toDegrees(Math.atan((closestRadarY()-selfRadarY())/(closestRadarX()-selfRadarX())));
                int turnDeg= (int)turnCalc;

                double enemTrig= Math.sqrt( (Math.abs(closestRadarY()-selfRadarY()))^2 + (Math.abs(closestRadarX()-selfRadarX()))^2);

                int dist2=150; //distance variable for feeler
                int wall90= wallFeeler(dist2,shipHeadInt + 90); //side of ship
                int wall270= wallFeeler(dist2,shipHeadInt +270); //side of ship
                int wallPerif1= wallFeeler(dist2+50, shipHeadInt+30); //30 degrees away from front
                int wallPerif2= wallFeeler(dist2+50, shipHeadInt-30); //30 degrees away from front 
                int wallBlind1= wallFeeler(dist2+50, shipHeadInt+150); //30 degrees away from back
                int wallBlind2= wallFeeler(dist2+50, shipHeadInt+210); //30 degrees away from back 

                int dist=530; 
                int feelAngle1= shipHeadInt +45; //Left of heading , and -45 is Right of heading
                int feelAngle2= shipHeadInt -45;
                double feel1=  wallFeeler(dist,feelAngle1); // This will be used to compute input 1
                double feel2=  wallFeeler(dist,feelAngle2); // This will be used to compute input 3
                double wallForward= wallFeeler(dist,shipHeadInt); //This will be used to compute input 2
                double wallBack= wallFeeler(dist, shipHeadInt+180); //This will be used to compute input 4

                /*------------------------------------------
                            Start of Neural Net
                ------------------------------------------*/
                double in1= zeroOne(feel1);
                double in2= zeroOne(wallForward);
                double in3= zeroOne(feel2);
                double in4= zeroOne(wallBack);

                double input1[]= new double[5];//hid1
                double input2[]= new double[5];//hid2
                double input3[]= new double[5];//hid3
                double input4[]= new double[4];//out

                input1[0]=-1.0;
                input2[0]=-1.0;
                input3[0]=-1.0;
                input4[0]=-1.0;

                input1[1]=in1;
                input2[1]=in1;
                input3[1]=in1;

                input1[2]=in2;
                input2[2]=in2;
                input3[2]=in2;

                input1[3]=in3;
                input2[3]=in3;
                input3[3]=in3;

                input1[4]=in4;
                input2[4]=in4;
                input3[4]=in4;

                double weight1[]= new double[5];//hid1
                double weight2[]= new double[5];//hid2
                double weight3[]= new double[5];//hid3
                double weight4[]= new double[4];//out

                //Weights set to those that have been learned off line of Xpilot
                weight1[0]= 1.6397947619662698;//threshold
                weight1[1]= 7.601543621181002;//input1
                weight1[2]= 0.701419447415988;//input2
                weight1[3]= -4.895225953229029;//input3
                weight1[4]= -3.48935092850464;//input4

                weight2[0]= -0.22081216091034042;
                weight2[1]= 2.5772448022514474;
                weight2[2]= 0.40956056292946247;
                weight2[3]= -5.625630591900949;
                weight2[4]= 7.515800416560737;

                weight3[0]= 0.34781338345502477;
                weight3[1]= 0.9265711328025786;
                weight3[2]= 0.5907256116090451;
                weight3[3]= 0.8517348946528827;
                weight3[4]= 0.8305281521188845;

                weight4[0]= 5.16367367723663;//threshold
                weight4[1]= 9.306183490766625;//output of hid1
                weight4[2]= 6.895728787222278;//output of hid2
                weight4[3]= -2.6660724040437613;//output of hid3

                double sigmoid1= summation(input1,weight1);
                double sigmoid2= summation(input2,weight2);
                double sigmoid3= summation(input3,weight3);

                //squashing function to output value between 0 and 1
                double out1 = calcOutput(sigmoid1);
                double out2 = calcOutput(sigmoid2);
                double out3 = calcOutput(sigmoid3);

                //inputting outputs of hidden nodes into output node
                input4[1]= out1;
                input4[2]= out2;
                input4[3]= out3;

                //summation and squashing for final output
                double sigmoid4= summation2(input4,weight4);
                double out4= calcOutput(sigmoid4);

                System.out.println(" In 1:" + in1 + ", In 2: "+ in2 + ", In 3: "+ in3 + ", In 4: " + in4);
                System.out.println("Final Output:");
                System.out.println("Output:   "+ out4);

                //if final output is near 0
                if(out4>0.0 && out4<.45)
                {
                    turnLeft(1);
                    thrust(1);
                }

                //if final output is near .5
                if(out4>=.45 && out4 <= .55)
                {
                    turnLeft(1);
                    turnRight(1);
                    thrust(1);
                }

                //if final output is near 1
                if(out4>.55 && out4<1.0)
                {
                    turnRight(1);
                    thrust(1);
                }

                // Not part of NN output, but wanted to keep 
                // ship from crashing with back to wall
                if(wallBack<150)
                {
                    setPower(12);
                    thrust(1);
                }

                /*------------------------------------------
                          End of Neural Net--(Output)
                          Start of Rule-Based System
                            (to control shooting)
                ------------------------------------------*/               
                if(enemTrig>250)
                {
                    turnToDeg(turnDeg);
                    thrust(1);
                    fireShot();
                }
                else if(enemTrig<=250)
                {
                    turnToDeg(turnDeg);
                    fireShot();
                }
                else 
                {
                    turnToDeg(turnDeg);
                    thrust(1);
                    fireShot();
                }
        }
        public MKhanNN(String args[]) 
        {
                super(args);
        }
        public static void main(String args[])
        {
                String[] new_args = {"-name","NeuralMind"};
                MKhanNN spinner = new MKhanNN(new_args);
        }
}
