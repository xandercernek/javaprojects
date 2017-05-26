//cerne010 and shahx252
public class VesselCreator{ //class that generates vessels

    private int vesselType; //0 is canoe, 1 is yacht, etc
    private double capacity;
    private double wait;
    public int length;

    public VesselCreator(int vesselType, double capacity, double wait){
        this.vesselType = vesselType;
        this.capacity = capacity;
        this.wait = wait;
    }

    public Vessel[] createVessels(){
        Vessel[] returnArr;
        String id_string;
        switch (vesselType){
            case 0:
                returnArr = new Vessel[100];
                for(int i=0; i<100; i++){
                    id_string = "Canoe";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 100;
                break;
            case 1:
                returnArr = new Vessel[10];
                for(int i=0; i<10; i++){
                    id_string = "Yacht";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 10;
                break;
            case 2:
                returnArr = new Vessel[20];
                for(int i=0; i<20; i++){
                    id_string = "Galleon";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 20;
                break;
            case 3:
                returnArr = new Vessel[15];
                for(int i=0; i<15; i++){
                    id_string = "Barge";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 15;
                break;
            case 4:
                returnArr = new Vessel[10];
                for(int i=0; i<10; i++){
                    id_string = "Freighter";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 10;
                break;
            case 5:
                returnArr = new Vessel[5];
                for(int i=0; i<5; i++){
                    id_string = "Airplane";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 5;
                break;
            case 6:
                returnArr = new Vessel[30];
                for(int i=0; i<30; i++){
                    id_string = "CarrierPigeonTeam";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 30;
                break;
            case 7:
                returnArr = new Vessel[10];
                for(int i=0; i<10; i++){
                    id_string = "Rocket";
                    returnArr[i] = new Vessel(id_string, capacity, wait);
                }
                length = 10;
                break;
            default:
                returnArr = null;
        }
        return returnArr;
    }


}