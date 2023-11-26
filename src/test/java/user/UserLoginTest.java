package user;

import client.api.UserClientApi;
import org.junit.Test;
import utils.ThreadSleep;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import user.api.UserApi;
import user.api.UserGeneration;
import user.api.UserLogin;

public class UserLoginTest {
    private final UserGeneration userGeneration = new UserGeneration();
    private final UserClientApi userClientApi = new UserClientApi();
    private String token;
    private UserApi userApi ;

    @Before
    public void setUp(){
        System.out.println("before");
        try {
            ThreadSleep.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        userApi = userGeneration.getDefault();
        System.out.println("setup userApi "+ userApi);
        ValidatableResponse createUser = userClientApi.create(userApi);
        token = createUser.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        if (token != null) {
            userClientApi.delete(token);
        }
    }

    @Test
    @DisplayName("Логин с валидными логином и паролем")
    public void userLogin() {
        System.out.println(userApi);
        ValidatableResponse login = userClientApi.login(UserLogin.from(userApi));
        Assert.assertEquals(200, login.extract().statusCode());
    }

    @Test
    @DisplayName("Логин несуществующего пользователя")
    public void notExistingUser() {
        UserApi notExisting = userGeneration.getDefault();
        ValidatableResponse login = userClientApi.login(UserLogin.from(notExisting));
        Assert.assertEquals(401, login.extract().statusCode());
    }

    @Test
    @DisplayName("Логин с валидным email и неверным паролем")
    public void userLoginWithIncorrectPassword() {
        String email = userApi.getEmail();
        ValidatableResponse login = userClientApi.login(UserLogin.create(email, "eiu8whjk"));
        Assert.assertEquals(401, login.extract().statusCode());
    }
}
