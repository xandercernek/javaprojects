//cerne010 and shahx252
// Example 27a from class
// Priority Queue and Simulation
// Segment class used in priority queue (PQ.java)
// Uses queue class Q1.java

public class Segment {

    private double time;
    private Q2 q;
    private Segment next;

    // constructor

    public Segment(double t) {
        time = t;
        q = new Q2();
        next = null;
    }

    // methods

    public double getTime() {
        return time;
    }

    public Q2 getEvents() {
        return q;
    }

    public Segment getNext() {
        return next;
    }

    public void setNext(Segment nextSegment) {
        next = nextSegment;
    }

}  // Segment class