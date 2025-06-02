package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatadores {
    public static String formatarData(LocalDateTime data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
