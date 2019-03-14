
public class Fibonacci {

    public static int times = 50;
    public static long fibArray[]=new long[times+1];
    
    public static void main(String[] args) {

        // Fibonacci with Iteration
        long preTime=System.currentTimeMillis();
        System.out.println("Value of " + times + "th number in fibonacci series-> " + fibIteration(times));
        long postTime=System.currentTimeMillis();
        System.out.println("Time taken to compute fibIteration in milliseconds-> "+ (postTime-preTime) + "ms");

        // Fibonacci with recursivity
        preTime=System.currentTimeMillis();
        System.out.println("Value of " + times + "th number in fibonacci series-> "+ fibRecursion(times));
        postTime=System.currentTimeMillis();
        System.out.println("Time taken to compute fibRecursion in milliseconds-> " + (postTime-preTime) + "ms");
        
        // Fibonacci with recursivity and memoïzation
        fibArray[0]=1;
        fibArray[1]=1;
        preTime=System.currentTimeMillis();
        System.out.println("Value of " + times + "th number in Fibonacci series->"+fibRecursionWithMemoization(times));
        postTime=System.currentTimeMillis();
        System.out.println("Time taken to compute fibRecursionWithMemoization in milliseconds->"+(postTime-preTime) + "ms");


    }
    
    public static int fibIteration(int n) {
        int x = 0, y = 1, z = 1;
        for (int i = 0; i < n; i++) {
            x = y;
            y = z;
            z = x + y;
        }
        return x;
    }

    public static int fibRecursion(int  n) {
        if ((n == 1) || (n == 0)) {
            return n;
        }
        return fibRecursion(n - 1) + fibRecursion(n - 2);
    }
    
    public static int fibRecursionWithMemoization(int  n) {
        long fibValue=0;
        if(n==0 ){
         return 0;
        }else if(n==1){
         return 1;
        }else if(fibArray[(int)n]!=0){
         return (int) fibArray[(int)n];
        }else{
         fibValue=fibRecursionWithMemoization(n-1)+fibRecursionWithMemoization(n-2);
         fibArray[(int) n]=fibValue;
         return (int) fibValue;
        }
    }

}
