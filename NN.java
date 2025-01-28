//import NN.TestValue;

public class NN {
    public static void main(String[] args){
        TestValue x1 = new TestValue(2, "x1");
        TestValue w1 = new TestValue(-3, "w1");
        TestValue x2 = new TestValue(0, "x2");
        TestValue w2 = new TestValue(1, "w2");
        TestValue b = new TestValue(6.8814, "b");

        TestValue x1w1 = x1.mul(w1); x1w1.label = "x1w1";
        TestValue x2w2 = x2.mul(w2); x2w2.label = "x2w2";
        TestValue x1w1x2w2 = x1w1.add(x2w2); x1w1x2w2.label = "x1w1x2w2";

        TestValue n = x1w1x2w2.add(b); n.label = "n";
        
        TestValue out = n.tanh(); out.label = "tanh";

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
