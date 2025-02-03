package ValueClass;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;

public class Value{
    public double data;
    public double grad = 0;
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

    public Value exp(double b){
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
