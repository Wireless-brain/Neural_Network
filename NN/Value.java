package NN;

import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;

public class Value{
    double data;
    public double grad = 0;
    public String label = "";
    public String op = "";
    public Set<Value> prev = new HashSet<>();
    ArrayList<Value> topo = new ArrayList<>();
    Set<Value> visited = new HashSet<>();

    public Value (double data, String label){
        this.data = data;
        this.label = label;
    }

    public Value(double data){
        this.data = data;
    }

    @Override
    public String toString(){
        return this.label + "=" + String.format("%.4f", data);
    }

    public Value add(Value a){
        Value out = new Value(this.data + a.data);
        out.prev.add(a);
        out.prev.add(this);
        out.op = "+";
        return out;
    }

    public Value sub(Value a){
        Value out = new Value(this.data - a.data);
        out.prev.add(a);
        out.prev.add(this);
        out.op = "-";
        return out;
    }

    public Value mul(Value a){
        Value out = new Value(this.data * a.data);
        out.prev.add(a);
        out.prev.add(this);
        out.op = "*";        
        return out;
    }

    public Value div(Value a){
        if (a.data == 0){
            System.out.println("Error: Division by zero is not possible.");
            return null;
        }
        Value out = new Value(this.data / a.data);
        out.prev.add(a);
        out.prev.add(this);
        out.op = "/";
        return out;
    }
    
    public Value tanh(){
        double a = this.data;
        double res = (Math.exp(a*2)-1)/(Math.exp(a*2)+1);
        Value out = new Value(res);
        out.prev.add(this);        
        out.op = "tanh";
        return out;
    }

    void build_Topo(Value v){
        if(!visited.contains(v)){
            visited.add(v);
            Iterator<Value> itr = v.prev.iterator();
            while (itr.hasNext()) {
                Value child = itr.next(); 
                build_Topo(child);
            }
            topo.add(v);
        }
    } 
    void _backward(){
        Iterator<Value> it = prev.iterator();
        switch (op) {
            case "+":
                Value v1 = it.next(); 
                Value v2 = it.next();
                v1.grad+=1*this.grad;
                v2.grad+=1*this.grad;
                break;
            case "*":
                Value v3 = it.next(); 
                Value v4 = it.next();
                v3.grad+=v4.data*this.grad;
                v4.grad+=v3.data*this.grad;
                break;
            case "tanh":
                Value v5 = it.next(); 
                v5.grad+=(1-this.data*this.data)*this.grad;
                break;
            default:
                break;
        }
    }

    public void backward(){
        this.grad = 1;
        build_Topo(this);
        for (int i = topo.size()-1; i >-1; i--) {
            topo.get(i)._backward();
          }
    }
}

class Neuron{
    ArrayList<Value> w = new ArrayList<>(); 
    Value b;
    Neuron(int nin){
        Random random = new Random();
        // double randomValue = (random.nextDouble() * 2) - 1;
        b = new Value((random.nextDouble() * 2) - 1);
        for(int i=0; i<nin; i++){
            Value temp = new Value((random.nextDouble() * 2) - 1);
            w.add(temp);
        }
    }

    Value call(ArrayList<double> x){

        ListIterator<Value>

        return out;
    }
}