//cerne010 and shahx252
import java.util.Scanner;
public class Main{
    public static double day = 161280; //minutes the simulation runs for
    public static int numdays = (int)day/1440; //days in the simulation
    public static PQ agenda = new PQ(); //main priority queue
    public static Port[] portArray = new Port[10]; //holds the 10 ports for easy access
    public static Vessel[] allShips; //holds the ships for easy access
    private static double money, totalcost, waittime, totalweight; //for stat purposes
    private static int vesselcount, shipcount, maxline, totalline; //for stat purposes
    //getters and setters---------------------
    public static void setMaxline(int m){
        maxline = m;
    }
    public static int getMaxline(){
        return maxline;
    }
    public static void setTotalline(int t){
        totalline += t;
    }
    public static void setMoney(double amount){
        money += amount;
    }
    public static void setTotalCost(double amount){
        totalcost += amount;
    }
    public static void setWaitTime(double amount){
        waittime += amount;
    }
    public static void setTotalWeight(double amount){
        totalweight += amount;
    }
    public static void setVesselCount(int amount){
        vesselcount += amount;
    }
    public static void setShipCount(int amount){
        shipcount += amount;
    }
    public static Port getPort(int portIndex){
        return portArray[portIndex];
    }
    //end getters and setters-----------------------
    public static void main(String[] args){ //instantiating each port and its coordinates
        int[] location = new int[2];
        location[0] = 0;
        location[1] = 1;
        portArray[0] = new Port("Minneapolis", location);
        location = new int[2];
        location[0] = 0;
        location[1] = 10;
        portArray[1] = new Port("Saint Paul", location);
        location = new int[2];
        location[0] = 0;
        location[1] = -6000;
        portArray[2] = new Port("Antartica", location);
        location = new int[2];
        location[0] = 4000;
        location[1] = 4000;
        portArray[3] = new Port("Japan", location);
        location = new int[2];
        location[0] = 6000;
        location[1] = 5000;
        portArray[4] = new Port("Korea", location);
        location = new int[2];
        location[0] = 5000;
        location[1] = 6000;
        portArray[5] = new Port("China", location);
        location = new int[2];
        location[0] = 0;
        location[1] = 1000000;
        portArray[6] = new Port("Moon", location);
        location = new int[2];
        location[0] = 1000;
        location[1] = 3000;
        portArray[7] = new Port("Panama", location);
        location = new int[2];
        location[0] = 2000;
        location[1] = 2000;
        portArray[8] = new Port("Hawaii", location);
        location = new int[2];
        location[0] = 3000;
        location[1] = 3000;
        portArray[9] = new Port("Pirate Town", location); //vvvv User input below vvvv
        System.out.println("Enter the boat type, percent boat must be filled before leaving, and time boat will wait before leaving: ");
        System.out.println("Boat types: \n0: Canoe\n1: Yacht\n2: Galleon\n3: Barge\n4:Freighter\n5: Airplane\n6: Carrier Pigeon\n7: Rocket");
        Scanner scanner1 = new Scanner(System.in);
        int input1 = scanner1.nextInt();
        double input2 = scanner1.nextDouble();
        double input3 = scanner1.nextDouble();
        scanner1.close();
        VesselCreator shipyard = new VesselCreator(input1, input2, input3); //VesselCreator creates vessels
        allShips = shipyard.createVessels(); //generates the correct number of vessels
        for(int i=0; i<10; i++){
            agenda.add(new ShipmentMaker(i), 0); //creates 10 shipment makers; one for each port
        }
        int portcounter = 0;
        for(int i=0; i<shipyard.length; i++){ //Sets vessels at ports. It will evenly spread out vessels initially
            agenda.add(new VesselEvent(allShips[i], portArray[portcounter]), 0);
            portcounter++;
            if(portcounter == 10){
                portcounter = 0;
            }
        }
        while(agenda.getCurrentTime() <= day){
            try{
                agenda.remove().run();
            }
            catch (NullPointerException e){ //if nothing is in the queue, end
                break;
            }
        }
        //STATISTICS
        double profit = money - totalcost; //Total Profit
        double avgpps = profit/shipcount; //Average Profit Per Shipment
        double avgppd = profit/numdays;//Average Profit Per Day
        double weightfilled = totalweight/vesselcount; //Average weight filled per Route
        double avgwtps = waittime/shipcount; //Average Wait Time Per Shipment
        double avgline = totalline/shipcount; //Average Line Length per Shipment
        System.out.println("STATISTICS");
        System.out.println("Number of Days: " + numdays);
        System.out.println("Number of Shipments: "+ shipcount);
        System.out.println("Total Weight: "+ totalweight);
        System.out.println("Vessel Count: " + vesselcount);
        System.out.println("Total Profit: $"+ profit);
        System.out.println("Average Profit per Shipment: $" + avgpps);
        System.out.println("Average Profit per Day: $" + avgppd);
        System.out.println("Average Weight of shipments in Vessel per Route: "+weightfilled);
        System.out.println("Average Wait Time per Shipment: "+avgwtps);
        System.out.println("Maximum Line Length: " + maxline);
        System.out.println("Average Line Length: " + avgline/10);
    }
}