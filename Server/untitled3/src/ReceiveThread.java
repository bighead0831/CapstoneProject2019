import java.io.*;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class ReceiveThread extends Thread{

    private Socket m_socket;

    public static int Hor_Up=1;
    public static int Hor_Down=3;
    public static int Ver_Left=5;
    public static int Ver_Right=7;

    public static long Casting_Hor_Up=0;
    public static long Casting_Hor_Down=0;
    public static long Casting_Ver_Left=0;
    public static long Casting_Ver_Right=0;

    public int getHor_Up()
    {
        return  Hor_Up;
    }
    public int getHor_Down()
    {
        return Hor_Down;
    }
    public int getVer_Left() { return Ver_Left; }
    public int getVer_Right() { return  Ver_Right; }

    // 라즈베리파이 측에서 어떻게 주는지 양식을 지정해야함. 채팅처럼 한문장받아서 바로 출력하는게 아니고 받아야될게 HRZ랑 VTC 2개가 있으니까
    // 한문장으로 받아서 파싱 적절히 해서 각각 쓰는게 바람직해보임
    public void run(){
        super.run();

        try{

            JSONParser parser = new JSONParser();

            while (true){
                try {
                    InputStream input_data = m_socket.getInputStream();
                    byte[] receiveBuffer = new byte[100];
                    input_data.read(receiveBuffer);
                    System.out.println("카메라 : " + new String(receiveBuffer));

                    FileWriter file = new FileWriter("C:\\Users\\bjwan\\Downloads\\bal\\CarNum.json");
                    file.write(new String(receiveBuffer).trim());
                    file.flush();
                    file.close();

                    Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarNum.json"));
                    JSONObject jsonObject = (JSONObject) obj;
                    Casting_Hor_Up = (long)jsonObject.get("hor_up");
                    //hor_bottom = hor_down
                    Casting_Hor_Down = (long) jsonObject.get("hor_bottom");
                    Casting_Ver_Left = (long) jsonObject.get("ver_left");
                    Casting_Ver_Right = (long) jsonObject.get("ver_right");

                    Hor_Up = (int)Casting_Hor_Up;
                    Hor_Down = (int)Casting_Hor_Down;
                    Ver_Left = (int)Casting_Ver_Left;
                    Ver_Right = (int)Casting_Ver_Right;

                } catch (IOException e) {
                }
            }

        }
        catch (Exception e){e.printStackTrace();}

    }
    public void setSocket(Socket _socket)
    {
        m_socket = _socket;
    }
}











//import java.io.BufferedReader;
//        import java.io.IOException;
//        import java.io.InputStreamReader;
//        import java.net.Socket;
//
//
//public class ReceiveThread extends Thread{
//
//    private Socket m_socket;
//
//    public static int HRZ=3;
//    public static int VTC=0;
//
//    public int getHRZ()
//    {
//        return  HRZ;
//    }
//    public  int getVTC()
//    {
//        return VTC;
//    }
//
//    // 라즈베리파이 측에서 어떻게 주는지 양식을 지정해야함. 채팅처럼 한문장받아서 바로 출력하는게 아니고 받아야될게 HRZ랑 VTC 2개가 있으니까
//    // 한문장으로 받아서 파싱 적절히 해서 각각 쓰는게 바람직해보임
//    public void run(){
//        super.run();
//
//        try{
//            BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
//
//            String receiveString;
//
//            while (true){
//                receiveString = tmpbuf.readLine();
//
//                if(receiveString == null){
//                    System.out.println("연결이 끊겼습니다.");
//                    break;
//                }
//                else{
//
//                    HRZ = Integer.valueOf(receiveString);   //일단 시험용으로 HRZ만 받는다.
//                    System.out.println(HRZ);
//
//                }
//            }
//            tmpbuf.close();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//    public void setSocket(Socket _socket)
//    {
//        m_socket = _socket;
//    }
//}
