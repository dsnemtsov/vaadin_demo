package ru.dimas224.vaadin_demo.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import ru.dimas224.vaadin_demo.domain.UserEntity;
import ru.dimas224.vaadin_demo.repo.UserRepo;

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout implements KeyNotifier {
    private final UserRepo userRepo;

    private UserEntity user;

    private TextField firstName = new TextField("Имя");
    private TextField lastName = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");

    private Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<UserEntity> binder = new Binder<>(UserEntity.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public UserEditor(UserRepo userRepo) {
        this.userRepo = userRepo;

        add(lastName, firstName, patronymic, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editUser(user));
        setVisible(false);
    }

    private void delete() {
        userRepo.delete(user);
        changeHandler.onChange();
    }

    private void save() {
        userRepo.save(user);
        changeHandler.onChange();
    }

    public void editUser(UserEntity newUser) {
        if (newUser == null) {
            setVisible(false);
            return;
        }

        if (newUser.getId() != null) {
            user = userRepo.findById(newUser.getId()).orElse(newUser);
        } else {
            user = newUser;
        }

        binder.setBean(user);

        setVisible(true);

        lastName.focus();
    }
}
