package ru.progmatik.sarafan.domain;

/**
 * интерфейс служит для ограничения видимости полей модели с помощью аннотации вида @JsonView(Views.IdName.class) на поле
 */

public final class Views {
    public interface IdName{};
    public interface FullMessage extends IdName{};
}
