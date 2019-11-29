using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using LitJson;
using System.IO;
using System.Net.Sockets;
using System.Net;
using System;
using System.Text;
using System.Threading;
using UnityEngine.UI;
using UnityEngine.Video;

public class carInfo
{
    public double hor_up;
    public double hor_down;
    public double ver_left;
    public double ver_right;

    public int hor_up_Num;
    public int hor_down_Num;
    public int ver_left_Num;
    public int ver_right_Num;

    public void carTimeInfo(double hu,double hd,double vl,double vr)
    {
        /*
        hor_up = hu;
        hor_down = hd;
        ver_left = vl;
        ver_right = vr;
        */
        hor_up = vl;
        hor_down = vr;
        ver_left = hd;
        ver_right = hu;
    }

    public void carNumInfo(int hu, int hd, int vl, int vr)
    {
        /*
        hor_up_Num = hu;
        hor_down_Num = hd;
        ver_left_Num = vl;
        ver_right_Num = vr;
        */
        hor_up_Num = vl;
        hor_down_Num = vr;
        ver_left_Num = hd;
        ver_right_Num = hu;
    }

}

public class timeInfo
{
    public string time_Info;
    public int Days;
    public timeInfo(string t,int d)
    {
        time_Info = t;
        Days = d;
    }
}

public class peopleInfo
{
    
    public int peopleWaitNumUP;
    public double peopleWaitTimeUP;

    public int peopleWaitNumDOWN;
    public double peopleWaitTimeDOWN;

    public int peopleWaitNumLEFT;
    public double peopleWaitTimeLEFT;

    public int peopleWaitNumRIGHT;
    public double peopleWaitTimeRIGHT;





    public void setNum(int pu, int pd, int pl, int pr)
    {
        peopleWaitNumUP = pu;
        peopleWaitNumDOWN = pd;
        peopleWaitNumLEFT = pl;
        peopleWaitNumRIGHT = pr;
    }
    
    public void setTime(double tu, double td, double tl, double tr)
    {

        peopleWaitTimeUP = tu;
        peopleWaitTimeDOWN = td;
        peopleWaitTimeLEFT = tl;
        peopleWaitTimeRIGHT = tr;
    }
    

}


// 유니티 JSON 사용법 https://iflife1124.tistory.com/30

public class car_json : MonoBehaviour {

    public carInfo ci;

    public peopleInfo pi;

    public timeInfo ti;

    car_Object carob;

    Byte[] _data;
    String _buf;

    string path;


    Dictionary<string, string> SiganlResult;

    public bool serverConnect = false;


    string timeinfo;

    // Use this for initialization
    void Start () {
        pi = new peopleInfo();
     
        carob = car_Object.getObject();
        path = Application.dataPath;

        if (AllObjectS.ServerConnect)
        {
     
            try
            {
                Thread t1 = new Thread(new ThreadStart(Run));
               

                t1.Start();

            }
            catch (Exception a)
            {
                Debug.Log(a.ToString());
            }
           

        }




        //RoadJson();
    }
	
	// Update is called once per frame
	void Update () {
        

    }



    void Run()
    {

 

        while (true)
        {
            try
            {
                _data = new Byte[1024];

                AllObjectS.client.Receive(_data);

                _buf = Encoding.UTF8.GetString(_data);
                

                // 차 정보 요청
                if (_buf.Contains("carinfo"))
                {
                    carob.showTime();

                    ci = new carInfo();
                    ci.carTimeInfo(carob.getTime(AllObjectS.CAR_HOR_UP), carob.getTime(AllObjectS.CAR_HOR_DOWN), carob.getTime(AllObjectS.CAR_VER_LEFT), carob.getTime(AllObjectS.CAR_VER_RIGHT));
                    ci.carNumInfo(carob.TrafficBlockNum(AllObjectS.CAR_HOR_UP), carob.TrafficBlockNum(AllObjectS.CAR_HOR_DOWN), carob.TrafficBlockNum(AllObjectS.CAR_VER_LEFT), carob.TrafficBlockNum(AllObjectS.CAR_VER_RIGHT));

                    sendJson(ci);

                }

                // 신호 변경 완료
                else if (_buf.Contains("signal"))
                {
                    ChangeSingnalJson(_buf);

                    _data = new Byte[1024];
                    _buf = "Signal conversion complete";
                    _data = Encoding.UTF8.GetBytes(_buf);

                    AllObjectS.client.Send(_data);
                }

                // 
                else if(_buf.Contains("timeinfo"))
                {
                    timeinfo = car_create_th.nowTimeInfo;
                    ti = new timeInfo(timeinfo,video.Days);

                    sendJson(ti);


                    
                }

                else if(_buf.Contains("peopleinfo"))
                {

                    pi.setNum(PeopleCreate.peopleTrafficWaitNumUp, PeopleCreate.peopleTrafficWaitNumDown, PeopleCreate.peopleTrafficWaitNumLeft, PeopleCreate.peopleTrafficWaitNumRight);
                    pi.setTime(PeopleCreate.peopleTrafficTimeUp, PeopleCreate.peopleTrafficTimeDown, PeopleCreate.peopleTrafficTimeLeft, PeopleCreate.peopleTrafficTimeRight);
                    sendJson(pi);


                }
            }

            catch(Exception e)
            {
                Debug.Log(e.ToString());

                var text = GameObject.Find("ServerCon").GetComponent<Text>();
                text.text = "서버 연결 상태 : 연결 끊김 ";

                break;
                //serverConnect = true;
            }
            
        }
        

    }

    void sendJson(object obj)
    {

        JsonData infoJson = JsonMapper.ToJson(obj);

        // 유니티에서 실행시 경로 : Display\Assets\Resources\CarInfo.json
        // 빌드 후 실행시 경로 : _DisplayExe32\_DisplayExe32_Data\Resources\CarInfo.json


        // 서버로 전달
        _data = new Byte[1024];

        _buf = infoJson.ToString();

        _data = Encoding.UTF8.GetBytes(_buf);

        AllObjectS.client.Send(_data);


    }

    void ChangeSingnalJson(string jsonStr)
    {

       
        string[] result = jsonStr.Replace("{","").Replace("}","").Replace("\"","").Split(',');
        string[] splitResult;

        SiganlResult = new Dictionary<string, string>();

        foreach(string n in result)
        {
            splitResult = n.Split(':');
            SiganlResult.Add(splitResult[0], splitResult[1]);
            
        }



        

        // 서버에서 받은 값하고 디스플레이에서 설정하는 값이 좀 다르다.
        // 서버에서 받은 신호 값 저장
        car_create_th.hor_Up_Traffic_Light = SetTrafficLight(SiganlResult["ver_right"].ToString());

        car_create_th.hor_Down_Traffic_Light = SetTrafficLight(SiganlResult["ver_left"].ToString());

        car_create_th.ver_Left_Traffic_Light = SetTrafficLight(SiganlResult["hor_up"].ToString());

        car_create_th.ver_Right_Traffic_Light = SetTrafficLight(SiganlResult["hor_down"].ToString());

        // 전체 보행자 신호 초기화
        PeopleCreate.peopleTrafficUP = false;
        PeopleCreate.peopleTrafficRight = false;
        PeopleCreate.peopleTrafficLeft = false;
        PeopleCreate.peopleTrafficDown = false;

        // WALKER 받았을 떄 값 변경
        if (car_create_th.hor_Up_Traffic_Light== AllObjectS.TRAFFIC_LIGHT_WALKER)
        {
            car_create_th.hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
            PeopleCreate.peopleTrafficRight = true;

        }
        if (car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_WALKER)
        {
            car_create_th.hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

            PeopleCreate.peopleTrafficLeft = true;

        }
        if (car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_WALKER)
        {
            car_create_th.ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

            PeopleCreate.peopleTrafficUP = true;

        }
        if (car_create_th.ver_Right_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_WALKER)
        {
            car_create_th.ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

            PeopleCreate.peopleTrafficDown = true;
        }

        // 신호 변경되서 변경된 신호에 있는 보행자 대기 시간 초기화
        PeopleCreate.initTime();

        
    }

    // 서버에서 받은 값 디스플레이에서 사용하는 값으로 변경
    public int SetTrafficLight(string traffic)
    {
        int result=-1;


        if (traffic.Contains("Left_Green"))
            result = AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN;
        else if (traffic.Contains("Red"))
            result = AllObjectS.TRAFFIC_LIGHT_RED;
        else if (traffic.Contains("Left"))
            result = AllObjectS.TRAFFIC_LIGHT_LEFT;
        else if (traffic.Contains("Right"))
            result = AllObjectS.TRAFFIC_LIGHT_RIGHT;
        else if (traffic.Contains("Green"))
            result = AllObjectS.TRAFFIC_LIGHT_GREEN;
        else if (traffic.Contains("Walker"))
            result = AllObjectS.TRAFFIC_LIGHT_WALKER;
            
        return result;
    }


}
