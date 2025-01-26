package NN;

import java.lang.Math;
public class Value{
    double data;
    double grad;
    
    public Value (double data){
        this.data = data;
    }

    @Override
    public String toString(){
        return "Value(Data)=" + String.format("%.4f", data);
    }

    public Value add(Value a){
        return new Value(this.data + a.data);
    }
    public Value sub(Value a){
        return new Value(this.data - a.data);
    }
    public Value mul(Value a){
        return new Value(this.data * a.data);
    }
    public Value div(Value a){
        if (a.data == 0){
            System.out.println("Error: Division by zero is not possible.");
            return null;
        }
        return new Value(this.data/a.data);
    }
    
    public Value tanh(){
        double a = this.data;
        double res = (Math.exp(a*2)-1)/(Math.exp(a*2)+1);
        return new Value(res);
    }
}
