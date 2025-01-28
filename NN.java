import NN.Value;

public class NN {
    public static void main(String[] args){
        Value x1 = new Value(2, "x1");
        Value w1 = new Value(-3, "w1");
        Value x2 = new Value(0, "x2");
        Value w2 = new Value(1, "w2");
        Value b = new Value(6.8814, "b");

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
        //ListIterator<Value> lst = out.prev.listIterator();
        //System.out.println("Child and operation of out: " + lst.next().label + " " + out.op + "Children index: " + out.i);

        System.out.println(out);
        out.backward();
        //out.bkWrdInitial();
        //n.bkWrdInitial();
        // x1w1x2w2.bkWrdInitial();
        // x1w1.bkWrdInitial();
        // x2w2.bkWrdInitial();

        System.out.println("Gradient of " + n.label + ": " + String.format("%5.3f", n.grad));
        System.out.println("Gradient of " + x1w1x2w2.label + ": " + String.format("%5.3f", x1w1x2w2.grad));
        System.out.println("Gradient of " + x1w1.label + ": " + String.format("%5.3f", x1w1.grad));
        System.out.println("Gradient of " + x2w2.label + ": " + String.format("%5.3f", x2w2.grad));
        System.out.println("Gradient of " + b.label + ": " + String.format("%5.3f", b.grad));
        System.out.println("Gradient of " + x1.label + ": " + String.format("%5.3f", x1.grad));
        System.out.println("Gradient of " + w1.label + ": " + String.format("%5.3f", w1.grad));
        System.out.println("Gradient of " + x2.label + ": " + String.format("%5.3f", x2.grad));
        System.out.println("Gradient of " + w2.label + ": " + String.format("%5.3f", w2.grad));

    }
}
