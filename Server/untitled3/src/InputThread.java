import java.util.Scanner;

public class InputThread extends Thread {
    public static int Input = 1;
    public static int mode = 1;
    public static int rush_mode = 5;
    public static int night_mode = 7;


    public int getMode(){ return mode;}
    public int getRushMode() { return rush_mode; }
    public int getNight_mode() {return night_mode; }

    public void run(){
        super.run();
        Scanner scan = new Scanner(System.in);

        while (true)
        {
            Input = scan.nextInt();
            System.out.println(Input);
            if(Input == 1)
                mode = 1;
            else if(Input == 2)
                mode = 2;
            else if(Input == 3 )
                mode = 3;
            else if(Input == 4)
                mode = 4;
            else if(Input == 5)
                rush_mode = 5;
            else if(Input == 6)
                rush_mode = 6;
            else if(Input == 7)
                night_mode = 7;
            else if(Input == 8)
                night_mode = 8;

        }
    }

}
