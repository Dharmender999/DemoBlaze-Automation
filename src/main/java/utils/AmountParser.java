package utils;

public class AmountParser {
    public static int extractAmount(String confirmation) {
        for (String line : confirmation.split("\\R")) {
            if (line.trim().startsWith("Amount:")) {
                String digits = line.replaceAll("[^0-9]", "");
                if (!digits.isEmpty()) return Integer.parseInt(digits);
            }
        }
        return -1;
    }
}
