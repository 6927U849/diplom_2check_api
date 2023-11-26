package order.api;

import com.github.javafaker.Faker;

import java.util.Locale;

public class BurgersApi {

    static Faker faker = new Faker(Locale.ENGLISH);
    public static final String[] FIRST_BURGER = {
            IngredientsApi.BUN_FLUORESCENT_ROLL,
            IngredientsApi.SAUCE_BRANDED_SPACE_SAUCE,
            IngredientsApi.MAIN_MEAT_OF_IMMORTAL_CLAMS_PROTOSTOMIA,
            IngredientsApi.MAIN_BEEF_METEORITE,
    };
    public static final String[] SECOND_BURGER = {
            IngredientsApi.BUN_KRATORNAYA,
            IngredientsApi.SAUCE_TRADITIONAL_GALACTIC,
            IngredientsApi.MAIN_MARTIAN_MAGNOLIA_BIO_CUTLET,
    };

    public static final String[] THIRTH_BURGER = {
            IngredientsApi.BUN_FLUORESCENT_ROLL,
            IngredientsApi.SAUCE_SPIKED_WITH_ANTARIAN_FLATWALKER,
            IngredientsApi.MAIN_FILLET_OF_LUMINESCENT_TETRAODONTIMFORM,
            IngredientsApi.MAIN_CHEESE_WITH_ASTEROID_MOLD,
    };

    public static final String[] FOURTH_BURGER = {
            IngredientsApi.BUN_KRATORNAYA,
            IngredientsApi.SAUCE_BRANDED_SPACE_SAUCE,
            IngredientsApi.MAIN_CRISPY_MINERAL_RINGS,
            IngredientsApi.MAIN_MINI_SALAD_EXO_PLANTAGO,
    };

    public static final String[] FIFTH_BURGER = {
            IngredientsApi.BUN_FLUORESCENT_ROLL,
            IngredientsApi.SAUCE_SPICY_X,
            IngredientsApi.MAIN_FRUITS_OF_THE_FALLENIAN_TREE,
            IngredientsApi.MAIN_MARTIAN_ALPHA_SACCHARIDO_CRYSTALS,
    };

    public static final String[] EMPTY_BURGER = new String[0];

    public static final String[] INCORRECT_BURGER = {
            faker.lorem().fixedString(25),
            faker.lorem().fixedString(25)
    };
}

