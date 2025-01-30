package MLP;

import ValueClass.Value;
import java.util.ArrayList;
import java.util.Random;

class Neuron{
    public ArrayList<Value> w = new ArrayList<>(); 
    Value b;
    public Neuron(int nin){
        Random random = new Random();
        this.b = new Value((random.nextDouble() * 2) - 1);
        for(int i=0; i<nin; i++){
            this.w.add(new Value((random.nextDouble() * 2) - 1));
        }
    }

    public Value call(ArrayList<Value> x){
        Value act = new Value(0);
        for (int i=0; i<x.size(); i++){
            act = act.add(this.w.get(i).mul(x.get(i)));
        }
        Value out = act.tanh();
        return out;
    }

    public ArrayList<Value> parameters(){
        ArrayList<Value> p = new ArrayList<>();
        p.addAll(this.w);
        p.add(b);
        return p;
    }
}

class Layer{
    public ArrayList<Neuron> neurons = new ArrayList<>();
    
    Layer(int nin, int nout){
        for(int i=0; i<nout; i++){
            this.neurons.add(new Neuron(nin));
        }
    }

    ArrayList<Value> call(ArrayList<Value> x){
        ArrayList<Value> out = new ArrayList<>();
        for (int i=0; i<this.neurons.size(); i++){
            out.add(this.neurons.get(i).call(x));
        }
        return out;
    }

    public ArrayList<Value> parameters(){
        ArrayList<Value> q = new ArrayList<>();
        for (int i=0; i<this.neurons.size(); i++){
            q.addAll(neurons.get(i).parameters());
        }
        return q;
    }

}

public class Mlp{ 
    public ArrayList<Layer> layers = new ArrayList<>();
    
    public Mlp(int nin, int[] nouts){
        this.layers.add(new Layer(nin, nouts[0]));
        for (int i=0; i<nouts.length-1; i++){
            layers.add(new Layer(nouts[i], nouts[i+1]));
        }
    }

    ArrayList<Value> call(ArrayList<Value> x){
        for (int i=0; i<this.layers.size(); i++){
            x = this.layers.get(i).call(x);
        }
        return x;
    }

    public ArrayList<Value> parameters(){
        ArrayList<Value> q = new ArrayList<>();
        for (int i=0; i<this.layers.size(); i++){
            q.addAll(layers.get(i).parameters());
        }
        return q;
    }
}