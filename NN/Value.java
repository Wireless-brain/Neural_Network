package NN;

import java.lang.Math;
public class Value{
    double data;
    double grad;
    public String label;
    
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
        return out;
    }
    public Value sub(Value a){
        Value out = new Value(this.data - a.data);
        return out;
    }
    public Value mul(Value a){
        Value out = new Value(this.data * a.data);
        return out;
    }
    public Value div(Value a){
        if (a.data == 0){
            System.out.println("Error: Division by zero is not possible.");
            return null;
        }
        Value out = new Value(this.data / a.data);
        return out;
    }
    
    public Value tanh(){
        double a = this.data;
        double res = (Math.exp(a*2)-1)/(Math.exp(a*2)+1);
        Value out = new Value(res);
        return out;
    }


}
