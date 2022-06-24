package ru.dimas224.vaadin_demo.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import ru.dimas224.vaadin_demo.components.UserEditor;
import ru.dimas224.vaadin_demo.domain.User;
import ru.dimas224.vaadin_demo.repo.UserRepo;

@Route
public class MainView extends VerticalLayout {
    private final UserRepo userRepo;

    private final Grid<User> grid = new Grid<>(User.class, false);

    private final TextField filter = new TextField("", "Поиск...");
    private final Button addNewBtn = new Button("Добавить");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBtn);

    private final UserEditor editor;

    public MainView(UserRepo userRepo, UserEditor editor) {
        this.userRepo = userRepo;
        this.editor = editor;

        initializeGrid();

        add(toolbar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showUsers(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> editor.editUser(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editUser(new User()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showUsers(filter.getValue());
        });

        showUsers("");
    }

    private void initializeGrid() {
        grid.addColumn(User::getId).setHeader("ID").setSortable(true);
        grid.addColumn(User::getLastName).setHeader("Фамилия").setSortable(true);
        grid.addColumn(User::getFirstName).setHeader("Имя").setSortable(true);
        grid.addColumn(User::getPatronymic).setHeader("Отчество").setSortable(true);

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void showUsers(String name) {
        if (name.isEmpty()) {
            grid.setItems(userRepo.findAll());
        } else {
            grid.setItems(userRepo.findByName(name));
        }



    }
}
