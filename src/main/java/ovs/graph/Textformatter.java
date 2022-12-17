package ovs.graph;

public class Textformatter {

    public static String prettyPrint(String source){
        int tabCount = 0;
        boolean openingQuotes = false;

        String[] lines = source.split("\n");
        String output = "";

        for (String line : lines){


            //Handle un-indent
            for(char c : line.toCharArray())
            {
                if(c == '"'){
                    openingQuotes = !openingQuotes;
                }

                if(c == '}' && !openingQuotes){
                    tabCount--;
                }
            }

            if(line.startsWith("End") || line.startsWith("Else If") || line.startsWith("Skip If") || line.startsWith("Else"))
            {
                tabCount--;
            }

            for (int i = 0; i < tabCount; i++) {
                output += "\t";
            }

            output += line + "\n";


            //Handle Indent
            if(line.startsWith("If")  || line.startsWith("Else If") || line.startsWith("Skip If") || line.startsWith("Else"))
            {
                tabCount++;
            }

            for(char c : line.toCharArray())
            {
                if(c == '"'){
                    openingQuotes = !openingQuotes;
                }

                if(c == '{' && !openingQuotes){
                    tabCount++;
                }
            }
        }

        return output;
    }
}
