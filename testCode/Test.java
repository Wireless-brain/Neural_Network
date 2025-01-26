class ABC{
    int a;
    float b;
    ABC(int a, float b){
        this.a = a;
        this.b = b;
    }

    int printValues(){
        System.out.println("\n\tValues: " + a + "\n\tNext value: " + b + "\n\n");
        return 0;
    }
}


class Test{
    public static void main(String[] args){
        int a = 0;
        int b = 0;
        System.out.println("\n\tHello World");
        a = 1;
        b = 5;
        System.out.print("\n\tSum is: " + (a+b));
        
        ABC ab = new ABC(2, 4);
        ab.printValues();
    }
}