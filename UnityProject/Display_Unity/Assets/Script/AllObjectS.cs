using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using UnityEngine;

public static class AllObjectS  {

    public static Socket client;

    public static Socket client_image;

    public static IPEndPoint ipep_image;

    public static bool ServerConnect=false;
    public static bool ServerConnect_image = false;


    public static int CAR_HOR_UP = 0;
    public static int CAR_HOR_DOWN = 1;
    public static int CAR_VER_LEFT = 2;
    public static int CAR_VER_RIGHT = 3;

    public static int TRAFFIC_LIGHT_RED = 10;
    public static int TRAFFIC_LIGHT_GREEN = 11;
    public static int TRAFFIC_LIGHT_RIGHT = 12;             // 우회전 신호 ->
    public static int TRAFFIC_LIGHT_LEFT = 13;
    public static int TRAFFIC_LIGHT_LEFT_GREEN = 14;
    public static int TRAFFIC_LIGHT_WALKER = 15;   // 워커 -> 보행자 GREEN / 차량 RED



    public static int CAR_DIRECTION_STRAIGHT = 20;
    public static int CAR_DIRECTION_LEFT = 21;
    public static int CAR_DIRECTION_RIGHT = 22;

   

    public static int getCarDirectionLeft()
    {
        // Range(a,b) -> a에서 b까지 // a, a+1, a+2, ..., b-1 까지만 나옴
        int r = Random.Range(20, 22);

        return r;
    }

    public static int getCarDirectionRight()
    {
        // Range(a,b) -> a에서 b까지 // a, a+1, a+2, ..., b-1 까지만 나옴
        int r = Random.Range(0, 2);

        if (r == 0)
            return CAR_DIRECTION_STRAIGHT;
        else
        {
            
            return CAR_DIRECTION_RIGHT;


        }

    }





}
