package user.api;

import io.qameta.allure.Step;
import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGeneration {

    Faker faker = new Faker(Locale.ENGLISH);
    @Step("Создание дефолтного пользователя")
    public UserApi getDefault() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String name = faker.name().username();
        return new UserApi(email, password, name);
    }


    @Step("Создание пользователя без email")
    public UserApi getDefaultWithOutEmail() {
        String password = faker.internet().password();
        String name = faker.name().username();
        return new UserApi(null, password, name);
    }


    @Step("Создание пользователя без пароля")
    public UserApi getDefaultWithOutPassword() {
        String email = faker.internet().emailAddress();
        String name = faker.name().username();
        return new UserApi(email, null, name);
    }


    @Step("Создание пользователя без имени")
    public UserApi getDefaultWithOutName() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        return new UserApi(email, password, null);
    }

}
