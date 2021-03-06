package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.model.PseData;
import at.andicover.passwordstrengthevaluator.pse.PasswordStrengthEvaluatorUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * UI and controller of the main view.
 */
@Route(MainView.PATH)
@PWA(name = "Password Strength Evaluator", shortName = "PSE")
public class MainView extends VerticalLayout {

    public static final String PATH = "";

    private static final String TITLE = "Password Strength Evaluator";
    private static final String TEXT_PASSWORD = "Password";
    private static final String TEXT_SCORE = "Score:";
    private static final String TEXT_PASSWORD_LENGTH = "PasswordLength:";
    private static final String TEXT_ENTROPY = "Entropy:";
    private static final String TEXT_UPPERCASE = "Uppercase letters:";
    private static final String TEXT_LOWERCASE = "Lowercase letters:";
    private static final String TEXT_NUMBERS = "Numbers:";
    private static final String TEXT_SYMBOLS = "Symbols:";
    private static final String TEXT_WEAK_LIST = "Is on weak password list:";
    private static final String TEXT_LOGIN = "Login";

    protected static final String UI_IDENTIFIER_PASSWORD = MainView.class.getSimpleName() + ".password";
    protected static final String UI_IDENTIFIER_SCORE = MainView.class.getSimpleName() + ".score";
    protected static final String UI_IDENTIFIER_PASSWORD_LENGTH = MainView.class.getSimpleName() + ".passwordLength";
    protected static final String UI_IDENTIFIER_ENTROPY = MainView.class.getSimpleName() + ".entropy";
    protected static final String UI_IDENTIFIER_UPPERCASE = MainView.class.getSimpleName() + ".uppercase";
    protected static final String UI_IDENTIFIER_LOWERCASE = MainView.class.getSimpleName() + ".lowercase";
    protected static final String UI_IDENTIFIER_NUMBERS = MainView.class.getSimpleName() + ".numbers";
    protected static final String UI_IDENTIFIER_SYMBOLS = MainView.class.getSimpleName() + ".symbols";
    protected static final String UI_IDENTIFIER_WEAK_PASSWORD = MainView.class.getSimpleName() + ".weakPassword";
    protected static final String UI_IDENTIFIER_LOGIN = MainView.class.getSimpleName() + ".login";

    private final Binder<PseData> binder;
    private ProgressBar scoreBar;

    public MainView() {
        this.binder = new Binder<>(PseData.class);
        this.binder.setBean(new PseData());
        binder.addValueChangeListener(e -> {
            binder.setBean(PasswordStrengthEvaluatorUtil.evaluate(binder.getBean()));
            scoreBar.setValue(binder.getBean().getScore() / 100.0);
        });

        final H1 headLabel = new H1(TITLE);

        final PasswordField passwordField = new PasswordField(TEXT_PASSWORD, TEXT_PASSWORD.toLowerCase());
        passwordField.setId(UI_IDENTIFIER_PASSWORD);
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        this.binder.forField(passwordField)
                .bind(PseData::getPassword, PseData::setPassword);

        scoreBar = new ProgressBar();
        scoreBar.setId(UI_IDENTIFIER_SCORE);

        final TextField passwordLengthLabel = new TextField();
        passwordLengthLabel.setReadOnly(true);
        passwordLengthLabel.setId(UI_IDENTIFIER_PASSWORD_LENGTH);
        this.binder.forField(passwordLengthLabel)
                .bind(x -> String.valueOf(x.getPasswordLength().getName()), null);

        final TextField entropyLabel = new TextField();
        entropyLabel.setReadOnly(true);
        entropyLabel.setId(UI_IDENTIFIER_ENTROPY);
        this.binder.forField(entropyLabel)
                .bind(x -> String.valueOf(x.getEntropy()), null);

        final TextField uppercaseLettersLabel = new TextField();
        uppercaseLettersLabel.setReadOnly(true);
        uppercaseLettersLabel.setId(UI_IDENTIFIER_UPPERCASE);
        this.binder.forField(uppercaseLettersLabel)
                .bind(x -> String.valueOf(x.getUppercaseLetters()), null);

        final TextField lowercaseLettersLabel = new TextField();
        lowercaseLettersLabel.setReadOnly(true);
        lowercaseLettersLabel.setId(UI_IDENTIFIER_LOWERCASE);
        this.binder.forField(lowercaseLettersLabel)
                .bind(x -> String.valueOf(x.getLowercaseLetters()), null);

        final TextField numbersLabel = new TextField();
        numbersLabel.setReadOnly(true);
        numbersLabel.setId(UI_IDENTIFIER_NUMBERS);
        this.binder.forField(numbersLabel)
                .bind(x -> String.valueOf(x.getNumbers()), null);

        final TextField symbolsLabel = new TextField();
        symbolsLabel.setReadOnly(true);
        symbolsLabel.setId(UI_IDENTIFIER_SYMBOLS);
        this.binder.forField(symbolsLabel)
                .bind(x -> String.valueOf(x.getSymbols()), null);

        final Checkbox weakPasswordLabel = new Checkbox();
        weakPasswordLabel.setReadOnly(true);
        weakPasswordLabel.setId(UI_IDENTIFIER_WEAK_PASSWORD);
        this.binder.forField(weakPasswordLabel)
                .bind(PseData::isOnWeakPasswordList, null);

        final FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(scoreBar, TEXT_SCORE);
        formLayout.addFormItem(passwordLengthLabel, TEXT_PASSWORD_LENGTH);
        formLayout.addFormItem(entropyLabel, TEXT_ENTROPY);
        formLayout.addFormItem(uppercaseLettersLabel, TEXT_UPPERCASE);
        formLayout.addFormItem(lowercaseLettersLabel, TEXT_LOWERCASE);
        formLayout.addFormItem(numbersLabel, TEXT_NUMBERS);
        formLayout.addFormItem(symbolsLabel, TEXT_SYMBOLS);
        formLayout.addFormItem(weakPasswordLabel, TEXT_WEAK_LIST);
        formLayout.setWidth("350px");

        final Button loginButton = new Button(TEXT_LOGIN, e -> UI.getCurrent().navigate(LoginView.PATH));
        loginButton.setId(UI_IDENTIFIER_LOGIN);

        add(headLabel, loginButton, passwordField, formLayout);
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, headLabel);
        setHorizontalComponentAlignment(Alignment.END, loginButton);
        setHorizontalComponentAlignment(Alignment.CENTER, passwordField);
        setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
    }
}