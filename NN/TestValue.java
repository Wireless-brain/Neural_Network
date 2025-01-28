import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;

class Value{
    public double data;
    public double grad = 0;
    public String label = "";
    public String op = "";
    public ArrayList<Value> prev = new ArrayList<>();
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
        //return this.label + "=" + String.format("%.4f", data);
        return "Value=" + String.format("%.4f", this.data) + " Grad=" + String.format("%.4f", this.grad);
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
        //Set<Value> children=new HashSet<>();
        // children.add(this);
        // children.add(new Value(b, Label));
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
        // double randomValue = (random.nextDouble() * 2) - 1;
        this.b = new Value((random.nextDouble() * 2) - 1);
        for(int i=0; i<nin; i++){
            //Value temp = new Value((random.nextDouble() * 2) - 1);
            this.w.add(new Value((random.nextDouble() * 2) - 1));
        }
    }

    // ArrayList<Value> makeVal(double[] x){
        
    //     ArrayList<Value> dub = new ArrayList<>();
    //     for(int i = 0; i<x.length; i++){
    //         dub.add(new Value(x[i]));
    //     }
    //     return dub;
    // }

    public Value call(ArrayList<Value> x){

        //ArrayList<Value> x = makeVal(x1);
        Value act = new Value(0);
        for (int i=0; i<x.size(); i++){
            act.data += this.w.get(i).data * x.get(i).data;
        }
        Value out = act.tanh();
        return out;
    }

    public ArrayList<Value> parameters(){
        ArrayList<Value> p = new ArrayList<>();
        // for (int i=0; i<this.w.size(); i++){
        //     p.add(w.get(i));
        // }
        p.addAll(this.w);
        p.add(b);
        return p;
    }
}

class Layer{
    public ArrayList<Neuron> neurons = new ArrayList<>();
    
    Layer(int nin, int nout){
        for(int i=0; i<nout; i++){
            //Value temp = new Value((random.nextDouble() * 2) - 1);
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
        //ArrayList<Value> p;
        for (int i=0; i<this.neurons.size(); i++){
            q.addAll(neurons.get(i).parameters());
            // for (int j=0; j<p.size(); j++){
            //     q.add(p.get(j));
            // }
        }
        return q;
    }

}

class MLP{
    public ArrayList<Layer> layers = new ArrayList<>();
    
    MLP(int nin, int[] nouts){
        this.layers.add(new Layer(nin, nouts[0]));
        //System.out.println("At creation, number of layers: " + this.layers.size());
        for (int i=0; i<nouts.length-1; i++){
            layers.add(new Layer(nouts[i], nouts[i+1]));
            //System.out.println("At creation, number of layers: " + this.layers.size());
        }

        //System.out.println("At creation, number of layers: " + this.layers.size());
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
            // for (int j=0; j<p.size(); j++){
            //     q.add(p.get(j));
            // }
            //System.out.println("Layerwise parameter number: " + q.size());
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

        //Layer n = new Layer(2, 3);

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
        //double[] ys = {1.0, -1.0, -1.0, 1.0};

        int [] nouts = {4, 4, 1};
        MLP n = new MLP(3, nouts);
        //System.out.println("Training data: " + xs);
        //ArrayList<Value> yPredAct = new ArrayList<>();
        ArrayList<Value> ypred = new ArrayList<>();

        for (int i=0; i<xs.size(); i++){
            //ypred.add(new ArrayList<Value>());
            //yPresAct.add
            ypred.add(n.call(xs.get(i)).get(0));
        }

        Value loss = new Value(0);

        for (int i=0; i<ypred.size(); i++){
            loss = loss.add((ypred.get(i).sub(ys.get(i))).exp(2));
        }

        System.out.println("Predictions: " + ypred);
        System.out.println("Loss value: " + loss);

        loss.backward();
        //System.out.println("Loss value: " + loss);

        double lr = 0.1, sum1 = 0, val1 = 0;

        ArrayList<Value> p = n.parameters();
        Value v1;
        for (int i=0; i<10; i++){
            for (int j=0; j<p.size(); j++){
                v1 = p.get(j);
                v1.data += -lr*v1.grad;
                v1.grad = 0;
            }

            for (int k=0; k<xs.size(); k++){
                //ypred.add(new ArrayList<Value>());
                //yPresAct.add
                ypred.add(n.call(xs.get(k)).get(0));
            }

            for (int l=0; l<ypred.size(); l++){
                val1 = ypred.get(l).data - ys.get(l).data;
                sum1 += val1*val1;
            }

            loss.data = sum1;
            System.out.println("Loss: " + sum1);

        }

        

        System.out.println("Predictions new: " + ypred);
        //System.out.println("Loss value: " + loss);

        //System.out.println("Parameter size: " + n.parameters().size());

        //System.out.println(n.layers.size());

    }
}