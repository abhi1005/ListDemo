import java.util.List;

public class CosineSimilarity {

    public static double getCosineSimilarity(List<Double> vectA, List<Double> vectB){
        double dotProduct = getDotProduct(vectA,vectB);
        return dotProduct/(getMagnitudeOfVector(vectA)*getMagnitudeOfVector(vectB));
    }

    private static double getDotProduct(List<Double> vectA, List<Double> vectB)
    {
        double product = 0;
        for (int i = 0; i < vectA.size(); i++)
            product = product + vectA.get(i) * vectB.get(i);
        return product;
    }

    private static double getMagnitudeOfVector(List<Double> vec){
        double mag = 0.0;
        for(int i=0;i<vec.size();i++){
            mag += (vec.get(i) * vec.get(i));
        }
        return Math.sqrt(mag);
    }
}
