//cerne010 and shahx252
public class Vessel {

    private double capacity; //how much it can hold
    private double capacPercent; //how much it will hold before leaving
    private double wait; //how much it will wait before leaving
    private double timeSpentWaiting; //how much time it has currently spend waiting
    private double currentMass; //current mass of shipments on vessel
    private int speed;
    private int cost; //cost/km
    private int currentPort; //current port it is at
    private Shipment[] cargo = new Shipment[20]; //initial cargo hold size
    private int cargoSize; //pointer method
    public boolean idle; //idle y/n
    public int currentLoadID; //to what port the vessel is currently loading
    //start getters and setters
    public int getCost(){
        return cost;
    }

    public double getTimeSpentWaiting(){
        return timeSpentWaiting;
    }

    public void setTimeSpentWaiting(double time){
        this.timeSpentWaiting += time;
    }

    public int getCargoSize(){
        return cargoSize;
    }

    public int getSpeed(){
        return speed;
    }

    public double getWait(){
        return wait;
    }

    public void setCurrentMass(double add){
        currentMass += add;
    }

    public double getCurrentMass(){
        return currentMass;
    }

    public double getCapacity(){
        return capacity;
    }

    public Shipment[] getCargo(){
        return cargo;
    }

    public void setCargo(Shipment[] cargo){
        this.cargo = cargo;
    }

    public void setCurrentPort(int port){
        currentPort = port;
    }

    public int getCurrentPort(){
        return currentPort;
    }

    //end getters and setters
    public boolean isFullWithAdd(double mass){ //checks if the vessel will be full
        if((currentMass + mass / capacity) * 100 >= capacPercent){ // if you add the proposed mass
            return true;
        }
        else{
            return false;
        }
    }

    public boolean readyToGo(){ // a method to determine whether the vessel is full enough to leave the port
        double calc = currentMass / capacity;
        if(calc < capacPercent){
            return false;
        }
        else{
            return true;
        }
    }

    public void add(Shipment ship1){ //add shipment to cargo
        if(cargoSize == cargo.length){ //double hold size if too big
            Shipment[] copyCargo = new Shipment[2 * cargo.length + 1];
            System.arraycopy(cargo, 0, copyCargo, 0, cargoSize);
        }
        cargo[cargoSize] = ship1;
        cargoSize++;
    }

    public Shipment remove(int index){ //remove shipment from cargo and return it
        if(index < -1 || index >= cargo.length){
            System.out.println("ERROR: removing index out of bounds");
            return null;
        }
        else{
            Shipment removed = cargo[index];
            cargo[index] = null;
            for(int i=0; i<cargo.length-1; i++){ //shifts over to get rid of 
                cargo[index] = cargo[index+1]; //null in the middle of the cargo
            }
            cargoSize--;
            return removed;
        }
    }

    public Shipment peek(int index){ //looks at the cargo at the index
        return cargo[index];
    }

    public Vessel(String vesselName, double capacPercent, double wait){
        this.capacPercent = capacPercent;//set name, capac%, wait%
        this.wait = wait;
        this.cargoSize = 0;
        switch (vesselName) {
            case "Canoe":
                capacity = 1000.0;
                speed = 10;
                cost = 1;
                break;
            case "Yacht":
                capacity = 2000.0;
                speed = 60;
                cost = 2000;
                break;
            case "Galleon":
                capacity = 15000;
                speed = 15;
                cost = 100;
                break;
            case "Barge":
                capacity = 1000000;
                speed = 10;
                cost = 1000;
                break;
            case "Freighter":
                capacity = 2000000;
                speed = 5;
                cost = 1000;
                break;
            case "Airplane":
                capacity = 50000;
                speed = 850;
                cost = 10000;
                break;
            case "CarrierPigeonTeam":
                capacity = 1000;
                speed = 10;
                cost = 0;
                break;
            case "Rocket":
                capacity = 10000;
                speed = 2000;
                cost = 100000;
                break;
            default:
                capacity = -1;
                speed = -1;
                cost = -1;
        }

    }

}
