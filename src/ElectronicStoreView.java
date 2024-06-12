import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

//--module-path C:\Users\kbsaj\javafx-sdk-18\lib --add-modules javafx.controls,javafx.fxml
public class ElectronicStoreView extends Pane {
    //Creating all necessary attributes of the view so that they can be changed in the GUI
    private TextField sales,revenue,incomePerSale;
    private ListView<Product> stockList,popularList;
    private ListView<String> cartList;
    private Button resetStore,addCart,removeCart,completeSale;
    private double cartMoney;
    private Label cart;

    //necessary getter setter methods
    public TextField getSales() {return sales;}
    public TextField getRevenue() {return revenue;}
    public TextField getIncomePerSale() {return incomePerSale;}

    public ListView<Product> getPopularList() {return popularList;}
    public ListView<Product> getStockList() {return stockList;}
    public ListView<String> getCartList() {return cartList;}

    public Button getResetStore() {return resetStore;}
    public Button getAddCart() {return addCart;}
    public Button getRemoveCart() {return removeCart;}
    public Button getCompleteSale() {return completeSale;}

    public double getCartMoney() {return cartMoney;}
    public void setCartMoney(double i) {cartMoney = i;}


    public ElectronicStoreView(){
        cartMoney = 0;
        Label summary = new Label("Store Summary:");
        summary.relocate(53, 15);
        Label stock = new Label("Store Stock:");
        stock.relocate(305, 15);
        cart = new Label("Current Cart ($"+cartMoney+"):");
        cart.relocate(600, 15);

        Label saleLabel = new Label("# Sales:");
        saleLabel.relocate(42,37);

        Label revenueLabel = new Label("Revenue:");
        revenueLabel.relocate(35,67);

        Label incomePerSaleLabel = new Label("$/Sale:");
        incomePerSaleLabel.relocate(46,97);

        Label mostPopular = new Label("Most Popular Items:");
        mostPopular.relocate(32,127);

        popularList = new ListView<>();
        popularList.setPrefSize(163,200);
        popularList.relocate(7,145);

        stockList = new ListView<>();
        stockList.setPrefSize(300,310);
        stockList.relocate(180,35);

        cartList = new ListView<>();
        cartList.setPrefSize(290,310);
        cartList.relocate(490,35);

        sales = new TextField();
        revenue = new TextField();
        incomePerSale = new TextField();

        sales.setPrefSize(80,20);
        sales.relocate(90,35);
        sales.setEditable(false);

        revenue.setPrefSize(80,20);
        revenue.relocate(90,65);
        revenue.setEditable(false);

        incomePerSale.setPrefSize(80,20);
        incomePerSale.relocate(90,95);
        incomePerSale.setEditable(false);

        resetStore = new Button("Reset Store");
        resetStore.setPrefSize(130,45);
        resetStore.relocate(25,350);

        addCart = new Button("Add To Cart");
        addCart.setPrefSize(140,45);
        addCart.relocate(260,350);

        removeCart = new Button("Remove from Cart");
        removeCart.setPrefSize(145,45);
        removeCart.relocate(490,350);

        completeSale = new Button("Complete Sale");
        completeSale.setPrefSize(145,45);
        completeSale.relocate(635,350);



        // Add all the components to the Pane
        getChildren().addAll(summary, stock, cart,sales,revenue,incomePerSale,saleLabel,revenueLabel,incomePerSaleLabel,mostPopular,popularList,stockList,cartList,resetStore,addCart,removeCart,completeSale);

        setPrefSize(800, 400);

    }
    public void update(ElectronicStore model) {
        ArrayList<Product> noNull= new ArrayList<>();
        for(int i=0;i<model.getCurProducts();i++){
            if(model.getStock()[i].getStockQuantity() > 0) {
                noNull.add(model.getStock()[i]);
            }
        }
        this.stockList.setItems(FXCollections.observableArrayList(noNull));

        if(cartList.getItems().size()>0){
            completeSale.setDisable(false);
        }
        else if(cartList.getItems().size()<=0){
            completeSale.setDisable(true);
        }
        if(cartList.getItems().size()<=0){
            removeCart.setDisable(true);
        }

        cart.setText("Current Cart ($"+cartMoney+"):");

        ArrayList<Product> topP = new ArrayList<Product>();
        for(int i=0;i<3;i++){
            topP.add(model.topProducts().get(i));
        }
        popularList.setItems(FXCollections.observableArrayList(topP));
    }

}
