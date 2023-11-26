package user.api;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class UserLogin {

    private String email;
    private String password;

    @Step("Получение Email и пароля после регистрации")
    public static UserLogin from(UserApi userApi) {
        return new UserLogin(userApi.getEmail(), userApi.getPassword());
    }
//т.к вход в авторизованную зону из емайла и пароля

    @Step("Создание собственного Email и пароля при авторизации")
    public static UserLogin create(String email, String password) {
        return new UserLogin(email, password);
    }

//уже ранее был зарегистрирован , необходимы данные для авторизации

}
