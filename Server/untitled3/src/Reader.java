import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Reader {


    static BufferedReader br = null;

    private static OutputStream outputdata;
    private static Socket m_socket;

    static String Time_now = "";
    static int Days = 0;
    static long Casting_Days=0;
    static int ComboDays = 0;
    static int Current_Days = 0;
    static String Policy = "";

    static int Mode_now = 1;
    static int Rush_Mode_now = 5;
    static int Night_Mode_now = 7;


    //차량 수(공용)
    static int hor_up = 0;
    static int hor_down = 0;
    static int ver_left = 0;
    static int ver_right = 0;

    static long Casting_hor_up = 0;
    static long Casting_hor_down = 0;
    static long Casting_ver_left = 0;
    static long Casting_ver_right = 0;


    //사람 수(공용)
    static int hor_up_people = 0;
    static int hor_down_people = 0;
    static int ver_left_people = 0;
    static int ver_right_people = 0;

    static long Casting_hor_up_people = 0;
    static long Casting_hor_down_people = 0;
    static long Casting_ver_left_people = 0;
    static long Casting_ver_right_people = 0;


    //차량 대기시간(공용)
    static Double hor_up_wait;
    static Double hor_down_wait;
    static Double ver_left_wait;
    static Double ver_right_wait;

    //사람 대기시간(공용)
    static Double hor_up_people_wait;
    static Double hor_down_people_wait;
    static Double ver_left_people_wait;
    static Double ver_right_people_wait;



    //통계용 변수
//Rush 정책의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_Rush = new long[100];
    static long[] Total_Car_Hor_Down_Rush = new long[100];
    static long[] Total_Car_Ver_Left_Rush = new long[100];
    static long[] Total_Car_Ver_Right_Rush = new long[100];

    //Rush 정책의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_Rush = new double[100];
    static double[] Total_Car_wait_Hor_Down_Rush = new double[100];
    static double[] Total_Car_wait_Ver_Left_Rush = new double[100];
    static double[] Total_Car_wait_Ver_Right_Rush = new double[100];

    //Rush 정책의 총 사람 수(일별)
    static long[] Total_People_Hor_Up_Rush = new long[100];
    static long[] Total_People_Hor_Down_Rush = new long[100];
    static long[] Total_People_Ver_Left_Rush = new long[100];
    static long[] Total_People_Ver_Right_Rush = new long[100];

    //Rush 정책의 총 사람 대기시간(일별)
    static double[] Total_People_wait_Hor_Up_Rush = new double[100];
    static double[] Total_People_wait_Hor_Down_Rush = new double[100];
    static double[] Total_People_wait_Ver_Left_Rush = new double[100];
    static double[] Total_People_wait_Ver_Right_Rush = new double[100];

    //Rush 정책의 차량의 정책평가
    static double[] Result_Car_Hor_Up_Rush = new double[100];
    static double[] Result_Car_Hor_Down_Rush = new double[100];
    static double[] Result_Car_Ver_Left_Rush = new double[100];
    static double[] Result_Car_Ver_Right_Rush = new double[100];

    //Rush 정책의 사람의 정책평가
    static double[] Result_People_Hor_Up_Rush = new double[100];
    static double[] Result_People_Hor_Down_Rush = new double[100];
    static double[] Result_People_Ver_Left_Rush = new double[100];
    static double[] Result_People_Ver_Right_Rush = new double[100];



    //Rush 2 정책의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_Rush_2 = new long[100];
    static long[] Total_Car_Hor_Down_Rush_2 = new long[100];
    static long[] Total_Car_Ver_Left_Rush_2 = new long[100];
    static long[] Total_Car_Ver_Right_Rush_2 = new long[100];

    //Rush 2 정책의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_Rush_2 = new double[100];
    static double[] Total_Car_wait_Hor_Down_Rush_2 = new double[100];
    static double[] Total_Car_wait_Ver_Left_Rush_2 = new double[100];
    static double[] Total_Car_wait_Ver_Right_Rush_2 = new double[100];

    //Rush 2 정책의 총 사람 수(일별)
    static long[] Total_People_Hor_Up_Rush_2 = new long[100];
    static long[] Total_People_Hor_Down_Rush_2 = new long[100];
    static long[] Total_People_Ver_Left_Rush_2 = new long[100];
    static long[] Total_People_Ver_Right_Rush_2 = new long[100];

    //Rush 2 정책의 총 사람 대기시간(일별)
    static double[] Total_People_wait_Hor_Up_Rush_2 = new double[100];
    static double[] Total_People_wait_Hor_Down_Rush_2 = new double[100];
    static double[] Total_People_wait_Ver_Left_Rush_2 = new double[100];
    static double[] Total_People_wait_Ver_Right_Rush_2 = new double[100];

    //Rush 2 정책의 차량의 정책평가
    static double[] Result_Car_Hor_Up_Rush_2 = new double[100];
    static double[] Result_Car_Hor_Down_Rush_2 = new double[100];
    static double[] Result_Car_Ver_Left_Rush_2 = new double[100];
    static double[] Result_Car_Ver_Right_Rush_2 = new double[100];

    //Rush 2 정책의 사람의 정책평가
    static double[] Result_People_Hor_Up_Rush_2 = new double[100];
    static double[] Result_People_Hor_Down_Rush_2 = new double[100];
    static double[] Result_People_Ver_Left_Rush_2 = new double[100];
    static double[] Result_People_Ver_Right_Rush_2 = new double[100];




    //Night 정책의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_Night = new long[100];
    static long[] Total_Car_Hor_Down_Night = new long[100];
    static long[] Total_Car_Ver_Left_Night = new long[100];
    static long[] Total_Car_Ver_Right_Night = new long[100];

    //Night 정책의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_Night = new double[100];
    static double[] Total_Car_wait_Hor_Down_Night = new double[100];
    static double[] Total_Car_wait_Ver_Left_Night = new double[100];
    static double[] Total_Car_wait_Ver_Right_Night = new double[100];

    //Night 정책의 총 사람 수(일별)
    static long[] Total_people_Hor_Up_Night = new long[100];
    static long[] Total_people_Hor_Down_Night = new long[100];
    static long[] Total_people_Ver_Left_Night = new long[100];
    static long[] Total_people_Ver_Right_Night = new long[100];

    //Night 정책의 총 사람 대기시간(일별)
    static double[] Total_people_wait_Hor_Up_Night = new double[100];
    static double[] Total_people_wait_Hor_Down_Night = new double[100];
    static double[] Total_people_wait_Ver_Left_Night = new double[100];
    static double[] Total_people_wait_Ver_Right_Night = new double[100];

    //Night 정책의 차량의 정책평가
    static double[] Result_Car_Hor_Up_Night = new double[100];
    static double[] Result_Car_Hor_Down_Night = new double[100];
    static double[] Result_Car_Ver_Left_Night = new double[100];
    static double[] Result_Car_Ver_Right_Night = new double[100];

    //Night 정책의 사람의 정책평가
    static double[] Result_People_Hor_Up_Night = new double[100];
    static double[] Result_People_Hor_Down_Night = new double[100];
    static double[] Result_People_Ver_Left_Night = new double[100];
    static double[] Result_People_Ver_Right_Night = new double[100];





    //Night 2 정책의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_Night_2 = new long[100];
    static long[] Total_Car_Hor_Down_Night_2 = new long[100];
    static long[] Total_Car_Ver_Left_Night_2 = new long[100];
    static long[] Total_Car_Ver_Right_Night_2 = new long[100];

    //Night 2 정책의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_Night_2 = new double[100];
    static double[] Total_Car_wait_Hor_Down_Night_2 = new double[100];
    static double[] Total_Car_wait_Ver_Left_Night_2 = new double[100];
    static double[] Total_Car_wait_Ver_Right_Night_2 = new double[100];

    //Night 2 정책의 총 사람 수(일별)
    static long[] Total_people_Hor_Up_Night_2 = new long[100];
    static long[] Total_people_Hor_Down_Night_2 = new long[100];
    static long[] Total_people_Ver_Left_Night_2 = new long[100];
    static long[] Total_people_Ver_Right_Night_2 = new long[100];

    //Night 2 정책의 총 사람 대기시간(일별)
    static double[] Total_people_wait_Hor_Up_Night_2 = new double[100];
    static double[] Total_people_wait_Hor_Down_Night_2 = new double[100];
    static double[] Total_people_wait_Ver_Left_Night_2 = new double[100];
    static double[] Total_people_wait_Ver_Right_Night_2 = new double[100];

    //Night 2 정책의 차량의 정책평가
    static double[] Result_Car_Hor_Up_Night_2 = new double[100];
    static double[] Result_Car_Hor_Down_Night_2 = new double[100];
    static double[] Result_Car_Ver_Left_Night_2 = new double[100];
    static double[] Result_Car_Ver_Right_Night_2 = new double[100];

    //Night 2 정책의 사람의 정책평가
    static double[] Result_People_Hor_Up_Night_2 = new double[100];
    static double[] Result_People_Hor_Down_Night_2 = new double[100];
    static double[] Result_People_Ver_Left_Night_2 = new double[100];
    static double[] Result_People_Ver_Right_Night_2 = new double[100];





    //정책1의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_1 = new long[100];
    static long[] Total_Car_Hor_Down_1 = new long[100];
    static long[] Total_Car_Ver_Left_1 = new long[100];
    static long[] Total_Car_Ver_Right_1 = new long[100];

    //정책1의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_1 = new double[100];
    static double[] Total_Car_wait_Hor_Down_1 = new double[100];
    static double[] Total_Car_wait_Ver_Left_1 = new double[100];
    static double[] Total_Car_wait_Ver_Right_1 = new double[100];

    //정책1의 총 사람 수(일별)
    static long[] Total_people_Hor_Up_1 = new long[100];
    static long[] Total_people_Hor_Down_1 = new long[100];
    static long[] Total_people_Ver_Left_1 = new long[100];
    static long[] Total_people_Ver_Right_1 = new long[100];

    //정책1의 총 사람 대기시간(일별)
    static double[] Total_people_wait_Hor_Up_1 = new double[100];
    static double[] Total_people_wait_Hor_Down_1 = new double[100];
    static double[] Total_people_wait_Ver_Left_1 = new double[100];
    static double[] Total_people_wait_Ver_Right_1 = new double[100];

    //정책 1 의 차량의 정책평가
    static double[] Result_Car_Hor_Up_1 = new double[100];
    static double[] Result_Car_Hor_Down_1 = new double[100];
    static double[] Result_Car_Ver_Left_1 = new double[100];
    static double[] Result_Car_Ver_Right_1 = new double[100];

    //정책 1 의 사람의 정책평가
    static double[] Result_People_Hor_Up_1 = new double[100];
    static double[] Result_People_Hor_Down_1 = new double[100];
    static double[] Result_People_Ver_Left_1 = new double[100];
    static double[] Result_People_Ver_Right_1 = new double[100];





    //정책2의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_2 = new long[100];
    static long[] Total_Car_Hor_Down_2 = new long[100];
    static long[] Total_Car_Ver_Left_2 = new long[100];
    static long[] Total_Car_Ver_Right_2 = new long[100];

    //정책2의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_2 = new double[100];
    static double[] Total_Car_wait_Hor_Down_2 = new double[100];
    static double[] Total_Car_wait_Ver_Left_2 = new double[100];
    static double[] Total_Car_wait_Ver_Right_2 = new double[100];

    //정책2의 총 사람 수(일별)
    static long[] Total_people_Hor_Up_2 = new long[100];
    static long[] Total_people_Hor_Down_2 = new long[100];
    static long[] Total_people_Ver_Left_2 = new long[100];
    static long[] Total_people_Ver_Right_2 = new long[100];

    //정책2의 총 사람 대기시간(일별)
    static double[] Total_people_wait_Hor_Up_2 = new double[100];
    static double[] Total_people_wait_Hor_Down_2 = new double[100];
    static double[] Total_people_wait_Ver_Left_2 = new double[100];
    static double[] Total_people_wait_Ver_Right_2 = new double[100];

    //정책 2 의 차량의 정책평가
    static double[] Result_Car_Hor_Up_2 = new double[100];
    static double[] Result_Car_Hor_Down_2 = new double[100];
    static double[] Result_Car_Ver_Left_2 = new double[100];
    static double[] Result_Car_Ver_Right_2 = new double[100];

    //정책 2 의 사람의 정책평가
    static double[] Result_People_Hor_Up_2 = new double[100];
    static double[] Result_People_Hor_Down_2 = new double[100];
    static double[] Result_People_Ver_Left_2 = new double[100];
    static double[] Result_People_Ver_Right_2 = new double[100];



    //정책3의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_3 = new long[100];
    static long[] Total_Car_Hor_Down_3 = new long[100];
    static long[] Total_Car_Ver_Left_3 = new long[100];
    static long[] Total_Car_Ver_Right_3 = new long[100];

    //정책3의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_3 = new double[100];
    static double[] Total_Car_wait_Hor_Down_3 = new double[100];
    static double[] Total_Car_wait_Ver_Left_3 = new double[100];
    static double[] Total_Car_wait_Ver_Right_3 = new double[100];

    //정책3의 총 사람 수(일별)
    static long[] Total_people_Hor_Up_3 = new long[100];
    static long[] Total_people_Hor_Down_3 = new long[100];
    static long[] Total_people_Ver_Left_3 = new long[100];
    static long[] Total_people_Ver_Right_3 = new long[100];

    //정책3의 총 사람 대기시간(일별)
    static double[] Total_people_wait_Hor_Up_3 = new double[100];
    static double[] Total_people_wait_Hor_Down_3 = new double[100];
    static double[] Total_people_wait_Ver_Left_3 = new double[100];
    static double[] Total_people_wait_Ver_Right_3 = new double[100];

    //정책 3 의 차량의 정책평가
    static double[] Result_Car_Hor_Up_3 = new double[100];
    static double[] Result_Car_Hor_Down_3 = new double[100];
    static double[] Result_Car_Ver_Left_3 = new double[100];
    static double[] Result_Car_Ver_Right_3 = new double[100];

    //정책 3 의 사람의 정책평가
    static double[] Result_People_Hor_Up_3 = new double[100];
    static double[] Result_People_Hor_Down_3 = new double[100];
    static double[] Result_People_Ver_Left_3 = new double[100];
    static double[] Result_People_Ver_Right_3 = new double[100];



    //정책4의 총 차량 수(일별)
    static long[] Total_Car_Hor_Up_4 = new long[100];
    static long[] Total_Car_Hor_Down_4 = new long[100];
    static long[] Total_Car_Ver_Left_4 = new long[100];
    static long[] Total_Car_Ver_Right_4 = new long[100];

    //정책4의 총 차량 대기시간(일별)
    static double[] Total_Car_wait_Hor_Up_4 = new double[100];
    static double[] Total_Car_wait_Hor_Down_4 = new double[100];
    static double[] Total_Car_wait_Ver_Left_4 = new double[100];
    static double[] Total_Car_wait_Ver_Right_4 = new double[100];

    //정책4의 총 사람 수(일별)
    static long[] Total_people_Hor_Up_4 = new long[100];
    static long[] Total_people_Hor_Down_4 = new long[100];
    static long[] Total_people_Ver_Left_4 = new long[100];
    static long[] Total_people_Ver_Right_4 = new long[100];

    //정책4의 총 사람 대기시간(일별)
    static double[] Total_people_wait_Hor_Up_4 = new double[100];
    static double[] Total_people_wait_Hor_Down_4 = new double[100];
    static double[] Total_people_wait_Ver_Left_4 = new double[100];
    static double[] Total_people_wait_Ver_Right_4 = new double[100];

    //정책 4 의 차량의 정책평가
    static double[] Result_Car_Hor_Up_4 = new double[100];
    static double[] Result_Car_Hor_Down_4 = new double[100];
    static double[] Result_Car_Ver_Left_4 = new double[100];
    static double[] Result_Car_Ver_Right_4 = new double[100];

    //정책 4 의 사람의 정책평가
    static double[] Result_People_Hor_Up_4 = new double[100];
    static double[] Result_People_Hor_Down_4 = new double[100];
    static double[] Result_People_Ver_Left_4 = new double[100];
    static double[] Result_People_Ver_Right_4 = new double[100];




    //모드1 (직좌 모드)에서 한 차선이 신호를 받았는지 안받았는지
//한 사이클 내에서 한번만 받아야 하므로
    static boolean hor_up_hadSignal = false;
    static boolean hor_down_hadSignal = false;
    static boolean ver_left_hadSignal = false;
    static boolean ver_right_hadSignal = false;


/*static double ain = 0;
static double bin = 0;
static double cin = 0;
static double din = 0;

public static double GetAin() { return ain; }
public static double GetBin() { return bin; }
public static double GetCin() { return cin; }
public static double GetDin() { return din; }*/






// Mode 1 은 직진 및 좌회전
// Mode 2 는 좌회전 후 직진
// Mode 3 은 직진 후 좌회전


    public static void main (String[] args) {

        JSONParser parser = new JSONParser();
        JSONParser parser1 = new JSONParser();
        JSONParser parser2 = new JSONParser();
        String CarInfoREQUEST = "carinfo";
        String PeopleInfoREQUEST = "peopleinfo";
        String TimeInfoREQUEST = "timeinfo";




        JFrame frame = new JFrame("Policy evaluation");
        frame.setLocation(500,200);
        frame.setPreferredSize(new Dimension(500,500));
        Container contentPane = frame.getContentPane();

        DrawingPanel drawingPanel = new DrawingPanel();
        contentPane.add(drawingPanel, BorderLayout.CENTER);
//그래프를 그릴 패널

        JPanel controlPanel = new JPanel();
        JPanel controlPanel2 = new JPanel();
        JTextField text1 = new JTextField(3);
        JTextField text2 = new JTextField(3);
        JTextField text3 = new JTextField(3);

        JLabel Current_Date = new JLabel("1");
        JLabel Current_Time = new JLabel("Normal");
        JLabel Current_Policy = new JLabel("Normal-1");



        String[] policy = {"Normal-1", "Normal-2", "Normal-3", "Normal-4", "Rush-1",
                "Rush-2", "Night-1", "Night-2"};

        String[] Date = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
                "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
                "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75",
                "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90"};

        JComboBox strCombo = new JComboBox(policy);
        JComboBox strCombo2 = new JComboBox(Date);

        controlPanel2.add(new JLabel("Date : "));
        controlPanel2.add(Current_Date);
        controlPanel2.add(new JLabel(" Time : "));
        controlPanel2.add(Current_Time);
        controlPanel2.add(new JLabel(" Policy : "));
        controlPanel2.add(Current_Policy);
        controlPanel.add(new JLabel("날짜"));
        controlPanel.add(strCombo2);
        controlPanel.add(strCombo);
        controlPanel2.add(Current_Policy);

        contentPane.add(controlPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        strCombo2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb2 = (JComboBox)e.getSource();
                int index = cb2.getSelectedIndex();
                ComboDays = index;

            }
        });

        strCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                int index = cb.getSelectedIndex();
                if(index == 0) {
                    drawingPanel.setAain(Result_Car_Hor_Up_1[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_1[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_1[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_1[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_1[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_1[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_1[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_1[ComboDays]);
                    drawingPanel.repaint();
                }
                else if(index == 1) {
                    drawingPanel.setAain(Result_Car_Hor_Up_2[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_2[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_2[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_2[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_2[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_2[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_2[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_2[ComboDays]);
                    drawingPanel.repaint();
                }
                else if (index == 2) {
                    drawingPanel.setAain(Result_Car_Hor_Up_3[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_3[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_3[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_3[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_3[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_3[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_3[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_3[ComboDays]);
                    drawingPanel.repaint();
                }
                else if(index == 3) {
                    drawingPanel.setAain(Result_Car_Hor_Up_4[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_4[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_4[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_4[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_4[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_4[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_4[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_4[ComboDays]);
                    drawingPanel.repaint();
                }
                else if(index == 4){
                    drawingPanel.setAain(Result_Car_Hor_Up_Rush[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_Rush[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_Rush[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_Rush[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_Rush[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_Rush[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_Rush[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_Rush[ComboDays]);
                    drawingPanel.repaint();
                }
                else if(index == 5){
                    drawingPanel.setAain(Result_Car_Hor_Up_Rush_2[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_Rush_2[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_Rush_2[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_Rush_2[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_Rush_2[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_Rush_2[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_Rush_2[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_Rush_2[ComboDays]);
                    drawingPanel.repaint();
                }
                else if(index == 6){
                    drawingPanel.setAain(Result_Car_Hor_Up_Night[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_Night[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_Night[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_Night[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_Night[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_Night[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_Night[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_Night[ComboDays]);
                    drawingPanel.repaint();
                }
                else if(index == 7){
                    drawingPanel.setAain(Result_Car_Hor_Up_Night_2[ComboDays]);
                    drawingPanel.setBbin(Result_Car_Hor_Down_Night_2[ComboDays]);
                    drawingPanel.setCcin(Result_Car_Ver_Left_Night_2[ComboDays]);
                    drawingPanel.setDdin(Result_Car_Ver_Right_Night_2[ComboDays]);

                    drawingPanel.setEein(Result_People_Hor_Up_Night_2[ComboDays]);
                    drawingPanel.setFfin(Result_People_Hor_Down_Night_2[ComboDays]);
                    drawingPanel.setGgin(Result_People_Ver_Left_Night_2[ComboDays]);
                    drawingPanel.setHhin(Result_People_Ver_Right_Night_2[ComboDays]);
                    drawingPanel.repaint();

                }
            }
        });
//button.addActionListener(new DrawActionListener(text1,text2,text3,drawingPanel));
//"그래프 그리기" 버튼을 눌렀을때 작동 할 리스터등록
        frame.add(controlPanel2,BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);

/*
try {
while (true) {
ain += 5;
bin += 5;
cin += 5;
din += 5;
Thread.sleep(1000);
drawingPanel.repaint();
}
}
catch (InterruptedException e){e.printStackTrace();}*/


        try {
            ServerSocket s_socket = new ServerSocket(8888);
            Socket c_socket = s_socket.accept();
            OutputStream output_data = c_socket.getOutputStream();

            System.out.println("디스플레이 접속됨");


            ServerSocket s_socket2 = new ServerSocket(8887);
            Socket c_socket2 = s_socket2.accept();
            System.out.println("카메라 접속됨");


            ReceiveThread receiveThread = new ReceiveThread();
            receiveThread.setSocket(c_socket2);
            receiveThread.start();

            WaitThread waitThread = new WaitThread();
            waitThread.setSocket(c_socket);
            waitThread.setOutputStream(output_data);
            waitThread.start();

            InputThread inputThread = new InputThread();
            inputThread.start();
            Mode_now = inputThread.getMode();
            Rush_Mode_now = inputThread.getRushMode();
            Night_Mode_now = inputThread.getNight_mode();

            TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
            TIME_INFO_RECEIVE(c_socket);


// =================================================================================
// 스레드에 아직 VTC_NUM 처럼 쓰여진게 있으므로 VTC_Left, VTC_Right 처럼 고칠것
// 지금 HRZ, VTC 생성자들 비활성화 상태이니 나중에 활성화 시킬것
// =================================================================================
            try {

                while (true) {
                    Mode_now = inputThread.getMode();
                    Rush_Mode_now = inputThread.getRushMode();
                    Night_Mode_now = inputThread.getNight_mode();

                    TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
                    TIME_INFO_RECEIVE(c_socket);
                    System.out.println("Time now : " + Time_now);
                    Thread.sleep(1000);

                    while (Time_now.equals("rush")) {
                        Current_Time.setText("Rush Hour");
                        while(Rush_Mode_now == 5) {

                            Policy = "Rush-1";
                            Current_Policy.setText(Policy);

                            try {
// "SavedSignal"이 신호등 정보가 담긴 파일. 정책결정할때 작성해야함
                                Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                                JSONObject jsonObject2 = (JSONObject) obj2;

                                int i = 0;
                                while (i < 4) {

                                    System.out.println("Rush Hour Now");
                                    System.out.println("Rush Hour Now");
                                    System.out.println("Rush Hour Now");
                                    System.out.println("Rush Hour Now");



                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    JSONObject jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if (hor_up_hadSignal == true) {
                                        hor_up = -1;
                                    }
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if (hor_down_hadSignal == true) {
                                        hor_down = -1;
                                    }
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if (ver_left_hadSignal == true) {
                                        ver_left = -1;
                                    }
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if (ver_right_hadSignal == true) {
                                        ver_right = -1;
                                    }*/


                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("==========================================================================================");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("==========================================================================================");


                                    if (hor_up >= hor_down && hor_up >= ver_left && hor_up >= ver_right && hor_up_hadSignal == false) {
                                        hor_up_hadSignal = true;

//==============================================================================================================================================
//디스플레이에서 차량수 직접 받는 상황 테스트중이므로 카메라가 잘 될때는 디스플레이에게서 차량수 받는건 지우고 바로 아랫줄에 있는 형식을 살릴 것
//==============================================================================================================================================
//hor_up = receiveThread.getHor_Up();


                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Hor_Up_Rush[Days] = Total_Car_Hor_Up_Rush[Days] + hor_up;


/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.,,
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/


                                        hor_up_wait = (Double) jsonObject.get("hor_up");
                                        Total_Car_wait_Hor_Up_Rush[Days] = Total_Car_wait_Hor_Up_Rush[Days] + hor_up_wait;
                                        System.out.println("hor up wait : " + hor_up_wait);
                                        System.out.println("Total hor up wait Rush : " + Total_Car_wait_Hor_Up_Rush[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                        ver_left_people = (int) Casting_ver_left_people;
                                        Total_People_Ver_Left_Rush[Days] = Total_People_Ver_Left_Rush[Days] + ver_left_people;
                                        ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                        Total_People_wait_Ver_Left_Rush[Days] = Total_People_wait_Ver_Left_Rush[Days] + ver_left_people_wait;
                                        System.out.println("ver left people : " + ver_left_people);
                                        System.out.println("Total ver left people Rush : " + Total_People_Ver_Left_Rush[Days]);
                                        System.out.println("Total ver left people wait Rush : " + Total_People_wait_Ver_Left_Rush[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Left_Green");
                                        jsonObject2.put("hor_down", "Right");
                                        jsonObject2.put("ver_left", "Walker");
                                        jsonObject2.put("ver_right", "Right");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("hor up Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Rush : " + Total_Car_Hor_Up_Rush[Days] + " Total hor down Rush : " + Total_Car_Hor_Down_Rush[Days] + " Total ver left Rush : " + Total_Car_Ver_Left_Rush[Days] + " Total ver right Rush : " + Total_Car_Ver_Right_Rush[Days]);
                                        System.out.println("");
                                        Thread.sleep(hor_up * 100 + 500);
                                        hor_up = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else if (hor_down > hor_up && hor_down >= ver_left && hor_down >= ver_right && hor_down_hadSignal == false) {
                                        hor_down_hadSignal = true;

//hor_down = receiveThread.getHor_Down();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Hor_Down_Rush[Days] = Total_Car_Hor_Down_Rush[Days] + hor_down;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        hor_down_wait = (Double) jsonObject.get("hor_down");
                                        Total_Car_wait_Hor_Down_Rush[Days] = Total_Car_wait_Hor_Down_Rush[Days] + hor_down_wait;
                                        System.out.println("hor down wait : " + hor_down_wait);
                                        System.out.println("Total hor down wait Rush : " + Total_Car_wait_Hor_Down_Rush[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                        ver_right_people = (int) Casting_ver_right_people;
                                        Total_People_Ver_Right_Rush[Days] = Total_People_Ver_Right_Rush[Days] + ver_right_people;
                                        ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                        Total_People_wait_Ver_Right_Rush[Days] = Total_People_wait_Ver_Right_Rush[Days] + ver_right_people_wait;
                                        System.out.println("ver right people : " + ver_right_people);
                                        System.out.println("Total ver right people Rush : " + Total_People_Ver_Right_Rush[Days]);
                                        System.out.println("Total ver right people wait Rush : " + Total_People_wait_Ver_Right_Rush[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Right");
                                        jsonObject2.put("hor_down", "Left_Green");
                                        jsonObject2.put("ver_left", "Right");
                                        jsonObject2.put("ver_right", "Walker");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("hor down Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Rush : " + Total_Car_Hor_Up_Rush[Days] + " Total hor down Rush : " + Total_Car_Hor_Down_Rush[Days] + " Total ver left Rush : " + Total_Car_Ver_Left_Rush[Days] + " Total ver right Rush : " + Total_Car_Ver_Right_Rush[Days]);
                                        System.out.println("");
                                        Thread.sleep(hor_down * 100 + 500);
                                        hor_down = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else if (ver_left > hor_up && ver_left > hor_down && ver_left >= ver_right && ver_left_hadSignal == false) {
                                        ver_left_hadSignal = true;

//ver_left = receiveThread.getVer_Left();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Ver_Left_Rush[Days] = Total_Car_Ver_Left_Rush[Days] + ver_left;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        ver_left_wait = (Double) jsonObject.get("ver_left");
                                        Total_Car_wait_Ver_Left_Rush[Days] = Total_Car_wait_Ver_Left_Rush[Days] + ver_left_wait;
                                        System.out.println("ver left wait : " + ver_left_wait);
                                        System.out.println("Total ver left wait Rush : " + Total_Car_wait_Ver_Left_Rush[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                        hor_down_people = (int) Casting_hor_down_people;
                                        Total_People_Hor_Down_Rush[Days] = Total_People_Hor_Down_Rush[Days] + hor_down_people;
                                        hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                        Total_People_wait_Hor_Down_Rush[Days] = Total_People_wait_Hor_Down_Rush[Days] + hor_down_people_wait;
                                        System.out.println("hor down people : " + hor_down_people);
                                        System.out.println("Total hor down people Rush : " + Total_People_Hor_Down_Rush[Days]);
                                        System.out.println("Total hor down people wait Rush : " + Total_People_wait_Hor_Down_Rush[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Right");
                                        jsonObject2.put("hor_down", "Walker");
                                        jsonObject2.put("ver_left", "Left_Green");
                                        jsonObject2.put("ver_right", "Right");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("ver left Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Rush : " + Total_Car_Hor_Up_Rush[Days] + " Total hor down Rush : " + Total_Car_Hor_Down_Rush[Days] + " Total ver left Rush : " + Total_Car_Ver_Left_Rush[Days] + " Total ver right Rush : " + Total_Car_Ver_Right_Rush[Days]);
                                        System.out.println("");
                                        Thread.sleep(ver_left * 100 + 500);
                                        ver_left = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else if (ver_right > ver_left && ver_right > hor_up && ver_right > hor_down && ver_right_hadSignal == false) {
                                        ver_right_hadSignal = true;

//ver_right = receiveThread.getVer_Right();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Ver_Right_Rush[Days] = Total_Car_Ver_Right_Rush[Days] + ver_right;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        ver_right_wait = (Double) jsonObject.get("ver_right");
                                        Total_Car_wait_Ver_Right_Rush[Days] = Total_Car_wait_Ver_Right_Rush[Days] + ver_right_wait;
                                        System.out.println("ver right wait : " + ver_right_wait);
                                        System.out.println("Total ver right wait Rush : " + Total_Car_wait_Ver_Right_Rush[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                        hor_up_people = (int) Casting_hor_up_people;
                                        Total_People_Hor_Up_Rush[Days] = Total_People_Hor_Up_Rush[Days] + hor_up_people;
                                        hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                        Total_People_wait_Hor_Up_Rush[Days] = Total_People_wait_Hor_Up_Rush[Days] + hor_up_people_wait;
                                        System.out.println("hor_up people : " + hor_up_people);
                                        System.out.println("Total hor_up people Rush : " + Total_People_Hor_Up_Rush[Days]);
                                        System.out.println("Total hor_up people wait Rush: " + Total_People_wait_Hor_Up_Rush[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Walker");
                                        jsonObject2.put("hor_down", "Right");
                                        jsonObject2.put("ver_left", "Right");
                                        jsonObject2.put("ver_right", "Left_Green");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("ver right Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Rush : " + Total_Car_Hor_Up_Rush[Days] + " Total hor down Rush : " + Total_Car_Hor_Down_Rush[Days] + " Total ver left Rush : " + Total_Car_Ver_Left_Rush[Days] + " Total ver right Rush : " + Total_Car_Ver_Right_Rush[Days]);
                                        System.out.println("");
                                        Thread.sleep(ver_right * 100 + 500);
                                        ver_right = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else {
//System.out.println("해당없음");

                                    }
                                }
                                hor_up_hadSignal = false;
                                hor_down_hadSignal = false;
                                ver_left_hadSignal = false;
                                ver_right_hadSignal = false;

//정책평가
                                Result_Car_Hor_Up_Rush[Days] = Total_Car_wait_Hor_Up_Rush[Days] / Total_Car_Hor_Up_Rush[Days];
                                Result_Car_Hor_Down_Rush[Days] = Total_Car_wait_Hor_Down_Rush[Days] / Total_Car_Hor_Down_Rush[Days];
                                Result_Car_Ver_Left_Rush[Days] = Total_Car_wait_Ver_Left_Rush[Days] / Total_Car_Ver_Left_Rush[Days];
                                Result_Car_Ver_Right_Rush[Days] = Total_Car_wait_Ver_Right_Rush[Days] / Total_Car_Ver_Right_Rush[Days];

                                Result_People_Hor_Up_Rush[Days] = Total_People_wait_Hor_Up_Rush[Days] / Total_People_Hor_Up_Rush[Days];
                                Result_People_Hor_Down_Rush[Days] = Total_People_wait_Hor_Down_Rush[Days] / Total_People_Hor_Down_Rush[Days];
                                Result_People_Ver_Left_Rush[Days] = Total_People_wait_Ver_Left_Rush[Days] / Total_People_Ver_Left_Rush[Days];
                                Result_People_Ver_Right_Rush[Days] = Total_People_wait_Ver_Right_Rush[Days] / Total_People_Ver_Right_Rush[Days];

                                if (Double.isNaN(Result_People_Hor_Up_Rush[Days]))
                                    Result_People_Hor_Up_Rush[Days] = 0;
                                if (Double.isNaN(Result_People_Hor_Down_Rush[Days]))
                                    Result_People_Hor_Down_Rush[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Left_Rush[Days]))
                                    Result_People_Ver_Left_Rush[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Right_Rush[Days]))
                                    Result_People_Ver_Right_Rush[Days] = 0;

                                if (Double.isNaN(Result_Car_Hor_Up_Rush[Days]))
                                    Result_Car_Hor_Up_Rush[Days] = 0;
                                if (Double.isNaN(Result_Car_Hor_Down_Rush[Days]))
                                    Result_Car_Hor_Down_Rush[Days] = 0;
                                if (Double.isNaN(Result_Car_Ver_Left_Rush[Days]))
                                    Result_Car_Ver_Left_Rush[Days] = 0;
                                if (Double.isNaN(Result_Car_Ver_Right_Rush[Days]))
                                    Result_Car_Ver_Right_Rush[Days] = 0;


                                System.out.println("1일 러시아워의 총 위쪽 차량수 : " + Total_Car_Hor_Up_Rush[0]);
                                System.out.println("1일 러시아워의 총 아래쪽 차량수 : " + Total_Car_Hor_Down_Rush[0]);
                                System.out.println("1일 러시아워의 총 왼쪽 차량수 : " + Total_Car_Ver_Left_Rush[0]);
                                System.out.println("1일 러시아워의 총 오른쪽 차량수 : " + Total_Car_Ver_Right_Rush[0]);
                                System.out.println("오늘의 총 위쪽 차량수(Rush) : " + Total_Car_Hor_Up_Rush[Days]);
                                System.out.println("오늘의 총 아래쪽 차량수(Rush) : " + Total_Car_Hor_Down_Rush[Days]);
                                System.out.println("오늘의 총 왼쪽 차량수(Rush) : " + Total_Car_Ver_Left_Rush[Days]);
                                System.out.println("오늘의 총 오른쪽 차량수(Rush) : " + Total_Car_Ver_Right_Rush[Days]);


                                System.out.println("위쪽 정책점수(Rush) : " + Result_Car_Hor_Up_Rush[Days]);
                                System.out.println("아래쪽 정책점수(Rush) : " + Result_Car_Hor_Down_Rush[Days]);
                                System.out.println("왼쪽 정책점수(Rush) : " + Result_Car_Ver_Left_Rush[Days]);
                                System.out.println("오른쪽 정책점수(Rush) : " + Result_Car_Ver_Right_Rush[Days]);

/*Result_Car_Hor_Up_Rush_Height[Days] = (int) Result_Car_Hor_Up_Rush[Days];
Result_Car_Hor_Down_Rush_Height[Days] = (int) Result_Car_Hor_Down_Rush[Days];
Result_Car_Ver_Left_Rush_Height[Days] = (int) Result_Car_Ver_Left_Rush[Days];
Result_Car_Ver_Right_Rush_Height[Days] = (int) Result_Car_Ver_Right_Rush[Days];*/

                                drawingPanel.setAain(Result_Car_Hor_Up_Rush[Days]);
                                drawingPanel.setBbin(Result_Car_Hor_Down_Rush[Days]);
                                drawingPanel.setCcin(Result_Car_Ver_Left_Rush[Days]);
                                drawingPanel.setDdin(Result_Car_Ver_Right_Rush[Days]);
                                drawingPanel.setEein(Result_People_Hor_Up_Rush[Days]);
                                drawingPanel.setFfin(Result_People_Hor_Down_Rush[Days]);
                                drawingPanel.setGgin(Result_People_Ver_Left_Rush[Days]);
                                drawingPanel.setHhin(Result_People_Ver_Right_Rush[Days]);
//drawingPanel.repaint();


                                Mode_now = inputThread.getMode();
                                Rush_Mode_now = inputThread.getRushMode();
                                Night_Mode_now = inputThread.getNight_mode();

                                TIME_INFO_REQUEST(output_data, TimeInfoREQUEST);
                                TIME_INFO_RECEIVE(c_socket);
                                Current_Date.setText(String.valueOf(Current_Days + 1));
                                if (!Time_now.equals("rush"))
                                    break;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        while (Rush_Mode_now == 6)
                        {
                            Policy = "Rush-2";
                            Current_Policy.setText(Policy);

                            try {
// "SavedSignal"이 신호등 정보가 담긴 파일. 정책결정할때 작성해야함
                                Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                                JSONObject jsonObject2 = (JSONObject) obj2;

                                int i = 0;
                                while (i < 4) {



// ================================================카메라 연결할때 여기 살리기================================================
/*hor_up = receiveThread.getHor_Up();
if(hor_up_hadSignal == true) {hor_up = -1;}
hor_down = receiveThread.getHor_Down();
if(hor_down_hadSignal == true) {hor_down = -1;}
ver_left = receiveThread.getVer_Left();
if(ver_left_hadSignal == true) {ver_left = -1;}
ver_right = receiveThread.getVer_Right();
if(ver_right_hadSignal == true) {ver_right = -1;}*/
// ================================================카메라 연결할때 여기 살리기================================================

                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    JSONObject jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("==========================================================================================");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("==========================================================================================");



                                    hor_up_hadSignal = true;

//==============================================================================================================================================
//디스플레이에서 차량수 직접 받는 상황 테스트중이므로 카메라가 잘 될때는 디스플레이에게서 차량수 받는건 지우고 바로 아랫줄에 있는 형식을 살릴 것
//==============================================================================================================================================
//hor_up = receiveThread.getHor_Up();


                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Hor_Up_Rush_2[Days] = Total_Car_Hor_Up_Rush_2[Days] + hor_up;


/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.,,
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/


                                    hor_up_wait = (Double) jsonObject.get("hor_up");
                                    Total_Car_wait_Hor_Up_Rush_2[Days] = Total_Car_wait_Hor_Up_Rush_2[Days] + hor_up_wait;
                                    System.out.println("hor up wait : " + hor_up_wait);
                                    System.out.println("Total hor up wait Rush-2 : " + Total_Car_wait_Hor_Up_Rush_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    JSONObject jsonObject3 = (JSONObject) obj3;
                                    Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                    ver_left_people = (int) Casting_ver_left_people;
                                    Total_People_Ver_Left_Rush_2[Days] = Total_People_Ver_Left_Rush_2[Days] + ver_left_people;
                                    ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                    Total_People_wait_Ver_Left_Rush_2[Days]= Total_People_wait_Ver_Left_Rush_2[Days] + ver_left_people_wait;
                                    System.out.println("ver left people : " + ver_left_people);
                                    System.out.println("Total ver left people Rush-2 : " + Total_People_Ver_Left_Rush_2[Days]);
                                    System.out.println("Total ver left people wait Rush-2 : " + Total_People_wait_Ver_Left_Rush_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Left_Green");
                                    jsonObject2.put("hor_down", "Right");
                                    jsonObject2.put("ver_left", "Walker");
                                    jsonObject2.put("ver_right", "Right");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("hor up Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Rush-2 : " + Total_Car_Hor_Up_Rush_2[Days] + " Total hor down Rush-2 : " + Total_Car_Hor_Down_Rush_2[Days] + " Total ver left Rush-2 : " + Total_Car_Ver_Left_Rush_2[Days] + " Total ver right Rush-2 : " + Total_Car_Ver_Right_Rush_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    hor_up = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);



                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    hor_down_hadSignal = true;

//hor_down = receiveThread.getHor_Down();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Hor_Down_Rush_2[Days] = Total_Car_Hor_Down_Rush_2[Days] + hor_down;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    hor_down_wait = (Double) jsonObject.get("hor_down");
                                    Total_Car_wait_Hor_Down_Rush_2[Days] = Total_Car_wait_Hor_Down_Rush_2[Days] + hor_down_wait;
                                    System.out.println("hor down wait : " + hor_down_wait);
                                    System.out.println("Total hor down wait Rush-2 : " + Total_Car_wait_Hor_Down_Rush_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                    ver_right_people = (int) Casting_ver_right_people;
                                    Total_People_Ver_Right_Rush_2[Days] = Total_People_Ver_Right_Rush_2[Days] + ver_right_people;
                                    ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                    Total_People_wait_Ver_Right_Rush_2[Days] = Total_People_wait_Ver_Right_Rush_2[Days] + ver_right_people_wait;
                                    System.out.println("ver right people : " + ver_right_people);
                                    System.out.println("Total ver right people Rush-2 : " + Total_People_Ver_Right_Rush_2[Days]);
                                    System.out.println("Total ver right people wait Rush-2 : " + Total_People_wait_Ver_Right_Rush_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Right");
                                    jsonObject2.put("hor_down", "Left_Green");
                                    jsonObject2.put("ver_left", "Right");
                                    jsonObject2.put("ver_right", "Walker");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("hor down Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Rush-2 : " + Total_Car_Hor_Up_Rush_2[Days] + " Total hor down Rush-2 : " + Total_Car_Hor_Down_Rush_2[Days] + " Total ver left Rush-2 : " + Total_Car_Ver_Left_Rush_2[Days] + " Total ver right Rush-2 : " + Total_Car_Ver_Right_Rush_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    hor_down = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);




                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    ver_left_hadSignal = true;

//ver_left = receiveThread.getVer_Left();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Ver_Left_Rush_2[Days] = Total_Car_Ver_Left_Rush_2[Days] + ver_left;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    ver_left_wait = (Double) jsonObject.get("ver_left");
                                    Total_Car_wait_Ver_Left_Rush_2[Days] = Total_Car_wait_Ver_Left_Rush_2[Days] + ver_left_wait;
                                    System.out.println("ver left wait : " + ver_left_wait);
                                    System.out.println("Total ver left wait Rush-2 : " + Total_Car_wait_Ver_Left_Rush_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                    hor_down_people = (int) Casting_hor_down_people;
                                    Total_People_Hor_Down_Rush_2[Days] = Total_People_Hor_Down_Rush_2[Days] + hor_down_people;
                                    hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                    Total_People_wait_Hor_Down_Rush_2[Days] = Total_People_wait_Hor_Down_Rush_2[Days] + hor_down_people_wait;
                                    System.out.println("hor down people : " + hor_down_people);
                                    System.out.println("Total hor down people Rush-2 : " + Total_People_Hor_Down_Rush_2[Days]);
                                    System.out.println("Total hor down people wait Rush-2 : " + Total_People_wait_Hor_Down_Rush_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Right");
                                    jsonObject2.put("hor_down", "Walker");
                                    jsonObject2.put("ver_left", "Left_Green");
                                    jsonObject2.put("ver_right", "Right");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("ver left Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Rush-2 : " + Total_Car_Hor_Up_Rush_2[Days] + " Total hor down Rush-2 : " + Total_Car_Hor_Down_Rush_2[Days] + " Total ver left Rush-2 : " + Total_Car_Ver_Left_Rush_2[Days] + " Total ver right Rush-2 : " + Total_Car_Ver_Right_Rush_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    ver_left = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);





                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    ver_right_hadSignal = true;

//ver_right = receiveThread.getVer_Right();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Ver_Right_Rush_2[Days] = Total_Car_Ver_Right_Rush_2[Days] + ver_right;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    ver_right_wait = (Double) jsonObject.get("ver_right");
                                    Total_Car_wait_Ver_Right_Rush_2[Days] = Total_Car_wait_Ver_Right_Rush_2[Days] + ver_right_wait;
                                    System.out.println("ver right wait : " + ver_right_wait);
                                    System.out.println("Total ver right wait Rush-2 : " + Total_Car_wait_Ver_Right_Rush_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                    hor_up_people = (int) Casting_hor_up_people;
                                    Total_People_Hor_Up_Rush_2[Days] = Total_People_Hor_Up_Rush_2[Days] + hor_up_people;
                                    hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                    Total_People_wait_Hor_Up_Rush_2[Days] = Total_People_wait_Hor_Up_Rush_2[Days] + hor_up_people_wait;
                                    System.out.println("hor_up people : " + hor_up_people);
                                    System.out.println("Total hor_up people Rush-2 : " + Total_People_Hor_Up_Rush_2[Days]);
                                    System.out.println("Total hor_up people wait Rush-2 : " + Total_People_wait_Hor_Up_Rush_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Walker");
                                    jsonObject2.put("hor_down", "Right");
                                    jsonObject2.put("ver_left", "Right");
                                    jsonObject2.put("ver_right", "Left_Green");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("ver right Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Rush-2 : " + Total_Car_Hor_Up_Rush_2[Days] + " Total hor down Rush-2 : " + Total_Car_Hor_Down_Rush_2[Days] + " Total ver left Rush-2 : " + Total_Car_Ver_Left_Rush_2[Days] + " Total ver right Rush-2 : " + Total_Car_Ver_Right_Rush_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    ver_right = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);


                                }
                                hor_up_hadSignal = false;
                                hor_down_hadSignal = false;
                                ver_left_hadSignal = false;
                                ver_right_hadSignal = false;

                                Result_Car_Hor_Up_Rush_2[Days] = Total_Car_wait_Hor_Up_Rush_2[Days] / Total_Car_Hor_Up_Rush_2[Days];
                                Result_Car_Hor_Down_Rush_2[Days] = Total_Car_wait_Hor_Down_Rush_2[Days] / Total_Car_Hor_Down_Rush_2[Days];
                                Result_Car_Ver_Left_Rush_2[Days] = Total_Car_wait_Ver_Left_Rush_2[Days] / Total_Car_Ver_Left_Rush_2[Days];
                                Result_Car_Ver_Right_Rush_2[Days] = Total_Car_wait_Ver_Right_Rush_2[Days] / Total_Car_Ver_Right_Rush_2[Days];

                                Result_People_Hor_Up_Rush_2[Days] = Total_People_wait_Hor_Up_Rush_2[Days] / Total_People_Hor_Up_Rush_2[Days];
                                Result_People_Hor_Down_Rush_2[Days] = Total_People_wait_Hor_Down_Rush_2[Days] / Total_People_Hor_Down_Rush_2[Days];
                                Result_People_Ver_Left_Rush_2[Days] = Total_People_wait_Ver_Left_Rush_2[Days] / Total_People_Ver_Left_Rush_2[Days];
                                Result_People_Ver_Right_Rush_2[Days] = Total_People_wait_Ver_Right_Rush_2[Days] / Total_People_Ver_Right_Rush_2[Days];

                                if (Double.isNaN(Result_People_Hor_Up_Rush_2[Days]))
                                    Result_People_Hor_Up_Rush_2[Days] = 0;
                                if (Double.isNaN(Result_People_Hor_Down_Rush_2[Days]))
                                    Result_People_Hor_Down_Rush_2[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Left_Rush_2[Days]))
                                    Result_People_Ver_Left_Rush_2[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Right_Rush_2[Days]))
                                    Result_People_Ver_Right_Rush_2[Days] = 0;

                                if(Double.isNaN(Result_Car_Hor_Up_Rush_2[Days]))
                                    Result_Car_Hor_Up_Rush_2[Days] = 0;
                                if(Double.isNaN(Result_Car_Hor_Down_Rush_2[Days]))
                                    Result_Car_Hor_Down_Rush_2[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Left_Rush_2[Days]))
                                    Result_Car_Ver_Left_Rush_2[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Right_Rush_2[Days]))
                                    Result_Car_Ver_Right_Rush_2[Days] = 0;


                                System.out.println("1일의 총 위쪽 차량수(4) : " + Total_Car_Hor_Up_Rush_2[0]);
                                System.out.println("1일의 총 아래쪽 차량수(4) : " + Total_Car_Hor_Down_Rush_2[0]);
                                System.out.println("1일의 총 왼쪽 차량수(4) : " + Total_Car_Ver_Left_Rush_2[0]);
                                System.out.println("1일의 총 오른쪽 차량수(4) : " + Total_Car_Ver_Right_Rush_2[0]);
                                System.out.println("오늘의 총 위쪽 차량수(4) : " + Total_Car_Hor_Up_Rush_2[Days]);
                                System.out.println("오늘의 총 아래쪽 차량수(4) : " + Total_Car_Hor_Down_Rush_2[Days]);
                                System.out.println("오늘의 총 왼쪽 차량수(4) : " + Total_Car_Ver_Left_Rush_2[Days]);
                                System.out.println("오늘의 총 오른쪽 차량수(4) : " + Total_Car_Ver_Right_Rush_2[Days]);


                                System.out.println("위쪽 정책점수(4) : " + Result_Car_Hor_Up_Rush_2[Days]);
                                System.out.println("아래쪽 정책점수(4) : " + Result_Car_Hor_Down_Rush_2[Days]);
                                System.out.println("왼쪽 정책점수(4) : " + Result_Car_Ver_Left_Rush_2[Days]);
                                System.out.println("오른쪽 정책점수(4) : " + Result_Car_Ver_Right_Rush_2[Days]);


                                drawingPanel.setAain(Result_Car_Hor_Up_Rush_2[Days]);
                                drawingPanel.setBbin(Result_Car_Hor_Down_Rush_2[Days]);
                                drawingPanel.setCcin(Result_Car_Ver_Left_Rush_2[Days]);
                                drawingPanel.setDdin(Result_Car_Ver_Right_Rush_2[Days]);
                                drawingPanel.setEein(Result_People_Hor_Up_Rush_2[Days]);
                                drawingPanel.setFfin(Result_People_Hor_Down_Rush_2[Days]);
                                drawingPanel.setGgin(Result_People_Ver_Left_Rush_2[Days]);
                                drawingPanel.setHhin(Result_People_Ver_Right_Rush_2[Days]);
//drawingPanel.repaint();

                                Mode_now = inputThread.getMode();
                                Rush_Mode_now = inputThread.getRushMode();
                                Night_Mode_now = inputThread.getNight_mode();

                                TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
                                TIME_INFO_RECEIVE(c_socket);
                                Current_Date.setText(String.valueOf(Current_Days+1));
                                if(!Time_now.equals("rush"))
                                    break;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }



                    }

                    while (Time_now.equals("night")) {
                        Current_Time.setText("Night");

                        if(Night_Mode_now == 7) {


                            Policy = "Night-1";
                            Current_Policy.setText(Policy);

                            try {
// "SavedSignal"이 신호등 정보가 담긴 파일. 정책결정할때 작성해야함
                                Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                                JSONObject jsonObject2 = (JSONObject) obj2;

                                int i = 0;
                                while(i<4) {


                                    System.out.println("Night Now");
                                    System.out.println("Night Now");
                                    System.out.println("Night Now");
                                    System.out.println("Night Now");



                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    JSONObject jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if (hor_up_hadSignal == true) {
                                        hor_up = -1;
                                    }
                                    hor_down = receiveThread.getHor_Down();
                                    if (hor_down_hadSignal == true) {
                                        hor_down = -1;
                                    }
                                    ver_left = receiveThread.getVer_Left();
                                    if (ver_left_hadSignal == true) {
                                        ver_left = -1;
                                    }
                                    ver_right = receiveThread.getVer_Right();
                                    if (ver_right_hadSignal == true) {
                                        ver_right = -1;
                                    }

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
//if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
//if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
//if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
//if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("==========================================================================================");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("==========================================================================================");

                                    if (hor_up == 0 && hor_down == 0 && ver_left == 0 && ver_right == 0) {
                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);
                                    } else if (hor_up >= hor_down && hor_up >= ver_left && hor_up >= ver_right) {


//==============================================================================================================================================
//디스플레이에서 차량수 직접 받는 상황 테스트중이므로 카메라가 잘 될때는 디스플레이에게서 차량수 받는건 지우고 바로 아랫줄에 있는 형식을 살릴 것
//==============================================================================================================================================
//hor_up = receiveThread.getHor_Up();


                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Hor_Up_Night[Days] = Total_Car_Hor_Up_Night[Days] + hor_up;


/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.,,
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/


                                        hor_up_wait = (Double) jsonObject.get("hor_up");
                                        Total_Car_wait_Hor_Up_Night[Days] = Total_Car_wait_Hor_Up_Night[Days] + hor_up_wait;
                                        System.out.println("hor up wait : " + hor_up_wait);
                                        System.out.println("Total hor up wait Night : " + Total_Car_wait_Hor_Up_Night[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                        ver_left_people = (int) Casting_ver_left_people;
                                        Total_people_Ver_Left_Night[Days] = Total_people_Ver_Left_Night[Days] + ver_left_people;
                                        ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                        Total_people_wait_Ver_Left_Night[Days] = Total_people_wait_Ver_Left_Night[Days] + ver_left_people_wait;
                                        System.out.println("ver left people : " + ver_left_people);
                                        System.out.println("Total ver left people Night : " + Total_people_Ver_Left_Night[Days]);
                                        System.out.println("Total ver left people wait Night : " + Total_people_wait_Ver_Left_Night[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Left_Green");
                                        jsonObject2.put("hor_down", "Right");
                                        jsonObject2.put("ver_left", "Walker");
                                        jsonObject2.put("ver_right", "Right");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("hor up Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Night : " + Total_Car_Hor_Up_Night[Days] + " Total hor down Night : " + Total_Car_Hor_Down_Night[Days] + " Total ver left Night : " + Total_Car_Ver_Left_Night[Days] + " Total ver right Night : " + Total_Car_Ver_Right_Night[Days]);
                                        System.out.println("");
                                        Thread.sleep(hor_up * 100 + 1000);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);
                                        i++;


                                    } else if (hor_down > hor_up && hor_down >= ver_left && hor_down >= ver_right) {

//hor_down = receiveThread.getHor_Down();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Hor_Down_Night[Days] = Total_Car_Hor_Down_Night[Days] + hor_down;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        hor_down_wait = (Double) jsonObject.get("hor_down");
                                        Total_Car_wait_Hor_Down_Night[Days] = Total_Car_wait_Hor_Down_Night[Days] + hor_down_wait;
                                        System.out.println("hor down wait : " + hor_down_wait);
                                        System.out.println("Total hor down wait Night : " + Total_Car_wait_Hor_Down_Night[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                        ver_right_people = (int) Casting_ver_right_people;
                                        Total_people_Ver_Right_Night[Days] = Total_people_Ver_Right_Night[Days] + ver_right_people;
                                        ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                        Total_people_wait_Ver_Right_Night[Days] = Total_people_wait_Ver_Right_Night[Days] + ver_right_people_wait;
                                        System.out.println("ver right people : " + ver_right_people);
                                        System.out.println("Total ver right people Night : " + Total_people_Ver_Right_Night[Days]);
                                        System.out.println("Total ver right people wait Night : " + Total_people_wait_Ver_Right_Night[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Right");
                                        jsonObject2.put("hor_down", "Left_Green");
                                        jsonObject2.put("ver_left", "Right");
                                        jsonObject2.put("ver_right", "Walker");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("hor down Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Night : " + Total_Car_Hor_Up_Night[Days] + " Total hor down Night : " + Total_Car_Hor_Down_Night[Days] + " Total ver left Night : " + Total_Car_Ver_Left_Night[Days] + " Total ver right Night : " + Total_Car_Ver_Right_Night[Days]);
                                        System.out.println("");
                                        Thread.sleep(hor_down * 100 + 1000);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);
                                        i++;


                                    } else if (ver_left > hor_up && ver_left > hor_down && ver_left >= ver_right) {

//ver_left = receiveThread.getVer_Left();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Ver_Left_Night[Days] = Total_Car_Ver_Left_Night[Days] + ver_left;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        ver_left_wait = (Double) jsonObject.get("ver_left");
                                        Total_Car_wait_Ver_Left_Night[Days] = Total_Car_wait_Ver_Left_Night[Days] + ver_left_wait;
                                        System.out.println("ver left wait : " + ver_left_wait);
                                        System.out.println("Total ver left wait Night : " + Total_Car_wait_Ver_Left_Night[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                        hor_down_people = (int) Casting_hor_down_people;
                                        Total_people_Hor_Down_Night[Days] = Total_people_Hor_Down_Night[Days] + hor_down_people;
                                        hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                        Total_people_wait_Hor_Down_Night[Days] = Total_people_wait_Hor_Down_Night[Days] + hor_down_people_wait;
                                        System.out.println("hor down people : " + hor_down_people);
                                        System.out.println("Total hor down people Night : " + Total_people_Hor_Down_Night[Days]);
                                        System.out.println("Total hor down people wait Night : " + Total_people_wait_Hor_Down_Night[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Right");
                                        jsonObject2.put("hor_down", "Walker");
                                        jsonObject2.put("ver_left", "Left_Green");
                                        jsonObject2.put("ver_right", "Right");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("ver left Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Night : " + Total_Car_Hor_Up_Night[Days] + " Total hor down Night : " + Total_Car_Hor_Down_Night[Days] + " Total ver left Night : " + Total_Car_Ver_Left_Night[Days] + " Total ver right Night : " + Total_Car_Ver_Right_Night[Days]);
                                        System.out.println("");
                                        Thread.sleep(ver_left * 100 + 1000);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);
                                        i++;


                                    } else if (ver_right > ver_left && ver_right > hor_up && ver_right > hor_down) {

//ver_right = receiveThread.getVer_Right();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Ver_Right_Night[Days] = Total_Car_Ver_Right_Night[Days] + ver_right;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        ver_right_wait = (Double) jsonObject.get("ver_right");
                                        Total_Car_wait_Ver_Right_Night[Days] = Total_Car_wait_Ver_Right_Night[Days] + ver_right_wait;
                                        System.out.println("ver right wait : " + ver_right_wait);
                                        System.out.println("Total ver right wait Night : " + Total_Car_wait_Ver_Right_Night[Days]);

                                        PEOPLE_INFO_REQUEST(output_data, PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                        hor_up_people = (int) Casting_hor_up_people;
                                        Total_people_Hor_Up_Night[Days] = Total_people_Hor_Up_Night[Days] + hor_up_people;
                                        hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                        Total_people_wait_Hor_Up_Night[Days] = Total_people_wait_Hor_Up_Night[Days] + hor_up_people_wait;
                                        System.out.println("hor_up people : " + hor_up_people);
                                        System.out.println("Total hor_up people Night : " + Total_people_Hor_Up_Night[Days]);
                                        System.out.println("Total hor_up people wait Night : " + Total_people_wait_Hor_Up_Night[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Walker");
                                        jsonObject2.put("hor_down", "Right");
                                        jsonObject2.put("ver_left", "Right");
                                        jsonObject2.put("ver_right", "Left_Green");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("ver right Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up Night : " + Total_Car_Hor_Up_Night[Days] + " Total hor down Night : " + Total_Car_Hor_Down_Night[Days] + " Total ver left Night : " + Total_Car_Ver_Left_Night[Days] + " Total ver right Night : " + Total_Car_Ver_Right_Night[Days]);
                                        System.out.println("");
                                        Thread.sleep(ver_right * 100 + 1000);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);
                                        i++;


                                    } else {
//System.out.println("해당없음");

                                    }

                                }
                                Result_Car_Hor_Up_Night[Days] = Total_Car_wait_Hor_Up_Night[Days] / Total_Car_Hor_Up_Night[Days];
                                Result_Car_Hor_Down_Night[Days] = Total_Car_wait_Hor_Down_Night[Days] / Total_Car_Hor_Down_Night[Days];
                                Result_Car_Ver_Left_Night[Days] = Total_Car_wait_Ver_Left_Night[Days] / Total_Car_Ver_Left_Night[Days];
                                Result_Car_Ver_Right_Night[Days] = Total_Car_wait_Ver_Right_Night[Days] / Total_Car_Ver_Right_Night[Days];

                                Result_People_Hor_Up_Night[Days] = Total_people_wait_Hor_Up_Night[Days] / Total_people_Hor_Up_Night[Days];
                                Result_People_Hor_Down_Night[Days] = Total_people_wait_Hor_Down_Night[Days] / Total_people_Hor_Down_Night[Days];
                                Result_People_Ver_Left_Night[Days] = Total_people_wait_Ver_Left_Night[Days] / Total_people_Ver_Left_Night[Days];
                                Result_People_Ver_Right_Night[Days] = Total_people_wait_Ver_Right_Night[Days] / Total_people_Ver_Right_Night[Days];

                                if (Double.isNaN(Result_People_Hor_Up_Night[Days]))
                                    Result_People_Hor_Up_Night[Days] = 0;
                                if (Double.isNaN(Result_People_Hor_Down_Night[Days]))
                                    Result_People_Hor_Down_Night[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Left_Night[Days]))
                                    Result_People_Ver_Left_Night[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Right_Night[Days]))
                                    Result_People_Ver_Right_Night[Days] = 0;

                                if (Double.isNaN(Result_Car_Hor_Up_Night[Days]))
                                    Result_Car_Hor_Up_Night[Days] = 0;
                                if (Double.isNaN(Result_Car_Hor_Down_Night[Days]))
                                    Result_Car_Hor_Down_Night[Days] = 0;
                                if (Double.isNaN(Result_Car_Ver_Left_Night[Days]))
                                    Result_Car_Ver_Left_Night[Days] = 0;
                                if (Double.isNaN(Result_Car_Ver_Right_Night[Days]))
                                    Result_Car_Ver_Right_Night[Days] = 0;


                                System.out.println("1일 Night 의 총 위쪽 차량수 : " + Total_Car_Hor_Up_Night[0]);
                                System.out.println("1일 Night 의 총 아래쪽 차량수 : " + Total_Car_Hor_Down_Night[0]);
                                System.out.println("1일 Night 의 총 왼쪽 차량수 : " + Total_Car_Ver_Left_Night[0]);
                                System.out.println("1일 Night 의 총 오른쪽 차량수 : " + Total_Car_Ver_Right_Night[0]);
                                System.out.println("오늘의 총 위쪽 차량수(Night) : " + Total_Car_Hor_Up_Night[Days]);
                                System.out.println("오늘의 총 아래쪽 차량수(Night) : " + Total_Car_Hor_Down_Night[Days]);
                                System.out.println("오늘의 총 왼쪽 차량수(Night) : " + Total_Car_Ver_Left_Night[Days]);
                                System.out.println("오늘의 총 오른쪽 차량수(Night) : " + Total_Car_Ver_Right_Night[Days]);


                                System.out.println("위쪽 정책점수(Night) : " + Result_Car_Hor_Up_Night[Days]);
                                System.out.println("아래쪽 정책점수(Night) : " + Result_Car_Hor_Down_Night[Days]);
                                System.out.println("왼쪽 정책점수(Night) : " + Result_Car_Ver_Left_Night[Days]);
                                System.out.println("오른쪽 정책점수(Night) : " + Result_Car_Ver_Right_Night[Days]);

                                drawingPanel.setAain(Result_Car_Hor_Up_Night[Days]);
                                drawingPanel.setBbin(Result_Car_Hor_Down_Night[Days]);
                                drawingPanel.setCcin(Result_Car_Ver_Left_Night[Days]);
                                drawingPanel.setDdin(Result_Car_Ver_Right_Night[Days]);
                                drawingPanel.setEein(Result_People_Hor_Up_Night[Days]);
                                drawingPanel.setFfin(Result_People_Hor_Down_Night[Days]);
                                drawingPanel.setGgin(Result_People_Ver_Left_Night[Days]);
                                drawingPanel.setHhin(Result_People_Ver_Right_Night[Days]);
//drawingPanel.repaint();


                                Mode_now = inputThread.getMode();
                                Rush_Mode_now = inputThread.getRushMode();
                                Night_Mode_now = inputThread.getNight_mode();

                                TIME_INFO_REQUEST(output_data, TimeInfoREQUEST);
                                TIME_INFO_RECEIVE(c_socket);
                                Current_Date.setText(String.valueOf(Current_Days + 1));
                                if (!Time_now.equals("night"))
                                    break;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        while(Night_Mode_now == 8)
                        {
                            Policy = "Night-2";
                            Current_Policy.setText(Policy);

                            try {
// "SavedSignal"이 신호등 정보가 담긴 파일. 정책결정할때 작성해야함
                                Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                                JSONObject jsonObject2 = (JSONObject) obj2;

                                int i = 0;
                                while (i < 4) {




                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    JSONObject jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("==========================================================================================");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("==========================================================================================");



                                    hor_up_hadSignal = true;

//==============================================================================================================================================
//디스플레이에서 차량수 직접 받는 상황 테스트중이므로 카메라가 잘 될때는 디스플레이에게서 차량수 받는건 지우고 바로 아랫줄에 있는 형식을 살릴 것
//==============================================================================================================================================
//hor_up = receiveThread.getHor_Up();


                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Hor_Up_Night_2[Days] = Total_Car_Hor_Up_Night_2[Days] + hor_up;


/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.,,
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/


                                    hor_up_wait = (Double) jsonObject.get("hor_up");
                                    Total_Car_wait_Hor_Up_Night_2[Days] = Total_Car_wait_Hor_Up_Night_2[Days] + hor_up_wait;
                                    System.out.println("hor up wait : " + hor_up_wait);
                                    System.out.println("Total hor up wait Night-2 : " + Total_Car_wait_Hor_Up_Night_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    JSONObject jsonObject3 = (JSONObject) obj3;
                                    Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                    ver_left_people = (int) Casting_ver_left_people;
                                    Total_people_Ver_Left_Night_2[Days] = Total_people_Ver_Left_Night_2[Days] + ver_left_people;
                                    ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                    Total_people_wait_Ver_Left_Night_2[Days]= Total_people_wait_Ver_Left_Night_2[Days] + ver_left_people_wait;
                                    System.out.println("ver left people : " + ver_left_people);
                                    System.out.println("Total ver left people Night-2 : " + Total_people_Ver_Left_Night_2[Days]);
                                    System.out.println("Total ver left people wait Night-2 : " + Total_people_wait_Ver_Left_Night_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Left_Green");
                                    jsonObject2.put("hor_down", "Right");
                                    jsonObject2.put("ver_left", "Walker");
                                    jsonObject2.put("ver_right", "Right");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("hor up Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Night-2 : " + Total_Car_Hor_Up_Night_2[Days] + " Total hor down Night_2 : " + Total_Car_Hor_Down_Night_2[Days] + " Total ver left Night-2 : " + Total_Car_Ver_Left_Night_2[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_Night_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    hor_up = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);



                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    hor_down_hadSignal = true;

//hor_down = receiveThread.getHor_Down();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Hor_Down_Night_2[Days] = Total_Car_Hor_Down_Night_2[Days] + hor_down;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    hor_down_wait = (Double) jsonObject.get("hor_down");
                                    Total_Car_wait_Hor_Down_Night_2[Days] = Total_Car_wait_Hor_Down_Night_2[Days] + hor_down_wait;
                                    System.out.println("hor down wait : " + hor_down_wait);
                                    System.out.println("Total hor down wait Night-2 : " + Total_Car_wait_Hor_Down_Night_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                    ver_right_people = (int) Casting_ver_right_people;
                                    Total_people_Ver_Right_Night_2[Days] = Total_people_Ver_Right_Night_2[Days] + ver_right_people;
                                    ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                    Total_people_wait_Ver_Right_Night_2[Days] = Total_people_wait_Ver_Right_Night_2[Days] + ver_right_people_wait;
                                    System.out.println("ver right people : " + ver_right_people);
                                    System.out.println("Total ver right people Night-2 : " + Total_people_Ver_Right_Night_2[Days]);
                                    System.out.println("Total ver right people wait Night-2 : " + Total_people_wait_Ver_Right_Night_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Right");
                                    jsonObject2.put("hor_down", "Left_Green");
                                    jsonObject2.put("ver_left", "Right");
                                    jsonObject2.put("ver_right", "Walker");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("hor down Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Night-2 : " + Total_Car_Hor_Up_Night_2[Days] + " Total hor down Night_2 : " + Total_Car_Hor_Down_Night_2[Days] + " Total ver left Night-2 : " + Total_Car_Ver_Left_Night_2[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_Night_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    hor_down = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);




                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    ver_left_hadSignal = true;

//ver_left = receiveThread.getVer_Left();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Ver_Left_Night_2[Days] = Total_Car_Ver_Left_Night_2[Days] + ver_left;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    ver_left_wait = (Double) jsonObject.get("ver_left");
                                    Total_Car_wait_Ver_Left_Night_2[Days] = Total_Car_wait_Ver_Left_Night_2[Days] + ver_left_wait;
                                    System.out.println("ver left wait : " + ver_left_wait);
                                    System.out.println("Total ver left wait Night-2 : " + Total_Car_wait_Ver_Left_Night_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                    hor_down_people = (int) Casting_hor_down_people;
                                    Total_people_Hor_Down_Night_2[Days] = Total_people_Hor_Down_Night_2[Days] + hor_down_people;
                                    hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                    Total_people_wait_Hor_Down_Night_2[Days] = Total_people_wait_Hor_Down_Night_2[Days] + hor_down_people_wait;
                                    System.out.println("hor down people : " + hor_down_people);
                                    System.out.println("Total hor down people Night-2 : " + Total_people_Hor_Down_Night_2[Days]);
                                    System.out.println("Total hor down people wait Night-2 : " + Total_people_wait_Hor_Down_Night_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Right");
                                    jsonObject2.put("hor_down", "Walker");
                                    jsonObject2.put("ver_left", "Left_Green");
                                    jsonObject2.put("ver_right", "Right");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("ver left Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Night-2 : " + Total_Car_Hor_Up_Night_2[Days] + " Total hor down Night_2 : " + Total_Car_Hor_Down_Night_2[Days] + " Total ver left Night-2 : " + Total_Car_Ver_Left_Night_2[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_Night_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    ver_left = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);





                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    ver_right_hadSignal = true;

//ver_right = receiveThread.getVer_Right();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Ver_Right_Night_2[Days] = Total_Car_Ver_Right_Night_2[Days] + ver_right;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    ver_right_wait = (Double) jsonObject.get("ver_right");
                                    Total_Car_wait_Ver_Right_Night_2[Days] = Total_Car_wait_Ver_Right_Night_2[Days] + ver_right_wait;
                                    System.out.println("ver right wait : " + ver_right_wait);
                                    System.out.println("Total ver right wait Night-2 : " + Total_Car_wait_Ver_Right_Night_2[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                    hor_up_people = (int) Casting_hor_up_people;
                                    Total_people_Hor_Up_Night_2[Days] = Total_people_Hor_Up_Night_2[Days] + hor_up_people;
                                    hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                    Total_people_wait_Hor_Up_Night_2[Days] = Total_people_wait_Hor_Up_4[Days] + hor_up_people_wait;
                                    System.out.println("hor_up people : " + hor_up_people);
                                    System.out.println("Total hor_up people Night-2 : " + Total_people_Hor_Up_Night_2[Days]);
                                    System.out.println("Total hor_up people wait Night-2: " + Total_people_wait_Hor_Up_Night_2[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Walker");
                                    jsonObject2.put("hor_down", "Right");
                                    jsonObject2.put("ver_left", "Right");
                                    jsonObject2.put("ver_right", "Left_Green");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("ver right Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up Night-2 : " + Total_Car_Hor_Up_Night_2[Days] + " Total hor down Night_2 : " + Total_Car_Hor_Down_Night_2[Days] + " Total ver left Night-2 : " + Total_Car_Ver_Left_Night_2[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_Night_2[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    ver_right = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);


                                }
                                hor_up_hadSignal = false;
                                hor_down_hadSignal = false;
                                ver_left_hadSignal = false;
                                ver_right_hadSignal = false;

                                Result_Car_Hor_Up_Night_2[Days] = Total_Car_wait_Hor_Up_Night_2[Days] / Total_Car_Hor_Up_Night_2[Days];
                                Result_Car_Hor_Down_Night_2[Days] = Total_Car_wait_Hor_Down_Night_2[Days] / Total_Car_Hor_Down_Night_2[Days];
                                Result_Car_Ver_Left_Night_2[Days] = Total_Car_wait_Ver_Left_Night_2[Days] / Total_Car_Ver_Left_Night_2[Days];
                                Result_Car_Ver_Right_Night_2[Days] = Total_Car_wait_Ver_Right_Night_2[Days] / Total_Car_Ver_Right_Night_2[Days];

                                Result_People_Hor_Up_Night_2[Days] = Total_people_wait_Hor_Up_Night_2[Days] / Total_people_Hor_Up_Night_2[Days];
                                Result_People_Hor_Down_Night_2[Days] = Total_people_wait_Hor_Down_Night_2[Days] / Total_people_Hor_Down_Night_2[Days];
                                Result_People_Ver_Left_Night_2[Days] = Total_people_wait_Ver_Left_Night_2[Days] / Total_people_Ver_Left_Night_2[Days];
                                Result_People_Ver_Right_Night_2[Days] = Total_people_wait_Ver_Right_Night_2[Days] / Total_people_Ver_Right_Night_2[Days];

                                if (Double.isNaN(Result_People_Hor_Up_Night_2[Days]))
                                    Result_People_Hor_Up_Night_2[Days] = 0;
                                if (Double.isNaN(Result_People_Hor_Down_Night_2[Days]))
                                    Result_People_Hor_Down_Night_2[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Left_Night_2[Days]))
                                    Result_People_Ver_Left_Night_2[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Right_Night_2[Days]))
                                    Result_People_Ver_Right_Night_2[Days] = 0;

                                if(Double.isNaN(Result_Car_Hor_Up_Night_2[Days]))
                                    Result_Car_Hor_Up_Night_2[Days] = 0;
                                if(Double.isNaN(Result_Car_Hor_Down_Night_2[Days]))
                                    Result_Car_Hor_Down_Night_2[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Left_Night_2[Days]))
                                    Result_Car_Ver_Left_Night_2[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Right_Night_2[Days]))
                                    Result_Car_Ver_Right_Night_2[Days] = 0;


                                System.out.println("1일의 총 위쪽 차량수(4) : " + Total_Car_Hor_Up_Night_2[0]);
                                System.out.println("1일의 총 아래쪽 차량수(4) : " + Total_Car_Hor_Down_Night_2[0]);
                                System.out.println("1일의 총 왼쪽 차량수(4) : " + Total_Car_Ver_Left_Night_2[0]);
                                System.out.println("1일의 총 오른쪽 차량수(4) : " + Total_Car_Ver_Right_Night_2[0]);
                                System.out.println("오늘의 총 위쪽 차량수(4) : " + Total_Car_Hor_Up_Night_2[Days]);
                                System.out.println("오늘의 총 아래쪽 차량수(4) : " + Total_Car_Hor_Down_Night_2[Days]);
                                System.out.println("오늘의 총 왼쪽 차량수(4) : " + Total_Car_Ver_Left_Night_2[Days]);
                                System.out.println("오늘의 총 오른쪽 차량수(4) : " + Total_Car_Ver_Right_Night_2[Days]);


                                System.out.println("위쪽 정책점수(4) : " + Result_Car_Hor_Up_Night_2[Days]);
                                System.out.println("아래쪽 정책점수(4) : " + Result_Car_Hor_Down_Night_2[Days]);
                                System.out.println("왼쪽 정책점수(4) : " + Result_Car_Ver_Left_Night_2[Days]);
                                System.out.println("오른쪽 정책점수(4) : " + Result_Car_Ver_Right_Night_2[Days]);


                                drawingPanel.setAain(Result_Car_Hor_Up_Night_2[Days]);
                                drawingPanel.setBbin(Result_Car_Hor_Down_Night_2[Days]);
                                drawingPanel.setCcin(Result_Car_Ver_Left_Night_2[Days]);
                                drawingPanel.setDdin(Result_Car_Ver_Right_Night_2[Days]);
                                drawingPanel.setEein(Result_People_Hor_Up_Night_2[Days]);
                                drawingPanel.setFfin(Result_People_Hor_Down_Night_2[Days]);
                                drawingPanel.setGgin(Result_People_Ver_Left_Night_2[Days]);
                                drawingPanel.setHhin(Result_People_Ver_Right_Night_2[Days]);
//drawingPanel.repaint();

                                Mode_now = inputThread.getMode();
                                Rush_Mode_now = inputThread.getRushMode();
                                Night_Mode_now = inputThread.getNight_mode();

                                TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
                                TIME_INFO_RECEIVE(c_socket);
                                Current_Date.setText(String.valueOf(Current_Days+1));
                                if(!Time_now.equals("night"))
                                    break;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    while (Time_now.equals("normal")) {
                        Mode_now = inputThread.getMode();
                        Rush_Mode_now = inputThread.getRushMode();
                        Night_Mode_now = inputThread.getNight_mode();

                        System.out.println("normal now");
                        System.out.println("Days : " + Days);
                        Current_Time.setText("Normal");

                        while (Mode_now == 1) {
                            Policy = "Normal-1";
                            Current_Policy.setText(Policy);

                            System.out.println("================================================================================================================================================================================================");
                            System.out.println("================================================================================================================================================================================================");
                            System.out.println("================================================================================================================================================================================================");

                            try {
// "SavedSignal"이 신호등 정보가 담긴 파일. 정책결정할때 작성해야함
                                Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                                JSONObject jsonObject2 = (JSONObject) obj2;

                                int i = 0;
                                while (i < 4) {


                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    JSONObject jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("==========================================================================================");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("==========================================================================================");


                                    if (hor_up >= hor_down && hor_up >= ver_left && hor_up >= ver_right && hor_up_hadSignal == false) {
                                        hor_up_hadSignal = true;

//==============================================================================================================================================
//디스플레이에서 차량수 직접 받는 상황 테스트중이므로 카메라가 잘 될때는 디스플레이에게서 차량수 받는건 지우고 바로 아랫줄에 있는 형식을 살릴 것
//==============================================================================================================================================
//hor_up = receiveThread.getHor_Up();


                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Hor_Up_1[Days] = Total_Car_Hor_Up_1[Days] + hor_up;


/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.,,
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/


                                        hor_up_wait = (Double) jsonObject.get("hor_up");
                                        Total_Car_wait_Hor_Up_1[Days] = Total_Car_wait_Hor_Up_1[Days] + hor_up_wait;
                                        System.out.println("hor up wait : " + hor_up_wait);
                                        System.out.println("Total hor up wait 1 : " + Total_Car_wait_Hor_Up_1[Days]);


                                        PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                        ver_left_people = (int) Casting_ver_left_people;
                                        Total_people_Ver_Left_1[Days] = Total_people_Ver_Left_1[Days] + ver_left_people;
                                        ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                        Total_people_wait_Ver_Left_1[Days] = Total_people_wait_Ver_Left_1[Days] + ver_left_people_wait;
                                        System.out.println("ver left people : " + ver_left_people);
                                        System.out.println("Total ver left people : " + Total_people_Ver_Left_1[Days]);
                                        System.out.println("Total ver left people wait 1 : " + Total_people_wait_Ver_Left_1[Days]);


                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Left_Green");
                                        jsonObject2.put("hor_down", "Right");
                                        jsonObject2.put("ver_left", "Walker");
                                        jsonObject2.put("ver_right", "Right");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("hor up Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up1 : " + Total_Car_Hor_Up_1[Days] + " Total hor down1 : " + Total_Car_Hor_Down_1[Days] + " Total ver left1 : " + Total_Car_Ver_Left_1[Days] + " Total ver right1 : " + Total_Car_Ver_Right_1[Days]);
                                        System.out.println("");
                                        Thread.sleep(hor_up * 100 + 1000);
                                        hor_up = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else if (hor_down > hor_up && hor_down >= ver_left && hor_down >= ver_right && hor_down_hadSignal == false) {
                                        hor_down_hadSignal = true;

//hor_down = receiveThread.getHor_Down();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Hor_Down_1[Days] = Total_Car_Hor_Down_1[Days] + hor_down;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        hor_down_wait = (Double) jsonObject.get("hor_down");
                                        Total_Car_wait_Hor_Down_1[Days] = Total_Car_wait_Hor_Down_1[Days] + hor_down_wait;
                                        System.out.println("hor down wait : " + hor_down_wait);
                                        System.out.println("Total hor down wait 1 : " + Total_Car_wait_Hor_Down_1[Days]);



                                        PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                        ver_right_people = (int) Casting_ver_right_people;
                                        Total_people_Ver_Right_1[Days] = Total_people_Ver_Right_1[Days] + ver_right_people;
                                        ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                        Total_people_wait_Ver_Right_1[Days] = Total_people_wait_Ver_Right_1[Days] + ver_right_people_wait;
                                        System.out.println("ver right people : " + ver_right_people);
                                        System.out.println("Total ver right people : " + Total_people_Ver_Right_1[Days]);
                                        System.out.println("Total ver right people wait 1 : " + Total_people_wait_Ver_Right_1[Days]);

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Right");
                                        jsonObject2.put("hor_down", "Left_Green");
                                        jsonObject2.put("ver_left", "Right");
                                        jsonObject2.put("ver_right", "Walker");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("hor down Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up1 : " + Total_Car_Hor_Up_1[Days] + " Total hor down1 : " + Total_Car_Hor_Down_1[Days] + " Total ver left1 : " + Total_Car_Ver_Left_1[Days] + " Total ver right1 : " + Total_Car_Ver_Right_1[Days]);
                                        System.out.println("");
                                        Thread.sleep(hor_down * 100 + 1000);
                                        hor_down = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else if (ver_left > hor_up && ver_left > hor_down && ver_left >= ver_right && ver_left_hadSignal == false) {
                                        ver_left_hadSignal = true;

//ver_left = receiveThread.getVer_Left();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Ver_Left_1[Days] = Total_Car_Ver_Left_1[Days] + ver_left;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        ver_left_wait = (Double) jsonObject.get("ver_left");
                                        Total_Car_wait_Ver_Left_1[Days] = Total_Car_wait_Ver_Left_1[Days] + ver_left_wait;
                                        System.out.println("ver left wait1 : " + ver_left_wait);
                                        System.out.println("Total ver left wait1 : " + Total_Car_wait_Ver_Left_1[Days]);


                                        PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                        hor_down_people = (int) Casting_hor_down_people;
                                        Total_people_Hor_Down_1[Days] = Total_people_Hor_Down_1[Days] + hor_down_people;
                                        hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                        Total_people_wait_Hor_Down_1[Days] = Total_people_wait_Hor_Down_1[Days] + hor_down_people_wait;
                                        System.out.println("hor down people : " + hor_down_people);
                                        System.out.println("Total hor down people : " + Total_people_Hor_Down_1[Days]);
                                        System.out.println("Total hor down people wait1 : " + Total_people_wait_Hor_Down_1[Days]);


                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Right");
                                        jsonObject2.put("hor_down", "Walker");
                                        jsonObject2.put("ver_left", "Left_Green");
                                        jsonObject2.put("ver_right", "Right");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("ver left Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up1 : " + Total_Car_Hor_Up_1[Days] + " Total hor down1 : " + Total_Car_Hor_Down_1[Days] + " Total ver left1 : " + Total_Car_Ver_Left_1[Days] + " Total ver right1 : " + Total_Car_Ver_Right_1[Days]);
                                        System.out.println("");
                                        Thread.sleep(ver_left * 100 + 1000);
                                        ver_left = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else if (ver_right > ver_left && ver_right > hor_up && ver_right > hor_down && ver_right_hadSignal == false) {
                                        ver_right_hadSignal = true;

//ver_right = receiveThread.getVer_Right();
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        Total_Car_Ver_Right_1[Days] = Total_Car_Ver_Right_1[Days] + ver_right;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                        ver_right_wait = (Double) jsonObject.get("ver_right");
                                        Total_Car_wait_Ver_Right_1[Days] = Total_Car_wait_Ver_Right_1[Days] + ver_right_wait;
                                        System.out.println("ver right wait : " + ver_right_wait);
                                        System.out.println("Total ver right wait1 : " + Total_Car_wait_Ver_Right_1[Days]);


                                        PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                        PEOPLE_INFO_RECEIVE(c_socket);
                                        Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                        JSONObject jsonObject3 = (JSONObject) obj3;
                                        Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                        hor_up_people = (int) Casting_hor_up_people;
                                        Total_people_Hor_Up_1[Days] = Total_people_Hor_Up_1[Days] + hor_up_people;
                                        hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                        Total_people_wait_Hor_Up_1[Days] = Total_people_wait_Hor_Up_1[Days] + hor_up_people_wait;
                                        System.out.println("hor up people : " + hor_up_people);
                                        System.out.println("Total hor up people : " + Total_people_Hor_Up_1[Days]);
                                        System.out.println("Total hor up people wait1 : " + Total_people_wait_Hor_Up_1[Days]);


                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Walker");
                                        jsonObject2.put("hor_down", "Right");
                                        jsonObject2.put("ver_left", "Right");
                                        jsonObject2.put("ver_right", "Left_Green");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        System.out.println("ver right Now");
                                        System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                        System.out.println("Total hor up1 : " + Total_Car_Hor_Up_1[Days] + " Total hor down1 : " + Total_Car_Hor_Down_1[Days] + " Total ver left1 : " + Total_Car_Ver_Left_1[Days] + " Total ver right1 : " + Total_Car_Ver_Right_1[Days]);
                                        System.out.println("");
                                        Thread.sleep(ver_right * 100 + 1000);
                                        ver_right = -1;
                                        i++;

                                        jsonObject2.put("File", "signal");
                                        jsonObject2.put("hor_up", "Red");
                                        jsonObject2.put("hor_down", "Red");
                                        jsonObject2.put("ver_left", "Red");
                                        jsonObject2.put("ver_right", "Red");
                                        Signal_Save(jsonObject2);
                                        Signal_Send(output_data);
                                        Display_Response(c_socket);
                                        Thread.sleep(1000);


                                    } else {
//System.out.println("해당없음");

                                    }
                                }
                                hor_up_hadSignal = false;
                                hor_down_hadSignal = false;
                                ver_left_hadSignal = false;
                                ver_right_hadSignal = false;

                                Result_Car_Hor_Up_1[Days] = Total_Car_wait_Hor_Up_1[Days] / Total_Car_Hor_Up_1[Days];
                                Result_Car_Hor_Down_1[Days] = Total_Car_wait_Hor_Down_1[Days] / Total_Car_Hor_Down_1[Days];
                                Result_Car_Ver_Left_1[Days] = Total_Car_wait_Ver_Left_1[Days] / Total_Car_Ver_Left_1[Days];
                                Result_Car_Ver_Right_1[Days] = Total_Car_wait_Ver_Right_1[Days] / Total_Car_Ver_Right_1[Days];

                                Result_People_Hor_Up_1[Days] = Total_people_wait_Hor_Up_1[Days] / Total_people_Hor_Up_1[Days];
                                Result_People_Hor_Down_1[Days] = Total_people_wait_Hor_Down_1[Days] / Total_people_Hor_Down_1[Days];
                                Result_People_Ver_Left_1[Days] = Total_people_wait_Ver_Left_1[Days] / Total_people_Ver_Left_1[Days];
                                Result_People_Ver_Right_1[Days] = Total_people_wait_Ver_Right_1[Days] / Total_people_Ver_Right_1[Days];

                                if (Double.isNaN(Result_People_Hor_Up_1[Days]))
                                    Result_People_Hor_Up_1[Days] = 0;
                                if (Double.isNaN(Result_People_Hor_Down_1[Days]))
                                    Result_People_Hor_Down_1[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Left_1[Days]))
                                    Result_People_Ver_Left_1[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Right_1[Days]))
                                    Result_People_Ver_Right_1[Days] = 0;

                                if(Double.isNaN(Result_Car_Hor_Up_1[Days]))
                                    Result_Car_Hor_Up_1[Days] = 0;
                                if(Double.isNaN(Result_Car_Hor_Down_1[Days]))
                                    Result_Car_Hor_Down_1[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Left_1[Days]))
                                    Result_Car_Ver_Left_1[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Right_1[Days]))
                                    Result_Car_Ver_Right_1[Days] = 0;


                                System.out.println("1일의 총 위쪽 차량수(1) : " + Total_Car_Hor_Up_1[0]);
                                System.out.println("1일의 총 아래쪽 차량수(1) : " + Total_Car_Hor_Down_1[0]);
                                System.out.println("1일의 총 왼쪽 차량수(1) : " + Total_Car_Ver_Left_1[0]);
                                System.out.println("1일의 총 오른쪽 차량수(1) : " + Total_Car_Ver_Right_1[0]);
                                System.out.println("오늘의 총 위쪽 차량수(normal) : " + Total_Car_Hor_Up_1[Days]);
                                System.out.println("오늘의 총 아래쪽 차량수(normal) : " + Total_Car_Hor_Down_1[Days]);
                                System.out.println("오늘의 총 왼쪽 차량수(normal) : " + Total_Car_Ver_Left_1[Days]);
                                System.out.println("오늘의 총 오른쪽 차량수(normal) : " + Total_Car_Ver_Right_1[Days]);

                                System.out.println("위쪽 정책점수(1) : " + Result_Car_Hor_Up_1[Days]);
                                System.out.println("아래쪽 정책점수(1) : " + Result_Car_Hor_Down_1[Days]);
                                System.out.println("왼쪽 정책점수(1) : " + Result_Car_Ver_Left_1[Days]);
                                System.out.println("오른쪽 정책점수(1) : " + Result_Car_Ver_Right_1[Days]);

                                drawingPanel.setAain(Result_Car_Hor_Up_1[Days]);
                                drawingPanel.setBbin(Result_Car_Hor_Down_1[Days]);
                                drawingPanel.setCcin(Result_Car_Ver_Left_1[Days]);
                                drawingPanel.setDdin(Result_Car_Ver_Right_1[Days]);
                                drawingPanel.setEein(Result_People_Hor_Up_1[Days]);
                                drawingPanel.setFfin(Result_People_Hor_Down_1[Days]);
                                drawingPanel.setGgin(Result_People_Ver_Left_1[Days]);
                                drawingPanel.setHhin(Result_People_Ver_Right_1[Days]);
//drawingPanel.repaint();


                                Mode_now = inputThread.getMode();
                                Rush_Mode_now = inputThread.getRushMode();
                                Night_Mode_now = inputThread.getNight_mode();

                                TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
                                TIME_INFO_RECEIVE(c_socket);
                                Current_Date.setText(String.valueOf(Current_Days+1));
                                System.out.println("Time now (in policy 1) : " + Time_now);
                                if(!Time_now.equals("normal"))
                                    break;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
// ================================================================================================================================
// 정책 2 - 좌회전 후 직진
// ================================================================================================================================
                        while (Mode_now == 2) {
                            System.out.println("좌회전 후 직진 실행중");

                            Policy = "Normal-2";
                            Current_Policy.setText(Policy);

//System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);

                            CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                            CAR_INFO_RECEIVE(c_socket);
                            Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                            JSONObject jsonObject = (JSONObject) obj;

                            hor_up = receiveThread.getHor_Up();
                            hor_down = receiveThread.getHor_Down();
                            ver_left = receiveThread.getVer_Left();
                            ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                            Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                            JSONObject jsonObject2 = (JSONObject) obj2;

                            if (hor_up + hor_down >= ver_left + ver_right) {

//================================카메라 연결할때 살리기==================================
//================================카메라 연결할때 살리기==================================
/*hor_up = receiveThread.getHor_Up();
hor_down = receiveThread.getHor_Down();
ver_left = receiveThread.getVer_Left();
ver_right = receiveThread.getVer_Right();*/
//================================카메라 연결할때 살리기==================================
//================================카메라 연결할때 살리기==================================

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_2[Days] = Total_Car_Hor_Up_2[Days] + hor_up;
                                Total_Car_Hor_Down_2[Days] = Total_Car_Hor_Down_2[Days] + hor_down;


                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_2[Days] = Total_Car_wait_Hor_Up_2[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_2[Days] = Total_Car_wait_Hor_Down_2[Days] + hor_down_wait;

                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait2 : " + Total_Car_wait_Hor_Up_2[Days]);
                                System.out.println("Total hor down wait2 : " + Total_Car_wait_Hor_Down_2[Days]);


                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Left");
                                jsonObject2.put("hor_down", "Left");
                                jsonObject2.put("ver_left", "Right");
                                jsonObject2.put("ver_right", "Right");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_2[Days] = Total_Car_Hor_Up_2[Days] + hor_up;
                                Total_Car_Hor_Down_2[Days] = Total_Car_Hor_Down_2[Days] + hor_down;


                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_2[Days] = Total_Car_wait_Hor_Up_2[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_2[Days] = Total_Car_wait_Hor_Down_2[Days] + hor_down_wait;

                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait 2 : " + Total_Car_wait_Hor_Up_2[Days]);
                                System.out.println("Total hor down wait 2 : " + Total_Car_wait_Hor_Down_2[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                JSONObject jsonObject3 = (JSONObject) obj3;

                                Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                ver_left_people = (int) Casting_ver_left_people;
                                Total_people_Ver_Left_2[Days] = Total_people_Ver_Left_2[Days] + ver_left_people;
                                ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                Total_people_wait_Ver_Left_2[Days] = Total_people_wait_Ver_Left_2[Days] + ver_left_people_wait;

                                Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                ver_right_people = (int) Casting_ver_right_people;
                                Total_people_Ver_Right_2[Days] = Total_people_Ver_Right_2[Days] + ver_right_people;
                                ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                Total_people_wait_Ver_Right_2[Days] = Total_people_wait_Ver_Right_2[Days] + ver_right_people_wait;

                                System.out.println("왼쪽 사람 수 : " + ver_left_people);
                                System.out.println("오른쪽 사람 수 : " + ver_right_people);
                                System.out.println("왼쪽 총 사람 수 2 : " + Total_people_Ver_Left_2[Days]);
                                System.out.println("오른쪽 총 사람 수 2 : " + Total_people_Ver_Right_2[Days]);
                                System.out.println("왼쪽 총 사람 대기시간 2 : " + Total_people_wait_Ver_Left_2[Days]);
                                System.out.println("오른쪽 총 사람 대기시간 2 : " + Total_people_wait_Ver_Right_2[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Green");
                                jsonObject2.put("hor_down", "Green");
                                jsonObject2.put("ver_left", "Walker");
                                jsonObject2.put("ver_right", "Walker");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 직진 중");
                                System.out.println("");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);

                                System.out.println("==================================================================================================================================================================");
                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                System.out.println("==================================================================================================================================================================");

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);




                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_2[Days] = Total_Car_Ver_Left_2[Days] + ver_left;
                                Total_Car_Ver_Right_2[Days] = Total_Car_Ver_Right_2[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_2[Days] = Total_Car_wait_Ver_Left_2[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_2[Days] = Total_Car_wait_Ver_Right_2[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait2 : " + Total_Car_wait_Ver_Left_2[Days]);
                                System.out.println("Total ver right wait2 : " + Total_Car_wait_Ver_Right_2[Days]);


                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Right");
                                jsonObject2.put("hor_down", "Right");
                                jsonObject2.put("ver_left", "Left");
                                jsonObject2.put("ver_right", "Left");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);




                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_2[Days] = Total_Car_Ver_Left_2[Days] + ver_left;
                                Total_Car_Ver_Right_2[Days] = Total_Car_Ver_Right_2[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_2[Days] = Total_Car_wait_Ver_Left_2[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_2[Days] = Total_Car_wait_Ver_Right_2[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait2 : " + Total_Car_wait_Ver_Left_2[Days]);
                                System.out.println("Total ver right wait2 : " + Total_Car_wait_Ver_Right_2[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                jsonObject3 = (JSONObject) obj3;

                                Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                hor_up_people = (int) Casting_hor_up_people;
                                Total_people_Hor_Up_2[Days] = Total_people_Hor_Up_2[Days] + hor_up_people;
                                hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                Total_people_wait_Hor_Up_2[Days] = Total_people_wait_Hor_Up_2[Days] + hor_up_people_wait;

                                Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                hor_down_people = (int) Casting_hor_down_people;
                                Total_people_Hor_Down_2[Days] = Total_people_Hor_Down_2[Days] + hor_down_people;
                                hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                Total_people_wait_Hor_Down_2[Days] = Total_people_wait_Hor_Down_2[Days] + hor_down_people_wait;

                                System.out.println("위쪽 사람 수 : " + hor_up_people);
                                System.out.println("아래쪽 사람 수 : " + hor_down_people);
                                System.out.println("위쪽 총 사람 수 2: " + Total_people_Hor_Up_2[Days]);
                                System.out.println("아래쪽 총 사람 수 2: " + Total_people_Hor_Down_2[Days]);
                                System.out.println("위쪽 총 사람 대기시간 2 : " + Total_people_wait_Hor_Up_2[Days]);
                                System.out.println("아래쪽 총 사람 대기시간 2 : " + Total_people_wait_Hor_Down_2[Days]);


                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Walker");
                                jsonObject2.put("hor_down", "Walker");
                                jsonObject2.put("ver_left", "Green");
                                jsonObject2.put("ver_right", "Green");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 직진 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);



                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                            } else if (hor_up + hor_down < ver_left + ver_right) {

                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_2[Days] = Total_Car_Ver_Left_2[Days] + ver_left;
                                Total_Car_Ver_Right_2[Days] = Total_Car_Ver_Right_2[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_2[Days] = Total_Car_wait_Ver_Left_2[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_2[Days] = Total_Car_wait_Ver_Right_2[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait2 : " + Total_Car_wait_Ver_Left_2[Days]);
                                System.out.println("Total ver right wait2 : " + Total_Car_wait_Ver_Right_2[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Right");
                                jsonObject2.put("hor_down", "Right");
                                jsonObject2.put("ver_left", "Left");
                                jsonObject2.put("ver_right", "Left");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_2[Days] = Total_Car_Ver_Left_2[Days] + ver_left;
                                Total_Car_Ver_Right_2[Days] = Total_Car_Ver_Right_2[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_2[Days] = Total_Car_wait_Ver_Left_2[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_2[Days] = Total_Car_wait_Ver_Right_2[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait2 : " + Total_Car_wait_Ver_Left_2[Days]);
                                System.out.println("Total ver right wait2 : " + Total_Car_wait_Ver_Right_2[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                JSONObject jsonObject3 = (JSONObject) obj3;

                                Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                hor_up_people = (int) Casting_hor_up_people;
                                Total_people_Hor_Up_2[Days] = Total_people_Hor_Up_2[Days] + hor_up_people;
                                hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                Total_people_wait_Hor_Up_2[Days] = Total_people_wait_Hor_Up_2[Days] + hor_up_people_wait;

                                Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                hor_down_people = (int) Casting_hor_down_people;
                                Total_people_Hor_Down_2[Days] = Total_people_Hor_Down_2[Days] + hor_down_people;
                                hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                Total_people_wait_Hor_Down_2[Days] = Total_people_wait_Hor_Down_2[Days] + hor_down_people_wait;

                                System.out.println("위쪽 사람 수 : " + hor_up_people);
                                System.out.println("아래쪽 사람 수 : " + hor_down_people);
                                System.out.println("위쪽 총 사람 수 2: " + Total_people_Hor_Up_2[Days]);
                                System.out.println("아래쪽 총 사람 수 2: " + Total_people_Hor_Down_2[Days]);
                                System.out.println("위쪽 총 사람 대기시간 2 : " + Total_people_wait_Hor_Up_2[Days]);
                                System.out.println("아래쪽 총 사람 대기시간 2 : " + Total_people_wait_Hor_Down_2[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Walker");
                                jsonObject2.put("hor_down", "Walker");
                                jsonObject2.put("ver_left", "Green");
                                jsonObject2.put("ver_right", "Green");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 직진 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);



                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_2[Days] = Total_Car_Hor_Up_2[Days] + hor_up;
                                Total_Car_Hor_Down_2[Days] = Total_Car_Hor_Down_2[Days] + hor_down;

                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_2[Days] = Total_Car_wait_Hor_Up_2[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_2[Days] = Total_Car_wait_Hor_Down_2[Days] + hor_down_wait;

                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait2 : " + Total_Car_wait_Hor_Up_2[Days]);
                                System.out.println("Total hor down wait2 : " + Total_Car_wait_Hor_Down_2[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Left");
                                jsonObject2.put("hor_down", "Left");
                                jsonObject2.put("ver_left", "Right");
                                jsonObject2.put("ver_right", "Right");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_2[Days] = Total_Car_Hor_Up_2[Days] + hor_up;
                                Total_Car_Hor_Down_2[Days] = Total_Car_Hor_Down_2[Days] + hor_down;


                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_2[Days] = Total_Car_wait_Hor_Up_2[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_2[Days] = Total_Car_wait_Hor_Down_2[Days] + hor_down_wait;

                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait2 : " + Total_Car_wait_Hor_Up_2[Days]);
                                System.out.println("Total hor down wait2 : " + Total_Car_wait_Hor_Down_2[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                jsonObject3 = (JSONObject) obj3;

                                Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                ver_left_people = (int) Casting_ver_left_people;
                                Total_people_Ver_Left_2[Days] = Total_people_Ver_Left_2[Days] + ver_left_people;
                                ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                Total_people_wait_Ver_Left_2[Days] = Total_people_wait_Ver_Left_2[Days] + ver_left_people_wait;

                                Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                ver_right_people = (int) Casting_ver_right_people;
                                Total_people_Ver_Right_2[Days] = Total_people_Ver_Right_2[Days] + ver_right_people;
                                ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                Total_people_wait_Ver_Right_2[Days] = Total_people_wait_Ver_Right_2[Days] + ver_right_people_wait;

                                System.out.println("왼쪽 사람 수 : " + ver_left_people);
                                System.out.println("오른쪽 사람 수 : " + ver_right_people);
                                System.out.println("왼쪽 총 사람 수 2 : " + Total_people_Ver_Left_2[Days]);
                                System.out.println("오른쪽 총 사람 수 2 : " + Total_people_Ver_Right_2[Days]);
                                System.out.println("왼쪽 총 사람 대기시간 2 : " + Total_people_wait_Ver_Left_2[Days]);
                                System.out.println("오른쪽 총 사람 대기시간 2 : " + Total_people_wait_Ver_Right_2[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Green");
                                jsonObject2.put("hor_down", "Green");
                                jsonObject2.put("ver_left", "Walker");
                                jsonObject2.put("ver_right", "Walker");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 직진 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up2 : " + Total_Car_Hor_Up_2[Days] + " Total hor down2 : " + Total_Car_Hor_Down_2[Days] + " Total ver left2 : " + Total_Car_Ver_Left_2[Days] + " Total ver right2 : " + Total_Car_Ver_Right_2[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);



                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                            }

                            Result_Car_Hor_Up_2[Days] = Total_Car_wait_Hor_Up_2[Days] / Total_Car_Hor_Up_2[Days];
                            Result_Car_Hor_Down_2[Days] = Total_Car_wait_Hor_Down_2[Days] / Total_Car_Hor_Down_2[Days];
                            Result_Car_Ver_Left_2[Days] = Total_Car_wait_Ver_Left_2[Days] / Total_Car_Ver_Left_2[Days];
                            Result_Car_Ver_Right_2[Days] = Total_Car_wait_Ver_Right_2[Days] / Total_Car_Ver_Right_2[Days];

                            Result_People_Hor_Up_2[Days] = Total_people_wait_Hor_Up_2[Days] / Total_people_Hor_Up_2[Days];
                            Result_People_Hor_Down_2[Days] = Total_people_wait_Hor_Down_2[Days] / Total_people_Hor_Down_2[Days];
                            Result_People_Ver_Left_2[Days] = Total_people_wait_Ver_Left_2[Days] / Total_people_Ver_Left_2[Days];
                            Result_People_Ver_Right_2[Days] = Total_people_wait_Ver_Right_2[Days] / Total_people_Ver_Right_2[Days];

                            if (Double.isNaN(Result_People_Hor_Up_2[Days]))
                                Result_People_Hor_Up_2[Days] = 0;
                            if (Double.isNaN(Result_People_Hor_Down_2[Days]))
                                Result_People_Hor_Down_2[Days] = 0;
                            if (Double.isNaN(Result_People_Ver_Left_2[Days]))
                                Result_People_Ver_Left_2[Days] = 0;
                            if (Double.isNaN(Result_People_Ver_Right_2[Days]))
                                Result_People_Ver_Right_2[Days] = 0;

                            if(Double.isNaN(Result_Car_Hor_Up_2[Days]))
                                Result_Car_Hor_Up_2[Days] = 0;
                            if(Double.isNaN(Result_Car_Hor_Down_2[Days]))
                                Result_Car_Hor_Down_2[Days] = 0;
                            if(Double.isNaN(Result_Car_Ver_Left_2[Days]))
                                Result_Car_Ver_Left_2[Days] = 0;
                            if(Double.isNaN(Result_Car_Ver_Right_2[Days]))
                                Result_Car_Ver_Right_2[Days] = 0;

                            System.out.println("1일의 총 위쪽 차량수(2) : " + Total_Car_Hor_Up_2[0]);
                            System.out.println("1일의 총 아래쪽 차량수(2) : " + Total_Car_Hor_Down_2[0]);
                            System.out.println("1일의 총 왼쪽 차량수(2) : " + Total_Car_Ver_Left_2[0]);
                            System.out.println("1일의 총 오른쪽 차량수(2) : " + Total_Car_Ver_Right_2[0]);
                            System.out.println("오늘의 총 위쪽 차량수(normal) : " + Total_Car_Hor_Up_2[Days]);
                            System.out.println("오늘의 총 아래쪽 차량수(normal) : " + Total_Car_Hor_Down_2[Days]);
                            System.out.println("오늘의 총 왼쪽 차량수(normal) : " + Total_Car_Ver_Left_2[Days]);
                            System.out.println("오늘의 총 오른쪽 차량수(normal) : " + Total_Car_Ver_Right_2[Days]);

                            System.out.println("위쪽 정책점수(2) : " + Result_Car_Hor_Up_2[Days]);
                            System.out.println("아래쪽 정책점수(2) : " + Result_Car_Hor_Down_2[Days]);
                            System.out.println("왼쪽 정책점수(2) : " + Result_Car_Ver_Left_2[Days]);
                            System.out.println("오른쪽 정책점수(2) : " + Result_Car_Ver_Right_2[Days]);


                            drawingPanel.setAain(Result_Car_Hor_Up_2[Days]);
                            drawingPanel.setBbin(Result_Car_Hor_Down_2[Days]);
                            drawingPanel.setCcin(Result_Car_Ver_Left_2[Days]);
                            drawingPanel.setDdin(Result_Car_Ver_Right_2[Days]);
                            drawingPanel.setEein(Result_People_Hor_Up_2[Days]);
                            drawingPanel.setFfin(Result_People_Hor_Down_2[Days]);
                            drawingPanel.setGgin(Result_People_Ver_Left_2[Days]);
                            drawingPanel.setHhin(Result_People_Ver_Right_2[Days]);
//drawingPanel.repaint();

                            Mode_now = inputThread.getMode();
                            Rush_Mode_now = inputThread.getRushMode();
                            Night_Mode_now = inputThread.getNight_mode();

                            TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
                            TIME_INFO_RECEIVE(c_socket);
                            Current_Date.setText(String.valueOf(Current_Days+1));
                            if(!Time_now.equals("normal"))
                                break;

                        }

                        while (Mode_now == 3) {
                            System.out.println("직진 후 좌회전 실행중");

                            Policy = "Normal-3";
                            Current_Policy.setText(Policy);

//System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);


                            CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                            CAR_INFO_RECEIVE(c_socket);
                            Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                            JSONObject jsonObject = (JSONObject) obj;

                            hor_up = receiveThread.getHor_Up();
                            hor_down = receiveThread.getHor_Down();
                            ver_left = receiveThread.getVer_Left();
                            ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                            Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                            JSONObject jsonObject2 = (JSONObject) obj2;

                            if (hor_up + hor_down >= ver_left + ver_right) {

//================================카메라 연결할때 살리기==================================
//================================카메라 연결할때 살리기==================================
/*hor_up = receiveThread.getHor_Up();
hor_down = receiveThread.getHor_Down();
ver_left = receiveThread.getVer_Left();
ver_right = receiveThread.getVer_Right();*/
//================================카메라 연결할때 살리기==================================
//================================카메라 연결할때 살리기==================================
                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_3[Days] = Total_Car_Hor_Up_3[Days] + hor_up;
                                Total_Car_Hor_Down_3[Days] = Total_Car_Hor_Down_3[Days] + hor_down;


                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_3[Days] = Total_Car_wait_Hor_Up_3[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_3[Days] = Total_Car_wait_Hor_Down_3[Days] + hor_down_wait;

//사람수 봐야하는데 콘솔 너무 난잡해
                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait3 : " + Total_Car_wait_Hor_Up_3[Days]);
                                System.out.println("Total hor down wait3 : " + Total_Car_wait_Hor_Down_3[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                JSONObject jsonObject3 = (JSONObject) obj3;

                                Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                ver_left_people = (int) Casting_ver_left_people;
                                Total_people_Ver_Left_3[Days] = Total_people_Ver_Left_3[Days] + ver_left_people;
                                ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                Total_people_wait_Ver_Left_3[Days] = Total_people_wait_Ver_Left_3[Days] + ver_left_people_wait;

                                Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                ver_right_people = (int) Casting_ver_right_people;
                                Total_people_Ver_Right_3[Days] = Total_people_Ver_Right_3[Days] + ver_right_people;
                                ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                Total_people_wait_Ver_Right_3[Days] = Total_people_wait_Ver_Right_3[Days] + ver_right_people_wait;

                                System.out.println("왼쪽 사람 수 : " + ver_left_people);
                                System.out.println("오른쪽 사람 수 : " + ver_right_people);
                                System.out.println("왼쪽 총 사람 수 3 : " + Total_people_Ver_Left_3[Days]);
                                System.out.println("오른쪽 총 사람 수 3 : " + Total_people_Ver_Right_3[Days]);
                                System.out.println("왼쪽 총 사람 대기시간 3 : " + Total_people_wait_Ver_Left_3[Days]);
                                System.out.println("오른쪽 총 사람 대기시간 3 : " + Total_people_wait_Ver_Right_3[Days]);



                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Green");
                                jsonObject2.put("hor_down", "Green");
                                jsonObject2.put("ver_left", "Walker");
                                jsonObject2.put("ver_right", "Walker");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 직진 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_3[Days] = Total_Car_Hor_Up_3[Days] + hor_up;
                                Total_Car_Hor_Down_3[Days] = Total_Car_Hor_Down_3[Days] + hor_down;

                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_3[Days] = Total_Car_wait_Hor_Up_3[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_3[Days] = Total_Car_wait_Hor_Down_3[Days] + hor_down_wait;

                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait3 : " + Total_Car_wait_Hor_Up_3[Days]);
                                System.out.println("Total hor down wait3 : " + Total_Car_wait_Hor_Down_3[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Left");
                                jsonObject2.put("hor_down", "Left");
                                jsonObject2.put("ver_left", "Right");
                                jsonObject2.put("ver_right", "Right");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_3[Days] = Total_Car_Ver_Left_3[Days] + ver_left;
                                Total_Car_Ver_Right_3[Days] = Total_Car_Ver_Right_3[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_3[Days] = Total_Car_wait_Ver_Left_3[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_3[Days] = Total_Car_wait_Ver_Right_3[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait3 : " + Total_Car_wait_Ver_Left_3[Days]);
                                System.out.println("Total ver right wait3 : " + Total_Car_wait_Ver_Right_3[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                jsonObject3 = (JSONObject) obj3;

                                Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                hor_up_people = (int) Casting_hor_up_people;
                                Total_people_Hor_Up_3[Days] = Total_people_Hor_Up_3[Days] + hor_up_people;
                                hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                Total_people_wait_Hor_Up_3[Days] = Total_people_wait_Hor_Up_3[Days] + hor_up_people_wait;

                                Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                hor_down_people = (int) Casting_hor_down_people;
                                Total_people_Hor_Down_3[Days] = Total_people_Hor_Down_3[Days] + hor_down_people;
                                hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                Total_people_wait_Hor_Down_3[Days] = Total_people_wait_Hor_Down_3[Days] + hor_down_people_wait;

                                System.out.println("위쪽 사람 수 : " + hor_up_people);
                                System.out.println("아래쪽 사람 수 : " + hor_down_people);
                                System.out.println("위쪽 총 사람 수 3 : " + Total_people_Hor_Up_3[Days]);
                                System.out.println("아래쪽 총 사람 수 3 : " + Total_people_Hor_Down_3[Days]);
                                System.out.println("위쪽 총 사람 대기시간 3 : " + Total_people_wait_Hor_Up_3[Days]);
                                System.out.println("아래쪽 총 사람 대기시간 3 : " + Total_people_wait_Hor_Down_3[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Walker");
                                jsonObject2.put("hor_down", "Walker");
                                jsonObject2.put("ver_left", "Green");
                                jsonObject2.put("ver_right", "Green");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 직진 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_3[Days] = Total_Car_Ver_Left_3[Days] + ver_left;
                                Total_Car_Ver_Right_3[Days] = Total_Car_Ver_Right_3[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_3[Days] = Total_Car_wait_Ver_Left_3[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_3[Days] = Total_Car_wait_Ver_Right_3[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait3 : " + Total_Car_wait_Ver_Left_3[Days]);
                                System.out.println("Total ver right wait3 : " + Total_Car_wait_Ver_Right_3[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Right");
                                jsonObject2.put("hor_down", "Right");
                                jsonObject2.put("ver_left", "Left");
                                jsonObject2.put("ver_right", "Left");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);

                            } else if (hor_up + hor_down < ver_left + ver_right) {

                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_3[Days] = Total_Car_Ver_Left_3[Days] + ver_left;
                                Total_Car_Ver_Right_3[Days] = Total_Car_Ver_Right_3[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_3[Days] = Total_Car_wait_Ver_Left_3[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_3[Days] = Total_Car_wait_Ver_Right_3[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait3 : " + Total_Car_wait_Ver_Left_3[Days]);
                                System.out.println("Total ver right wait3 : " + Total_Car_wait_Ver_Right_3[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                JSONObject jsonObject3 = (JSONObject) obj3;

                                Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                hor_up_people = (int) Casting_hor_up_people;
                                Total_people_Hor_Up_3[Days] = Total_people_Hor_Up_3[Days] + hor_up_people;
                                hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                Total_people_wait_Hor_Up_3[Days] = Total_people_wait_Hor_Up_3[Days] + hor_up_people_wait;

                                Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                hor_down_people = (int) Casting_hor_down_people;
                                Total_people_Hor_Down_3[Days] = Total_people_Hor_Down_3[Days] + hor_down_people;
                                hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                Total_people_wait_Hor_Down_3[Days] = Total_people_wait_Hor_Down_3[Days] + hor_down_people_wait;

                                System.out.println("위쪽 사람 수 : " + hor_up_people);
                                System.out.println("아래쪽 사람 수 : " + hor_down_people);
                                System.out.println("위쪽 총 사람 수 3 : " + Total_people_Hor_Up_3[Days]);
                                System.out.println("아래쪽 총 사람 수 3 : " + Total_people_Hor_Down_3[Days]);
                                System.out.println("위쪽 총 사람 대기시간 3 : " + Total_people_wait_Hor_Up_3[Days]);
                                System.out.println("아래쪽 총 사람 대기시간 3 : " + Total_people_wait_Hor_Down_3[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Walker");
                                jsonObject2.put("hor_down", "Walker");
                                jsonObject2.put("ver_left", "Green");
                                jsonObject2.put("ver_right", "Green");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 직진 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Ver_Left_3[Days] = Total_Car_Ver_Left_3[Days] + ver_left;
                                Total_Car_Ver_Right_3[Days] = Total_Car_Ver_Right_3[Days] + ver_right;


                                ver_left_wait = (Double) jsonObject.get("ver_left");
                                ver_right_wait = (Double) jsonObject.get("ver_right");
                                Total_Car_wait_Ver_Left_3[Days] = Total_Car_wait_Ver_Left_3[Days] + ver_left_wait;
                                Total_Car_wait_Ver_Right_3[Days] = Total_Car_wait_Ver_Right_3[Days] + ver_right_wait;

                                System.out.println("ver left wait : " + ver_left_wait);
                                System.out.println("ver right wait : " + ver_right_wait);
                                System.out.println("Total ver left wait3 : " + Total_Car_wait_Ver_Left_3[Days]);
                                System.out.println("Total ver right wait3 : " + Total_Car_wait_Ver_Right_3[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Right");
                                jsonObject2.put("hor_down", "Right");
                                jsonObject2.put("ver_left", "Left");
                                jsonObject2.put("ver_right", "Left");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("ver 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((ver_left + ver_right)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/


                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_3[Days] = Total_Car_Hor_Up_3[Days] + hor_up;
                                Total_Car_Hor_Down_3[Days] = Total_Car_Hor_Down_3[Days] + hor_down;


                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_3[Days] = Total_Car_wait_Hor_Up_3[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_3[Days] = Total_Car_wait_Hor_Down_3[Days] + hor_down_wait;

                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait3 : " + Total_Car_wait_Hor_Up_3[Days]);
                                System.out.println("Total hor down wait3 : " + Total_Car_wait_Hor_Down_3[Days]);


                                PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                PEOPLE_INFO_RECEIVE(c_socket);
                                obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                jsonObject3 = (JSONObject) obj3;

                                Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                ver_left_people = (int) Casting_ver_left_people;
                                Total_people_Ver_Left_3[Days] = Total_people_Ver_Left_3[Days] + ver_left_people;
                                ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                Total_people_wait_Ver_Left_3[Days] = Total_people_wait_Ver_Left_3[Days] + ver_left_people_wait;

                                Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                ver_right_people = (int) Casting_ver_right_people;
                                Total_people_Ver_Right_3[Days] = Total_people_Ver_Right_3[Days] + ver_right_people;
                                ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                Total_people_wait_Ver_Right_3[Days] = Total_people_wait_Ver_Right_3[Days] + ver_right_people_wait;

                                System.out.println("왼쪽 사람 수 : " + ver_left_people);
                                System.out.println("오른쪽 사람 수 : " + ver_right_people);
                                System.out.println("왼쪽 총 사람 수 3 : " + Total_people_Ver_Left_3[Days]);
                                System.out.println("오른쪽 총 사람 수 3 : " + Total_people_Ver_Right_3[Days]);
                                System.out.println("왼쪽 총 사람 대기시간 3 : " + Total_people_wait_Ver_Left_3[Days]);
                                System.out.println("오른쪽 총 사람 대기시간 3 : " + Total_people_wait_Ver_Right_3[Days]);


                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Green");
                                jsonObject2.put("hor_down", "Green");
                                jsonObject2.put("ver_left", "Walker");
                                jsonObject2.put("ver_right", "Walker");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 직진 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);


                                CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                CAR_INFO_RECEIVE(c_socket);
                                obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                jsonObject = (JSONObject) obj;

                                hor_up = receiveThread.getHor_Up();
                                hor_down = receiveThread.getHor_Down();
                                ver_left = receiveThread.getVer_Left();
                                ver_right = receiveThread.getVer_Right();

                            /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                            hor_up = (int) Casting_hor_up;
                            Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                            hor_down = (int) Casting_hor_down;
                            Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                            ver_left = (int) Casting_ver_left;
                            Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                            ver_right = (int) Casting_ver_right;*/

                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                Total_Car_Hor_Up_3[Days] = Total_Car_Hor_Up_3[Days] + hor_up;
                                Total_Car_Hor_Down_3[Days] = Total_Car_Hor_Down_3[Days] + hor_down;

                                hor_up_wait = (Double) jsonObject.get("hor_up");
                                hor_down_wait = (Double) jsonObject.get("hor_down");
                                Total_Car_wait_Hor_Up_3[Days] = Total_Car_wait_Hor_Up_3[Days] + hor_up_wait;
                                Total_Car_wait_Hor_Down_3[Days] = Total_Car_wait_Hor_Down_3[Days] + hor_down_wait;

                                System.out.println("hor up wait : " + hor_up_wait);
                                System.out.println("hor down wait : " + hor_down_wait);
                                System.out.println("Total hor up wait3 : " + Total_Car_wait_Hor_Up_3[Days]);
                                System.out.println("Total hor down wait3 : " + Total_Car_wait_Hor_Down_3[Days]);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Left");
                                jsonObject2.put("hor_down", "Left");
                                jsonObject2.put("ver_left", "Right");
                                jsonObject2.put("ver_right", "Right");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                System.out.println("hor 좌회전 중");
                                System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                System.out.println("Total hor up3 : " + Total_Car_Hor_Up_3[Days] + " Total hor down3 : " + Total_Car_Hor_Down_3[Days] + " Total ver left3 : " + Total_Car_Ver_Left_3[Days] + " Total ver right3 : " + Total_Car_Ver_Right_3[Days]);
                                System.out.println("");
                                Thread.sleep((hor_up + hor_down)/2 * 100 + 1000);

                                jsonObject2.put("File", "signal");
                                jsonObject2.put("hor_up", "Red");
                                jsonObject2.put("hor_down", "Red");
                                jsonObject2.put("ver_left", "Red");
                                jsonObject2.put("ver_right", "Red");
                                Signal_Save(jsonObject2);
                                Signal_Send(output_data);
                                Display_Response(c_socket);
                                Thread.sleep(1000);
                            }

                            Result_Car_Hor_Up_3[Days] = Total_Car_wait_Hor_Up_3[Days] / Total_Car_Hor_Up_3[Days];
                            Result_Car_Hor_Down_3[Days] = Total_Car_wait_Hor_Down_3[Days] / Total_Car_Hor_Down_3[Days];
                            Result_Car_Ver_Left_3[Days] = Total_Car_wait_Ver_Left_3[Days] / Total_Car_Ver_Left_3[Days];
                            Result_Car_Ver_Right_3[Days] = Total_Car_wait_Ver_Right_3[Days] / Total_Car_Ver_Right_3[Days];

                            Result_People_Hor_Up_3[Days] = Total_people_wait_Hor_Up_3[Days] / Total_people_Hor_Up_3[Days];
                            Result_People_Hor_Down_3[Days] = Total_people_wait_Hor_Down_3[Days] / Total_people_Hor_Down_3[Days];
                            Result_People_Ver_Left_3[Days] = Total_people_wait_Ver_Left_3[Days] / Total_people_Ver_Left_3[Days];
                            Result_People_Ver_Right_3[Days] = Total_people_wait_Ver_Right_3[Days] / Total_people_Ver_Right_3[Days];

                            if (Double.isNaN(Result_People_Hor_Up_3[Days]))
                                Result_People_Hor_Up_3[Days] = 0;
                            if (Double.isNaN(Result_People_Hor_Down_3[Days]))
                                Result_People_Hor_Down_3[Days] = 0;
                            if (Double.isNaN(Result_People_Ver_Left_3[Days]))
                                Result_People_Ver_Left_3[Days] = 0;
                            if (Double.isNaN(Result_People_Ver_Right_3[Days]))
                                Result_People_Ver_Right_3[Days] = 0;

                            if(Double.isNaN(Result_Car_Hor_Up_3[Days]))
                                Result_Car_Hor_Up_3[Days] = 0;
                            if(Double.isNaN(Result_Car_Hor_Down_3[Days]))
                                Result_Car_Hor_Down_3[Days] = 0;
                            if(Double.isNaN(Result_Car_Ver_Left_3[Days]))
                                Result_Car_Ver_Left_3[Days] = 0;
                            if(Double.isNaN(Result_Car_Ver_Right_3[Days]))
                                Result_Car_Ver_Right_3[Days] = 0;

                            System.out.println("1일의 총 위쪽 차량수(3) : " + Total_Car_Hor_Up_3[0]);
                            System.out.println("1일의 총 아래쪽 차량수(3) : " + Total_Car_Hor_Down_3[0]);
                            System.out.println("1일의 총 왼쪽 차량수(3) : " + Total_Car_Ver_Left_3[0]);
                            System.out.println("1일의 총 오른쪽 차량수(3) : " + Total_Car_Ver_Right_3[0]);
                            System.out.println("오늘의 총 위쪽 차량수(normal) : " + Total_Car_Hor_Up_3[Days]);
                            System.out.println("오늘의 총 아래쪽 차량수(normal) : " + Total_Car_Hor_Down_3[Days]);
                            System.out.println("오늘의 총 왼쪽 차량수(normal) : " + Total_Car_Ver_Left_3[Days]);
                            System.out.println("오늘의 총 오른쪽 차량수(normal) : " + Total_Car_Ver_Right_3[Days]);

                            System.out.println("위쪽 정책점수(3) : " + Result_Car_Hor_Up_3[Days]);
                            System.out.println("아래쪽 정책점수(3) : " + Result_Car_Hor_Down_3[Days]);
                            System.out.println("왼쪽 정책점수(3) : " + Result_Car_Ver_Left_3[Days]);
                            System.out.println("오른쪽 정책점수(3) : " + Result_Car_Ver_Right_3[Days]);


                            drawingPanel.setAain(Result_Car_Hor_Up_3[Days]);
                            drawingPanel.setBbin(Result_Car_Hor_Down_3[Days]);
                            drawingPanel.setCcin(Result_Car_Ver_Left_3[Days]);
                            drawingPanel.setDdin(Result_Car_Ver_Right_3[Days]);
                            drawingPanel.setEein(Result_People_Hor_Up_3[Days]);
                            drawingPanel.setFfin(Result_People_Hor_Down_3[Days]);
                            drawingPanel.setGgin(Result_People_Ver_Left_3[Days]);
                            drawingPanel.setHhin(Result_People_Ver_Right_3[Days]);
//drawingPanel.repaint();


                            Mode_now = inputThread.getMode();
                            Rush_Mode_now = inputThread.getRushMode();
                            Night_Mode_now = inputThread.getNight_mode();

                            TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
                            TIME_INFO_RECEIVE(c_socket);
                            Current_Date.setText(String.valueOf(Current_Days+1));
                            if(!Time_now.equals("normal"))
                                break;
                        }

                        while (Mode_now == 4)
                        {
                            Policy = "Normal-4";
                            Current_Policy.setText(Policy);

                            try {
// "SavedSignal"이 신호등 정보가 담긴 파일. 정책결정할때 작성해야함
                                Object obj2 = parser1.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json"));
                                JSONObject jsonObject2 = (JSONObject) obj2;

                                int i = 0;
                                while (i < 4) {

                                    System.out.println("4 Now");
                                    System.out.println("4 Now");
                                    System.out.println("4 Now");
                                    System.out.println("4 Now");



                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    JSONObject jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("");
                                    System.out.println("==========================================================================================");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("==========================================================================================");



                                    hor_up_hadSignal = true;

//==============================================================================================================================================
//디스플레이에서 차량수 직접 받는 상황 테스트중이므로 카메라가 잘 될때는 디스플레이에게서 차량수 받는건 지우고 바로 아랫줄에 있는 형식을 살릴 것
//==============================================================================================================================================
//hor_up = receiveThread.getHor_Up();


                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Hor_Up_4[Days] = Total_Car_Hor_Up_4[Days] + hor_up;


/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.,,
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/


                                    hor_up_wait = (Double) jsonObject.get("hor_up");
                                    Total_Car_wait_Hor_Up_4[Days] = Total_Car_wait_Hor_Up_4[Days] + hor_up_wait;
                                    System.out.println("hor up wait : " + hor_up_wait);
                                    System.out.println("Total hor up wait 4 : " + Total_Car_wait_Hor_Up_4[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    Object obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    JSONObject jsonObject3 = (JSONObject) obj3;
                                    Casting_ver_left_people = (long) jsonObject3.get("peopleWaitNumLEFT");
                                    ver_left_people = (int) Casting_ver_left_people;
                                    Total_people_Ver_Left_4[Days] = Total_people_Ver_Left_4[Days] + ver_left_people;
                                    ver_left_people_wait = (Double) jsonObject3.get("peopleWaitTimeLEFT");
                                    Total_people_wait_Ver_Left_4[Days]= Total_people_wait_Ver_Left_4[Days] + ver_left_people_wait;
                                    System.out.println("ver left people : " + ver_left_people);
                                    System.out.println("Total ver left people 4 : " + Total_people_Ver_Left_4[Days]);
                                    System.out.println("Total ver left people wait 4 : " + Total_people_wait_Ver_Left_4[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Left_Green");
                                    jsonObject2.put("hor_down", "Right");
                                    jsonObject2.put("ver_left", "Walker");
                                    jsonObject2.put("ver_right", "Right");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("hor up Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up 4 : " + Total_Car_Hor_Up_4[Days] + " Total hor down 4 : " + Total_Car_Hor_Down_4[Days] + " Total ver left 4 : " + Total_Car_Ver_Left_4[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_4[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    hor_up = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);



                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    hor_down_hadSignal = true;

//hor_down = receiveThread.getHor_Down();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Hor_Down_4[Days] = Total_Car_Hor_Down_4[Days] + hor_down;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    hor_down_wait = (Double) jsonObject.get("hor_down");
                                    Total_Car_wait_Hor_Down_4[Days] = Total_Car_wait_Hor_Down_4[Days] + hor_down_wait;
                                    System.out.println("hor down wait : " + hor_down_wait);
                                    System.out.println("Total hor down wait 4 : " + Total_Car_wait_Hor_Down_4[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_ver_right_people = (long) jsonObject3.get("peopleWaitNumRIGHT");
                                    ver_right_people = (int) Casting_ver_right_people;
                                    Total_people_Ver_Right_4[Days] = Total_people_Ver_Right_4[Days] + ver_right_people;
                                    ver_right_people_wait = (Double) jsonObject3.get("peopleWaitTimeRIGHT");
                                    Total_people_wait_Ver_Right_4[Days] = Total_people_wait_Ver_Right_4[Days] + ver_right_people_wait;
                                    System.out.println("ver right people : " + ver_right_people);
                                    System.out.println("Total ver right people 4 : " + Total_people_Ver_Right_4[Days]);
                                    System.out.println("Total ver right people wait 4 : " + Total_people_wait_Ver_Right_4[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Right");
                                    jsonObject2.put("hor_down", "Left_Green");
                                    jsonObject2.put("ver_left", "Right");
                                    jsonObject2.put("ver_right", "Walker");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("hor down Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up 4 : " + Total_Car_Hor_Up_4[Days] + " Total hor down 4 : " + Total_Car_Hor_Down_4[Days] + " Total ver left 4 : " + Total_Car_Ver_Left_4[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_4[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    hor_down = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);




                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    ver_left_hadSignal = true;

//ver_left = receiveThread.getVer_Left();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Ver_Left_4[Days] = Total_Car_Ver_Left_4[Days] + ver_left;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    ver_left_wait = (Double) jsonObject.get("ver_left");
                                    Total_Car_wait_Ver_Left_4[Days] = Total_Car_wait_Ver_Left_4[Days] + ver_left_wait;
                                    System.out.println("ver left wait1 : " + ver_left_wait);
                                    System.out.println("Total ver left wait 4 : " + Total_Car_wait_Ver_Left_4[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_hor_down_people = (long) jsonObject3.get("peopleWaitNumDOWN");
                                    hor_down_people = (int) Casting_hor_down_people;
                                    Total_people_Hor_Down_4[Days] = Total_people_Hor_Down_4[Days] + hor_down_people;
                                    hor_down_people_wait = (Double) jsonObject3.get("peopleWaitTimeDOWN");
                                    Total_people_wait_Hor_Down_4[Days] = Total_people_wait_Hor_Down_4[Days] + hor_down_people_wait;
                                    System.out.println("hor down people : " + hor_down_people);
                                    System.out.println("Total hor down people 4 : " + Total_people_Hor_Down_4[Days]);
                                    System.out.println("Total hor down people wait 4 : " + Total_people_wait_Hor_Down_4[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Right");
                                    jsonObject2.put("hor_down", "Walker");
                                    jsonObject2.put("ver_left", "Left_Green");
                                    jsonObject2.put("ver_right", "Right");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("ver left Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up 4 : " + Total_Car_Hor_Up_4[Days] + " Total hor down 4 : " + Total_Car_Hor_Down_4[Days] + " Total ver left 4 : " + Total_Car_Ver_Left_4[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_4[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    ver_left = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);





                                    CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
                                    CAR_INFO_RECEIVE(c_socket);
                                    obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
                                    jsonObject = (JSONObject) obj;

                                    hor_up = receiveThread.getHor_Up();
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    hor_down = receiveThread.getHor_Down();
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    ver_left = receiveThread.getVer_Left();
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    ver_right = receiveThread.getVer_Right();
                                    if(ver_right_hadSignal == true) {ver_right = -1;}

                                    /*Casting_hor_up = (long) jsonObject.get("hor_up_Num");
                                    hor_up = (int) Casting_hor_up;
                                    if(hor_up_hadSignal == true) {hor_up = -1;}
                                    Casting_hor_down = (long) jsonObject.get("hor_down_Num");
                                    hor_down = (int) Casting_hor_down;
                                    if(hor_down_hadSignal == true) {hor_down = -1;}
                                    Casting_ver_left = (long) jsonObject.get("ver_left_Num");
                                    ver_left = (int) Casting_ver_left;
                                    if(ver_left_hadSignal == true) {ver_left = -1;}
                                    Casting_ver_right = (long) jsonObject.get("ver_right_Num");
                                    ver_right = (int) Casting_ver_right;
                                    if(ver_right_hadSignal == true) {ver_right = -1;}*/

                                    ver_right_hadSignal = true;

//ver_right = receiveThread.getVer_Right();
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    Total_Car_Ver_Right_4[Days] = Total_Car_Ver_Right_4[Days] + ver_right;

/*CAR_INFO_REQUEST(output_data, CarInfoREQUEST);
CAR_INFO_RECEIVE(c_socket);
// "CarWait"가 차량의 대기시간이 담긴 파일.
// ex) {"hor_up":0.0,"hor_down":0.0,"ver_left":0.0,"ver_right":0.0}
Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json"));
JSONObject jsonObject = (JSONObject) obj;*/

                                    ver_right_wait = (Double) jsonObject.get("ver_right");
                                    Total_Car_wait_Ver_Right_4[Days] = Total_Car_wait_Ver_Right_4[Days] + ver_right_wait;
                                    System.out.println("ver right wait : " + ver_right_wait);
                                    System.out.println("Total ver right wait 4 : " + Total_Car_wait_Ver_Right_4[Days]);

                                    PEOPLE_INFO_REQUEST(output_data,PeopleInfoREQUEST);
                                    PEOPLE_INFO_RECEIVE(c_socket);
                                    obj3 = parser2.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json"));
                                    jsonObject3 = (JSONObject) obj3;
                                    Casting_hor_up_people = (long) jsonObject3.get("peopleWaitNumUP");
                                    hor_up_people = (int) Casting_hor_up_people;
                                    Total_people_Hor_Up_4[Days] = Total_people_Hor_Up_4[Days] + hor_up_people;
                                    hor_up_people_wait = (Double) jsonObject3.get("peopleWaitTimeUP");
                                    Total_people_wait_Hor_Up_4[Days] = Total_people_wait_Hor_Up_4[Days] + hor_up_people_wait;
                                    System.out.println("hor_up people : " + hor_up_people);
                                    System.out.println("Total hor_up people 4 : " + Total_people_Hor_Up_4[Days]);
                                    System.out.println("Total hor_up people wait 4: " + Total_people_wait_Hor_Up_4[Days]);

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Walker");
                                    jsonObject2.put("hor_down", "Right");
                                    jsonObject2.put("ver_left", "Right");
                                    jsonObject2.put("ver_right", "Left_Green");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    System.out.println("ver right Now");
                                    System.out.println(" hor up : " + hor_up + " hor down : " + hor_down + " ver left : " + ver_left + " ver right : " + ver_right);
                                    System.out.println("Total hor up 4 : " + Total_Car_Hor_Up_4[Days] + " Total hor down 4 : " + Total_Car_Hor_Down_4[Days] + " Total ver left 4 : " + Total_Car_Ver_Left_4[Days] + " Total ver right 4 : " + Total_Car_Ver_Right_4[Days]);
                                    System.out.println("");
                                    Thread.sleep(4000);
                                    ver_right = -1;
                                    i++;

                                    jsonObject2.put("File", "signal");
                                    jsonObject2.put("hor_up", "Red");
                                    jsonObject2.put("hor_down", "Red");
                                    jsonObject2.put("ver_left", "Red");
                                    jsonObject2.put("ver_right", "Red");
                                    Signal_Save(jsonObject2);
                                    Signal_Send(output_data);
                                    Display_Response(c_socket);
                                    Thread.sleep(1000);


                                }
                                hor_up_hadSignal = false;
                                hor_down_hadSignal = false;
                                ver_left_hadSignal = false;
                                ver_right_hadSignal = false;

                                Result_Car_Hor_Up_4[Days] = Total_Car_wait_Hor_Up_4[Days] / Total_Car_Hor_Up_4[Days];
                                Result_Car_Hor_Down_4[Days] = Total_Car_wait_Hor_Down_4[Days] / Total_Car_Hor_Down_4[Days];
                                Result_Car_Ver_Left_4[Days] = Total_Car_wait_Ver_Left_4[Days] / Total_Car_Ver_Left_4[Days];
                                Result_Car_Ver_Right_4[Days] = Total_Car_wait_Ver_Right_4[Days] / Total_Car_Ver_Right_4[Days];

                                Result_People_Hor_Up_4[Days] = Total_people_wait_Hor_Up_4[Days] / Total_people_Hor_Up_4[Days];
                                Result_People_Hor_Down_4[Days] = Total_people_wait_Hor_Down_4[Days] / Total_people_Hor_Down_4[Days];
                                Result_People_Ver_Left_4[Days] = Total_people_wait_Ver_Left_4[Days] / Total_people_Ver_Left_4[Days];
                                Result_People_Ver_Right_4[Days] = Total_people_wait_Ver_Right_4[Days] / Total_people_Ver_Right_4[Days];

                                if (Double.isNaN(Result_People_Hor_Up_4[Days]))
                                    Result_People_Hor_Up_4[Days] = 0;
                                if (Double.isNaN(Result_People_Hor_Down_4[Days]))
                                    Result_People_Hor_Down_4[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Left_4[Days]))
                                    Result_People_Ver_Left_4[Days] = 0;
                                if (Double.isNaN(Result_People_Ver_Right_4[Days]))
                                    Result_People_Ver_Right_4[Days] = 0;

                                if(Double.isNaN(Result_Car_Hor_Up_4[Days]))
                                    Result_Car_Hor_Up_4[Days] = 0;
                                if(Double.isNaN(Result_Car_Hor_Down_4[Days]))
                                    Result_Car_Hor_Down_4[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Left_4[Days]))
                                    Result_Car_Ver_Left_4[Days] = 0;
                                if(Double.isNaN(Result_Car_Ver_Right_4[Days]))
                                    Result_Car_Ver_Right_4[Days] = 0;


                                System.out.println("1일의 총 위쪽 차량수(4) : " + Total_Car_Hor_Up_4[0]);
                                System.out.println("1일의 총 아래쪽 차량수(4) : " + Total_Car_Hor_Down_4[0]);
                                System.out.println("1일의 총 왼쪽 차량수(4) : " + Total_Car_Ver_Left_4[0]);
                                System.out.println("1일의 총 오른쪽 차량수(4) : " + Total_Car_Ver_Right_4[0]);
                                System.out.println("오늘의 총 위쪽 차량수(4) : " + Total_Car_Hor_Up_4[Days]);
                                System.out.println("오늘의 총 아래쪽 차량수(4) : " + Total_Car_Hor_Down_4[Days]);
                                System.out.println("오늘의 총 왼쪽 차량수(4) : " + Total_Car_Ver_Left_4[Days]);
                                System.out.println("오늘의 총 오른쪽 차량수(4) : " + Total_Car_Ver_Right_4[Days]);


                                System.out.println("위쪽 정책점수(4) : " + Result_Car_Hor_Up_4[Days]);
                                System.out.println("아래쪽 정책점수(4) : " + Result_Car_Hor_Down_4[Days]);
                                System.out.println("왼쪽 정책점수(4) : " + Result_Car_Ver_Left_4[Days]);
                                System.out.println("오른쪽 정책점수(4) : " + Result_Car_Ver_Right_4[Days]);


                                drawingPanel.setAain(Result_Car_Hor_Up_4[Days]);
                                drawingPanel.setBbin(Result_Car_Hor_Down_4[Days]);
                                drawingPanel.setCcin(Result_Car_Ver_Left_4[Days]);
                                drawingPanel.setDdin(Result_Car_Ver_Right_4[Days]);
                                drawingPanel.setEein(Result_People_Hor_Up_4[Days]);
                                drawingPanel.setFfin(Result_People_Hor_Down_4[Days]);
                                drawingPanel.setGgin(Result_People_Ver_Left_4[Days]);
                                drawingPanel.setHhin(Result_People_Ver_Right_4[Days]);
//drawingPanel.repaint();

                                Mode_now = inputThread.getMode();
                                Rush_Mode_now = inputThread.getRushMode();
                                Night_Mode_now = inputThread.getNight_mode();

                                TIME_INFO_REQUEST(output_data,TimeInfoREQUEST);
                                TIME_INFO_RECEIVE(c_socket);
                                Current_Date.setText(String.valueOf(Current_Days+1));
                                if(!Time_now.equals("normal"))
                                    break;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }




                    }
                }

            }catch (Exception e){}
        }catch (Exception e){}

    }


    public static void Signal_Save(JSONObject jsonObject2){
        try {
            FileWriter file = new FileWriter("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json");
            file.write(jsonObject2.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
        }
    }

    //신호등정보 작성이 완료됐다고 소켓으로 신호를 보내는 부분
    public static void Signal_Send(OutputStream output_data){
        try {
            FileReader reader = new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\SavedSignal.json");
            BufferedReader bufReader = new BufferedReader(reader);
            String send = "";
            send = bufReader.readLine();
            output_data.write(send.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //신호전송이 완료됐을때 디스플레이에게서 신호변경됐다고 응답받는부분
    public static void Display_Response(Socket c_socket){
        try {
            InputStream input_data = c_socket.getInputStream();
            byte[] receiveBuffer = new byte[150];
            input_data.read(receiveBuffer);
//System.out.println(new String(receiveBuffer));
        } catch (IOException e) {
        }
    }

    public static void CAR_INFO_REQUEST(OutputStream output_data, String CarInfoREQUEST){
        try {
            output_data.write(CarInfoREQUEST.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 디스플레이에게서 받은 대기시간을 JSON 파일로 저장
    public static void CAR_INFO_RECEIVE(Socket c_socket){
        try {
            InputStream input_data = c_socket.getInputStream();
            byte[] receiveBuffer = new byte[300];
            input_data.read(receiveBuffer);
            System.out.println("****************************************************");
            System.out.println(new String(receiveBuffer));
            System.out.println(new String(receiveBuffer));
            System.out.println(new String(receiveBuffer));
            System.out.println("****************************************************");

            FileWriter file = new FileWriter("C:\\Users\\bjwan\\Downloads\\bal\\CarWait.json");
            file.write(new String(receiveBuffer).trim());
            file.flush();
            file.close();

        } catch (IOException e) {
        }
    }

    public static void PEOPLE_INFO_REQUEST(OutputStream output_data, String PeopleInfoREQUEST){
        try{
            output_data.write(PeopleInfoREQUEST.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void PEOPLE_INFO_RECEIVE(Socket c_socket){
        try{
            InputStream input_data = c_socket.getInputStream();
            byte[] receiveBuffer = new byte[400];
            input_data.read(receiveBuffer);
            System.out.println(new String(receiveBuffer));

            FileWriter file = new FileWriter("C:\\Users\\bjwan\\Downloads\\bal\\PeopleWait.json");
            file.write(new String(receiveBuffer).trim());
            file.flush();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void TIME_INFO_REQUEST(OutputStream output_data, String TimeInfoREQUEST){
        try {
            output_data.write(TimeInfoREQUEST.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void TIME_INFO_RECEIVE(Socket c_socket){
        try {
            InputStream input_data = c_socket.getInputStream();
            byte[] receiveBuffer = new byte[150];
            input_data.read(receiveBuffer);
            System.out.println(new String(receiveBuffer).trim());

//Time_now = (new String(receiveBuffer)).trim();
            FileWriter file = new FileWriter("C:\\Users\\bjwan\\Downloads\\bal\\Time.json");
            file.write(new String(receiveBuffer).trim());
            file.flush();
            file.close();

            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader("C:\\Users\\bjwan\\Downloads\\bal\\Time.json"));
                JSONObject jsonObject = (JSONObject) obj;

                Time_now = (String) jsonObject.get("time_Info");
                Casting_Days = (long) jsonObject.get("Days");
                Days = (int)Casting_Days;
                Current_Days = Days;

            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

        } catch (IOException e) {
        }
    }

    public String getTimeNow(){ return Time_now;}

/*public void setOutputStream(OutputStream output_data)
{
outputdata = output_data;
}
public void setSocket(Socket _socket)
{
m_socket = _socket;
}*/

}