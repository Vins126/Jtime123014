package it.unicam.cs.mpgc.jtime123014;

import java.time.LocalDate;
import java.util.UUID;

public class TestUtils {
    public static UUID subUUID(int i) {
        return new UUID(0, i);
    }

    public static LocalDate subDate(int i) {
        return LocalDate.of(2024, 1, 1).plusDays(i);
    }
}
