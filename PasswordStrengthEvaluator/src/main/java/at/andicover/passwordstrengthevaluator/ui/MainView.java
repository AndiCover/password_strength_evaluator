package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.model.PweData;
import at.andicover.passwordstrengthevaluator.util.PasswordStrengthEvaluatorUtil;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * UI and controller of the main view.
 */
@Route(MainView.PATH)
@PWA(name = "Password Strength Evaluator", shortName = "PSE")
public class MainView extends VerticalLayout {

    public static final String PATH = "";
    private static final String TEXT_PASSWORD = "Password";

    private final Binder<PweData> binder;

    public MainView() {
        this.binder = new Binder<>(PweData.class);
        this.binder.setBean(new PweData());
        binder.addValueChangeListener(e -> binder.setBean(PasswordStrengthEvaluatorUtil.evaluate(binder.getBean())));

        final PasswordField passwordField = new PasswordField(TEXT_PASSWORD, TEXT_PASSWORD.toLowerCase());
        passwordField.setId("password");
        this.binder.forField(passwordField).asRequired("Password must be set").bind(PweData::getPassword, PweData::setPassword);

        final FormLayout formLayout = new FormLayout(passwordField);
        formLayout.setWidth("250px");

        //TODO add result to UI

        add(formLayout);
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
    }
}