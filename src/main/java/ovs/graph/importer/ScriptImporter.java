package ovs.graph.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptImporter {

//    public static List<Rule> importFromString(String rules){
//        List<Rule> ruleList = new ArrayList<>();
//        Pattern rulePattern = Pattern.compile("rule\\(\"(.*?)\"\\)\\s*\\{(.*?)\\}", Pattern.DOTALL);
//        Matcher ruleMatcher = rulePattern.matcher(rules);
////        System.out.println("Rule Size: " + ruleList.size());
//
//        while (ruleMatcher.find()) {
//            String ruleName = ruleMatcher.group(1).trim();
//            String ruleContent = ruleMatcher.group(2).trim();
//
//            Rule rule = new Rule(ruleName);
////            System.out.println(ruleName);
////            System.out.println(ruleContent);
//
//            // Extract conditions and actions using regular expressions
//            Pattern eventPattern = Pattern.compile("event\\s*\\{(.*?)\\}", Pattern.DOTALL);
//            Matcher eventMatcher = eventPattern.matcher(ruleContent);
//            if (eventMatcher.find()) {
//                String eventContent = eventMatcher.group(1).trim();
////                System.out.println(eventContent);
//                String[] conditions = eventContent.split(";");
//                for (String condition : conditions) {
//                    rule.conditions.add(condition.trim());
//                }
//            }
//
//            Pattern actionsPattern = Pattern.compile("actions\\s*\\{(.*?)\\}", Pattern.DOTALL);
//            Matcher actionsMatcher = actionsPattern.matcher(ruleContent);
//            if (actionsMatcher.find()) {
//                String actionsContent = actionsMatcher.group(1).trim();
//                String[] actions = actionsContent.split(";");
//                for (String action : actions) {
//                    rule.actions.add(action.trim());
//                }
//            }
//
//            ruleList.add(rule);
//        }
//
//        return ruleList;
//    }

//    private static List<Rule> findRules(String rules) {
//        List<Rule> ruleList = new ArrayList<>();
//        Pattern pattern = Pattern.compile("rule\\(\"(.+?)\"\\)\\s*\\{\\s*event\\s*\\{(.+?)\\}\\s*conditions\\s*\\{(.+?)\\}\\s*actions\\s*\\{(.+?)\\}\\s*\\}", Pattern.DOTALL);
//        Matcher matcher = pattern.matcher(rules);
//
//        while (matcher.find()) {
//            Rule rule = new Rule();
//            rule.ruleName = matcher.group(1).trim();
//            rule.setEvent(findStatements(matcher.group(2)));
//            rule.conditions = findStatements(matcher.group(3));
//            rule.actions = findStatements(matcher.group(4));
//            ruleList.add(rule);
//        }
//        return ruleList;
//    }

//    private static ArrayList<String> findStatements(String block) {
//        ArrayList<String> statements = new ArrayList<>();
//
//        String[] lines = block.split(";\\s*");
//        for (String line : lines) {
//            String statement = line.trim();
//            if (!statement.isEmpty()) {
//                statements.add(statement);
//            }
//        }
//
//        return statements;
//    }

    public static String[] getActionLines(LineType type, String rule){
        if(!rule.contains(type.name())){
            return new String[0];
        }
        String actionsBlock = rule.split(type.name() + "\\s*\\{")[1].split("\\}")[0].trim();
        String[] actionLines = actionsBlock.split(";");

        // Trim each action line
        for (int i = 0; i < actionLines.length; i++) {
            actionLines[i] = actionLines[i].trim();
        }

        return actionLines;
    }

    /**
     * gets the name of the action for example
     * Create Effect (All Players(All Teams), Ring, Color(Blue), Global.RingLocation, Global.RingRadius, Visible To Position And Radius); will return Create Effect
     * or All Players(All Teams) will return All Players
     * @param action
     * @return
     */
    public static String getActionName(String action){
        return action.split("\\(")[0];
    }

    public static String[] splitRules(String importText){
        String[] rulesList = importText.split("rule\\(");
        String[] result = new String[rulesList.length - 1];

        for (int i = 1; i < rulesList.length; i++) {
            result[i - 1] = "rule(" + rulesList[i].trim();
        }

        return result;
    }

    public static String getRuleName(String rule) {
        return rule.split("\n")[0].split("\"")[1];
    }

    public static String[] getActionArguments(String actionLine) {
        if(actionLine.startsWith("Global.")){
            String variable = actionLine.split("=")[0].split("\\.")[1].trim();
            String value = actionLine.split("=")[1].trim();
            return new String[]{variable, value};
        }else if(actionLine.contains("=")){
            String player = actionLine.split("=")[0].split("\\.")[0].trim();;
            String variable = actionLine.split("=")[0].split("\\.")[1].trim();
            String value = actionLine.split("=")[1].trim();
            return new String[]{player, variable, value};
        }
        int startIndex = actionLine.indexOf('(');
        int endIndex = actionLine.lastIndexOf(')');
        if (startIndex != -1 && endIndex != -1) {
            String arguments = actionLine.substring(startIndex + 1, endIndex);
            return splitArguments(arguments);
        } else {
            return new String[0]; // Return an empty array if no arguments are found
        }
    }

    private static String[] splitArguments(String arguments) {
        List<String> result = new ArrayList<>();
        StringBuilder currentArgument = new StringBuilder();
        int nestedParens = 0;

        for (char c : arguments.toCharArray()) {
            if (c == ',' && nestedParens == 0) {
                result.add(currentArgument.toString().trim());
                currentArgument.setLength(0);
            } else {
                if (c == '(') {
                    nestedParens++;
                } else if (c == ')') {
                    nestedParens--;
                }
                currentArgument.append(c);
            }
        }
        result.add(currentArgument.toString().trim()); // Add the last argument

        return result.toArray(new String[0]);
    }

    public enum LineType{
        event, conditions, actions
    }

//    public static class Rule{
//        public String ruleName;
//        public String eventType;
//        public String teamType;
//        public String playerType;
//        public ArrayList<String> conditions = new ArrayList<>();
//        public ArrayList<String> actions = new ArrayList<>();
//
//        public Rule() {
//        }
//
//        public Rule(String ruleName) {
//            this.ruleName = ruleName;
//        }
//
//        void setEvent(ArrayList<String> event) {
//            // This assumes that event type is always first, team type is second, and player type is third
//            // Adjust as necessary if this order can vary
//            if (!event.isEmpty()) {
//                eventType = event.get(0);
//            }
//            if (event.size() > 1) {
//                teamType = event.get(1);
//            }
//            if (event.size() > 2) {
//                playerType = event.get(2);
//            }
//        }
//    }
}
