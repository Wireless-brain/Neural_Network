import NN.Value;

public class NN {
    public static void main(String[] args){
        Value x1 = new Value(2, "x1");
        Value w1 = new Value(-3, "w1");
        Value x2 = new Value(0, "x2");
        Value w2 = new Value(1, "w2");
        Value b = new Value(6.7, "b");

        Value x1w1 = new Value(x1.mul(w1), "x1w1");
        Value x2w2 = new Value(x2.mul(w2), "x2w2");

        //System.out.println("Value of x1w1 and x2w2: " + x1w1 + " " + x2w2);

        Value x1w1x2w2 = new Value(x1w1.add(x2w2), "x1w1x2w2");
        Value n = new Value(x1w1x2w2.add(b), "n");
        
        //System.out.println("Before tanh(): " + n);
        Value out = new Value(n.tanh(), "tanh");

        System.out.println(out);
    }
}
