import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

class Value{
    double data;
    double grad = 0;
    String label = "";
    String op = "";
    ArrayList<Value> prev = new ArrayList<>();
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
        out.prev.add(this);
        out.prev.add(a);
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

    Value exp(double b){
        Value out = new Value(Math.pow(this.data, b));
        out.prev.add(this);
        out.prev.add(new Value(b));
        out.op = "^";
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
        ListIterator<Value> it = prev.listIterator();
        
        Value v1, v2;
        switch (op) {
            case "+":
                v1 = it.next(); 
                v2 = it.next();
                v1.grad += 1*this.grad;
                v2.grad += 1*this.grad;
                break;
            case "-":
                v1 = it.next();
                v2 = it.next();
                v1.grad += 1*this.grad;
                v2.grad += -1*this.grad;
                break;
            case "*":
                v1 = it.next(); 
                v2 = it.next();
                v1.grad += v2.data*this.grad;
                v2.grad += v1.data*this.grad;
                break;
            case "tanh":
                v1 = it.next(); 
                v1.grad+=(1-this.data*this.data)*this.grad;
                break;
            case "^":
                v1 = it.next();
                v2 = it.next();
                v1.grad += v2.data*Math.pow(v1.data, v2.data-1)*this.grad;
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

class MLP{
    public ArrayList<Layer> layers = new ArrayList<>();
    
    MLP(int nin, int[] nouts){
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

public class TestValue{
    public static void main(String[] args){
        ArrayList<Value> x = new ArrayList<>();
        
        x.add(new Value(2.0));
        x.add(new Value(3.0));
        x.add(new Value(-1.0));

        ArrayList<ArrayList<Value>> xs = new ArrayList<>();
        xs.add(new ArrayList<Value>());
        xs.get(0).add(0, new Value(2.0));
        xs.get(0).add(1, new Value(3.0));
        xs.get(0).add(2, new Value(-1.0));
        
        xs.add(new ArrayList<Value>());
        xs.get(1).add(0, new Value(3.0));
        xs.get(1).add(1, new Value(-1.0));
        xs.get(1).add(2, new Value(0.5));

        xs.add(new ArrayList<Value>());
        xs.get(2).add(0, new Value(0.5));
        xs.get(2).add(1, new Value(1.0));
        xs.get(2).add(2, new Value(1.0));

        xs.add(new ArrayList<Value>());
        xs.get(3).add(0, new Value(1.0));
        xs.get(3).add(1, new Value(1.0));
        xs.get(3).add(2, new Value(-1.0));
        
        ArrayList<Value> ys = new ArrayList<>();
        ys.add(new Value(1.0));
        ys.add(new Value(-1.0));
        ys.add(new Value(-1.0));
        ys.add(new Value(1.0));

        int [] nouts = {4, 4, 1};
        MLP n = new MLP(3, nouts);
        ArrayList<Value> ypred = new ArrayList<>();

        Value loss;

        double lr = 0.5;
        int start = 0;
        Scanner sc = new Scanner(System.in);
        int cntnue = 0, epoch = 100;

        ArrayList<Value> p;
        Value v1;
        do{
            for (int i=0; i<epoch; i++){
                ypred = new ArrayList<>();
                for (int j=0; j<xs.size(); j++){
                    ypred.add(n.call(xs.get(j)).get(0));
                }

                if (start == 0){
                    System.out.println("Predictions new: " + ypred);
                    start = 1;
                }

                loss = new Value(0);
                for (int l=0; l<ypred.size(); l++){
                    loss = loss.add((ypred.get(l).sub(ys.get(l))).exp(2));
                }
                System.out.println("Loss new: " + loss.data);
                loss.backward();
                p = n.parameters();
                for (int j=0; j<p.size(); j++){
                    v1 = p.get(j);
                    v1.data += -lr*v1.grad;
                }
                for (int j=0; j<p.size(); j++){
                    v1 = p.get(j);
                    v1.grad = 0;
                }
            }
            System.out.print("\n\tContinue or not(0/1): ");
            cntnue = sc.nextInt();

            if (cntnue == 1){
                System.out.println("Predictions new: " + ypred);
            }

        }while (cntnue == 0);
    }
}