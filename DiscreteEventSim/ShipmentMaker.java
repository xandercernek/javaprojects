//cerne010 and shahx252
//Class generates shipments
import java.util.Arrays;
public class ShipmentMaker implements Event{
    private int portIndex; //ID of port
    private static int shipCount; //Number of shipments
    public ShipmentMaker(int portIndex){
        this.portIndex = portIndex;
        this.shipCount = 0;
    }
    public static int getShipCount(){
        return shipCount;
    }
    public void run(){
        if(portIndex == 6){ //0.1 percent chance to send to moon
            int rand = (int) (Math.random() * 9 + 0);
            if(rand != 6){
                portIndex = rand;
            }
        }
        shipCount ++;
        double time = Main.agenda.getCurrentTime();
        double check = (Math.random() * 100 + 1);
        int shipPerDay;
        switch (portIndex){ //how many shipments should be produced per day
            case 0:
                shipPerDay = 50;
                break;
            case 1:
                shipPerDay = 50;
                break;
            case 2:
                shipPerDay = 10;
                break;
            case 3:
                shipPerDay = 100;
                break;
            case 4:
                shipPerDay = 50;
                break;
            case 5:
                shipPerDay = 100;
                break;
            case 6:
                shipPerDay = 0;
                break;
            case 7:
                shipPerDay = 50;
                break;
            case 8:
                shipPerDay = 50;
                break;
            case 9:
                shipPerDay = 100;
                break;
            default:
                shipPerDay = -1;
                break;
        }
        double mu = 1440.0/shipPerDay;
        try { // random distribution model{
            mu = 1440/shipPerDay;
        }catch(java.lang.ArithmeticException e){
            mu = 1;
        }
        if(time == 0 && mu <= 2) {
            time = 1;
        }else{
            if(check<11){
                time = time + (mu/4);
            }else if(check>=11 || check <26){
                time = time + (mu/2);
            }else if(check>=26 || check<46){
                time = time + (4*mu/5);
            }else if(check>=46 || check<56){
                time = time + (mu);
            }else if(check>=56 || check<76){
                time = time + (6*mu/5);
            }else if(check>=76 || check<91){
                time = time + (3*mu/2);
            }else if(check>=91 || check<101){
                time = time + (7*mu/4);
            } //}
        }
        System.out.println("Adding a shipment at time " + time + " at port " + portIndex);
        for(int i=0; i<Main.allShips.length; i++){
            if(Main.allShips[i].idle == true && Main.allShips[i].getCurrentPort() == portIndex){ //check if ship is waiting to leave
                Main.agenda.add(new VesselEvent(Main.allShips[i], Main.portArray[portIndex]), Main.agenda.getCurrentTime() + 1); //wake it up
            }
        }
        if(Main.agenda.getCurrentTime() + time <= Main.day){
            Main.agenda.add(new ShipmentMaker(portIndex),  time); //make new event
        }
        Main.portArray[portIndex].getQ2().add(new Shipment()); //add shipment to port
        Main.setTotalline( Main.portArray[portIndex].getQ2().length());
        if(Main.getMaxline() <  Main.portArray[portIndex].getQ2().length()){
            Main.setMaxline( Main.portArray[portIndex].getQ2().length());
        }
    }
}