package ovs.graph.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptImporter {

    public static List<Rule> importFromString(String rules){
        List<Rule> ruleList = findRules(rules);

//        for (int i = 0; i < ruleList.size(); i++) {
//            Rule rule = ruleList.get(i);
//            System.out.println(rule.ruleName);
//            System.out.println(rule.conditions);
//            System.out.println(rule.actions);
//        }

        return ruleList;
    }

    private static List<Rule> findRules(String rules) {
        List<Rule> ruleList = new ArrayList<>();
        Pattern pattern = Pattern.compile("rule\\(\"(.+?)\"\\)\\s*\\{\\s*event\\s*\\{(.+?)\\}\\s*conditions\\s*\\{(.+?)\\}\\s*actions\\s*\\{(.+?)\\}\\s*\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(rules);

        while (matcher.find()) {
            Rule rule = new Rule();
            rule.ruleName = matcher.group(1).trim();
            rule.setEvent(findStatements(matcher.group(2)));
            rule.conditions = findStatements(matcher.group(3));
            rule.actions = findStatements(matcher.group(4));
            ruleList.add(rule);
        }
        return ruleList;
    }

    private static ArrayList<String> findStatements(String block) {
        ArrayList<String> statements = new ArrayList<>();

        String[] lines = block.split(";\\s*");
        for (String line : lines) {
            String statement = line.trim();
            if (!statement.isEmpty()) {
                statements.add(statement);
            }
        }

        return statements;
    }

    public static class Rule{
        public String ruleName;
        public String eventType;
        public String teamType;
        public String playerType;
        public ArrayList<String> conditions = new ArrayList<>();
        public ArrayList<String> actions = new ArrayList<>();

        void setEvent(ArrayList<String> event) {
            // This assumes that event type is always first, team type is second, and player type is third
            // Adjust as necessary if this order can vary
            if (!event.isEmpty()) {
                eventType = event.get(0);
            }
            if (event.size() > 1) {
                teamType = event.get(1);
            }
            if (event.size() > 2) {
                playerType = event.get(2);
            }
        }
    }
}
