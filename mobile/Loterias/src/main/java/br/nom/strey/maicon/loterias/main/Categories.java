package br.nom.strey.maicon.loterias.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categories {

    public static final String MEGASENA = "mega";
    public static final String QUINA = "quina";
    public static List<Category> ITEMS = new ArrayList<Category>();
    public static Map<String, Category> ITEM_MAP = new HashMap<String, Category>();

    static {
        // Add 3 categrory items.
//        addItem(new Category(1, "Todos"));
        addItem(new Category(2, "Mega-Sena"));
        addItem(new Category(3, "Quina"));
    }

    private static void addItem(Category item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id.toString(), item);
    }

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
