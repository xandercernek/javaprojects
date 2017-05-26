//cerne010 and shahx252
import java.util.Random;

public class VesselEvent implements Event{

    private Port currentPort; //where vessel is 
    private Port nextPort; //where it is going to next
    private Vessel vessel; //what vessel it is
    private double currentTime;
    private boolean ready; //ready to leave when told or no
    private int destID; //where it is going to
    private double money; //how much it made

    public VesselEvent(Vessel vessel, Port port){
        this.vessel = vessel;
        this.currentPort = port;
        vessel.setCurrentPort(port.getPortID());
        this.currentTime = Main.agenda.getCurrentTime();
        vessel.idle = false;
        this.ready = false;
    }

    public double calculateDistance(int currentID, int destID){ //distance to 
        double x1,x2,y1,y2;                                     //next port
        switch (currentID){
            case 0:
                x1 = 0;
                y1 = 0;
                break;
            case 1:
                x1 = 0;
                y1 = 10;
                break;
            case 2:
                x1 = 0;
                y1 = -6000;
                break;
            case 3:
                x1 = 4000;
                y1 = 4000;
                break;
            case 4:
                x1 = 6000;
                y1 = 5000;
                break;
            case 5:
                x1 = 5000;
                y1 = 6000;
                break;
            case 6:
                x1 = 0;
                y1 = 1000000;
                break;
            case 7:
                x1 = 1000;
                y1 = 3000;
                break;
            case 8:
                x1 = 2000;
                y1 = 2000;
                break;
            case 9:
                x1 = 3000;
                y1 = 3000;
                break;
            default:
                x1 = -1;
                y1 = -1;
                break;
        }

        switch (destID){
            case 0:
                x2 = 0;
                y2 = 0;
                break;
            case 1:
                x2 = 0;
                y2 = 10;
                break;
            case 2:
                x2 = 0;
                y2 = -6000;
                break;
            case 3:
                x2 = 4000;
                y2 = 4000;
                break;
            case 4:
                x2 = 6000;
                y2 = 5000;
                break;
            case 5:
                x2 = 5000;
                y2 = 6000;
                break;
            case 6:
                x2 = 0;
                y2 = 1000000;
                break;
            case 7:
                x2 = 1000;
                y2 = 3000;
                break;
            case 8:
                x2 = 2000;
                y2 = 2000;
                break;
            case 9:
                x2 = 3000;
                y2 = 3000;
                break;
            default:
                x2 = -1;
                y2 = -1;
                break;
        }
        double distance = Math.pow(((Math.pow((x2-x1),2))+(Math.pow((y2-y1),2))),0.5);
        return distance;
    }

    public double removeCargo(){ //removes cargo and returns the total money made
        int portID = currentPort.getPortID();
        double returnMoney = 0;
        System.out.println("CARGO SIZE: " + vessel.getCargoSize());
        for(int i=0; i<vessel.getCargoSize(); i++){
            System.out.println(i);
            if(vessel.peek(i).getID() == portID){
                Main.setShipCount(1);
                Shipment removed = vessel.remove(i);
                vessel.setCurrentMass(removed.getWeight() * -1);
                returnMoney += 10 * removed.getWeight();
            }
        }
        return returnMoney;
    }



    public void run(){
        while(destID == currentPort.getPortID()){
           destID = (int)(Math.random()* 9+0);
        }
        System.out.println("Creating Vessel Event at Port " + currentPort.toString());
        money = removeCargo();
        if(currentPort.getPortID() != 9){
            Main.setMoney(money);
        }
        else{ //PIRATE TOWN xxxxxxxxxxxxxxxx GET OUT, LANDLUBBERS
            Random random10 = new Random();
            double perc10 = random10.nextInt(10) + 1;
            if(perc10 < 2){
                System.out.println("Cargo was stolen at Pirate Town");
            }
            else{
                Main.setMoney(money);
            }
        }
        System.out.println("Removed $" + money + " worth of cargo from " + currentPort.toString());
        if(currentPort.getQ2().length() == 0){ //if the port has no shipments
            if(vessel.getTimeSpentWaiting() < vessel.getWait() || vessel.readyToGo() == false){ //and the time that the vessel will wait hasnt run up OR percent the vessel wants has hasnt fileld up
                vessel.idle = true; //make the vessel idle at the port
            }
            else{ //otherwise, if there is another shipment waiting in the vessel, go to that port
                if(vessel.getCargoSize() != 0){
                    destID = vessel.peek(0).getID();
                }
                else{
                    vessel.idle = true;
                }
            }
        }
        else {
            if(vessel.currentLoadID != currentPort.getPortID()){
                destID = vessel.currentLoadID; //if woken from idle,
            }else{                              //then keep the same load ID
                Shipment peekThis = (Shipment) currentPort.getQ2().peek();
                destID = peekThis.getID();//otherwise make a new ID
                vessel.currentLoadID = destID;
            }
            System.out.println("Destination ID: " + destID);
            int size = currentPort.getQ2().length();
            for(int i=0; i<size; i++){
                Shipment removed = (Shipment) currentPort.getQ2().remove();
                if(removed.getID() == destID){
                    if(vessel.isFullWithAdd(removed.getWeight())){
                        System.out.println("Vessel is full");
                        System.out.println(removed.getWeight());
                        System.out.println(vessel.getCargoSize());
                        Main.setWaitTime((Main.agenda.getCurrentTime() - removed.getcreationTime()));
                        System.out.println(vessel.getCurrentMass());
                        ready = true;
                    }
                    else{
                        System.out.println("Adding shipment to vessel");
                        vessel.setCurrentMass(removed.getWeight());
                        vessel.add(removed);
                        ready = true;
                    }
                }
                else{ //put back in port queue if going to wrong location
                    currentPort.getQ2().add(removed);
                }
            }
        }
        if(ready == true){ //if ready to go, send it
            double time = calculateDistance(currentPort.getPortID(), destID);
            Main.setTotalCost(time * (double) vessel.getCost());
            Main.setVesselCount(1);
            Main.setTotalWeight(vessel.getCurrentMass());
            time = time/vessel.getSpeed()*60;
            Port nextPort = Main.getPort(destID);
            if(Main.agenda.getCurrentTime() + time <= Main.day && vessel.idle == false){
                System.out.println("Ship moving from " + currentPort.toString() + " to " + nextPort.toString());
                Main.agenda.add(new VesselEvent(vessel, nextPort),  time+1);
            }
        }
        else{
            vessel.idle = true;
        }
    }
}