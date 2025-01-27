package NN;

import java.lang.Math;
public class Value{
    double data;
    double grad;
    public String label;
    public String op;
    public Value[] prev = new Value[2];
    int i;

    public Value (double data, String label){
        this.data = data;
        this.label = label;
        this.op = null;
        this.i = -1;
        this.grad = 0;
    }

    public Value(double data){
        this.data = data;
        this.grad = 0;
        this.i = -1;
    }

    @Override
    public String toString(){
        return this.label + "=" + String.format("%.4f", data);
    }

    public Value add(Value a){
        Value out = new Value(this.data + a.data);
        out.prev[++i] = a;
        out.prev[++i] = this;
        out.op = "+";
        // public void backward()
        // this.grad = 1.0 * out.grad;
        // a.grad = 1.0 * out.grad;
        return out;
    }

    public Value sub(Value a){
        Value out = new Value(this.data - a.data);
        out.prev[++i] = a;
        out.prev[++i] = this;
        out.op = "-";
        return out;
    }

    public Value mul(Value a){
        Value out = new Value(this.data * a.data);
        out.prev[++i] = a;
        out.prev[++i] = this;
        out.op = "*";        
        return out;
    }

    public Value div(Value a){
        if (a.data == 0){
            System.out.println("Error: Division by zero is not possible.");
            return null;
        }
        Value out = new Value(this.data / a.data);
        out.prev[++i] = a;
        out.prev[++i] = this;
        out.op = "/";
        return out;
    }
    
    public Value tanh(){
        double a = this.data;
        double res = (Math.exp(a*2)-1)/(Math.exp(a*2)+1);
        Value out = new Value(res);
        out.prev[++i] = this;
        out.op = "tanh";
        return out;
    }
}
