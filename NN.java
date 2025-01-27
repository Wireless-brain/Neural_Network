import NN.Value;

public class NN {
    public static void main(String[] args){
        Value x1 = new Value(2, "x1");
        Value w1 = new Value(-3, "w1");
        Value x2 = new Value(0, "x2");
        Value w2 = new Value(1, "w2");
        Value b = new Value(6.7, "b");

        Value x1w1 = x1.mul(w1); x1w1.label = "x1w1";
        Value x2w2 = x2.mul(w2); x2w2.label = "x2w2";

        // System.out.println("Child and operation of X1: Label " + x1.op);

        // System.out.println("Child and operation of X1W1: " + x1w1.prev[0].label + " " + x1w1.prev[1].label + " " + x1w1.op);
        //System.out.println("Value of x1w1 and x2w2: " + x1w1 + " " + x2w2);
        // System.out.println("Child and operation of X2W2: " + x2w2.prev[0].label + " " + x2w2.prev[1].label + " " + x2w2.op);

        Value x1w1x2w2 = x1w1.add(x2w2); x1w1x2w2.label = "x1w1x2w2";
        // System.out.println("Child and operation of X1W1x2w2: " + x1w1x2w2.prev[0].label + " " + x1w1x2w2.prev[1].label + " " + x1w1x2w2.op);

        Value n = x1w1x2w2.add(b); n.label = "n";
        // System.out.println("Child and operation of n: " + n.prev[0].label + " " + n.prev[1].label + " " + n.op);
        
        //System.out.println("Before tanh(): " + n);
        Value out = n.tanh(); out.label = "tanh";
        // System.out.println("Child and operation of out: " + out.prev[0].label + " " + out.op);

        System.out.println(out);
    }
}
