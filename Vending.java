import java.util.ArrayList;

/**
 * Created by JMG on 9/8/2015.
 */
public class Vending {
    //PSEUDORANDOM LIMITERS
    ArrayList<Food> foods = new ArrayList<>();
    ArrayList<Integer> foodEnd = new ArrayList<>();
    ArrayList<Integer> foodStart = new ArrayList<>();
    int purchased;
    boolean debug = false;
    //CONSTRUCTORS
    public Vending (boolean debug) 
    {
        this.debug = debug;
    }

    public void addItem (String name, int price, int quantity)
    {
        foods.add(new Food(name,price));
        foodStart.add(quantity);
        foodEnd.add(quantity);
    }

    public int purchase (int itemchoice,int total, boolean reciept)
    {
        Food f = foods.get(itemchoice);
        if(debug)
        {
            print("Customer is attempting to purchase" + f.getName());
            print("Money entered: " + total);
            print("Money required: " + f.getPrice());
            print("Quantity on hand: " + foodEnd.get(itemchoice));
        }
        if(total > f.getPrice() && foodEnd.get(itemchoice)>0)
        {
            foodEnd.set(itemchoice,foodEnd.get(itemchoice)-1);
            purchased+=f.getPrice();
            if(debug)
            {
                if(reciept)
                {
                    print("    Purchased: " + f.getName());
                    print("    Price:     " + f.getPrice());
                    print("    Change:    " + ( total -= f.getPrice() ));
                    print("    Calories:  " + f.getCalories());
                    print("    Fat:       " + f.getFat());
                    print("    Carbs:     " + f.getCarbohydrates());
                    print("    Protein:   " + f.getProtein());
                }
            }
            
            return total;
        }
        else
        {
            if(debug)
            {
                if(total > f.getPrice())
                {
                    print("NO PURCHASE, INSUFFICIENT FUNDS.");
                }
                else //implied quantity is <1
                {
                    print("NO PURCHASE, INSUFFICIENT QUANTITY.");
                }
            }
            return 0;
        }
    }
    public int total(){
        return purchased;
    }
    public int sold(String itemName)
    {
        for (int i = 0; i < foods.size(); i++)
        {
            if(foods.get(i).getName()==itemName)
                return foodStart.get(i) - foodEnd.get(i);
        }
        return 0;
    }
    public int endQtyByName(String itemName)
    {
        for (int i = 0; i < foods.size(); i++)
        {
            if(foods.get(i).getName()==itemName)
                return (foodEnd.get(i));
        }
        return 0;
    }
    public int endQtyByIndex(int itemIndex)
    {
        if(itemIndex<foodEnd.size())
        {
            return foodEnd.get(itemIndex);
        }
        return 0;
    }

    private static int r(int num)
    {
        return (int)(Math.random()*num);
    }
    private static void print(String out)
    {
        System.out.println(out);
    }
}
