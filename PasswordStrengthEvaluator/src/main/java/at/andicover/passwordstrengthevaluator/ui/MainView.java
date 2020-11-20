package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.model.PweData;
import at.andicover.passwordstrengthevaluator.util.PasswordStrengthEvaluatorUtil;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private static final String TEXT_SCORE = "Score";
    private static final String TEXT_PASSWORD_LENGTH = "PasswordLength";
    private static final String TEXT_ENTROPY = "Entropy";
    private static final String TEXT_UPPERCASE = "Uppercase letters";
    private static final String TEXT_LOWERCASE = "Lowercase letters";
    private static final String TEXT_NUMBERS = "Numbers";
    private static final String TEXT_SYMBOLS = "Symbols";
    private static final String TEXT_WEAK_LIST = "Is on weak password list";

    private final Binder<PweData> binder;

    public MainView() {
        this.binder = new Binder<>(PweData.class);
        this.binder.setBean(new PweData());
        binder.addValueChangeListener(e -> binder.setBean(PasswordStrengthEvaluatorUtil.evaluate(binder.getBean())));

        final Label headLabel = new Label(TITLE);

        final PasswordField passwordField = new PasswordField(TEXT_PASSWORD, TEXT_PASSWORD.toLowerCase());
        passwordField.setId("password");
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        this.binder.forField(passwordField)
                .asRequired("Password must be set")
                .bind(PweData::getPassword, PweData::setPassword);

        final TextField scoreLabel = new TextField(TEXT_SCORE);
        scoreLabel.setReadOnly(true);
        scoreLabel.setId("score");
        this.binder.forField(scoreLabel)
                .bind(x -> String.valueOf(x.getScore()), null);

        final TextField passwordLengthLabel = new TextField(TEXT_PASSWORD_LENGTH);
        passwordLengthLabel.setReadOnly(true);
        passwordLengthLabel.setId("passwordLength");
        this.binder.forField(passwordLengthLabel)
                .bind(x -> String.valueOf(x.getPasswordLength().toString()), null);

        final TextField entropyLabel = new TextField(TEXT_ENTROPY);
        entropyLabel.setReadOnly(true);
        entropyLabel.setId("entropy");
        this.binder.forField(entropyLabel)
                .bind(x -> String.valueOf(x.getEntropy()), null);

        final TextField uppercaseLettersLabel = new TextField(TEXT_UPPERCASE);
        uppercaseLettersLabel.setReadOnly(true);
        uppercaseLettersLabel.setId("uppercase");
        this.binder.forField(uppercaseLettersLabel)
                .bind(x -> String.valueOf(x.getUppercaseLetters()), null);

        final TextField lowercaseLettersLabel = new TextField(TEXT_LOWERCASE);
        lowercaseLettersLabel.setReadOnly(true);
        lowercaseLettersLabel.setId("lowercase");
        this.binder.forField(lowercaseLettersLabel)
                .bind(x -> String.valueOf(x.getLowercaseLetters()), null);

        final TextField numbersLabel = new TextField(TEXT_NUMBERS);
        numbersLabel.setReadOnly(true);
        numbersLabel.setId("numbers");
        this.binder.forField(numbersLabel)
                .bind(x -> String.valueOf(x.getNumbers()), null);

        final TextField symbolsLabel = new TextField(TEXT_SYMBOLS);
        symbolsLabel.setReadOnly(true);
        symbolsLabel.setId("symbols");
        this.binder.forField(symbolsLabel)
                .bind(x -> String.valueOf(x.getSymbols()), null);

        final TextField weakPasswordLabel = new TextField(TEXT_WEAK_LIST);
        weakPasswordLabel.setReadOnly(true);
        weakPasswordLabel.setId("weakPassword");
        this.binder.forField(weakPasswordLabel)
                .bind(x -> String.valueOf(x.isOnWeakPasswordList()), null);

        final FormLayout formLayout = new FormLayout(passwordField, scoreLabel, passwordLengthLabel, entropyLabel,
                uppercaseLettersLabel, lowercaseLettersLabel, numbersLabel, symbolsLabel, weakPasswordLabel);
        formLayout.setWidth("250px");

        add(headLabel, formLayout);
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, headLabel);
        setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
    }
}