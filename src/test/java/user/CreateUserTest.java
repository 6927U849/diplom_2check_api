package user;

import client.api.UserClientApi;
import utils.ThreadSleep;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.api.UserApi;
import user.api.UserGeneration;

public class CreateUserTest {

    private final UserGeneration userGeneration = new UserGeneration();
    private final UserClientApi userClientApi = new UserClientApi();
    private String token;

    @Before
    public void waiting() throws InterruptedException {
        ThreadSleep.run();
    }

    @After
    public void cleanUp() {
        if (token != null) {
            userClientApi.delete(token);
        }
    }

    @Test
    @DisplayName("Регистрация пользователя")

    public void userCreate() {
        UserApi userApi = userGeneration.getDefault();
        ValidatableResponse createUser = userClientApi.create(userApi);

        int statusCode = createUser.extract().statusCode();

        if (statusCode == 200) {
            token =createUser.extract().path("accessToken");
        }
        Assert.assertEquals(200, statusCode);
    }




    @Test
    @DisplayName("Регистрация двух одинаковых пользователей")

    public void equalUser() {
        UserApi userApi = userGeneration.getDefault();
        ValidatableResponse createUser = userClientApi.create(userApi);
        ValidatableResponse createEqualUser = userClientApi.create(userApi);

        int statusCode = createUser.extract().statusCode();

        if (statusCode == 200) {
            token = createUser.extract().path("accessToken");
        }
        Assert.assertEquals(403, createEqualUser.extract().statusCode());
    }

    @Test
    @DisplayName("Регистрация пользователя без email")
    public void userCreateWithOutEmail() {
        UserApi userApi = userGeneration.getDefaultWithOutEmail();
        ValidatableResponse createUser = userClientApi.create(userApi);
        int statusCode = createUser.extract().statusCode();
        if (statusCode == 200) {
            token = createUser.extract().path("accessToken");
        }
        Assert.assertEquals(403, statusCode);
    }
    @Test
    @DisplayName("Регистрация пользователя без пароля")
    public void userCreateWithOutPassword() {
        UserApi userApi = userGeneration.getDefaultWithOutPassword();
        ValidatableResponse createUser = userClientApi.create(userApi);

        int statusCode = createUser.extract().statusCode();

        if (statusCode == 200) {
            token = createUser.extract().path("accessToken");
        }
        Assert.assertEquals(403, statusCode);
    }
    @Test
    @DisplayName("Регистрация пользоватлея без имени")
    public void userCreateWithOutName() {
        UserApi userApi = userGeneration.getDefaultWithOutName();
        ValidatableResponse createUser = userClientApi.create(userApi);

        int statusCode = createUser.extract().statusCode();

        if (statusCode == 200) {
            token = createUser.extract().path("accessToken");
        }
        Assert.assertEquals(403, statusCode);
    }
}

