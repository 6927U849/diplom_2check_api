package user.api;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserData {
    private String email;
    private String password;
    private String name;

    @Step("Передача данных пользователя: email, пароль, имя")
    public static UserData from(String email, String password, String name) {
        return new UserData(email, password, name);
    }
}

