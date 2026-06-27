public class BoxPrinter {
    public static void header(String areaName) {
        printBox(areaName);
    }

    public static void printBox(String title, String... lines) {
        int width = visibleLength(title);

        for (String line : lines) {
            width = Math.max(width, visibleLength(line));
        }

        String border = "+" + repeat("-", width + 2) + "+";

        System.out.println();
        System.out.println(border);
        printBoxLine(title, width);
        System.out.println(border);

        for (String line : lines) {
            printBoxLine(line, width);
        }

        if (lines.length > 0) {
            System.out.println(border);
        }
    }

    private static void printBoxLine(String text, int width) {
        System.out.println("| " + text + repeat(" ", width - visibleLength(text)) + " |");
    }

    private static String repeat(String value, int times) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < times; i++) {
            result.append(value);
        }

        return result.toString();
    }

    private static int visibleLength(String text) {
        int length = 0;

        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);
            length += charWidth(codePoint);
            i += Character.charCount(codePoint);
        }

        return length;
    }

    private static int charWidth(int codePoint) {
        if (Character.getType(codePoint) == Character.OTHER_SYMBOL) {
            return 2;
        }

        return 1;
    }
}
