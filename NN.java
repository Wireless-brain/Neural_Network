import NN.Value;

public class NN {
    public static void main(String[] args){
        Value x1 = new Value(2);
        Value w1 = new Value(-3);
        Value x2 = new Value(0);
        Value w2 = new Value(1);
        Value b = new Value(6.7);

        Value x1w1 = x1.mul(w1);
        Value x2w2 = x2.mul(w2);

        //System.out.println("Value of x1w1 and x2w2: " + x1w1 + " " + x2w2);

        Value adRes = x1w1.add(x2w2);
        Value n = adRes.add(b);
        
        //System.out.println("Before tanh(): " + n);
        Value out = n.tanh();

        System.out.println("Result:" + out);
    }
}
