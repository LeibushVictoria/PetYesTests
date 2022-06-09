package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ChatPage {

    @Step("Открыть чат")
    public ChatPage openChat(String name) {
        $$(".chat-dialog").findBy(text(name)).click();
        return this;
    }

    @Step("Ввести сообщение")
    public ChatPage writeMessage(String message) {
        $(".chat-form__field").setValue(message);
        return this;
    }

    @Step("Нажать на кнопку отправки сообщения")
    public ChatPage sendMessage() {
        $(".chat-form__btn.chat-form__submit").click();
        return this;
    }

    @Step("Проверить отображение сообщения")
    public ChatPage checkMessage(String message) {
        $$(".chat-message__container").findBy(text(message)).should(exist);
        return this;
    }
}
