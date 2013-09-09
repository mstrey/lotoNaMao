package br.nom.strey.maicon.loterias.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categories {

    /**
     * An array of category items.
     */
    public static List<Category> ITEMS = new ArrayList<Category>();

    /**
     * A map of category items, by ID.
     */
    public static Map<String, Category> ITEM_MAP = new HashMap<String, Category>();

    static {
        // Add 3 categrory items.
        addItem(new Category(1, "Em aberto"));
        addItem(new Category(2, "Mega-Sena"));
        addItem(new Category(3, "Quina"));
    }

    private static void addItem(Category item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id.toString(), item);
    }

    /**
     * A category item representing a piece of content.
     */
    public static class Category {
        public Integer id;
        public String content;

        public Category(Integer id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
