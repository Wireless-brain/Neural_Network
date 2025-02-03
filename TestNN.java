import ValueClass.Value;
import MLP.Mlp;
import java.util.ArrayList;
import java.util.Scanner;

public class TestNN{
    public static void main(String[] args){
        ArrayList<Value> x = new ArrayList<>();
        
        x.add(new Value(2.0));
        x.add(new Value(3.0));
        x.add(new Value(-1.0));

        ArrayList<ArrayList<Value>> xs = new ArrayList<>();
        xs.add(new ArrayList<Value>());
        xs.get(0).add(0, new Value(2.0));
        xs.get(0).add(1, new Value(3.0));
        xs.get(0).add(2, new Value(-1.0));
        
        xs.add(new ArrayList<Value>());
        xs.get(1).add(0, new Value(3.0));
        xs.get(1).add(1, new Value(-1.0));
        xs.get(1).add(2, new Value(0.5));

        xs.add(new ArrayList<Value>());
        xs.get(2).add(0, new Value(0.5));
        xs.get(2).add(1, new Value(1.0));
        xs.get(2).add(2, new Value(1.0));

        xs.add(new ArrayList<Value>());
        xs.get(3).add(0, new Value(1.0));
        xs.get(3).add(1, new Value(1.0));
        xs.get(3).add(2, new Value(-1.0));
        
        ArrayList<Value> ys = new ArrayList<>();
        ys.add(new Value(1.0));
        ys.add(new Value(-1.0));
        ys.add(new Value(-1.0));
        ys.add(new Value(1.0));

        int [] nouts = {4, 4, 1};
        Mlp n = new Mlp(3, nouts);
        ArrayList<Value> ypred = new ArrayList<>();

        Value loss;

        double lr = 0.5;
        int start = 0;
        Scanner sc = new Scanner(System.in);
        int cntnue = 0, epoch = 100;

        ArrayList<Value> p;
        Value v1;
        do{
            for (int i=0; i<epoch; i++){
                ypred = new ArrayList<>();
                for (int j=0; j<xs.size(); j++){
                    ypred.add(n.call(xs.get(j)).get(0));
                }

                if (start == 0){
                    System.out.println("Predictions new: " + ypred);
                    start = 1;
                }

                loss = new Value(0);
                for (int l=0; l<ypred.size(); l++){
                    loss = loss.add((ypred.get(l).sub(ys.get(l))).exp(2));
                }
                System.out.println("Loss new: " + loss.data);
                loss.backward();
                p = n.parameters();
                for (int j=0; j<p.size(); j++){
                    v1 = p.get(j);
                    v1.data += -lr*v1.grad;
                }
                for (int j=0; j<p.size(); j++){
                    v1 = p.get(j);
                    v1.grad = 0;
                }
            }
            System.out.print("\n\tContinue or not(0/1): ");
            cntnue = sc.nextInt();

            if (cntnue == 1){
                System.out.println("Predictions new: " + ypred);
            }

        }while (cntnue == 0);
    }
}