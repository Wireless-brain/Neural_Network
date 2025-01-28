//package NN;

import java.util.ArrayList;
import java.util.Random;

//import NN.TestValue;

public class Neuron{
    ArrayList<TestValue> w = new ArrayList<>(); 
    TestValue b;
    public Neuron(int nin){
        Random random = new Random();
        // double randomValue = (random.nextDouble() * 2) - 1;
        b = new TestValue((random.nextDouble() * 2) - 1);
        for(int i=0; i<nin; i++){
            //Value temp = new Value((random.nextDouble() * 2) - 1);
            w.add(new TestValue((random.nextDouble() * 2) - 1));
        }
    }

    ArrayList<TestValue> makeVal(double[] x){
        
        ArrayList<TestValue> dub = new ArrayList<>();
        for(int i = 0; i<x.length; i++){
            dub.add(new TestValue(x[i]));
        }
        return dub;
    }

    public TestValue call(double[] x1){

        ArrayList<TestValue> x = makeVal(x1);
        //ListIterator<Value> itrW = this.w.listIterator();
        //ListIterator<Value> itrX = x.listIterator();
        TestValue act = new TestValue(0);
        // while (itrW.hasNext() && itrX.hasNext()){
        //     sum += itrW.next().data + itrX.next().data;
        // }
        for (int i=0; i<x.size(); i++){
            act.data += this.w.get(i).data + x.get(i).data;
        }
        TestValue out = act.tanh();
        return out;
    }
}