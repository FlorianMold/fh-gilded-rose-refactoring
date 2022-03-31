package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemHelper {

    public static void assertItemEquals(Item actual, Item expected) {
        assertEquals(expected.name, actual.name);
        assertEquals(expected.quality, actual.quality);
        assertEquals(expected.sellIn, actual.sellIn);
    }
}
