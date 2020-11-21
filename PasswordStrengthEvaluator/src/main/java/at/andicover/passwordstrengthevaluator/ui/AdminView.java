package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.model.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Text;
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
            Component component = createComponent(event.getMIMEType(), buffer.getInputStream());
            showOutput(event.getFileName(), component, output);
        });

        //TODO show weak password list info from DB
        //TODO process file and insert weak passwords into DB

        add(logoutButton, helloLabel, upload, output);

        setHorizontalComponentAlignment(Alignment.END, logoutButton);
        setHorizontalComponentAlignment(Alignment.CENTER, helloLabel);
        setHorizontalComponentAlignment(Alignment.CENTER, upload);
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

    private void showOutput(final String text, final Component content, final HasComponents outputContainer) {
        final HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }

    private Component createComponent(final String mimeType, final InputStream stream) {
        if (mimeType.startsWith("text")) {
            return createTextComponent(stream);
        }
        final Div content = new Div();
        final String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, Arrays.toString(MessageDigestUtil.sha256(stream.toString())));
        content.setText(text);
        return content;

    }

    private Component createTextComponent(final InputStream stream) {
        String text;
        try {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            text = "exception reading stream";
        }
        return new Text(text);
    }
}