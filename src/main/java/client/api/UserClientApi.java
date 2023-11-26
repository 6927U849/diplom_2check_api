package client.api;

import utils.AnotherConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import user.api.UserApi;
import user.api.UserData;
import user.api.UserLogin;

import static io.restassured.RestAssured.given;

public class UserClientApi extends Client {

    private final String AUTH_USER = AnotherConfig.AUTH_USER;

    @Step("регистрация клиента на сайте")
    public ValidatableResponse create(UserApi userApi) {
        return given().log().all()
                .spec(getSpec())
                .body(userApi)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("авторизация клиента на сайте")
    public ValidatableResponse login(UserLogin userLogin) {
        return given().log().all()
                .spec(getSpec())
                .body(userLogin)
                .when()
                .post("/api/auth/login")
                .then();
    }

//    @Step("Выход из системы") для третьего задания в дипломе
//    public ValidatableResponse logout(String accessToken) {
//        return given().log().all()
//                .spec(getSpec())
//                .header("Authorization", accessToken)
//                .when()
//                .post("/api/auth/logout")
//                .then();
//
//
//    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {

        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(AnotherConfig.AUTH_USER)
                .then();

    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse changeData(UserData userData, String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .patch(AUTH_USER)
                .then();

    }

    @Step("Получение данных пользователя")
    public ValidatableResponse getUserData(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(AUTH_USER)
                .then();

    }

}

