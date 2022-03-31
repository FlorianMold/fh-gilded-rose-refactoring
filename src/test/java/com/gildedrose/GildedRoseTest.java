package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemHelper.assertItemEquals;

class GildedRoseTest {

    @Test
    void sellInDate_should_decrease_butQualityCannotBeNegative() {
        GildedRose app = new GildedRose(new Item("foo", 0, 0));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item("foo", -1, 0));
    }

    @Test
    void quality_should_Decrease() {
        GildedRose app = new GildedRose(new Item("foo", 10, 10));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item("foo", 9, 9));
    }

    @Test
    void quality_should_decreasesFasterAfterSellInDateExpired() {
        GildedRose app = new GildedRose(new Item("foo", 0, 10));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item("foo", -1, 8));
    }

}
