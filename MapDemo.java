import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        String input = "This is Spot. See Spot run. Run, Spot, Run.";
        Map<String, Integer> wordsCountMap = new HashMap<>();
        String inputArray[] = Arrays.stream(input.split("\\s+"))
                .map(String::trim)
                .map(str -> {
                    return str.replaceAll("[,.]","");
                })
                .toArray(String[]::new);
        for(String str:inputArray){
            String lowerCaseStr = str.toLowerCase();
            if(wordsCountMap.containsKey(lowerCaseStr)){
                wordsCountMap.put(lowerCaseStr,wordsCountMap.get(lowerCaseStr)+1);
            }else{
                wordsCountMap.put(lowerCaseStr,1);
            }
        }
        wordsCountMap.forEach((str,num) -> {
            System.err.println(str+":"+num);
        });
    }
}
