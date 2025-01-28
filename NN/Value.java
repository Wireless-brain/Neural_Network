package NN;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

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

    // public void bkWrdInitial(){
    //     this.grad = 1.0;
    //     TopoSort t = new TopoSort();

    //     ArrayList<Value> topoLst = t.get_topo(this);
    //     //ListIterator<Value> itr = topoLst.listIterator();
    //     ArrayList<Value> revTopoLst = new ArrayList<Value>();

    //     for(int i = topoLst.size()-1; i>=0; i--){
    //         revTopoLst.add(topoLst.get(i));
    //     }

    //     ListIterator<Value> itr = revTopoLst.listIterator();
    //     backward(this);
    //     // while (itr.hasNext()){
    //     //     //System.out.println(itr.next());
    //     //     backward(itr.next());
    //     // }
    // }

    // void backward(Value node){
    //     ListIterator<Value> lst = node.prev.listIterator();
    //     Value a;
    //     Value b;

    //     switch (node.op){
    //         case "+": a = lst.next();
    //                   b = lst.next();
    //                   a.grad += 1.0 * node.grad;
    //                   b.grad += 1.0 * node.grad;
    //                   break;
    //         case "-": a = lst.next();
    //                   b = lst.next();
    //                   a.grad += 1.0 * node.grad;
    //                   b.grad += 1.0 * node.grad;
    //                   break;
    //         case "*": a = lst.next();
    //                   b = lst.next();
    //                   a.grad += b.data * node.grad;
    //                   b.grad += a.data * node.grad;
    //                   break;
    //         case "/": break;
    //         case "tanh": a = lst.next();
    //                      a.grad += (1-(node.data * node.data)*node.grad);
    //                      break;
    //     }
    // }

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

// class TopoSort{
//     ArrayList<Value> topoLst = new ArrayList<Value>();
//     HashSet<Value> visited = new HashSet<Value>();

//     ArrayList<Value> get_topo(Value a){

//         build_topo(a);
//         return topoLst;
//     }

//     void build_topo(Value v){

//         if (!visited.contains(v)){
//             visited.add(v);
//             //System.out.println("In visited: " + v);
//             //System.out.println("Value of i: " + v.i);
//             ListIterator<Value> lst = v.prev.listIterator();
            
//             while (lst.hasNext()){
//                 Value ab = lst.next();
//                 //System.out.println("Children of " + v + ": " + ab.label);
//                 build_topo(ab);
//             }
//             //System.out.println(v);
//             topoLst.add(v);
//         }
        
//     }

// }