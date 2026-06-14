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


    
        //Scene_question_1 components

        Pane root_question = new Pane();
        Scene scene1_question = new Scene(root_question,500, 300);
        Button createButton = new Button("Create an Account");
        Button loginButton_scene = new Button("Login Account");
        Label scene2label= new Label("Do you have an account ?");


        createButton.relocate(200, 150);
        loginButton_scene.relocate(100, 150);
        scene2label.relocate(125, 200);

        root_question.getChildren().addAll(createButton, loginButton_scene, scene2label);


        //Scene_log_into_account_2 components
        Pane root_log_account = new Pane();
        Scene scene_log_account = new Scene(root_log_account, 500, 300);
        Button login_button = new Button("Login");

        login_button.relocate(125, 200);

        root_log_account.getChildren().addAll(login_button);

        

        //Scene_create_account_3 components
        Pane root_create_account = new Pane();
        Scene scene_create_account = new Scene(root_create_account, 500, 300);
        Button account_button = new Button("Create new Account");

        root_create_account.getChildren().addAll(account_button);

        account_button.relocate(125, 200);
       
        

        //Scene_show_account_4 components
        Pane root_show_account = new Pane();
        Scene scene_show_account = new Scene(root_show_account, 500, 300);


        //Switching scenes
        createButton.setOnAction(e -> stage.setScene(scene_create_account));

        login_button.setOnAction(e -> stage.setScene(scene_show_account));

        loginButton_scene.setOnAction(e -> stage.setScene(scene_log_account));

        account_button.setOnAction(e -> stage.setScene(scene_log_account));


        stage.setTitle("Abdul's Bank");
        stage.setScene(scene1_question);

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

        root_show_account.getChildren().addAll(appTitle, depositButton, withdrawButton, exitButton, inputBox, bankLogo, AbdulHeader, moneyLogo, messageLabel);

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
