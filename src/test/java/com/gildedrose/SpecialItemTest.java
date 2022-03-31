package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemHelper.assertItemEquals;

class SpecialItemTest {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURUS = "Sulfuras, Hand of Ragnaros";

    @Test
    void agedBrie_should_increasesInQuality() {
        GildedRose app = new GildedRose(new Item(AGED_BRIE, 2, 2));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(AGED_BRIE, 1, 3));
    }

    @Test
    void agedBrie_should_increasesInQuality_doublesWhenOff() {
        GildedRose app = new GildedRose(new Item(AGED_BRIE, 0, 2));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(AGED_BRIE, -1, 4));
    }

    @Test
    void agedBrie_should_cannotGoOver50Quality() {
        GildedRose app = new GildedRose(new Item(AGED_BRIE, 2, 50));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(AGED_BRIE, 1, 50));
    }

    @Test
     void backStagePasses_should_increasesInQuality_byOneOutside10Days() {
        GildedRose app = new GildedRose(new Item(BACKSTAGE, 20, 2));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE, 19, 3));
    }

    @Test
     void backStagePasses_should_increasesInQuality_byTwoInside10Days() {
        GildedRose app = new GildedRose(new Item(BACKSTAGE, 10, 2));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE, 9, 4));
    }

    @Test
     void backStagePasses_should_increasesInQuality_byThreeInside5Days() {
        GildedRose app = new GildedRose(new Item(BACKSTAGE, 5, 2));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE, 4, 5));
    }

    @Test
     void backStagePasses_should_increasesInQuality_goesToZeroWhenSellInExpires() {
        GildedRose app = new GildedRose(new Item(BACKSTAGE, 0, 20));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE, -1, 0));
    }

    @Test
    void sulfuras_should_neverChange() {
        GildedRose app = new GildedRose(new Item(SULFURUS, 100, 100));

        app.updateItemQuality();

        assertItemEquals(app.items[0], new Item(SULFURUS, 100, 100));
    }
}
