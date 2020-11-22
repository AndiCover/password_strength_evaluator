package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.model.User;
import at.andicover.passwordstrengthevaluator.model.WeakPassword;
import at.andicover.passwordstrengthevaluator.pse.PasswordService;
import at.andicover.passwordstrengthevaluator.util.PasswordFileUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static at.andicover.passwordstrengthevaluator.ui.AdminView.PATH;

/**
 * UI and controller of the admin view.
 */
@Route(PATH)
public class AdminView extends VerticalLayout {

    public static final String PATH = "admin";
    private User user;

    protected static final String UI_IDENTIFIER_HELLO = AdminView.class.getSimpleName() + ".hello";
    protected static final String UI_IDENTIFIER_LOGOUT = AdminView.class.getSimpleName() + ".logout";
    protected static final String UI_IDENTIFIER_UPLOAD = AdminView.class.getSimpleName() + ".upload";
    protected static final String UI_IDENTIFIER_NOT_LOGGED_IN = AdminView.class.getSimpleName() + ".notLoggedIn";
    protected static final String UI_IDENTIFIER_BACK = AdminView.class.getSimpleName() + ".back";
    protected static final String UI_IDENTIFIER_TABLE = AdminView.class.getSimpleName() + ".table";

    /**
     * Checks if user is authenticated and initializes UI components.
     */
    public AdminView() {
        if (isAuthenticated()) {
            showAdminView();
        } else {
            showError();
        }
    }

    private void showAdminView() {
        final Label helloLabel = new Label(String.format("Hello %s!", user.getName()));
        helloLabel.setId(UI_IDENTIFIER_HELLO);

        final Button logoutButton = new Button("Logout", e -> logout());
        logoutButton.setId(UI_IDENTIFIER_LOGOUT);

        final MemoryBuffer buffer = new MemoryBuffer();
        final Upload upload = new Upload(buffer);
        final Div output = new Div();
        upload.setId(UI_IDENTIFIER_UPLOAD);

        upload.addSucceededListener(event -> {
            uploadFile(buffer.getInputStream());
        });

        Grid<WeakPassword> weakPasswordGrid = new Grid<>(WeakPassword.class);
        weakPasswordGrid.setItems(PasswordService.getWeakPasswords());
        weakPasswordGrid.setWidth("800px");
        weakPasswordGrid.setId(UI_IDENTIFIER_TABLE);

        add(logoutButton, helloLabel, upload, output, weakPasswordGrid);

        setHorizontalComponentAlignment(Alignment.END, logoutButton);
        setHorizontalComponentAlignment(Alignment.CENTER, helloLabel);
        setHorizontalComponentAlignment(Alignment.CENTER, upload);
        setHorizontalComponentAlignment(Alignment.CENTER, weakPasswordGrid);
    }

    private void showError() {
        final Label errorLabel = new Label("You are not logged in!");
        errorLabel.setId(UI_IDENTIFIER_NOT_LOGGED_IN);

        final Button loginButton = new Button("Go back", e -> UI.getCurrent().navigate(LoginView.PATH));
        loginButton.setId(UI_IDENTIFIER_BACK);

        add(errorLabel, loginButton);
        setHorizontalComponentAlignment(Alignment.CENTER, errorLabel, loginButton);
    }

    private boolean isAuthenticated() {
        user = (User) VaadinSession.getCurrent().getAttribute(User.SESSION_ATTRIBUTE);
        return user != null;
    }

    private void logout() {
        VaadinSession.getCurrent().setAttribute(User.SESSION_ATTRIBUTE, null);
        UI.getCurrent().navigate(LoginView.PATH);
    }

    private void uploadFile(final InputStream inputStream) {
        PasswordFileUtil.uploadWeakPasswords(inputStream);
    }
}