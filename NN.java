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
        Value x1w1x2w2 = x1w1.add(x2w2); x1w1x2w2.label = "x1w1x2w2";

        Value n = x1w1x2w2.add(b); n.label = "n";
        
        Value out = n.tanh(); out.label = "tanh";

        System.out.println(out);
        out.backward();

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
