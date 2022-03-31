package com.gildedrose;

class GildedRose {
    public final Item[] items;

    public GildedRose(Item... items) {
        this.items = items;
    }

    /**
     * Maximum quality of products.
     */
    private static final int MAXIMUM_QUALITY = 50;
    /**
     * Minimum quality of products.
     */
    private static final int MINIMUM_QUALITY = 0;
    /**
     * Sell in decrease on every check.
     */
    private static final int DEFAULT_SELL_IN_DECREASE = -1;
    /**
     * Default quality decrease.
     */
    private static final int DEFAULT_QUALITY_DECREASE = -1;
    /**
     * Default quality increase.
     */
    private static final int DEFAULT_QUALITY_INCREASE = 1;

    /**
     * Special change of quality when the sell-in reaches a specific value.
     */
    private static final int BACKSTAGE_GREAT_CHANGE_DAYS = 10;
    /**
     * Special change of quality when the sell-in reaches a specific value.
     */
    private static final int BACKSTAGE_SMALL_CHANGE_DAYS = 5;

    /**
     * Special Quality increases for backstage pass, which has a sell-in < BACKSTAGE_GREAT_CHANGE_DAYS.
     */
    private static final int BACKSTAGE_SMALL_QUALITY_INCREASE = 2;
    /**
     * Special Quality increases for backstage pass, which has a sell-in < BACKSTAGE_SMALL_CHANGE_DAYS.
     */
    private static final int BACKSTAGE_GREAT_QUALITY_INCREASE = 3;

    /**
     * Degrades the quality of every item in the store.
     */
    public void updateItemQuality() {
        for (Item item : items) {
            this.degradeItemQuality(item);
        }
    }

    /**
     * Degrades the item quality for the given item.
     * Sulfuras do not get changed.
     *
     * @param item Item to change
     */
    private void degradeItemQuality(Item item) {
        if (isSulfuras(item)) {
            return;
        }

        this.updateQualityFor(item);
        this.decreaseSellIn(item);

        if (item.sellIn < 0) {
            this.reduceQualityOfExpiredItems(item);
        }
    }

    /**
     * Decreases the sell-in value of the item by DEFAULT_SELL_IN_DECREASE.
     *
     * @param item Item where the sell-in is changed.
     */
    private void decreaseSellIn(Item item) {
        item.sellIn += DEFAULT_SELL_IN_DECREASE;
    }

    /**
     * Decreases the quality of the given item. Special items are handled differently.
     * Backstage passes see an increase in the quality as older they become.
     *
     * @param item Item where the quality is changed.
     */
    private void updateQualityFor(Item item) {
        if (isBackstagePass(item) && item.sellIn <= BACKSTAGE_GREAT_CHANGE_DAYS) {
            this.updateItemQuality(item, item.sellIn <= BACKSTAGE_SMALL_CHANGE_DAYS ? BACKSTAGE_GREAT_QUALITY_INCREASE : BACKSTAGE_SMALL_QUALITY_INCREASE);
            return;
        }

        this.updateItemQuality(item, isSpecialItem(item) ? DEFAULT_QUALITY_INCREASE : DEFAULT_QUALITY_DECREASE);
    }

    /**
     * Reduces the quality of the given expired item.
     * Expired backstage-passes receive MINIMUM quality.
     *
     * @param item Item where the quality is changed based on the sell-in
     */
    private void reduceQualityOfExpiredItems(Item item) {
        if (isBackstagePass(item)) {
            item.quality = MINIMUM_QUALITY;
            return;
        }

        if (isAgedBrie(item)) {
            this.updateItemQuality(item, DEFAULT_QUALITY_INCREASE);
            return;
        }

        this.updateItemQuality(item, DEFAULT_QUALITY_DECREASE);
    }

    /**
     * Updates the quantity of an item by a given change. The value of the item can only be
     * in the interval of MINIMUM_QUALITY <= quality <= MAXIMUM_QUALITY
     *
     * @param item   Item to change.
     * @param change Change which is applied to the item
     */
    private void updateItemQuality(Item item, int change) {
        item.quality = Math.max(Math.min(MAXIMUM_QUALITY, item.quality + change), MINIMUM_QUALITY);
    }

    /**
     * Whether the item is an aged brie.
     *
     * @param item Item to check.
     */
    private boolean isAgedBrie(Item item) {
        final String AGED_BRIE_TEXT = "Aged Brie";
        return item.name.equals(AGED_BRIE_TEXT);
    }

    /**
     * Whether the item is a sulfuras.
     *
     * @param item Item to check.
     */
    private boolean isSulfuras(Item item) {
        final String SULFURAS_TEXT = "Sulfuras, Hand of Ragnaros";
        return item.name.equals(SULFURAS_TEXT);
    }

    /**
     * Whether the item is a backstage.
     *
     * @param item Item to check.
     */
    private boolean isBackstagePass(Item item) {
        final String BACKSTAGE_TEXT = "Backstage passes to a TAFKAL80ETC concert";
        return item.name.equals(BACKSTAGE_TEXT);
    }

    /**
     * Whether the item is either an aged brie, sulfuras or a backstage pass.
     *
     * @param item Item to check.
     */
    private boolean isSpecialItem(Item item) {
        return isAgedBrie(item) || isSulfuras(item) || isBackstagePass(item);
    }

}
