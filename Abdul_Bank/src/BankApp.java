import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BankApp extends Application {

    private BankAccount account;
    private Label appTitle;
    private TextField inputBox;
    private Button depositButton;
    private Button withdrawButton;
    private Button exitButton;
    private ImageView bankLogo;
    private Label AbdulHeader;
    private ImageView moneyLogo;
    private Label messageLabel;
    private Button createButton;
    private Button backButton;
    private Label scene2label;
    private Button loginButton;

    private static final String PASSWORD = "Abdul_1762";

    private boolean authenticateUser() {
        int attempts = 3;
        while (attempts > 0) {
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Authentication check!");
            passwordDialog.setHeaderText("Please enter password: ");
            passwordDialog.setContentText("Password:");

            Optional<String> result = passwordDialog.showAndWait();
            if (result.isPresent() && result.get().equals(PASSWORD)) {
                return true;
            } else {
                attempts--;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Authentication Failed");
                alert.setHeaderText("Incorrect Password");
                alert.setContentText("You have " + attempts + " attempts remaining.");
                alert.showAndWait();
            }
        }
        return false;
    }

    @Override
    public void start(Stage stage) {
        if (!authenticateUser()) {
            System.out.println("Access Denied: Incorrect Password");
            System.exit(0);
        }


        //Scene1 components
        Pane root2 = new Pane();
        Scene scene2 = new Scene(root2, 500, 300);
        Button backButton = new Button("Go back");


        //Scene2 components
        Pane root1 = new Pane();
        Scene scene1 = new Scene(root1,500, 300);
        Button createButton = new Button("Create an Account");
        Label scene2label= new Label("Hello");
        root1.getChildren().addAll(createButton, scene2label);

        //Scene3 components
        Pane root3 = new Pane();
        Scene scene3 = new Scene(root3, 500, 300);


        //Switching scenes
        createButton.setOnAction(e -> stage.setScene(scene1));
        backButton.setOnAction(e -> stage.setScene(scene2));


        stage.setTitle("Abdul's Bank");
        stage.setScene(scene1);

        account = new BankAccount("Abdul");
        appTitle = new Label("Balance: $0.00");
        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");
        exitButton = new Button("Exit");
        inputBox = new TextField("0.00");
        AbdulHeader = new Label("Welcome to Abdul's Bank!");
        messageLabel = new Label("");

        bankLogo = new ImageView(new Image("bank.png"));
        bankLogo.setFitWidth(100);
        bankLogo.setFitHeight(100);
        bankLogo.relocate(10, 10);

        moneyLogo = new ImageView(new Image("money.png"));
        moneyLogo.setFitHeight(150);
        moneyLogo.setFitWidth(150);
        moneyLogo.relocate(350,150);

        root2.getChildren().addAll(appTitle, depositButton, withdrawButton, exitButton, inputBox, bankLogo, AbdulHeader, moneyLogo, messageLabel, backButton);

        appTitle.relocate(150, 125);
        appTitle.setFont(new Font("System", 20));
        inputBox.relocate(150, 100);
        depositButton.relocate(120, 180);
        withdrawButton.relocate(230, 180);
        exitButton.relocate(120, 225);
        AbdulHeader.relocate(150,10);
        AbdulHeader.setFont(new Font("System", 20));
        exitButton.setPrefWidth(50);
        messageLabel.relocate(150, 70);
        messageLabel.setFont(new Font("System", 14));

        depositButton.setOnAction(this::deposit);
        withdrawButton.setOnAction(this::withdraw);
        exitButton.setOnAction(e -> System.exit(0));
        backButton.relocate(230, 225);




        stage.show();
    }

    private void deposit(ActionEvent e){
        double amt = Double.parseDouble(inputBox.getText());
        if(amt > 0.0) {
            account.deposit(amt);
            appTitle.setText("Balance: $" + String.format("%.2f", account.getBalance()));
            messageLabel.setText(amt + " dollars deposited!");
        }
        inputBox.setText("0.00");
    }

    private void withdraw(ActionEvent e){
        double amt = Double.parseDouble(inputBox.getText());
        if (amt > 0.0 && account.getBalance() >= amt) {
            account.withdraw(amt);
            appTitle.setText("Balance: $" + String.format("%.2f", account.getBalance()));
            messageLabel.setText(amt + " dollars withdrawn!");
        } else {
            messageLabel.setText("Insufficient funds! Cannot withdraw " + amt);
        }
        inputBox.setText("0.00");
    }

    public static void main(String[]args){
        launch(args);
    }

}
