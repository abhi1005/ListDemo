import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        List<Integer> oddIntegerList = getOddList();
        List<Integer> evenIntegerList = getEvenList();
        List<Integer> powerOfTwoIntegerList = getPowerOfTwoList();
        System.out.println("odd integers : "+oddIntegerList);
        System.out.println("even integers : "+evenIntegerList);
        System.out.println("power of 2 integers : "+powerOfTwoIntegerList);

    }

    public static List<Integer> getOddList(){
        List<Integer> oddIntegerList = new ArrayList<>();
        for(int num=1;num<=1024;num++){
            if(isNumberOdd(num)){
                oddIntegerList.add(num);
            }
        }
        return oddIntegerList;
    }

    public static List<Integer> getEvenList(){
        List<Integer> evenIntegerList = new ArrayList<>();
        for(int num=1;num<=1024;num++){
            if(isNumberEven(num)){
                evenIntegerList.add(num);
            }
        }
        return evenIntegerList;
    }

    public static List<Integer> getPowerOfTwoList(){
        List<Integer> powerOfTwoIntegerList = new ArrayList<>();
        for(int num=1;num<=1024;num++){
            if(isNumberPowerOfTwo(num)){
                powerOfTwoIntegerList.add(num);
            }
        }
        return powerOfTwoIntegerList;
    }

    public static boolean isNumberOdd(int num){
        return num%2 != 0;
    }

    public static boolean isNumberEven(int num){
        return num%2 == 0;
    }

    public static boolean isNumberPowerOfTwo(int num){
        return num!=0 &&  (num & num-1) == 0;
    }
}
