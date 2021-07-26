package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {

        final String regex = "ManagedElement=1,Equipment=1,(Subrack=\\d*,Slot=\\d*$|AuxPlugInUnit=RRU-\\d*$|RbsSubrack=\\d*,RbsSlot=\\d*,AuxPlugInUnit=RU-\\d*-\\d*$)";
        final String string = "ManagedElement=1,Equipment=1,Subrack=1,Slot=1\n"
                + "ManagedElement=1,Equipment=1,Subrack=2,Slot=2\n"
                + "ManagedElement=1,Equipment=1,Subrack=2,Slot=3\n"
                + "ManagedElement=1,Equipment=1,Subrack=15,Slot=32\n"
                + "ManagedElement=1,Equipment=1,Subrack=1,Slot=2,AuxPlugInUnit=RR\n"
                + "ManagedElement=1,Equipment=1,Subrack=2,Slot=2,AuxPlugInUnit=RR\n\n"
                + "ManagedElement=1,Equipment=1,AuxPlugInUnit=RRU-1\n"
                + "ManagedElement=1,Equipment=1,AuxPlugInUnit=RRU-2\n"
                + "ManagedElement=1,Equipment=1,AuxPlugInUnit=RRU-3\n"
                + "ManagedElement=1,Equipment=1,AuxPlugInUnit=RRU-21\n\n"
                + "ManagedElement=1,Equipment=1,AuxPlugInUnit=RRU-1,AuxPlun\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=11,RbsSlot=4,AuxPlugInUnit=RU-12-4\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=1,AuxPlugInUnit=RU-1-1\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=4,AuxPlugInUnit=RU-1-4\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=1,AuxPlugInUnit=RU-1-3\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=4,AuxPlugInUnit=RU-2-4\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=4,AuxPlugInUnit=RU-12-4\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=4,AuxPlugInUnit=RU-12-41\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=1,AuxPlugInUnit=RU-1-1,RbsSubrack\n"
                + "ManagedElement=1,Equipment=1,RbsSubrack=1,RbsSlot=4,AuxPlugInUnit=RU-1-4,RbsSlot=";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }

    }
}
