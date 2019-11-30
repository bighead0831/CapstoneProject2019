import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Socket;

public class WaitThread extends Thread {

    private OutputStream outputdata;
    private Socket m_socket;

    int Mode = 1;
    int Rush_Mode = 5;
    int Night_Mode =7;
    String Time_Now = "rush";

    InputThread inputThread = new InputThread();
    Reader reader = new Reader();

    int Total_Time_rush = 0;
    int Total_Time_rush_2 = 0;
    int Total_Time_night = 0;
    int Total_Time_night_2 = 0;

    int Total_Time1 = 0;
    int Total_Time2 = 0;
    int Total_Time3 = 0;
    int Total_Time4 = 0;

    public void run(){
        super.run();

        try{

            while(true)
            {
                Time_Now = reader.getTimeNow();


                while(Time_Now.equals("rush"))
                {
                    Rush_Mode = inputThread.getRushMode();

                    if(Rush_Mode == 5){
                        Total_Time_rush++;
                        //System.out.println("rush 1 정책 적용시간 : " + Total_Time_rush);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }
                    if(Rush_Mode == 6){
                        Total_Time_rush_2++;
                        //System.out.println("rush 2 정책 적용시간 : " + Total_Time_rush_2);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }
                }

                while(Time_Now.equals("night"))
                {
                    Night_Mode = inputThread.getNight_mode();

                    if(Night_Mode == 7)
                    {
                        Total_Time_night++;
                        //System.out.println("night 1 정책 적용시간 : " + Total_Time_night);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }

                    if(Night_Mode == 8)
                    {
                        Total_Time_night_2++;
                        //System.out.println("night 2 정책 적용시간 : " + Total_Time_night_2);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }
                }

                while (Time_Now.equals("normal")) {
                    Mode = inputThread.getMode();

                    if (Mode == 1) {
                        Total_Time1++;
                        //System.out.println("정책1 적용시간 : " + Total_Time1);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }
                    if (Mode == 2) {
                        Total_Time2++;
                        //System.out.println("정책2 적용시간 : " + Total_Time2);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }
                    if (Mode == 3) {
                        Total_Time3++;
                        //System.out.println("정책3 적용시간 : " + Total_Time3);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }
                    if (Mode == 4) {
                        Total_Time4++;
                        //System.out.println("정책4 적용시간 : " + Total_Time4);
                        Thread.sleep(1000);
                        Time_Now = reader.getTimeNow();
                    }
                }
            }

        }
        catch(Exception e)
        {e.printStackTrace();}
    }

    public void setOutputStream(OutputStream output_data)
    {
        outputdata = output_data;
    }
    public void setSocket(Socket _socket)
    {
        m_socket = _socket;
    }


}