package be.kdg.programming3.domain;

public enum Breed {
    SYRIAN("Syrian",
            """
            The Syrian hamster, also known as the golden hamster, is a solitary breed growing up to 6-7 inches (15-18 cm) and weighing around 100-150 grams.
            They live for 2-3 years and need spacious cages, wheels, and a balanced diet of pellets, fresh vegetables, and occasional fruits.
            """,
            "/images/syrian_hamster.jpg"),
    DWARF_CAMPBELL_RUSSIAN("Dwarf Campbell Russian",
            """
            The Dwarf Campbell Russian hamster is a small, sociable breed, ideal for groups if introduced early.
            "They grow to about 3-4 inches (8-10 cm), weigh 30-50 grams, and live for 1.5-2 years. Their diet includes hamster mix, vegetables, and mealworms.
            """,
            "/images/dwarf_campbell_russian_hamster.jpg"),
    SIBERIAN("Siberian",
            """
            The Siberian (Winter White) hamster is known for its seasonal fur changes.
            Growing up to 3.5-4 inches (9-10 cm) and weighing 30-45 grams, they are calm and can live in pairs.
            They live for 1.5-2 years and enjoy burrowing and running.
            """,
            "/images/siberian_hamster.jpg"),
    ROBOROVSKI("Roborovski",
            """
            The Roborovski hamster is the smallest and fastest breed, growing up to 2-3 inches (5-7 cm) and weighing 20-30 grams.
            With a lifespan of 3-3.5 years, they are active and require ample space, wheels, and toys.
            """,
            "/images/roborovski_hamster.jpg"),
    CHINESE("Chinese",
            """
            The Chinese hamster is mouse-like and shy but bonds well with owners.
            They grow up to 4 inches (10 cm), weigh 30-50 grams, and live for 2-3 years.
            They require climbing accessories and a vertical cage layout.
            """,
            "/images/chinese_hamster.jpg");

    public final String properName;
    public final String description;
    public final String imageUrl;

    Breed(String properName, String description, String imageUrl) {
        this.properName = properName;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
