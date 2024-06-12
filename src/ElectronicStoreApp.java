//Import all necessary libraries
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.*;


public class ElectronicStoreApp extends Application {
    private ElectronicStore model;

    public void start(Stage primaryStage) {
        //initilize pane, model, and view
        Pane aPane = new Pane();
        model = ElectronicStore.createStore();

        ElectronicStoreView view = new ElectronicStoreView();
        aPane.getChildren().add(view);

        //set all buttons to disabled as stated in specification
        view.getAddCart().setDisable(true);
        view.getCompleteSale().setDisable(true);
        view.getRemoveCart().setDisable(true);


        view.update(model);
        view.getRevenue().setText(String.valueOf(model.getRevenue()));
        view.getSales().setText(String.valueOf(model.getSales()));

        if(model.getSales()==0) {
            view.getIncomePerSale().setText("N/A");
        }
        else {
            view.getSales().setText(String.valueOf(model.getRevenue()/model.getSales()));
        }

        ArrayList<Product> topP = new ArrayList<Product>();
        for(int i=0;i<3;i++){
            topP.add(model.topProducts().get(i));
        }
        view.getPopularList().setItems(FXCollections.observableArrayList(topP));


        HashMap<String,Integer> amounts = new HashMap<String,Integer>();

        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent mouseEvent) {
                view.getAddCart().setDisable(false);
                Product selected = view.getStockList().getSelectionModel().getSelectedItem();
                view.getAddCart().setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent actionEvent) {
                        if (selected.getStockQuantity() > 0) {
                            view.setCartMoney(view.getCartMoney()+selected.getPrice());
                            selected.setStockQuantity(selected.getStockQuantity() - 1);
                            if (!amounts.containsKey(selected.toString())) {
                                amounts.put(selected.toString(), 1);
                            } else {
                                amounts.put(selected.toString(), amounts.get(selected.toString()) + 1);
                            }

                            ArrayList<String> Alist = new ArrayList<>();
                            for (Map.Entry<String, Integer> amount : amounts.entrySet()) {
                                String s = amount.getValue() + " x " + amount.getKey();
                                Alist.add(s);
                            }
                            view.getCartList().setItems(FXCollections.observableArrayList(Alist));
                            view.update(model);
                        }
                    }
                });
            }

        });

        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent mouseEvent) {
                view.getRemoveCart().setDisable(false);
                String selected = view.getCartList().getSelectionModel().getSelectedItem();
                if(selected!=null) {
                    ArrayList<String> selected2 = new ArrayList<String>(Arrays.asList(selected.split(" ")));
                    selected2.remove(0);
                    selected2.remove(0);
                    String selected3 = String.join(" ", selected2);

                    Product pSelected = model.searchProducts(selected3).get(0);


                    view.getRemoveCart().setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {

                            view.setCartMoney(view.getCartMoney() - pSelected.getPrice());
                            pSelected.setStockQuantity(pSelected.getStockQuantity() + 1);
                            amounts.put(pSelected.toString(), amounts.get(pSelected.toString()) - 1);

                            ArrayList<String> Alist = new ArrayList<>();
                            ArrayList<String> toRemove = new ArrayList<>();

                            for (Map.Entry<String, Integer> amount : amounts.entrySet()) {
                                if (amount.getValue() == 0) {
                                    toRemove.add(amount.getKey());
                                } else {
                                    String s = amount.getValue() + " x " + amount.getKey();
                                    Alist.add(s);
                                }
                            }
                            for (int i = 0; i < toRemove.size(); i++) {
                                amounts.remove(toRemove.get(i));
                            }
                            view.getCartList().setItems(FXCollections.observableArrayList(Alist));
                            view.update(model);
                        }

                        ;
                    });
                }
        }
    });
        view.getCompleteSale().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                int currentSales = Integer.parseInt(view.getSales().getText()) + 1;
                ArrayList<String> theSold = new ArrayList<>(view.getCartList().getItems());
                for(int i = 0;i<theSold.size();i++){
                    //Process of getting each string in the cart list and converting them into a string list to access the amount of each product and use the built in searchProducts method to create the product and update the amounts hashmap
                    ArrayList<String> theProduct = new ArrayList<String>(Arrays.asList(theSold.get(i).split(" ")));
                    int amount = Integer.parseInt(theProduct.get(0));
                    theProduct.remove(0);
                    theProduct.remove(0);
                    String theProductJoined = String.join(" ", theProduct);
                    Product finalProduct = model.searchProducts(theProductJoined).get(0);
                    model.getProductFrequincy().put(finalProduct,model.getProductFrequincy().get(finalProduct)+amount);
                }
                view.getSales().setText(String.valueOf((currentSales)));
                view.getRevenue().setText(String.valueOf(Double.parseDouble(view.getRevenue().getText())+view.getCartMoney()));
                view.getIncomePerSale().setText(String.valueOf((Double.parseDouble(view.getRevenue().getText()))/currentSales));
                view.setCartMoney(0);
                amounts.clear();
                view.getCartList().setItems(FXCollections.observableArrayList(new ArrayList<>()));
                view.update(model);
            }
            ;
        });

        view.getResetStore().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                amounts.clear();
                view.getCartList().setItems(FXCollections.observableArrayList(new ArrayList<>()));
                view.setCartMoney(0);
                view.getIncomePerSale().setText("N/A");
                view.getRevenue().setText("0.0");
                view.getSales().setText("0");
                for (Map.Entry<Product, Integer> item : model.getProductFrequincy().entrySet()) {
                    item.setValue(0);
                }
                for(int i=0;i< model.getCurProducts();i++){
                    model.getStock()[i].setStockQuantity(10);
                }
                view.update(model);
            }

            ;
        });

        primaryStage.setTitle("Electronic Store Application - "+model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
