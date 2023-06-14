import com.google.common.base.CaseFormat;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Assignment02 {

    enum EnumDictionary {

        ARROW(
                "arrow",
                new String[]{
                        "noun"
                },
                new String[]{
                        "Here is one arrow: <IMG> -=>> </IMG>"
                }
        ),
        BOOK(
                "book",
                new String[]{
                        "noun",
                        "noun",
                        "verb",
                        "verb",
                },
                new String[]{
                        "A set of pages.",
                        "A written work published in printed or electronic form.",
                        "To arrange for someone to have a seat on a plane.",
                        "To arrange something on a particular date."
                }

        ),
        DISTINCT(
                "distinct",
                new String[]{
                        "adjective",
                        "adjective",
                        "adverb",
                        "noun",
                        "noun",
                        "noun",
                        "noun",
                        "noun",
                },
                new String[]{
                        "Familiar. Worked in Java.",
                        "Unique. No duplicates. Clearly different or of a different kind.",
                        "Uniquely. Written \"distinctly\".",
                        "A keyword in this assignment.",
                        "A keyword in this assignment.",
                        "A keyword in this assignment.",
                        "An advanced search option.",
                        " Distinct is a parameter in this assignment.",
                }
        ),
        PLACEHOLDER(
                "placeholder",
                new String[]{
                        "adjective",
                        "adjective",
                        "adverb",
                        "conjunction",
                        "interjection",
                        "noun",
                        "noun",
                        "noun",
                        "preposition",
                        "pronoun",
                        "verb",
                },
                new String[]{
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                }
        ),
        REVERSE(
                "reverse",
                new String[]{
                        "adjective",
                        "adjective",
                        "noun",
                        "noun",
                        "noun",
                        "noun",
                        "noun",
                        "noun",
                        "noun",
                        "verb",
                        "verb",
                        "verb",
                        "verb",
                        "verb",
                        "verb",
                },
                new String[]{
                        "On back side.",
                        "Opposite to usual or previous arrangement.",
                        "A dictionary program's parameter.",
                        "Change to opposite direction.",
                        "The opposite.",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "To be updated...",
                        "Change something to opposite.",
                        "Go back",
                        "Revoke ruling.",
                        "To be updated...",
                        "To be updated...",
                        "Turn something inside out.",
                }
        );

        private String keyWord;
        private String[] partOfSpeech;
        private String[] definition;

        /*Constructor*/
        EnumDictionary(String keyword, String[] partofspeech, String[] definitions) {
            this.keyWord = keyword;
            this.partOfSpeech = partofspeech;
            this.definition = definitions;
        }

        public String getKeyWord() {
            return keyWord;
        }
        public String[] getPartOfSpeech() {
            return partOfSpeech;
        }
        public String[] getDefinition() {
            return definition;
        }

        @Override
        public String toString() {
            return "    "+getKeyWord()+ " ["+getPartOfSpeech()+"] : "+getDefinition();
        }

    }

    public static void loadData(){
        System.out.println("! Loading data...");
        System.out.println("! Loading completed...\n");
    }

    public static void instructions(){
        System.out.println("   |\n" +"     PARAMETER HOW-TO,  please enter:\n" +"     1. A search key -then 2. An optional part of speech -then\n" +"     3. An optional 'distinct' -then 4. An optional 'reverse'\n" +"    |");
    }

    public static void requestDistinct(String find, Multimap<String, String[]> dictionary){
        SetMultimap<String, String> distinctMap = HashMultimap.create();
        for (EnumDictionary value: EnumDictionary.values()) {
            distinctMap.put(value.keyWord, String.valueOf(value.definition));
        }
        String [] requestAction = find.split(" ", 2);
        for (String s : distinctMap.get(requestAction[0])) {
            String [] arr = find.split(" ", 2);
            System.out.println("    "+ CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,arr[0])+" "+s);
        }
    }

    public static void requestReverse(String find, Multimap<String, String[]> dictionary){
        Map<String, String> reverse = new HashMap<>();
        for (EnumDictionary value: EnumDictionary.values()) {
            reverse.put(value.keyWord, String.valueOf(value.definition));
        }
        Map<String, String> sorted = reverse
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,LinkedHashMap::new));
        String [] requestAction = find.split(" ", 2);
        for (String name: sorted.keySet()) {
            String key = name.toString();
            String value = sorted.get(name).toString();
            if(key.equals(requestAction[0])) {
                System.out.println("   |");
                System.out.println("    "+ CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,key) + " " + value);
                System.out.println("   |");
            }
        }
    }

    public static void requestCategory(String find, Multimap<String, String[]> dictionary){
        System.out.println("   |");
        for (String[] s : dictionary.get(find)) {
            String [] arr = find.split(" ", 2);
            System.out.println("    "+CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,arr[0])+" "+s.toString());
        }
        System.out.println("   |");
    }

    public static void checkKeyWord(String find, Multimap<String, String[]> dictionary){
        String[] inputSplit = find.split(" ");
        if (inputSplit.length == 1){
            if (dictionary.containsKey(find)){
                requestCategory(find, dictionary);
            } else{
                System.out.println("   |\n" +"     <NOT FOUND> To be considered for the next release. Thank you.\n" +"   |");
                instructions();
            }
        }
        else if (inputSplit.length == 2){
            if (inputSplit[1].contains("distinct")){
                requestDistinct(find, dictionary);
            }else if (inputSplit[1].contains("reverse")){
                requestReverse(find, dictionary);
            }
            else if (inputSplit[1].equals("adjective") || inputSplit[1].equals("adverb") || inputSplit[1].equals("conjunction") || inputSplit[1].equals("interjection") || inputSplit[1].equals("noun") || inputSplit[1].equals("preposition") || inputSplit[1].equals("pronoun") || inputSplit[1].equals("verb") ){
                requestCategory(find, dictionary);
            }
            else{
                System.out.println("   |");
                System.out.println("    <The entered 2nd parameter '"+inputSplit[1]+"' is NOT a part of speech.>\n" +
                        "    <The entered 2nd parameter '"+inputSplit[1]+"' is NOT 'distinct'.>\n" +
                        "    <The entered 2nd parameter '"+inputSplit[1]+"' is NOT 'reverse'.>\n" +
                        "    <The entered 2nd parameter '"+inputSplit[1]+"' was disregarded.>\n" +
                        "    <The 2nd parameter should be a part of speech or 'distinct' or 'reverse'.>");
                System.out.println("   |");
                requestCategory(inputSplit[0],dictionary);
            }
        }
        else if (inputSplit.length == 3){
            if (inputSplit[2].contains("distinct")){
                requestDistinct(find, dictionary);
            }else if (inputSplit[2].contains("reverse")){
                requestReverse(find, dictionary);
            }
            else if (inputSplit[2].equals("adjective") || inputSplit[2].equals("adverb") || inputSplit[2].equals("conjunction") || inputSplit[2].equals("interjection") || inputSplit[2].equals("noun") || inputSplit[2].equals("preposition") || inputSplit[2].equals("pronoun") || inputSplit[2].equals("verb") ){
                requestCategory(find, dictionary);
            }
            else{
                System.out.println("   |");
                System.out.println("    <The entered 3rd parameter '"+inputSplit[2]+"' is NOT 'distinct'.>\n" +
                        "    <The entered 3rd parameter '"+inputSplit[2]+"' is NOT 'reverse'.>\n" +
                        "    <The entered 3rd parameter '"+inputSplit[2]+"' was disregarded.>\n" +
                        "    <The 3rd parameter should be 'distinct' or 'reverse'.>");
                System.out.println("   |");
                String transfer = inputSplit[0]+" "+inputSplit[1];
                checkKeyWord(transfer,dictionary);
            }
        }
        else if (inputSplit.length == 4){
            if (inputSplit[3].contains("distinct")){
                requestDistinct(find, dictionary);
            }else if (inputSplit[3].contains("reverse")){
                requestReverse(find, dictionary);
            }
            else if (inputSplit[3].equals("adjective") || inputSplit[3].equals("adverb") || inputSplit[3].equals("conjunction") || inputSplit[3].equals("interjection") || inputSplit[3].equals("noun") || inputSplit[3].equals("preposition") || inputSplit[3].equals("pronoun") || inputSplit[3].equals("verb") ){
                requestCategory(find, dictionary);
            }
            else{
                System.out.println("   |");
                System.out.println("     <The entered 4th parameter '"+inputSplit[3]+"' is NOT 'reverse'.>\n" +
                        "     <The entered 4th parameter '"+inputSplit[3]+"' was disregarded.>\n" +
                        "     <The 4th parameter should be 'reverse'.>");
                System.out.println("   |");
                String transfer = inputSplit[0]+" "+inputSplit[1]+" "+inputSplit[2];
                checkKeyWord(transfer,dictionary);
            }
        }
        else{
            System.out.println("    |\n" +
                    "     PARAMETER HOW-TO,  please enter:\n" +
                    "     1. A search key -then 2. An optional part of speech -then\n" +
                    "     3. An optional 'distinct' -then 4. An optional 'reverse'\n" +
                    "    |");
        }
    }

    public static void process() {
        String inputWord;
        int counter = 1;
        Scanner scan = new Scanner(System.in);
        loadData();

        int keyCount = 0;

        Multimap<String, String[]> enumMap = ArrayListMultimap.create();

        for (EnumDictionary value : EnumDictionary.values()) {
            keyCount++;
            String keyWord = value.getKeyWord();
            String[] partOfSpeech = value.getPartOfSpeech();
            String[] definition = value.definition;
            for (int i = 0; i < partOfSpeech.length; i++){
                String[] keyWordData = new String[2];
                keyWordData[0] = partOfSpeech[i];
                keyWordData[1] = definition[i];
                enumMap.put(keyWord, keyWordData);
            }
        }

        System.out.println("===== DICTIONARY 340 JAVA =====");
        System.out.println("----- Keywords: " + keyCount);
        System.out.println("----- Definitions: "+ enumMap.size());

        do{
            System.out.print("Search ["+ counter++ +"] : ");
            inputWord = scan.nextLine().toLowerCase();
            if(!inputWord.equals("") && !inputWord.contains("!help")){
                checkKeyWord(inputWord, enumMap);
            }else{
                instructions();
            }
        }while(!inputWord.equals("!q") && !inputWord.equals("!Q"));
        System.out.println("\n-----THANK YOU-----");
    }


    public static void main(String[] args) {
        process();
    }
}