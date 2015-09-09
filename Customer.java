/**
 * Created by JMG on 9/8/2015.
 */
public class Customer
{
    private int money, vm, desire;
    public Customer (int money,int vm, int desire)
    {
        this.money = money;
        this.vm = vm;
        this.desire = desire;
    }
    public int getMoney()
    {
        return money;
    }
    public int getVm()
    {
        return vm;
    }
    public int getDesire()
    {
        return desire;
    }
}
