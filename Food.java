/**
 * Created by JMG on 9/8/2015.
 */
public class Food {
    private int fatMax = 15, carbMax = 20, proteinMax = 10;
    private String name;
    private int price;
    private int fat;
    private int carbohydrates;
    private int protein;
    public Food (String name, int price)
    {
        this.name = name;
        this.fat = r(fatMax);
        this.carbohydrates = r(carbMax);
        this.protein = r(proteinMax);
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
    public int getCalories() {
        return (fat * 9) + ( (carbohydrates + protein) * 4);
    }
    public int getFat() {
        return fat;
    }
    public int getCarbohydrates() {
        return carbohydrates;
    }
    public int getProtein() {
        return protein;
    }
    public String getName() {
        return name;
    }
    private static int r(int num){
        return (int)(Math.random()*num);
    }
}
