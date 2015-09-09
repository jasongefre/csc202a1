import java.util.ArrayList;

/**
 * Created by JMG on 9/8/2015.
 */
public class Vending {
    //PSEUDORANDOM LIMITERS
    ArrayList<Food> foods = new ArrayList<>();
    ArrayList<Integer> foodq = new ArrayList<>();
    int purchased;
    //CONSTRUCTORS
    public Vending () {}

    public void addItem (String name, int price, int quantity)
    {
        foods.add(new Food(name,price));
        foodq.add(quantity);
    }

    public boolean purchase (int itemchoice,int total, boolean reciept)
    {
        Food f = foods.get(itemchoice);
        if(total > f.getPrice() && foodq.get(itemchoice)>0)
        {
            foodq.set(itemchoice,foodq.get(itemchoice)-1);
            purchased+=f.getPrice();
            if(reciept)
            {
                print("Purchased: " + f.getName());
                print("Price:     " + f.getPrice());
                print("Calories:  " + f.getCalories());
                print("Fat:       " + f.getFat());
                print("Carbs:     " + f.getCarbohydrates());
                print("Protein:   " + f.getProtein());
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    public int total(){
        return purchased;
    }
    public int endQtyByName(String itemName)
    {
        for (int i = 0; i < foods.size(); i++)
        {
            if(foods.get(i).getName()==itemName)
                return (foodq.get(i));
        }
        return 0;
    }
    public int endQtyByIndex(int itemIndex)
    {
        if(itemIndex<foodq.size())
        {
            return foodq.get(itemIndex);
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
