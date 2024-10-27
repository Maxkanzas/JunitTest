package data;

public enum Catalog {
    ELECTRONICS("Электроника"),
    TOYS("Игрушки"),
    FURNITURE("Мебель"),
    PFA("Товары для взрослых"),
    FLOWERS("Цветы"),
    PETSUPPLIES("Зоотовары"),
    AUTOPRODUCTS("Автотовары"),
    BOOKS("Книги"),
    JEWELRY("Ювелирные изделия");

    public final String name;
    Catalog(String name) {
        this.name = name;
    }
}
