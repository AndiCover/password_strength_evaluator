package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.login.UserService;
import at.andicover.passwordstrengthevaluator.model.LoginData;
import at.andicover.passwordstrengthevaluator.model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import static at.andicover.passwordstrengthevaluator.ui.LoginView.PATH;

/**
 * UI and controller of the login view.
 */
@Route(PATH)
public class LoginView extends VerticalLayout {

    public static final String PATH = "login";

    private static final String LOGIN_SUCCESSFUL = "Login successful";
    private static final String LOGIN_FAILED = "Login failed";
    private static final String TEXT_USERNAME = "Username";
    private static final String TEXT_PASSWORD = "Password";

    private final Binder<LoginData> binder;

    /**
     * Inits UI components.
     */
    public LoginView() {
        this.binder = new Binder<>(LoginData.class);
        this.binder.setBean(new LoginData());

        final TextField usernameField = new TextField(TEXT_USERNAME, TEXT_USERNAME.toLowerCase());
        usernameField.setId("username");
        this.binder.forField(usernameField).asRequired("Username must be set").bind(LoginData::getUsername, LoginData::setUsername);

        final PasswordField passwordField = new PasswordField(TEXT_PASSWORD, TEXT_PASSWORD.toLowerCase());
        passwordField.setId("password");
        this.binder.forField(passwordField).asRequired("Password must be set").bind(LoginData::getPassword, LoginData::setPassword);

        final Button loginButton = new Button("Login", e -> login());
        final FormLayout formLayout = new FormLayout(usernameField, passwordField, loginButton);
        formLayout.setWidth("250px");

        final Button backButton = new Button("Back", e -> UI.getCurrent().navigate(MainView.PATH));
        backButton.setId("back");

        add(backButton, formLayout);
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.END, backButton);
        setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
    }

    private void login() {
        if (!isValidInput()) {
            return;
        }

        final User user = UserService.login(binder.getBean());
        if (user != null) {
            Notification.show(LOGIN_SUCCESSFUL);
            VaadinSession.getCurrent().setAttribute(User.SESSION_ATTRIBUTE, user);
            UI.getCurrent().navigate(AdminView.PATH);
        } else {
            Notification.show(LOGIN_FAILED);
        }
    }

    private boolean isValidInput() {
        binder.validate();
        return binder.isValid();
    }
}