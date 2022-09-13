package ch.zuehlke.hacking.file;

import com.google.common.base.Strings;

import java.util.List;

class InputFileValidator {
    public void validate(List<String> lines) {
        lines.stream().forEach(line -> {
            if (!isYear(line)) {
                validateCommand(line);
            }
        });
    }

    private boolean isYear(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void validateCommand(String command) {
        if (Strings.isNullOrEmpty(command)) {
            return;
        }

        String[] commandParts = command.split("\\s+");

        if (commandParts.length < 2) {
            throw new IllegalStateException("Die Operation \"" + command + "\" hat nicht genügend Attribute!");
        }

        checkOperationKeyWords(commandParts[0]);
        checkBuyCommand(commandParts);
        checkSellAndRenovationCommand(commandParts);
    }

    private void checkOperationKeyWords(String operationKeyWord) {
        if (!operationKeyWord.equals("KAUFE")
                && !operationKeyWord.equals("VERKAUFE")
                && !operationKeyWord.equals("RENOVIERE")) {
            throw new IllegalStateException("Die Operation \"" + operationKeyWord + "\" existiert nicht!");
        }
    }

    private void checkBuyCommand(String[] commandParts) {
        if (!commandParts[0].equals("KAUFE")) {
            return;
        }

        if (commandParts.length != 3) {
            throw new IllegalStateException("Die Operation \"" + String.join(" ", commandParts) + "\" hat die falsche Anzahl Attribute!");
        }

        try {
            int hypoVolume = Integer.parseInt(commandParts[2]);

            if (hypoVolume < 0 || hypoVolume > 50) {
                throw new IllegalStateException("Das Hypothekarvolumen der Operation \"" + String.join(" ", commandParts) + "\" ist ausserhalb des Range (0-50)!");
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Die Operation \"" + String.join(" ", commandParts) + "\" hat das falsche Format beim letzten Attribut! Erwartet wird ein Integer.");
        }
    }

    private void checkSellAndRenovationCommand(String[] commandParts) {
        if (commandParts[0].equals("KAUFE")) {
            return;
        }

        if (commandParts.length != 2) {
            throw new IllegalStateException("Die Operation \"" + String.join(" ", commandParts) + "\" hat die falsche Anzahl Attribute!");
        }
    }
}
