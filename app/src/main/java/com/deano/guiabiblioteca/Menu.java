package com.deano.guiabiblioteca;

public class Menu {
    private String title;
    private int icon;
    private String slug;

    public Menu(String title, int icon, String slug) {
        this.title = title;
        this.icon = icon;
        this.slug = slug;
    }

    public String getTitle() { return title; }
    public int getIcon() { return icon; }
    public String getSlug() { return slug; }
}