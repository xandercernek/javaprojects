//cerne010 and shahx252
public class Shipment {
    //shipments stats
    private double weight; 
    private double creationTime;
    private int destinationID;
    private double value;

    public double getWeight(){
        return weight;
    }

    public double getcreationTime(){return creationTime;}

    public int getID(){
        return destinationID;
    }

    public Shipment(){
        weight = Math.random() * 999.0; //random weight 1-1000 kg
        value = weight * 10.0;
        creationTime = Main.agenda.getCurrentTime();
        destinationID = (int)Math.floor(Math.random() * 9.0) + 1; //random int
        //0-9 which determines which port it should be put at
    }
}
