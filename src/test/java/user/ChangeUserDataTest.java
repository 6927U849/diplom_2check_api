package user;

import client.api.UserClientApi;
import com.github.javafaker.Faker;
import org.junit.Test;
import utils.ThreadSleep;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import user.api.UserApi;
import user.api.UserData;
import user.api.UserGeneration;

import java.util.Locale;

public class ChangeUserDataTest {
    Faker faker = new Faker(Locale.ENGLISH);
    private final UserGeneration userGeneration = new UserGeneration();
    private final UserClientApi userClient = new UserClientApi();
    private final String expectName = faker.name().firstName();
    private final String expectEmail = faker.internet().emailAddress();
    private final String expectPassword = faker.internet().password();
    private String token;
    private UserData userData;

    @Before
    public void setUp() throws InterruptedException {
        ThreadSleep.run();
        UserApi userApi = userGeneration.getDefault();
        ValidatableResponse createUser = userClient.create(userApi);
        token = createUser.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        if (token != null) {
            userClient.delete(token);
        }
    }

    @Test
    @DisplayName("Изменение почтового ящика пользователя")
    public void changeEmail() {
        userData = UserData.from(expectEmail, null, null);

        ValidatableResponse changeEmail = userClient.changeData(userData, token);
        ValidatableResponse getData = userClient.getUserData(token);

        String actualEmail = getData.extract().path("user.email");

        Assert.assertEquals(200, changeEmail.extract().statusCode());
        Assert.assertEquals(expectEmail, actualEmail);
    }

    @Test
    @DisplayName("Изменение пароля пользователя")
    public void changePassword() {
        userData = UserData.from(null, expectPassword, null);

        ValidatableResponse changePassword = userClient.changeData(userData, token);

        Assert.assertEquals(200, changePassword.extract().statusCode());
    }

    @Test
    @DisplayName("Изменение имени пользоватлея")
    public void changeName() {
        userData = UserData.from(null, null, expectName);

        ValidatableResponse changeName = userClient.changeData(userData, token);
        ValidatableResponse getData = userClient.getUserData(token);

        String actualName = getData.extract().path("user.name");

        Assert.assertEquals(200, changeName.extract().statusCode());
        Assert.assertEquals(expectName, actualName);
    }

    @Test
    @DisplayName("Изменение всех данных о пользователе")
    public void changeAllData() {
        userData = UserData.from(expectEmail, expectPassword, expectName);

        ValidatableResponse changeName = userClient.changeData(userData, token);
        ValidatableResponse getData = userClient.getUserData(token);

        String actualEmail = getData.extract().path("user.email");
        String actualName = getData.extract().path("user.name");

        Assert.assertEquals(200, changeName.extract().statusCode());
        Assert.assertEquals(expectEmail, actualEmail);
        Assert.assertEquals(expectName, actualName);
    }

    @Test
    @DisplayName("Проверка изменения email уже зарегистрированный другим пользователем")
    public void changeToExistingEmail() {
        ValidatableResponse createSecondUser = userClient.create(userGeneration.getDefault());
        String secondToken = createSecondUser.extract().path("accessToken");

        ValidatableResponse getData = userClient.getUserData(secondToken);
        String equalEmail = getData.extract().path("user.email");
        userData = UserData.from(equalEmail, null, null);

        ValidatableResponse changeEmail = userClient.changeData(userData, token);
        userClient.delete(secondToken);

        Assert.assertEquals(403, changeEmail.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка изменения данных без авторизации")
    public void changeDataWithOutAuthorization() {
        userData = UserData.from(expectEmail, expectPassword, expectName);
        ValidatableResponse changeData = userClient.changeData(userData, "");

        Assert.assertEquals(401, changeData.extract().statusCode());
    }
}
