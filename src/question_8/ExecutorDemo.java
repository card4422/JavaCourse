package question_8;

/**
 * Created by Jimmy on 2017/1/2.
 */
public class ExecutorDemo {
    public static void main(String [] args){
        int x = 0;
        int[] numbers = new int[100] ;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++) {
                x = 1 + (int) (Math.random() * 100);
                numbers[i*10+j] = x;
                System.out.print(x+"  ");
            }
            System.out.println();
        }
        ExecutorCalculator calc = new ExecutorCalculator();
        Integer max = calc.max(numbers);
        System.out.println(max);
        calc.close();
    }
}
