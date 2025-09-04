package com.rifugio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {
    @Test
    void mainMethodShouldRunWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}