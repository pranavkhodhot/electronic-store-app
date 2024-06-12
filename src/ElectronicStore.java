//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.*;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private int sales;
    private HashMap<Product,Integer> productFrequincy;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        sales = 0;
        productFrequincy = new HashMap<>();
    }
    public int getSales(){return sales;}

    public Product[] getStock() {return stock;}

    public String getName() {
        return name;
    }

    public int getCurProducts() {return curProducts;}

    public double getRevenue() {return revenue;}

    public HashMap<Product,Integer> getProductFrequincy(){return productFrequincy;}

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS && !productFrequincy.containsKey(newProduct)) {
            stock[curProducts] = newProduct;
            curProducts++;
            productFrequincy.put(newProduct,0);
            return true;
        }
        return false;
    }

    public ArrayList<Product> searchProducts(String x){
        ArrayList<Product> matched = new ArrayList<Product>();
        x = x.toLowerCase();
        for(int i=0;i<curProducts;i++){
            if(stock[i].toString().toLowerCase().contains(x)){
                matched.add(stock[i]);
            }
        }
        return matched;
    }

    public boolean sellProduct(Product p, int amount){
        ArrayList<Product> searched = searchProducts(p.toString());
        if(searched.contains(p)){
            p.setStockQuantity(p.getStockQuantity()-amount);
            sales += amount;
            revenue += amount*p.getPrice();
            productFrequincy.put(p,productFrequincy.get(p)+amount);
            return true;
        }
        return false;
    }

    public ArrayList<Product> topProducts(){
        ArrayList<Integer> sold = new ArrayList<Integer>();
        ArrayList<Product> soldCopy = new ArrayList<Product>();
        HashMap<Product,Integer> copyFrequincy = new HashMap<Product, Integer>(productFrequincy);

        for (Map.Entry<Product, Integer> frequent : productFrequincy.entrySet()) {
            sold.add(frequent.getValue());
        }
        Collections.sort(sold);
        for(int i=0;i<sold.size();i++){
            for(Map.Entry<Product, Integer> frequent: copyFrequincy.entrySet()) {
                if(frequent.getValue() == sold.get(i)){
                    soldCopy.add(frequent.getKey());
                }
            }
        }
        Collections.reverse(soldCopy);
        return soldCopy;

    }



    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
} 