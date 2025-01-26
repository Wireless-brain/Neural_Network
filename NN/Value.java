package NN;

import java.lang.Math;
public class Value{
    double data;
    double grad;
    String label;
    
    public Value (double data, String label){
        this.data = data;
        this.label = label;
    }

    @Override
    public String toString(){
        return this.label + "=" + String.format("%.4f", data);
    }

    public double add(Value a){
        
        return this.data + a.data;
    }
    public double sub(Value a){
        return this.data - a.data;
    }
    public double mul(Value a){
        return this.data * a.data;
    }
    public double div(Value a){
        if (a.data == 0){
            System.out.println("Error: Division by zero is not possible.");
            return 0;
        }
        return this.data/a.data;
    }
    
    public double tanh(){
        double a = this.data;
        double res = (Math.exp(a*2)-1)/(Math.exp(a*2)+1);
        return res;
    }


}
