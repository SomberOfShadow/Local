package string.line;

public class Line {
    public static void main(String[] args) {
        String field = "test";
        String s = "{\n" +
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"text\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        System.out.println(s);

        String ss = "{\n" +
                "  \"properties\": {\n" +
                "    \"" + field + "\": {\n" +
                "      \"type\": \"text\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        System.out.println(ss);
    }
}
