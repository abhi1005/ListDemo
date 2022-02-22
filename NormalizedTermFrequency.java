import java.util.*;

public class NormalizedTermFrequency {
    public static Map<String, List<Double>> getNormalizedFrequency(List<String> sentences){
        Map<String, List<Double>> normalizedFrequency = new HashMap<>();
        LinkedHashSet<Map<String,Integer>> setOfMap = getSetOfMap(sentences);
        Set<String> wordSet = getAllWords(setOfMap);

        for(String word : wordSet){
            List<Double> frequencyList = normalizedFrequency.get(word);
            for(Map<String, Integer> wordCountMap : setOfMap){
                if(frequencyList == null){
                    frequencyList = new ArrayList<>();
                }
                if(wordCountMap.containsKey(word)){
                    int count = wordCountMap.get(word);
                    double frequency = (double)count/wordCountMap.size();
                    frequencyList.add(frequency);
                }else{
                    frequencyList.add(0.0);
                }
            }
            normalizedFrequency.put(word, frequencyList);
        }
        return normalizedFrequency;
    }

    private static Set<String> getAllWords(LinkedHashSet<Map<String,Integer>> setOfMap){
        Set<String> wordSet = new HashSet<>();
        for(Map<String, Integer> wordCountMap : setOfMap){
            for(Map.Entry<String, Integer> wordCountEntry : wordCountMap.entrySet()){
                wordSet.add(wordCountEntry.getKey());
            }
        }
        return wordSet;
    }

    private static LinkedHashSet<Map<String,Integer>> getSetOfMap(List<String> sentences){
        LinkedHashSet<Map<String,Integer>> setOfMap = new LinkedHashSet<>();
        for(String sentence : sentences){
            Map<String, Integer> frequencyMap = MapDemo.getStringCountMap(sentence);
            setOfMap.add(frequencyMap);
        }
        return setOfMap;
    }

    public static Map<String, Double> getInverseFrequencyDocument(List<String> sentences){
        Map<String, List<Double>> normalizedFrequency = getNormalizedFrequency(sentences);
        Map<String, Double> inverseFrequencyDocument = new HashMap<>();
        for(Map.Entry<String, List<Double>> normalizedMapEntry : normalizedFrequency.entrySet()){
            List<Double> normalizedList = normalizedMapEntry.getValue();
            int sentencesContainingWord = 0;
            for(Double value : normalizedList){
                if(value != 0.0){
                    sentencesContainingWord++;
                }
            }
            Double idf = Math.log(sentences.size()/sentencesContainingWord);
            inverseFrequencyDocument.put(normalizedMapEntry.getKey(), idf);
        }
        return inverseFrequencyDocument;
    }

    public static List<String>  getTermFreqInverseDocFreq(List<String> sentences){
        Map<String, List<Double>> normalizedFrequency = getNormalizedFrequency(sentences);
        Map<String, Double> inverseFrequencyDocument = getInverseFrequencyDocument(sentences);

        Set<String> words = new LinkedHashSet<>();
        for(Map.Entry<String, Double> wordsEntry : inverseFrequencyDocument.entrySet()){
            words.add(wordsEntry.getKey());
        }

        Map<String, List<Double>> termFreqInverseDoc = getTermFreqInverseDoc(inverseFrequencyDocument, words, normalizedFrequency);
        List<String> similarSentences = getSimilarSentences(sentences, words, termFreqInverseDoc);
        return similarSentences;
    }

    public static List<String> getSimilarSentences(List<String> sentences,Set<String> words, Map<String, List<Double>> termFreqInverseDoc){
        double cosineMatrix[][] = new double[words.size()][sentences.size()];
        int row=0;
        for(String word : words){
            List<Double> termFreqInverseDocList = termFreqInverseDoc.get(word);
            for(int j=0;j<sentences.size();j++){
                cosineMatrix[row][j] = termFreqInverseDocList.get(j);
            }
            row++;
        }

        List<List<Double>> cosineSentencesMatrix = new ArrayList<>();
        for(int j=0;j<sentences.size();j++){
            List<Double> list = new ArrayList<>();
            for(int i=0;i<words.size();i++){
                list.add(cosineMatrix[i][j]);
            }
            cosineSentencesMatrix.add(list);
        }

        List<Double> similarityList = new ArrayList<>();
        for(int i=0;i<cosineSentencesMatrix.size();i++){
            double highestSimilarityMeasure = Double.MIN_VALUE;
            List<Double> ithSentence = cosineSentencesMatrix.get(i);
            for(int j=i+1;j<sentences.size();j++){
                double cosineValue = CosineSimilarity.getCosineSimilarity(ithSentence, cosineSentencesMatrix.get(j));
                if(cosineValue > highestSimilarityMeasure){
                    highestSimilarityMeasure = cosineValue;
                }
            }
            similarityList.add(highestSimilarityMeasure);
        }

        double highestSimilarityMeasure = Double.MIN_VALUE;

        for(int i=0;i<similarityList.size();i++){
            if(similarityList.get(i) > highestSimilarityMeasure){
                highestSimilarityMeasure = similarityList.get(i);
            }
        }

        List<String> similarSentences = new ArrayList<>();
        for(int i=0;i<similarityList.size();i++){
            if(similarityList.get(i) == highestSimilarityMeasure){
                similarSentences.add(sentences.get(i));
            }
        }
        return similarSentences;
    }

    private static Map<String, List<Double>> getTermFreqInverseDoc(Map<String, Double> inverseFrequencyDocument,
                                                                   Set<String> words, Map<String, List<Double>> normalizedFrequency){
        Map<String, List<Double>> termFreqInverseDoc = new HashMap<>();
        for(String word : words){
            List<Double> normalizedFreqList = normalizedFrequency.get(word);
            List<Double> termFreqInverseDocFreqList = new ArrayList<>();
            for(Double normalizedFreq : normalizedFreqList){
                double value = normalizedFreq * (inverseFrequencyDocument.get(word));
                termFreqInverseDocFreqList.add(value);
            }
            termFreqInverseDoc.put(word, termFreqInverseDocFreqList);
        }
        return termFreqInverseDoc;
    }

    public static void main(String[] args) {
        List<String> sentences = Arrays.asList("This is Spot. See Spot run. Run, Spot, Run.","This is Jane. See Spot run to Jane.");
//        Map<String, Double> inverseFrequencyDocument = getInverseFrequencyDocument(sentences);
//        System.err.println(inverseFrequencyDocument);
        List<String> similarSentences = getTermFreqInverseDocFreq(sentences);
        System.err.println(similarSentences);
    }
}
