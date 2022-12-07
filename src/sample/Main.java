package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project2.Queue;
import project2.Shares;
import project2.Stack;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class Main extends Application {

    File fileShares;
    File fileDailyPrice;
    Queue queueSellOld = new Queue();
    ///////////////////////
    Stack stackSellNew = new Stack();
    Stack stackDailyPrice = new Stack();
    Stack tempStack = new Stack();
    Stack subStack = new Stack();
    int flag = 0; //1 = queue , 2 = stack

    double capitalGain = 0;


    int flagOldNew = 0; //flag = 1 -> old ,,, flag = 2 ->new

    @Override
    public void start(Stage stageFirst) throws Exception {

        //shadow for button sell old and new
        DropShadow shadow = new DropShadow(8, Color.web("fff2cc"));

        Stage stage0 = new Stage();
        Group pageFileChooser = new Group();
        Scene sceneFileChooser = new Scene(pageFileChooser, 800, 600, Color.web("323e4f"));


        //btn browse for file shares
        Button btnBrowseShares = new Button();
        btnBrowseShares.setText("Browse");
        btnBrowseShares.setMaxHeight(30);
        btnBrowseShares.setMinHeight(30);
        btnBrowseShares.setMinWidth(90);
        btnBrowseShares.setMaxWidth(90);
        btnBrowseShares.setTextFill(Color.BLACK);
        btnBrowseShares.setBackground(new Background((new BackgroundFill(Color.web("cfcdcd"), new CornerRadii(20), Insets.EMPTY))));
        btnBrowseShares.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        //Text filed for file shares path
        TextField txtFileShares = new TextField();
        txtFileShares.setPrefColumnCount(35);
        txtFileShares.setPromptText("Shares file path");
        txtFileShares.setBackground(new Background((new BackgroundFill(Color.BLACK, new CornerRadii(20), Insets.EMPTY))));
        txtFileShares.setStyle("-fx-text-inner-color: #989190");
        txtFileShares.setEditable(false);

        //Hbox for btn browse shares and txtFileShares
        HBox hBoxShares = new HBox(btnBrowseShares, txtFileShares);
        hBoxShares.setSpacing(40);

        //btn browse for file daily price
        Button btnBrowseDaily = new Button();
        btnBrowseDaily.setText("Browse");
        btnBrowseDaily.setMaxHeight(30);
        btnBrowseDaily.setMinHeight(30);
        btnBrowseDaily.setMinWidth(90);
        btnBrowseDaily.setMaxWidth(90);
        btnBrowseDaily.setTextFill(Color.BLACK);
        btnBrowseDaily.setBackground(new Background((new BackgroundFill(Color.web("cfcdcd"), new CornerRadii(20), Insets.EMPTY))));
        btnBrowseDaily.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        //Text filed for file daily price path
        TextField txtFileDaily = new TextField();
        txtFileDaily.setPrefColumnCount(35);
        txtFileDaily.setPromptText("Daily price file path");
        txtFileDaily.setBackground(new Background((new BackgroundFill(Color.BLACK, new CornerRadii(20), Insets.EMPTY))));
        txtFileDaily.setStyle("-fx-text-inner-color: #989190");
        txtFileDaily.setEditable(false);
        //txtFileDaily.getStylesheets().add("text-field");

        //Hbox for btnBrowse file daily price and txt daily price
        HBox hBoxDaily = new HBox(btnBrowseDaily, txtFileDaily);
        hBoxDaily.setSpacing(40);

        //VBox for file chooser
        VBox vBoxChooser = new VBox(hBoxShares, hBoxDaily);
        vBoxChooser.setLayoutX(138);
        vBoxChooser.setLayoutY(240);
        vBoxChooser.setSpacing(40);
        pageFileChooser.getChildren().add(vBoxChooser);

        //event handler for btn browse file shares when mouse entered-> shadow
        btnBrowseShares.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnBrowseShares.setEffect(shadow);
        });

        //event handler for btn browse file daily price  when mouse entered-> shadow
        btnBrowseDaily.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnBrowseDaily.setEffect(shadow);
        });

        //event handler for btn browse file shares when mouse exit-> shadow
        btnBrowseShares.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnBrowseShares.setEffect(null);
        });

        //event handler for btn browse file daily price when mouse exit-> shadow
        btnBrowseDaily.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnBrowseDaily.setEffect(null);
        });

        /*
        Separator horizontal_separator = new Separator(Orientation.HORIZONTAL);
        horizontal_separator.setTranslateX(164);
        horizontal_separator.setTranslateY(390);
        horizontal_separator.setPrefWidth(488);
        horizontal_separator.setStyle("-fx-border-color:black; -fx-border-width:0");
        //horizontal_separator.setPrefHeight(20);
        pageFileChooser.getChildren().add(horizontal_separator);

         */

        //Button for read file daily and shares
        Button btnReadBoth = new Button();
        btnReadBoth.setText("Read shares and daily price");
        btnReadBoth.setMaxHeight(40);
        btnReadBoth.setMinHeight(40);
        btnReadBoth.setMinWidth(290);
        btnReadBoth.setMaxWidth(290);
        btnReadBoth.setLayoutX(265);
        btnReadBoth.setLayoutY(465);
        btnReadBoth.setTextFill(Color.web("fff2cc"));
        btnReadBoth.setBackground(new Background((new BackgroundFill(Color.BLACK, new CornerRadii(20), Insets.EMPTY))));
        btnReadBoth.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        pageFileChooser.getChildren().add(btnReadBoth);

        //event handler for btn read files when mouse exit-> shadow
        btnReadBoth.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnReadBoth.setEffect(shadow);
        });

        //event handler for btn read files when mouse exit-> shadow
        btnReadBoth.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnReadBoth.setEffect(null);
        });


        //btn creat for file share
        Button btnCreatFileShare = new Button();
        btnCreatFileShare.setText("Creat new file shares");
        btnCreatFileShare.setMaxHeight(40);
        btnCreatFileShare.setMinHeight(40);
        btnCreatFileShare.setMinWidth(290);
        btnCreatFileShare.setMaxWidth(290);
        btnCreatFileShare.setLayoutX(265);
        btnCreatFileShare.setLayoutY(395);
        btnCreatFileShare.setTextFill(Color.web("fff2cc"));
        btnCreatFileShare.setBackground(new Background((new BackgroundFill(Color.BLACK, new CornerRadii(20), Insets.EMPTY))));
        btnCreatFileShare.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        pageFileChooser.getChildren().add(btnCreatFileShare);


        //event handler for btn creat when mouse exit-> shadow
        btnCreatFileShare.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnCreatFileShare.setEffect(shadow);
        });

        //event handler for btn creat when mouse exit-> shadow
        btnCreatFileShare.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnCreatFileShare.setEffect(null);
        });

        //Image for Welcome page - MOHAMMAD COMPANY -
        InputStream streamStock = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\StockMarketChoose.png");
        Image imageStock = new Image(streamStock);
        ImageView imageViewStock = new ImageView();
        imageViewStock.setImage(imageStock);
        imageViewStock.setX(190);
        imageViewStock.setY(56);
        pageFileChooser.getChildren().add(imageViewStock);

        // button browse shares set on action
        btnBrowseShares.setOnAction(actionEvent -> {

            FileChooser fileChooserShares = new FileChooser();
            fileChooserShares.setTitle("Select shares file .txt");
            fileChooserShares.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("Text Document", "*.java")
            );

            File selectedFile = fileChooserShares.showOpenDialog(stage0);
            if (String.valueOf(selectedFile).equals("null")) {
                return;
            } else {
                txtFileShares.setText(selectedFile.toString());
            }


        });

        // button browse daily price set on action
        btnBrowseDaily.setOnAction(actionEvent -> {

            FileChooser fileChooserShares = new FileChooser();
            fileChooserShares.setTitle("Select daily price file .txt");
            fileChooserShares.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("Text Document", "*.java"),
                    new FileChooser.ExtensionFilter("TXT files (' txt')", "*.txt")

            );

            File selectedFile = fileChooserShares.showOpenDialog(stage0);
            if (String.valueOf(selectedFile).equals("null")) {
                return;
            } else {
                txtFileDaily.setText(selectedFile.toString());
            }


        });

        //btn creat set on action
        btnCreatFileShare.setOnAction(actionEvent -> {

            try {

                String print = "";

                FileChooser fileChooser1 = new FileChooser();
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("TXT files (' txt')", "txt");
                fileChooser1.getExtensionFilters().add(ext);

                File file2 = fileChooser1.showSaveDialog(stage0);

                PrintWriter writer = new PrintWriter(file2);
                writer.print(print);
                writer.close();


                Alert alertCreat = new Alert(Alert.AlertType.INFORMATION);
                alertCreat.setTitle("Information");
                alertCreat.setHeaderText(null);
                alertCreat.setContentText("File was created ...");
                alertCreat.showAndWait();

            } catch (FileNotFoundException e) {
                Alert alertCreat = new Alert(Alert.AlertType.ERROR);
                alertCreat.setTitle("Error");
                alertCreat.setHeaderText(null);
                alertCreat.setContentText("File not found");
                alertCreat.showAndWait();
            }

        });

        Stage stageSecond = new Stage();
        //btn read set on action
        btnReadBoth.setOnAction(actionEvent -> {

            try {

                if (txtFileShares.getText().isEmpty() || txtFileShares.getText().equals("null")) {
                    throw new IllegalArgumentException("Pleas choose a share file");
                } else if (txtFileDaily.getText().isEmpty() || txtFileDaily.getText().equals("null")) {
                    throw new IllegalArgumentException("Pleas choose a daily price file");
                } else {


                    fileDailyPrice = new File(txtFileDaily.getText());
                    fileShares = new File(txtFileShares.getText());

                    Alert alertCreat = new Alert(Alert.AlertType.INFORMATION);
                    alertCreat.setTitle("Information");
                    alertCreat.setHeaderText(null);
                    alertCreat.setContentText("The path for Shares and daily price files was saved ...");
                    alertCreat.showAndWait();

                    Scanner scanShares = new Scanner(fileShares);
                    while (scanShares.hasNextLine()) {

                        String line = scanShares.nextLine();

                        //System.out.println(line.isEmpty());


                        while (line.isEmpty()) {
                            line = scanShares.nextLine();
                        }


                        int q1 = line.indexOf(',');
                        int numberOfShares = Integer.parseInt(line.substring(0, q1).trim());
                        int q4 = line.lastIndexOf(',');
                        String dateOfPurchase = line.substring(q4 + 1, line.length()).trim();
                        String middle = line.substring(q1 + 1, q4);
                        int qMiddle = middle.indexOf(',');
                        double priceOfShare = Double.parseDouble(middle.substring(0, qMiddle).trim());
                        String nameOfCompany = middle.substring(qMiddle + 1, middle.length()).trim();

                        queueSellOld.EnQeue(new Shares(numberOfShares, priceOfShare, nameOfCompany, dateOfPurchase));
                        //shareList.add(new SharesFX(nameOfCompany, String.valueOf(numberOfShares), String.valueOf(priceOfShare), dateOfPurchase));
                        flag = 1;


                    }

                    Scanner scanShares2 = new Scanner(fileShares);
                    while (scanShares2.hasNextLine()) {

                        String line = scanShares2.nextLine();

                        //System.out.println(line.isEmpty());


                        while (line.isEmpty()) {
                            line = scanShares2.nextLine();
                        }


                        int q1 = line.indexOf(',');
                        int numberOfShares = Integer.parseInt(line.substring(0, q1).trim());
                        int q4 = line.lastIndexOf(',');
                        String dateOfPurchase = line.substring(q4 + 1, line.length()).trim();
                        String middle = line.substring(q1 + 1, q4);
                        int qMiddle = middle.indexOf(',');
                        double priceOfShare = Double.parseDouble(middle.substring(0, qMiddle).trim());
                        String nameOfCompany = middle.substring(qMiddle + 1, middle.length()).trim();

                        stackSellNew.push(new Shares(numberOfShares, priceOfShare, nameOfCompany, dateOfPurchase));
                        //shareList.add(new SharesFX(nameOfCompany, String.valueOf(numberOfShares), String.valueOf(priceOfShare), dateOfPurchase));
                        stackSellNew.show();
                        flag = 2;


                    }

                /*
                Scanner scanDailyPrice = new Scanner(fileDailyPrice);
                while ( scanDailyPrice.hasNextLine()){

                    String line = scanDailyPrice.nextLine();
                    int q1 = line.indexOf(',');
                    String companyName = line.substring(0,q1).trim();
                    int q2 = line.lastIndexOf(',');
                    double dailyPrice = Double.parseDouble(line.substring(q2+1,line.length()).trim());

                    queueDailyPrice.EnQeue(new DailyPrice(companyName,dailyPrice));

                }

                 */

                    flag = 1;
                    stage0.close();
                    stageSecond.show();

                    stackSellNew.show();
                    System.out.println("---------------------------");
                    queueSellOld.show();

                }
            } catch (FileNotFoundException e4) {
                Alert alertCreat = new Alert(Alert.AlertType.WARNING);
                alertCreat.setTitle("Warning");
                alertCreat.setHeaderText(null);
                alertCreat.setContentText(e4.getMessage());
                alertCreat.showAndWait();
            } catch (IllegalArgumentException e2) {

                Alert alertCreat = new Alert(Alert.AlertType.WARNING);
                alertCreat.setTitle("Warning");
                alertCreat.setHeaderText(null);
                alertCreat.setContentText(e2.getMessage());
                alertCreat.showAndWait();


            }

        });


        stage0.setScene(sceneFileChooser);
        stage0.setTitle("File choose");
        stage0.setMaxWidth(800);
        stage0.setMaxHeight(600);
        stage0.show();

        sceneFileChooser.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        ///////////////////////////////////////////////////////////////

        Group pageChooseStackOrQueue = new Group();
        Scene scenePageChoose = new Scene(pageChooseStackOrQueue, 1013, 800);

        //scenePageChoose.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        //Image for Welcome page - MOHAMMAD COMPANY -
        InputStream streamWelcome = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\WelcomePage.png");
        Image imageWelcome = new Image(streamWelcome);
        ImageView imageViewWeelcome = new ImageView();
        imageViewWeelcome.setImage(imageWelcome);
        pageChooseStackOrQueue.getChildren().add(imageViewWeelcome);
        //---------------------------------------------------------

        //button for sell new and old with vbox

        //Button for Sell old
        Button btnSellOld = new Button();
        btnSellOld.setText("Sell old shares");
        btnSellOld.setMaxHeight(50);
        btnSellOld.setMinHeight(50);
        btnSellOld.setMinWidth(250);
        btnSellOld.setMaxWidth(250);
        btnSellOld.setTextFill(Color.BLACK);
        btnSellOld.setBackground(new Background((new BackgroundFill(Color.web("ff9900"), new CornerRadii(4), Insets.EMPTY))));
        btnSellOld.setFont(Font.font("Verdana", FontWeight.BOLD, 17));


        //Button for sell new
        Button btnSellNew = new Button();
        btnSellNew.setText("Sell new shares");
        btnSellNew.setMaxHeight(50);
        btnSellNew.setMinHeight(50);
        btnSellNew.setMinWidth(250);
        btnSellNew.setMaxWidth(250);
        btnSellNew.setTextFill(Color.BLACK);
        btnSellNew.setBackground(new Background((new BackgroundFill(Color.web("ff9900"), new CornerRadii(4), Insets.EMPTY))));
        btnSellNew.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

        //Vbox for btnSellOldNew
        VBox VBoxSellOldNew = new VBox(btnSellOld, btnSellNew);
        VBoxSellOldNew.setLayoutX(580);
        VBoxSellOldNew.setLayoutY(555);
        VBoxSellOldNew.setSpacing(50);
        pageChooseStackOrQueue.getChildren().add(VBoxSellOldNew);
        //--------------------------------------------------------


        //event handler for btn sell old when mouse entered-> shadow
        btnSellOld.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSellOld.setEffect(shadow);
        });

        //event handler for btn sell new when mouse entered-> shadow
        btnSellNew.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSellNew.setEffect(shadow);
        });

        //event handler for btn sell old when mouse exit-> shadow
        btnSellOld.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSellOld.setEffect(null);
        });

        //event handler for btn sell new when mouse exit-> shadow
        btnSellNew.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSellNew.setEffect(null);
        });
        //--------------------------------------------------------

        stageFirst.setScene(scenePageChoose);
        stageFirst.setTitle("File choose");
        stageFirst.setMaxWidth(1013);
        stageFirst.setMaxHeight(800);
        stageFirst.show();

        //--------------------------------------------------------

        //new stage for stage operation page 2
        //Stage stageSecond = new Stage();
        Group pageOperation = new Group();
        Scene scenePageOperation = new Scene(pageOperation, 1300, 800);
        scenePageOperation.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        //--------------------------------------------------

        //toggle group for radio buttons new and old
        ToggleGroup tg = new ToggleGroup();

        //radio button for old share
        RadioButton radioButtonOld = new RadioButton();
        radioButtonOld.setMaxWidth(5);
        radioButtonOld.setMinWidth(5);
        radioButtonOld.setMaxHeight(5);
        radioButtonOld.setMinHeight(5);
        radioButtonOld.setToggleGroup(tg);
        //radioButtonOld.setDisable(true);

        //radio button for new share
        RadioButton radioButtonNew = new RadioButton();
        radioButtonNew.setMaxWidth(5);
        radioButtonNew.setMinWidth(5);
        radioButtonNew.setMaxHeight(5);
        radioButtonNew.setMinHeight(5);
        radioButtonNew.setToggleGroup(tg);
        //radioButtonNew.setDisable(true);

        ///////////////////////////////////////////////////////////

        /*
        ObservableList<SharesFX> shareList = FXCollections.observableArrayList();

        TableView<SharesFX> tabelShares = new TableView<SharesFX>(shareList);

        TableColumn<SharesFX , String> nameCompanyC = new TableColumn<>("Company name");

        nameCompanyC.setCellValueFactory(new PropertyValueFactory("nameOfCompanyFX"));
        TableColumn<SharesFX , String> numberOfshareC = new TableColumn<>("Number of shares");

        numberOfshareC.setCellValueFactory(new PropertyValueFactory("numberOfSharesFX"));
        TableColumn<SharesFX , String> priceOfShareC = new TableColumn<>("Share price");

        priceOfShareC.setCellValueFactory(new PropertyValueFactory("priceOfShareFX"));
        TableColumn<SharesFX , String> dateC = new TableColumn<>("Date of purchase");
        dateC.setCellValueFactory(new PropertyValueFactory("dateOfPurchaseFX"));

        tabelShares.getColumns().addAll(nameCompanyC,numberOfshareC,priceOfShareC,dateC);
        tabelShares.setEditable(false);
        dateC.setPrefWidth(140);
        nameCompanyC.setPrefWidth(130);
        priceOfShareC.setPrefWidth(100);
        numberOfshareC.setPrefWidth(120);
        tabelShares.setLayoutX(600);
        tabelShares.setLayoutY(50);

         */
        // tabelShares.getStylesheets().add("table-view");
        //tabelShares.getStylesheets().add(".table-row-cell:selected");
        //tabelShares.getStylesheets().add(".table-view .column-header .label");
        //tabelShares.getStylesheets().add(".table-view .scroll-bar:horizontal .track");
        //tabelShares.getStylesheets().add(".table-view .scroll-bar:vertical .track");
        // tabelShares.getStylesheets().add(".chart");
        //ScrollPane scrollPane = new ScrollPane(tabelShares);
        //scrollPane.setLayoutX(600);
        //scrollPane.setLayoutY(300);
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //scrollPane.setPrefSize(300,600);
        //tabelShares.setLayoutX(600);
        //tabelShares.setLayoutY(300);


        ///////////////////////////////////////////////////////////

        //btn sell new set on action
        btnSellNew.setOnAction(actionEvent -> {


            try {

                //btn
                radioButtonNew.setSelected(true);


                Scanner scanShares = new Scanner(fileShares);
                while (scanShares.hasNextLine()) {

                    String line = scanShares.nextLine();

                    //System.out.println(line.isEmpty());


                    while (line.isEmpty()) {
                        line = scanShares.nextLine();
                    }


                    int q1 = line.indexOf(',');
                    int numberOfShares = Integer.parseInt(line.substring(0, q1).trim());
                    int q4 = line.lastIndexOf(',');
                    String dateOfPurchase = line.substring(q4 + 1, line.length()).trim();
                    String middle = line.substring(q1 + 1, q4);
                    int qMiddle = middle.indexOf(',');
                    double priceOfShare = Double.parseDouble(middle.substring(0, qMiddle).trim());
                    String nameOfCompany = middle.substring(qMiddle + 1, middle.length()).trim();

                    stackSellNew.push(new Shares(numberOfShares, priceOfShare, nameOfCompany, dateOfPurchase));
                    //shareList.add(new SharesFX(nameOfCompany, String.valueOf(numberOfShares), String.valueOf(priceOfShare), dateOfPurchase));
                    stackSellNew.show();


                }
                /*
                Scanner scanDailyPrice = new Scanner(fileDailyPrice);
                while ( scanDailyPrice.hasNextLine()){

                    String line = scanDailyPrice.nextLine();
                    int q1 = line.indexOf(',');
                    String companyName = line.substring(0,q1).trim();
                    int q2 = line.lastIndexOf(',');
                    double dailyPrice = Double.parseDouble(line.substring(q2+1,line.length()).trim());

                    stackDailyPrice.push(new DailyPrice(companyName,dailyPrice));

                }

                 */


                stageSecond.show();
                stageFirst.close();

            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(stringIndexOutOfBoundsException.getMessage());
                alert.showAndWait();
            }


        });


        btnSellOld.setOnAction(actionEvent -> {

            try {

                radioButtonOld.setSelected(true);


                Scanner scanShares = new Scanner(fileShares);
                while (scanShares.hasNextLine()) {

                    String line = scanShares.nextLine();

                    //System.out.println(line.isEmpty());


                    while (line.isEmpty()) {
                        line = scanShares.nextLine();
                    }


                    int q1 = line.indexOf(',');
                    int numberOfShares = Integer.parseInt(line.substring(0, q1).trim());
                    int q4 = line.lastIndexOf(',');
                    String dateOfPurchase = line.substring(q4 + 1, line.length()).trim();
                    String middle = line.substring(q1 + 1, q4);
                    int qMiddle = middle.indexOf(',');
                    double priceOfShare = Double.parseDouble(middle.substring(0, qMiddle).trim());
                    String nameOfCompany = middle.substring(qMiddle + 1, middle.length()).trim();

                    queueSellOld.EnQeue(new Shares(numberOfShares, priceOfShare, nameOfCompany, dateOfPurchase));
                    //shareList.add(new SharesFX(nameOfCompany, String.valueOf(numberOfShares), String.valueOf(priceOfShare), dateOfPurchase));


                }

                /*
                Scanner scanDailyPrice = new Scanner(fileDailyPrice);
                while ( scanDailyPrice.hasNextLine()){

                    String line = scanDailyPrice.nextLine();
                    int q1 = line.indexOf(',');
                    String companyName = line.substring(0,q1).trim();
                    int q2 = line.lastIndexOf(',');
                    double dailyPrice = Double.parseDouble(line.substring(q2+1,line.length()).trim());

                    queueDailyPrice.EnQeue(new DailyPrice(companyName,dailyPrice));

                }

                 */

                stageSecond.show();
                stageFirst.close();


            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(stringIndexOutOfBoundsException.getMessage());
                alert.showAndWait();
            }

        });


        //Image for Operation page - Blue background -
        InputStream streamBlue = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\Blue-BackGround.png");
        Image imageBlue = new Image(streamBlue);
        ImageView imageViewBlue = new ImageView();
        imageViewBlue.setImage(imageBlue);
        imageViewBlue.setFitHeight(800);
        imageViewBlue.setFitWidth(1300);
        pageOperation.getChildren().add(imageViewBlue);
        //---------------------------------------------------------

        /*
        //Image for market stock
        InputStream streamMarket = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\Market-stock.png");
        Image imageMarket = new Image(streamMarket);
        ImageView imageViewMarket = new ImageView();
        imageViewMarket.setImage(imageMarket);
        imageViewMarket.setFitHeight(500);
        imageViewMarket.setFitWidth(350);
        imageViewMarket.setX(75);
        imageViewMarket.setY(250);
        pageOperation.getChildren().add(imageViewMarket);
        //---------------------------------------------------------

         */

        //Image rectangle under logo company
        InputStream streamRec = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\rectangle-gray.png");
        Image imageRec = new Image(streamRec);
        ImageView imageViewRec = new ImageView();
        imageViewRec.setImage(imageRec);
        imageViewRec.setFitHeight(140);
        imageViewRec.setFitWidth(1400);
        imageViewRec.setX(0);
        imageViewRec.setY(50);
        pageOperation.getChildren().add(imageViewRec);

        //Image rectangle stock market behind logo company
        InputStream streamSM = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\stock-market-rec.png");
        Image imageSM = new Image(streamSM);
        ImageView imageViewSM = new ImageView();
        imageViewSM.setImage(imageSM);
        imageViewSM.setFitHeight(50);
        imageViewSM.setFitWidth(300);
        imageViewSM.setX(170);
        imageViewSM.setY(95);
        pageOperation.getChildren().add(imageViewSM);
        //------------------------------------------------

        //Rectangle Black for contact us
        Rectangle rect = new Rectangle();
        rect.setFill(Color.BLACK);
        rect.setHeight(70);
        rect.setWidth(765);
        rect.setY(80);
        rect.setX(500);
        pageOperation.getChildren().add(rect);



        /*
        //Image circle under logo company
        InputStream streamC = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\circle-black.png");
        Image imageC = new Image(streamC);
        ImageView imageViewC = new ImageView();
        imageViewC.setImage(imageC);
        imageViewC.setFitHeight(220);
        imageViewC.setFitWidth(220);
        imageViewC.setX(30);
        imageViewC.setY(50);
        pageOperation.getChildren().add(imageViewC);
         */

        //Image for logo company
        InputStream streamLogo = new FileInputStream("C:\\Users\\twitter\\IdeaProjects\\Project 2 - FX\\src\\Images\\Logo-Company-Orange.png");
        Image imageLogo = new Image(streamLogo);
        ImageView imageViewLogo = new ImageView();
        imageViewLogo.setImage(imageLogo);
        imageViewLogo.setFitHeight(140);
        imageViewLogo.setFitWidth(140);
        imageViewLogo.setX(30);
        imageViewLogo.setY(50);
        pageOperation.getChildren().add(imageViewLogo);


        //----------------------------------------------

        //Label for Captial gain 2- top of image stock market
        Label lblCapitalGain2 = new Label();
        lblCapitalGain2.setText("Capital gain");
        lblCapitalGain2.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        lblCapitalGain2.setTextFill(Color.BLACK);
        //lblCapitalGain2.setBackground(new Background((new BackgroundFill(Color.web("#323e4f") , new CornerRadii(0) , Insets.EMPTY))));
        lblCapitalGain2.setLayoutX(75);
        lblCapitalGain2.setLayoutY(145);
        //pageOperation.getChildren().add(lblCapitalGain);
        //---------------------------------------------------------

        //Label for value of capital gain 2- top of image stock market
        Label lblValueCapitalGain2 = new Label();
        lblValueCapitalGain2.setText("0");
        lblValueCapitalGain2.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        lblValueCapitalGain2.setTextFill(Color.web("fff2cc"));

        //Hbox for capital gain
        HBox hBoxCapitalGain2 = new HBox(lblCapitalGain2, lblValueCapitalGain2);
        hBoxCapitalGain2.setLayoutX(700);
        hBoxCapitalGain2.setLayoutY(600);
        hBoxCapitalGain2.setSpacing(50);
        pageOperation.getChildren().add(hBoxCapitalGain2);

        //Rectangle Black for Buy shares
        Rectangle rectBuy = new Rectangle();
        rectBuy.setFill(Color.BLACK);
        rectBuy.setHeight(200);
        rectBuy.setWidth(380);
        rectBuy.setY(220);
        rectBuy.setX(50);
        pageOperation.getChildren().add(rectBuy);
        //----------------------------------------------

        //text field for number of shares - buy -
        TextField txtBuyNumber = new TextField();
        txtBuyNumber.setPrefColumnCount(20);
        txtBuyNumber.setPromptText("Number of shares");
        txtBuyNumber.setBackground(new Background((new BackgroundFill(Color.web("cfcdcd"), new CornerRadii(20), Insets.EMPTY))));
        txtBuyNumber.setStyle("-fx-text-inner-color: Black");
        //txtBuyNumber.setStyle(".text-field");
        pageOperation.getChildren().add(txtBuyNumber);

        //text field for company name
        TextField txtCompanyBuy = new TextField();
        txtCompanyBuy.setPrefColumnCount(20);
        txtCompanyBuy.setPromptText("Company name");
        txtCompanyBuy.setBackground(new Background((new BackgroundFill(Color.web("cfcdcd"), new CornerRadii(20), Insets.EMPTY))));
        txtCompanyBuy.setStyle("-fx-text-inner-color: Black");
        //txtCompanyBuy.setStyle(".text-field");
        pageOperation.getChildren().add(txtCompanyBuy);

        //Button for buy new Shares
        Button btnBuy = new Button();
        btnBuy.setText("Buy");
        btnBuy.setMaxHeight(40);
        btnBuy.setMinHeight(40);
        btnBuy.setMinWidth(80);
        btnBuy.setMaxWidth(80);
        btnBuy.setTextFill(Color.BLACK);
        btnBuy.setBackground(new Background((new BackgroundFill(Color.web("ff9900"), new CornerRadii(20), Insets.EMPTY))));
        btnBuy.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

        //Vbox for buy new shares
        VBox vboxBuy = new VBox(txtBuyNumber, txtCompanyBuy, btnBuy);
        vboxBuy.setLayoutX(120);
        vboxBuy.setLayoutY(245);
        vboxBuy.setSpacing(25);
        vboxBuy.setAlignment(Pos.CENTER);
        pageOperation.getChildren().add(vboxBuy);
        //----------------------------------------------------


        //Rectangle Black for sell shares
        Rectangle rectSell = new Rectangle();
        rectSell.setFill(Color.BLACK);
        rectSell.setHeight(250);
        rectSell.setWidth(380);
        rectSell.setY(450);
        rectSell.setX(50);
        pageOperation.getChildren().add(rectSell);
        //----------------------------------------------

        //text field for number of shares - sell -
        TextField txtSellNumber = new TextField();
        txtSellNumber.setPrefColumnCount(20);
        txtSellNumber.setPromptText("Number of shares");
        txtSellNumber.setBackground(new Background((new BackgroundFill(Color.web("cfcdcd"), new CornerRadii(20), Insets.EMPTY))));
        txtSellNumber.setStyle("-fx-text-inner-color: Black");
        //txtSellNumber.setStyle(".text-field");
        pageOperation.getChildren().add(txtSellNumber);

        //text field for company name - sell -
        TextField txtCompanySell = new TextField();
        txtCompanySell.setPrefColumnCount(20);
        txtCompanySell.setPromptText("Company name");
        txtCompanySell.setBackground(new Background((new BackgroundFill(Color.web("cfcdcd"), new CornerRadii(20), Insets.EMPTY))));
        txtCompanySell.setStyle("-fx-text-inner-color: Black");
        //txtCompanySell.setStyle(".text-field");
        pageOperation.getChildren().add(txtCompanySell);


        //lbl for old
        Label lblOld = new Label();
        lblOld.setText("Old");
        lblOld.setFont(Font.font("Verdana", 16));
        lblOld.setTextFill(Color.web("ffc000"));
        //lblOld.setStyle("-fx-font-weight: bold");

        //lbl for new
        Label lblNew = new Label();
        lblNew.setText("New");
        lblNew.setFont(Font.font("Verdana", 16));
        lblNew.setTextFill(Color.web("ffc000"));
        //lblNew.setStyle("-fx-font-weight: bold");

        //hbox for label new and old
        HBox hBoxNewOld = new HBox(lblOld, lblNew);
        hBoxNewOld.setLayoutY(569);
        hBoxNewOld.setLayoutX(136);
        hBoxNewOld.setSpacing(95);
        pageOperation.getChildren().add(hBoxNewOld);

        //Hbox for radio button old and new
        HBox hBoxRadioButton = new HBox(radioButtonOld, radioButtonNew);
        hBoxRadioButton.setSpacing(130);
        hBoxRadioButton.setAlignment(Pos.CENTER);

        //Button for buy new Shares
        Button btnSell = new Button();
        btnSell.setText("Sell");
        btnSell.setMaxHeight(40);
        btnSell.setMinHeight(40);
        btnSell.setMinWidth(80);
        btnSell.setMaxWidth(80);
        btnSell.setTextFill(Color.BLACK);
        btnSell.setBackground(new Background((new BackgroundFill(Color.web("ff9900"), new CornerRadii(20), Insets.EMPTY))));
        btnSell.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

        //event handler for btn sell  when mouse entered-> shadow
        btnSell.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnSell.setEffect(shadow);
        });

        //event handler for btn buy  when mouse entered-> shadow
        btnBuy.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnBuy.setEffect(shadow);
        });

        //event handler for btn sell  when mouse exit-> shadow
        btnSell.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnSell.setEffect(null);
        });

        //event handler for btn buy new when mouse exit-> shadow
        btnBuy.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnBuy.setEffect(null);
        });

        //Vbox for sell new shares
        VBox vboxSell = new VBox(txtSellNumber, txtCompanySell, hBoxRadioButton, btnSell);
        vboxSell.setLayoutX(120);
        vboxSell.setLayoutY(475);
        vboxSell.setSpacing(25);
        vboxSell.setAlignment(Pos.CENTER);
        pageOperation.getChildren().add(vboxSell);
        //-------------------------------------------------------------------

        //Button for show capital gain
        Button btnShowCapitalGain = new Button();
        btnShowCapitalGain.setText("Show CG");
        btnShowCapitalGain.setMaxHeight(40);
        btnShowCapitalGain.setMinHeight(40);
        btnShowCapitalGain.setMinWidth(80);
        btnShowCapitalGain.setMaxWidth(80);
        btnShowCapitalGain.setTextFill(Color.BLACK);
        btnShowCapitalGain.setBackground(new Background((new BackgroundFill(Color.web("ff9900"), new CornerRadii(20), Insets.EMPTY))));
        btnShowCapitalGain.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

        //Button for buy new Shares
        Button btnStore = new Button();
        btnStore.setText("Store");
        btnStore.setMaxHeight(40);
        btnStore.setMinHeight(40);
        btnStore.setMinWidth(80);
        btnStore.setMaxWidth(80);
        btnStore.setTextFill(Color.BLACK);
        btnStore.setBackground(new Background((new BackgroundFill(Color.web("ff9900"), new CornerRadii(20), Insets.EMPTY))));
        btnStore.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

        //Button for prev
        Button btnPrev = new Button();
        btnPrev.setText("Prev");
        btnPrev.setMaxHeight(40);
        btnPrev.setMinHeight(40);
        btnPrev.setMinWidth(80);
        btnPrev.setMaxWidth(80);
        btnPrev.setTextFill(Color.BLACK);
        btnPrev.setBackground(new Background((new BackgroundFill(Color.web("ff9900"), new CornerRadii(20), Insets.EMPTY))));
        btnPrev.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

        //Hbox for prev and store
        HBox hbox = new HBox(btnPrev, btnStore);
        hbox.setSpacing(35);
        hbox.setLayoutX(140);
        hbox.setLayoutY(715);
        pageOperation.getChildren().add(hbox);
        //stageSecond.show();

        btnPrev.setOnAction(actionEvent -> {

            stageSecond.close();
            stage0.show();

        });


        stageSecond.setScene(scenePageOperation);
        stageSecond.setTitle("Operations");
        stageSecond.setMaxWidth(1300);
        stageSecond.setMaxHeight(800);
        //stageSecond.show();

        //Table view
        /*
        Scanner scanList = new Scanner(fileShares);
        ObservableList<SharesFX> shareList = FXCollections.observableArrayList(



        );



        TableView<SharesFX> tabelShares = new TableView<SharesFX>(shareList);
        TableColumn<SharesFX , String> nameCompanyC = new TableColumn<>("Company name");
        nameCompanyC.setCellValueFactory(new PropertyValueFactory("nameOfCompanyFX"));
        TableColumn<SharesFX , String> numberOfshareC = new TableColumn<>("Number of shares");
        numberOfshareC.setCellValueFactory(new PropertyValueFactory("numberOfSharesFX"));
        TableColumn<SharesFX , String> priceOfShareC = new TableColumn<>("Share price");
        priceOfShareC.setCellValueFactory(new PropertyValueFactory("priceOfShareFX"));
        TableColumn<SharesFX , String> dateC = new TableColumn<>("Date of purchase");
        dateC.setCellValueFactory(new PropertyValueFactory("dateOfPurchaseFX"));
        tabelShares.getColumns().addAll(nameCompanyC,numberOfshareC,priceOfShareC,dateC);
        tabelShares.setEditable(false);
        dateC.setPrefWidth(140);
        nameCompanyC.setPrefWidth(130);
        priceOfShareC.setPrefWidth(100);
        numberOfshareC.setPrefWidth(120);

        pageOperation.getChildren().add(tabelShares);
        //
         */


        //////////////////////////////// Buttons set on action //////////////////////////////


        //Button Buy set on action
        btnBuy.setOnAction(actionEvent -> {

            try {


                if (txtBuyNumber.getText().equals("")) {
                    throw new IllegalArgumentException("Please enter the number of shares you want to purchase ...");
                } else if (txtCompanyBuy.getText().equals("")) {
                    throw new IllegalArgumentException("Please enter the name of the company from which you want to buy the shares ...");
                }

                int numberShares = Integer.parseInt(txtBuyNumber.getText().trim());

                if (numberShares < 0) {
                    throw new IllegalArgumentException("Number of shares cannot be negative !!");
                } else if (numberShares == 0) {
                    throw new IllegalArgumentException("Number of shares cannot be zero !!");
                }


                String companyName = String.valueOf(txtCompanyBuy.getText().trim());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy".trim());
                LocalDateTime now = LocalDateTime.now();
                boolean check = false;
                double dailyPrice = 0;

                Scanner scan = new Scanner(fileDailyPrice);
                while (true) {

                    String line = scan.nextLine();
                    System.out.println("line" + line);

                    //System.out.println(line.isEmpty());


                    while (line.isEmpty()) {
                        line = scan.nextLine();
                        System.out.println("While");
                    }

                    int q1 = line.indexOf(',');
                    String companyNameD = line.substring(0, q1).trim();
                    int q2 = line.lastIndexOf(',');
                    double dailyPriceD = Double.parseDouble(line.substring(q2 + 1, line.length()).trim());


                    if (companyNameD.trim().equalsIgnoreCase(companyName)) {

                        System.out.println("if");
                        check = true;
                        dailyPrice = dailyPriceD;
                        break;

                    }

                    if (!scan.hasNext()) {

                        break;
                    }


                }


                if (check == false) {

                    throw new IllegalArgumentException("Company " + companyName + " not found !! ");

                } else {

                    stackSellNew.show();

                    if (radioButtonNew.isSelected()) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure to buy " + numberShares + " from " + companyName + " company ?");
                        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getDialogPane().getButtonTypes().add(btnCancel);
                        Optional<ButtonType> action = alert.showAndWait();

                        if (action.get() == ButtonType.OK) {

                            stackSellNew.push(new Shares(numberShares, dailyPrice, companyName, now.format(dtf)));

                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Information");
                            alert2.setHeaderText(null);
                            alert2.setContentText("You have just purchased " + numberShares + "from " + companyName + " company ...");
                            alert2.showAndWait();

                        }


                    } else {
                        ////////// buy qeueu old //////////

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure to buy " + numberShares + " from " + companyName + " company ?");
                        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getDialogPane().getButtonTypes().add(btnCancel);
                        Optional<ButtonType> action = alert.showAndWait();

                        if (action.get() == ButtonType.OK) {

                            queueSellOld.EnQeue(new Shares(numberShares, dailyPrice, companyName, now.toString()));

                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Information");
                            alert2.setHeaderText(null);
                            alert2.setContentText("You have just purchased " + numberShares + "from " + companyName + " company ...");
                            alert2.showAndWait();

                            queueSellOld.show();
                        }


                    }// else for qeue

                    stackSellNew.show();
                    txtCompanySell.setText("");
                    txtSellNumber.setText("");
                }


            } catch (FileNotFoundException exception2) {

                System.out.println("File not found");

            } catch (NumberFormatException exception) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(exception.getMessage());
                alert.showAndWait();

            } catch (IllegalArgumentException e) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();

            }

        });

        //Button Sell set on action
        btnSell.setOnAction(actionEvent -> {

            try {

                if (radioButtonNew.isSelected()) {

                    //////////Stack sell new shares/////////

                    if (txtSellNumber.getText().trim().equals("")) {
                        throw new IllegalArgumentException("Please enter the number of shares you want to sell ...");
                    } else if (txtCompanySell.getText().trim().equals("")) {
                        throw new IllegalArgumentException("Please enter the name of the company from which you want to sell the shares ...");
                    }

                    int numberShares = Integer.parseInt(txtSellNumber.getText().trim());

                    if (numberShares < 0) {
                        throw new IllegalArgumentException("Number of shares cannot be negative !!");
                    } else if (numberShares == 0) {
                        throw new IllegalArgumentException("Number of shares cannot be zero !!");
                    }

                    String companyName = txtCompanySell.getText().trim();
                    boolean checkCompany = false;
                    boolean checkSub = false;
                    int tempNumber = numberShares;
                    int newNumber = 0;
                    double dailyPrice = 0;
                    double subCapitalGain = 0;
                    Stack tempStack2 = new Stack();

                    try {
                        Scanner scan = new Scanner(fileDailyPrice);
                        while (true) {

                            String line = scan.nextLine();

                            //System.out.println(line.isEmpty());
                            while (line.isEmpty()) {
                                line = scan.nextLine();
                            }

                            int q1 = line.indexOf(',');
                            String companyNameD = line.substring(0, q1).trim();
                            int q2 = line.lastIndexOf(',');
                            double dailyPriceD = Double.parseDouble(line.substring(q2 + 1, line.length()).trim());


                            if (companyNameD.trim().equalsIgnoreCase(companyName)) {

                                checkCompany = true;
                                dailyPrice = dailyPriceD;
                                break;

                            }

                            if (!scan.hasNext()) {

                                break;
                            }

                        }
                    } catch (FileNotFoundException ex) {
                        System.out.println("File not found exception");
                    }
                    if (checkCompany == false) {
                        throw new IllegalArgumentException("Company not found !!");
                    }

                    Stack excStack = new Stack();

                    int sum = 0;
                    while (stackSellNew.length() != 0) {

                        if (((Shares) stackSellNew.top.getData()).getNameOfCompany().trim().equalsIgnoreCase(companyName)) {
                            sum = sum + ((Shares) stackSellNew.top.getData()).getNumberOfShares();
                            excStack.push(stackSellNew.pop());
                        } else {
                            excStack.push(stackSellNew.pop());
                        }

                    }

                    while (excStack.length() != 0) {

                        stackSellNew.push(excStack.pop());

                    }

                    System.out.println("Sum = " + sum);
                    if (numberShares > sum) {
                        throw new IllegalArgumentException("There is not enough shares in");
                    }

                    while (stackSellNew.length() != 0) {

                        if (((Shares) stackSellNew.top.getData()).getNameOfCompany().equalsIgnoreCase(companyName) && tempNumber != 0) {

                            if (((Shares) stackSellNew.top.getData()).getNumberOfShares() == tempNumber) {

                                System.out.println("if");
                                capitalGain = capitalGain + ((Shares) stackSellNew.top.getData()).getNumberOfShares() * (dailyPrice - ((Shares) stackSellNew.top.getData()).getPriceOfShare());
                                stackSellNew.pop();
                                tempNumber = 0;
                                break;
                            } else if (((Shares) stackSellNew.top.getData()).getNumberOfShares() < tempNumber) {

                                tempNumber = tempNumber - ((Shares) stackSellNew.top.getData()).getNumberOfShares();
                                capitalGain = capitalGain + ((Shares) stackSellNew.top.getData()).getNumberOfShares() * (dailyPrice - ((Shares) stackSellNew.top.getData()).getPriceOfShare());
                                subStack.push(stackSellNew.pop());

                            } else if (((Shares) stackSellNew.top.getData()).getNumberOfShares() > tempNumber) {

                                newNumber = ((Shares) stackSellNew.top.getData()).getNumberOfShares() - tempNumber;
                                ((Shares) stackSellNew.top.getData()).setNumberOfShares(newNumber);
                                capitalGain = capitalGain + tempNumber * (dailyPrice - ((Shares) stackSellNew.top.getData()).getPriceOfShare());
                                checkSub = true;
                                tempNumber = 0;
                                break;

                            }

                        } else {

                            Shares obj = (Shares) stackSellNew.top.getData();
                            tempStack2.push(stackSellNew.pop());
                            subStack.push(obj);

                        }


                    }

                    /*
                    //if statement to check if there is enough share in the stack or not
                    System.out.println(tempNumber);
                    if (tempNumber != 0 && checkSub == false) {


                        while (stackSellNew.length() != 0) {

                            stackSellNew.pop();

                        }


                        while (subStack.length() != 0) {

                            stackSellNew.push(subStack.pop());

                        }




                        //capitalGain = subCapitalGain ;
                        stackSellNew.show();
                        throw new IllegalArgumentException("There is not enough shares to sell from " + companyName);
                    }

                     */

                    while (tempStack2.length() != 0) {

                        stackSellNew.push(tempStack2.pop());

                    }

                    //subCapitalGain = capitalGain ;
                    if (capitalGain > 0) {
                        lblValueCapitalGain2.setTextFill(Color.GREEN);
                        lblValueCapitalGain2.setText("+" + String.valueOf(capitalGain));
                    } else if (capitalGain < 0) {
                        lblValueCapitalGain2.setTextFill(Color.RED);
                        lblValueCapitalGain2.setText("-" + String.valueOf(capitalGain));
                    } else {
                        lblValueCapitalGain2.setText(String.valueOf(capitalGain));
                    }
                    stackSellNew.show();


                } else {

                    //////////Queue sell old shares////////

                    queueSellOld.show();
                    if (txtSellNumber.getText().trim().equals("")) {
                        throw new IllegalArgumentException("Please enter the number of shares you want to sell ...");
                    } else if (txtCompanySell.getText().trim().equals("")) {
                        throw new IllegalArgumentException("Please enter the name of the company from which you want to sell the shares ...");
                    }

                    int numberShares = Integer.parseInt(txtSellNumber.getText().trim());

                    if (numberShares < 0) {
                        throw new IllegalArgumentException("Number of shares cannot be negative !!");
                    } else if (numberShares == 0) {
                        throw new IllegalArgumentException("Number of shares cannot be zero !!");
                    }

                    String companyName = txtCompanySell.getText().trim();
                    boolean checkCompany = false;
                    int tempNumber = numberShares;
                    double dailyPrice = 0;
                    Queue tempQueue = new Queue();
                    Queue excQueue = new Queue();

                    Scanner scan = new Scanner(fileDailyPrice);
                    while (scan.hasNextLine()) {


                        String line = scan.nextLine();
                        System.out.println(line);
                        int q1 = line.indexOf(',');
                        String companyNameD = line.substring(0, q1).trim();
                        int q2 = line.lastIndexOf(',');
                        double dailyPriceD = Double.parseDouble(line.substring(q2 + 1, line.length()).trim());

                        if (companyName.equalsIgnoreCase(companyNameD)) {

                            dailyPrice = dailyPriceD;
                            checkCompany = true;
                            break;

                        }
                    }

                    if (checkCompany == false) {
                        throw new IllegalArgumentException("Company not found !!");
                    }

                    int sum = 0;
                    while (queueSellOld.length() != 0) {

                        if (((Shares) queueSellOld.first.getData()).getNameOfCompany().trim().equalsIgnoreCase(companyName)) {
                            sum = sum + ((Shares) queueSellOld.first.getData()).getNumberOfShares();
                            excQueue.EnQeue(queueSellOld.Deqeue());
                        } else {
                            excQueue.EnQeue(queueSellOld.Deqeue());
                        }

                    }
                    queueSellOld = excQueue;

                    System.out.println(sum);
                    if (numberShares > sum) {
                        throw new IllegalArgumentException("There is not enough shares in");
                    }


                    while (queueSellOld.length() != 0) {

                        if (((Shares) queueSellOld.first.getData()).getNameOfCompany().equalsIgnoreCase(companyName)) {

                            if (((Shares) queueSellOld.first.getData()).getNumberOfShares() == tempNumber) {

                                System.out.println("if first");
                                capitalGain = capitalGain + tempNumber * (dailyPrice - ((Shares) queueSellOld.first.getData()).getPriceOfShare());
                                queueSellOld.Deqeue();
                                tempNumber = 0;
                                break;

                            } else if (((Shares) queueSellOld.first.getData()).getNumberOfShares() > tempNumber) {

                                System.out.println("if second");
                                int numberShareUbdate = ((Shares) queueSellOld.first.getData()).getNumberOfShares() - tempNumber;
                                System.out.println("number new : " + numberShareUbdate);
                                ((Shares) queueSellOld.first.getData()).setNumberOfShares(numberShareUbdate);
                                System.out.println("First = " + queueSellOld.first.getData());
                                capitalGain = capitalGain + tempNumber * (dailyPrice - ((Shares) queueSellOld.first.getData()).getPriceOfShare());
                                tempNumber = 0;
                                break;

                            } else {
                                System.out.println("else");
                                Shares obj2 = (Shares) queueSellOld.first.getData();
                                tempNumber = tempNumber - obj2.getNumberOfShares();
                                capitalGain = capitalGain + ((Shares) queueSellOld.first.getData()).getNumberOfShares() * (dailyPrice - ((Shares) queueSellOld.first.getData()).getPriceOfShare());
                                //tempQueue.EnQeue(queueSellOld.Deqeue());
                                queueSellOld.Deqeue();

                            }

                        } else {
                            System.out.println("hi");
                            Shares obj = (Shares) queueSellOld.Deqeue();
                            tempQueue.EnQeue(obj);

                        }

                    }


                    while (queueSellOld.length() != 0) {

                        tempQueue.EnQeue(queueSellOld.Deqeue());

                    }

                    queueSellOld = tempQueue;

                    if (capitalGain > 0) {
                        lblValueCapitalGain2.setTextFill(Color.GREEN);
                        lblValueCapitalGain2.setText("+" + String.valueOf(capitalGain));
                    } else if (capitalGain < 0) {
                        lblValueCapitalGain2.setTextFill(Color.RED);
                        lblValueCapitalGain2.setText("-" + String.valueOf(capitalGain));
                    } else {
                        lblValueCapitalGain2.setText(String.valueOf(capitalGain));
                    }


                }


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText(txtSellNumber.getText() + " shares was sell from " + txtCompanySell.getText() + " company ...");
                alert.showAndWait();

                txtCompanySell.setText("");
                txtSellNumber.setText("");
                queueSellOld.show();

            } catch (FileNotFoundException e) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();

            } catch (NumberFormatException exception) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(exception.getMessage());
                alert.showAndWait();

            } catch (IllegalArgumentException illegalArgumentException) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText(illegalArgumentException.getMessage());
                alert.showAndWait();

            }


        });//btn

        //pageOperation.getChildren().add(tabelShares);
        //pageOperation.getChildren().add(scrollPane);


        btnStore.setOnAction(actionEvent -> {

            try {

                if (radioButtonNew.isSelected()) {

                    FileWriter w = new FileWriter(fileShares.getAbsolutePath());
                    //PrintWriter printWriter = new PrintWriter(fileShares.getName().toString());
                    String str = "";

                    Scanner sc = new Scanner(fileShares);
                    while (stackSellNew.isEmpty() == false) {

                        //String obj = String.valueOf((Shares) stackSellNew.top.getData()) + "\n";
                        String str1 = String.valueOf(((Shares) stackSellNew.top.getData()).getNumberOfShares());
                        String str12 = String.valueOf(((Shares) stackSellNew.top.getData()).getPriceOfShare());
                        String str3 = String.valueOf(((Shares) stackSellNew.top.getData()).getNameOfCompany());
                        String str4 = String.valueOf(((Shares) stackSellNew.top.getData()).getDateOfPurchase());
                        str = str1 + ',' + str12 + ',' + str3 + ',' + str4 + "\n" + str;
                        stackSellNew.pop();
                        //printWriter.print(obj);

                    }
                    w.write(str);

                    w.close();

                } else {
                    Queue tempQueue = new Queue();

                    FileWriter w = new FileWriter(fileShares.getAbsolutePath());
                    //PrintWriter printWriter = new PrintWriter(fileShares.getName().toString());
                    String str = "";

                    Scanner sc = new Scanner(fileShares);
                    while (queueSellOld.isEmpty() == false) {

                        //String obj = String.valueOf((Shares) stackSellNew.top.getData()) + "\n";
                        String str1 = String.valueOf(((Shares) queueSellOld.first.getData()).getNumberOfShares());
                        String str12 = String.valueOf(((Shares) queueSellOld.first.getData()).getPriceOfShare());
                        String str3 = String.valueOf(((Shares) queueSellOld.first.getData()).getNameOfCompany());
                        String str4 = String.valueOf(((Shares) queueSellOld.first.getData()).getDateOfPurchase());
                        str = str + str1 + ',' + str12 + ',' + str3 + ',' + str4 + "\n";
                        tempQueue.EnQeue(queueSellOld.Deqeue());
                        //printWriter.print(obj);

                    }
                    w.write(str);

                    w.close();

                    while (tempQueue.length() != 0) {

                        queueSellOld.EnQeue(tempQueue.Deqeue());

                    }

                }
            } catch (IOException e1) {

            }

        });


        //event handler for btn store when mouse entered-> shadow
        btnPrev.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnPrev.setEffect(shadow);
        });

        //event handler for btn  store when mouse exit-> shadow
        btnStore.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnStore.setEffect(null);
        });

        //event handler for btn  pre when mouse exit-> shadow
        btnPrev.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            btnPrev.setEffect(null);
        });

        //event handler for btn store when mouse entered-> shadow
        btnStore.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            btnStore.setEffect(shadow);
        });


        //stageSecond.show();

        radioButtonOld.setOnAction(actionEvent -> {

            radioButtonOld.setSelected(true);
            radioButtonNew.setSelected(false);
            flag = 1;

        });

        radioButtonNew.setOnAction(actionEvent -> {

            radioButtonOld.setSelected(false);
            radioButtonNew.setSelected(true);
            flag = 2;

        });

        if (flag == 1) {

            radioButtonOld.setSelected(true);
            radioButtonNew.setSelected(false);

        } else {

            radioButtonOld.setSelected(false);
            radioButtonNew.setSelected(true);

        }


    }//stage


    public static void main(String[] args) {
        launch(args);
    }
}
