package frc.utils;

import java.util.LinkedList;
import java.util.Queue;

public class BoundingBox {
    private int size;
    Queue<Double> queue = new LinkedList<>();

    public BoundingBox(int size) {
        this.size = size;
    }

    public void addElement(double number) {
        queue.add(number);
        if (queue.size() > size) {
            queue.remove();
        }
    }

    public double getAverage() {
        double sum = 0;
        for(Double num: queue){
            sum+=num;
        }
        return sum/queue.size();
    }
}