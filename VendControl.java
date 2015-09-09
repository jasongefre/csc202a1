import java.lang.Math;
import java.io.*;
import java.util.ArrayList;
/**
 * Created by JMG on 9/8/2015.
 */
public class VendControl
{
    public static void main(String[] args)
    {
        //DATES
	    DateFormat dateY = new SimpleDateFormat("yyyy");
	    DateFormat dateM = new SimpleDateFormat("mm");
	    DateFormat dateM = new SimpleDateFormat("mm");
	    Date date = new Date();
	    int yyyy = Integer.parseInt(dateFormat.format(dateY));
	    int mm = Integer.parseInt(dateFormat.format(dateM));
	    int dd = Integer.parseInt(dateFormat.format(dateD));
        yyyy*= 10000;
        mm*=100;
        //variables
        String filepath = "C:/_Java/";
        String filename = "Inventory.txt";
        String file = "";
        boolean seek = false;
        boolean flag = false;
        ArrayList<Vending> vend = new ArrayList<>();
        ArrayList<String> iNames = new ArrayList<>();
        ArrayList<Integer> iPrice = new ArrayList<>();
        /////////////////////////////
        // DEFAULTS FOR DEBUGGING: //
        /////////////////////////////
        int vmNum = 4; //count for number of vending machines
        int inTotal = 40; //inventory total (count for number of items
        int maxCost = 250;
        int maxQuantity = 35;
      	boolean debug = true;
        boolean receipt = true;
        //////////////////////////////
        // PSEUDORANDOM CONTROLLERS //
        //////////////////////////////
        int custCount = r(200); //number of potential clients
        int custChance = 75; //liklihood of getting a customer
        int custMoney = 1000; //max amount of money in cents
        int custDesire = 5; //max number of items a customer will purchase
        //SEEK FOR NEWEST FILE; READ IT INTO INVENTORY
        while (!seek && !flag)
        {
            file = filepath + Integer.toString(yyyy+mm+dd) + filename;
            try
            {
                //CREATE VENDING MACHINES
                if((vmNum = countVm(file))==-1) throw new FileNotFoundException();
                for (int i = 0; i < vmNum; i++)
                {
                    vend.add(new Vending(debug));
                }
                BufferedReader br = new BufferedReader(new FileReader(file));
                seek = true;
                String l;
                inTotal = 0;
                while((l=br.readLine())!=null)
                //FOR EACH ITEM IN THE INVENTORY, ADD IF QTY>0
                {
                    String name = l.split(",")[0];
                    iNames.add(name);
                    int price = Integer.parseInt(l.split(",")[1]);
                    iPrice.add(price);
                    for (int i = 0; i < vmNum; i++)
                    {
                        int quantity = Integer.parseInt(l.split(",")[i + 2]);
                        if (quantity > 0)
                        {
                            vend.get(i).addItem(name, price, quantity);
                        }
                    }
                    inTotal++;
                }
                br.close();
                print(file);
            }
            catch (Exception e) //DIDN'T FIND A FILE FOR THIS DATE, SEARCHING FOR EARLIER DATE
            {
                if (dd > 1)
                    dd--;
                else
                {
                    if (mm > 100)
                    {
                        mm-=100;
                        dd = 31;
                    }
                    else
                    {
                        if (yyyy > 20150000)
                        {
                            yyyy-=10000;
                            mm = 1200;
                            dd = 31;
                        }
                        else
                        {
                            print("ERROR, NO FILE FOUND");
                            flag = true;
                        }
                    }
                }
            }
        }
        if(flag) //NO FILE FOUND, CREATE RANDOMIZED INVENTORY.
        {
            vmNum = 3;
            for (int i = 0; i < vmNum; i++)
            {
                vend.add(new Vending(debug));
            }
            for (int i = 0; i < inTotal; i++)
            {
                String name = "item " + i;
                iNames.add(name);
                int price = r(maxCost);
                iPrice.add(price);
                for (int i1 = 0; i1 < vmNum; i1++)
                {
                    int quantity = r(maxQuantity);
                    if (quantity > 8)
                    {
                        vend.get(i1).addItem(name, price, quantity);
                    }
                }
            }
        }
        //END LOADING OF INVENTORY

        //LOOP THROUGH RANDOM NUMBER OF CUSTOMERS
        for (int i = 0; i < custCount; i++)
        {
            //RANDOM % CHANCE TO HAVE A CLIENT
            if(r(100)<custChance)
            {
                //RANDOM SELECTION TO DETERMINE WHICH VENDING MACHINE
                Customer c = new Customer(r(custMoney),r(vmNum),r(custDesire));
                if(debug){
                  print("");
                  print("");
                  print("New customer arriving");
                  print("Customer wishes to buy " + c.getDesire() + " items.");
                }
                //ATTEMPT TO PURCHASE RANDOM FOOD, MAKE 5 ATTEMPTS IN CASE OF INSUFFICIENT FUNDS (TRY ANOTHER ITEM)
                for (int i1 = 0; i1 < c.getDesire(); i1++)
                {
                    Vending v = vend.get(c.getVm());
                    int attempts = 0;
                    for (int i2 = 0; i2<5; i2++)
                    {
                      	int selected = r(v.foods.size());
                      	int purchased = v.purchase(selected,c.getMoney(),receipt);
                      	if (purchased!=c.getMoney()) {
                        	c.setMoney(purchased);
                          	i2 = 5;
                        }
                    }
                }
            }
        }
        //DONE LOOPING THROUGH CUSTOMERS
      
        //PRINT OUTPUT TO CONSOLE
        debug = false;
        if(debug)
        {
            String d = vend.get(i).total()/100;
            String c = vend.get(i).total()%100;
            if(c.length()<2) c = "0" + c;
            print("Sales Totals"); 
            for (int i = 0; i < vmNum; i++) {
                print("Vending machine " + i + " sold: $" + d + "." + c );///////////////////////////////////////////////////////////////////
                Vending v = vend.get(i);
                for (int i1 = 0; i1 < inTotal; i1++) 
                {
                    print("  " + iNames.get(i1) + ": " + v.sold(iNames.get(i1)));
                }
            }
            print("");
            print("");
            String l = "";
            for (int i = 0; i < inTotal; i++) 
            {
                l= iNames.get(i) + "," + iPrice.get(i) + ",";
                for (int i1 = 0; i1 < vmNum; i1++) 
                {
                    if(i1<vmNum-1)
                    {
                        l = l + vend.get(i1).endQtyByName(iNames.get(i)) + ",";
                    }
                    else
                    {
                        print(l + vend.get(i1).endQtyByName(iNames.get(i)));
                    }
                }
            }
        }
        //END PRINTING TO CONSOLE
        yyyy = Integer.parseInt(dateFormat.format(dateY));
	    mm = Integer.parseInt(dateFormat.format(dateM));
	    dd = Integer.parseInt(dateFormat.format(dateD));
        yyyy*= 10000;
        mm*=100;
        //EXPORT SALES
        try {
            File fileW = new File(filepath + (yyyy+mm+dd+1) + "Sales.txt");
            if(!fileW.exists()) fileW.createNewFile();
            PrintStream write = new PrintStream(fileW);
            write.println("Sales Totals");
                for (int i = 0; i < vmNum; i++) {
                    String d = vend.get(i).total()/100;
                    String c = vend.get(i).total()%100;
                    if(c.length()<2) c = "0" + c;
                    write.println("Vending machine " + i + " sold: $" + d + "." + c );
                    Vending v = vend.get(i);
                    for (int i1 = 0; i1 < inTotal; i1++) {
                      	write.println("  " + iNames.get(i1) + ": " + v.sold(iNames.get(i1)));
                    }
                }
            write.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        //END EXPORTING SALES
        //EXPORT INVENTORY
        try {
            File fileW = new File(filepath + (yyyy+mm+dd+1) + "Inventory.txt");
            if(!fileW.exists()) fileW.createNewFile();
            PrintStream write = new PrintStream(fileW);
            ArrayList<String> items;
            String l = "";
            for (int i = 0; i < inTotal; i++) {
                l= iNames.get(i) + "," + iPrice.get(i) + ",";
                for (int i1 = 0; i1 < vmNum; i1++) {
                    if(i1<vmNum-1)
                    {
                        l = l + vend.get(i1).endQtyByName(iNames.get(i)) + ",";
                    }
                    else
                    {
                        write.println(l + vend.get(i1).endQtyByName(iNames.get(i)));
                    }
                }
            }
            write.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        //END EXPORTING INVENTORY
    }
    private static int r(int num)
    {
        return (int)(Math.random()*num);
    }
    private static void print(String out){
        System.out.println(out);
    }
    public static int countVm(String aFile) throws IOException {
        int vm;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(aFile));
            if ((br.readLine()) != null);
            vm = br.readLine().split(",").length-2;
            if(vm>0) return vm; else return -1;
        } catch (Exception ex) {
            return -1;
        } finally {
            if(br != null)
                br.close();
        }
    }
}
