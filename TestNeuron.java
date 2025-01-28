//import NN.Neuron;

//import NN.TestValue;

class TestNeuron{
    public static void main(String[] args){
        
        double[] x = {0.6, 8};
        Neuron n = new Neuron(2);
        System.out.println(n.call(x));
    }
}