using System.Collections;
using System.Collections.Generic;
using System.Threading;
using UnityEngine;
using UnityEngine.Video;

public class car_create_th : MonoBehaviour {

    //AllObjectS.CAR_HOR_UP, AllObjectS.CAR_HOR_DOWN, AllObjectS.CAR_VER_LEFT, AllObjectS.CAR_VER_RIGHT
    enum CarRoad { CAR_HOR_UP_1, CAR_HOR_UP_2, CAR_HOR_DOWN_1, CAR_HOR_DOWN_2,
        CAR_VER_LEFT_1, CAR_VER_LEFT_2, CAR_VER_RIGHT_1, CAR_VER_RIGHT_2}

    CarRoad lastCar=CarRoad.CAR_HOR_UP_1;

    float timer = 0;
    public static GameObject car_object;


    public float setSpeed=4;

    public float maxSpeed = 6f;
    public float minSpeed = 4f;


    // 수평(가로)축이 0 이면  빨간불 1 이면 반대
    public static int hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
    public static int hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



    // 수직(세로)축이 0 이면  빨간불 1 이면 반대
    public static int ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_GREEN;
    public static int ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_GREEN;

    public static int trafficNum = 0;



    public static List<int> car_hor_down_list;
    public static List<int> car_hor_up_list;
    public static List<int> car_ver_left_list;
    public static List<int> car_ver_right_list;


    // 8 , 4
    public int car_hor_Limit;
    public int car_ver_Limit;


    //public static float carCreatTime = 0.35f;
    public static float carCreatTime = 0.35f;

    int CarDirction;

    private Stack<CarRoad> CarStack;
    private List<CarRoad> CarRandomList1;
    private List<CarRoad> CarRandomList2;
    private List<CarRoad> CarRandomListNow;


    // 테스트중
    static car_Object carob;


    public static float carCreateTimeRush = 0.16f;
    //public static float carCreateTimeRush = 0.15f;
    public static float carCreateTimeNormal = 0.32f;
    public static float carCreateTimeNight = 1.80f;

    public static string nowTimeInfo = "rush";


    private void Awake()
    {
        car_object = new GameObject();


        carob = car_Object.getObject();

    }

    void Start () {
        video.Days = 0;
        video.isDay = true;

        car_hor_Limit = 13;
        car_ver_Limit = 8;

        car_hor_down_list = new List<int>();
        car_hor_up_list = new List<int>();
        car_ver_left_list = new List<int>();
        car_ver_right_list = new List<int>();

        for(int i=0;i<2;i++)
        {
            car_hor_down_list.Add(0);
            car_hor_up_list.Add(0);
            car_ver_left_list.Add(0);
            car_ver_right_list.Add(0);

        }

        CarStack = new Stack<CarRoad>();

        
        CarRandomList1 = new List<CarRoad> { CarRoad.CAR_HOR_UP_1, CarRoad.CAR_HOR_UP_2, CarRoad.CAR_HOR_DOWN_1, CarRoad.CAR_HOR_DOWN_2,
            CarRoad.CAR_VER_LEFT_1, CarRoad.CAR_VER_LEFT_2, CarRoad.CAR_VER_RIGHT_1, CarRoad.CAR_VER_RIGHT_2 };
        /*
        CarRandomList1 = new List<CarRoad> {  CarRoad.CAR_HOR_UP_2, CarRoad.CAR_HOR_DOWN_1, CarRoad.CAR_HOR_DOWN_2,
            CarRoad.CAR_VER_LEFT_1, CarRoad.CAR_VER_LEFT_2, CarRoad.CAR_VER_RIGHT_1, CarRoad.CAR_VER_RIGHT_2 };*/
        CarRandomList2 = new List<CarRoad> { CarRoad.CAR_HOR_UP_1, CarRoad.CAR_HOR_UP_2, CarRoad.CAR_HOR_DOWN_1, CarRoad.CAR_HOR_DOWN_2 };
        CarRandomListNow = new List<CarRoad>();



        Screen.SetResolution(1920, 1080, true);

        Application.targetFrameRate = 60;

        carCreatTime = carCreateTimeNormal;

        hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RIGHT;
        hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

        ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
        ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
    }


    float createTime = 0;
   
    // Update is called once per frame
    void Update()
    {
        
        timer += Time.deltaTime;

        /*
         * speedRush = 0.12f;
         * public float speedNormal = 0.3f;
         * public float speedNight = 1.2f;
         * 
         *                 | 평상시 -> 퇴근  |           |    퇴근 -> 야간 |            |  야간 -> 출근  |        |    출근 -> 평상시 |
         * -------------------------------------------------------------------------------------------------------------------------------------
         * 0              70        80       90         125      140       175         210    230      240      270                  290     
         *    ->        평상시       |             퇴근            |          야간              |     출근       |      평상시       ->      
         */

         // 평상시
         if (video.vp.time < 70f)
         {
             setCarSpeed("평상시");
             carCreatTime = carCreateTimeNormal;
          
             PeopleCreate.PeopleCreatTime = 0.5f;
         }
         // 평상시 -> 퇴근 변경 중 1
         else if(video.vp.time<80f)
         {
            setCarSpeed("평상시");
            if (carCreatTime > carCreateTimeRush)
                carCreatTime -= 0.008f*Time.deltaTime;
            else
                carCreatTime = carCreateTimeRush;
            PeopleCreate.PeopleCreatTime = 0.5f;

        }
        // 평상시 -> 퇴근 변경 중 2
        else if (video.vp.time < 90f)
        {
            setCarSpeed("러시아워-퇴근");
            if (carCreatTime > carCreateTimeRush)
                carCreatTime -= 0.008f * Time.deltaTime;
            else
                carCreatTime = carCreateTimeRush;
            PeopleCreate.PeopleCreatTime = 0.2f;

        }
        // 러시아워 - 퇴근
        else if (video.vp.time < 125)
         {
             setCarSpeed("러시아워-퇴근");
             carCreatTime = carCreateTimeRush;
            PeopleCreate.PeopleCreatTime = 0.2f;

            //carCreatTime = 0.12f;

        }
        //러시아워 퇴근 -> 야간 변경 중1
        else if (video.vp.time < 140)
        {
            setCarSpeed("러시아워-퇴근");
            if (carCreatTime < carCreateTimeNight)
                carCreatTime += 0.0328f*Time.deltaTime;
            else
                carCreatTime = carCreateTimeNight;
            PeopleCreate.PeopleCreatTime = 0.2f;


        }
        //러시아워 퇴근 -> 야간 변경 중2
        else if (video.vp.time < 175)
        {
            setCarSpeed("야간");
            if (carCreatTime < carCreateTimeNight)
                carCreatTime += 0.0328f * Time.deltaTime;
            else
                carCreatTime = carCreateTimeNight;

            //carCreatTime = 0.12f;
            PeopleCreate.PeopleCreatTime = 2f;

        }
        // 야간
        else if (video.vp.time < 210)
         {
             setCarSpeed("야간");

            
            carCreatTime = carCreateTimeNight;

             PeopleCreate.PeopleCreatTime = 2f;

        }
         // 야간 -> 러시아워 - 출근 으로 변경 중1
        else if (video.vp.time < 230)
        {
            setCarSpeed("야간");

            if (carCreatTime > carCreateTimeRush)
                carCreatTime -= 0.054f * Time.deltaTime;
            else
                carCreatTime = carCreateTimeRush;
            PeopleCreate.PeopleCreatTime = 2f;


        }
        // 야간 -> 러시아워 - 출근 으로 변경 중2
        else if (video.vp.time < 240)
        {
            setCarSpeed("러시아워-출근");

            if (carCreatTime > carCreateTimeRush)
                carCreatTime -= 0.054f * Time.deltaTime;
            else
                carCreatTime = carCreateTimeRush;
            PeopleCreate.PeopleCreatTime = 0.2f;


        }
        // 러시아워 - 출근
        else if (video.vp.time < 270)
         {
             setCarSpeed("러시아워-출근");

             
             carCreatTime = carCreateTimeRush;


             PeopleCreate.PeopleCreatTime = 0.2f;

         }
        // 러시아워 - 출근 -> 평상시 변경 , 평상시 시간에 적용
        else if (video.vp.time < 290)
        {
            setCarSpeed("평상시");

            if (carCreatTime < carCreateTimeNormal)
                carCreatTime += 0.008f * Time.deltaTime;
            else
                carCreatTime = carCreateTimeNormal;
            PeopleCreate.PeopleCreatTime = 0.5f;
        }
        // 평상시
        else
         {
             setCarSpeed("평상시");

             carCreatTime = carCreateTimeNormal;
             PeopleCreate.PeopleCreatTime = 0.5f;

         }

        
         if (Input.GetKeyDown(KeyCode.Space))
         {
            changeTrafficLight();

        }




        // 차량 생성 루틴 가지고
        // carCreatTime 마다 한 번씩 생성
        
        if (timer>=carCreatTime)
        {
            if(CarStack.Count==0)
            {
                
                CarRandomList1 = new List<CarRoad> { CarRoad.CAR_HOR_UP_1, CarRoad.CAR_HOR_UP_2, CarRoad.CAR_HOR_DOWN_1, CarRoad.CAR_HOR_DOWN_2,
            CarRoad.CAR_VER_LEFT_1, CarRoad.CAR_VER_LEFT_2, CarRoad.CAR_VER_RIGHT_1, CarRoad.CAR_VER_RIGHT_2 };
                CarRandomList2 = new List<CarRoad> { CarRoad.CAR_HOR_UP_1, CarRoad.CAR_HOR_UP_2, CarRoad.CAR_HOR_DOWN_1, CarRoad.CAR_HOR_DOWN_2 };

                int rvalue = Random.Range(4, 7);
                CarRoad start = CarRandomList1[rvalue];
                CarRandomList1.RemoveAt(rvalue);
                CarStack.Push(start);

                rvalue = Random.Range(4, 6);
                CarRoad last = CarRandomList1[rvalue];
                CarRandomList1.RemoveAt(rvalue);

                for(int i= CarRandomList1.Count-1; i>=0;i--)
                {
                    rvalue = Random.Range(0, i);
                    CarStack.Push(CarRandomList1[rvalue]);
                    CarRandomList1.RemoveAt(rvalue);

                }

                CarStack.Push(last);

                rvalue = Random.Range(0, 4);
                CarStack.Push(CarRandomList2[rvalue]);
                CarRandomList2.RemoveAt(rvalue);

                rvalue = Random.Range(0, 3);
                CarStack.Push(CarRandomList2[rvalue]);
                CarRandomList2.RemoveAt(rvalue);

 
            }

            CarCreateRoad(CarStack.Pop());
            timer -= carCreatTime;
        }
        /*
        // 차량 생성 - 완전 랜덤
        if (timer >= carCreatTime)
        {

            //r = Random.Range((int)CarRoad.CAR_HOR_UP_1, (int)CarRoad.CAR_VER_RIGHT_2+1);

            CarRandomList1.Add(lastCar);
            r = Random.Range(0, CarRandomList1.Count);
            lastCar = CarRandomList1[r];
            CarCreateRoad(lastCar);
            CarRandomList1.RemoveAt(r);

            timer -= carCreatTime;
        }*/

    }

    public void setCarSpeed(string n)
    {


        switch (n)
        {
            case "러시아워-출근": // 5.8 기준
                minSpeed = 4.6f;
                maxSpeed = 5.4f;
                nowTimeInfo = "rush";
                break;
            case "평상시":
                minSpeed = 4.8f;
                maxSpeed = 5.2f;
                nowTimeInfo = "normal";
                break;
            case "러시아워-퇴근":  // 5.5
                minSpeed = 4.2f;
                maxSpeed = 4.8f;
                nowTimeInfo = "rush";
                break;
            case "야간":
                minSpeed = 4.2f;
                maxSpeed = 5.4f;
                nowTimeInfo = "night";

                break;
            default:
                minSpeed = 4f;
                maxSpeed = 6f;
                nowTimeInfo = "normal";

                break;
        }
    }

    private void CarCreateRoad(CarRoad cr)
    {
        CarDirction = AllObjectS.CAR_DIRECTION_STRAIGHT;

        // 차량 속도 지정
        setSpeed = Random.Range(minSpeed, maxSpeed);

        switch (cr)
        {
            case CarRoad.CAR_HOR_UP_1:
                if(car_hor_up_list[0] < car_hor_Limit)
                {
                    
                    CarDirction = AllObjectS.getCarDirectionLeft();

                    carob.setCarObject(AllObjectS.CAR_HOR_UP, carCreate(("car_hor_up_Black"), 13, 0.5f), setSpeed, CarDirction);

                    car_hor_up_list[0]++;
                }
                
                break;
            case CarRoad.CAR_HOR_UP_2:

                if (car_hor_up_list[1] < car_hor_Limit)
                {

                    CarDirction = AllObjectS.getCarDirectionRight();

                    carob.setCarObject(AllObjectS.CAR_HOR_UP, carCreate(("car_hor_up_Black"), 13, 1.0f), setSpeed, CarDirction);

                    
                    car_hor_up_list[1]++;
                }

                break;
            case CarRoad.CAR_HOR_DOWN_1:
                if (car_hor_down_list[0] < car_hor_Limit)
                {

                    CarDirction = AllObjectS.getCarDirectionLeft();


                    carob.setCarObject(AllObjectS.CAR_HOR_DOWN, carCreate(("car_hor_down_Black"), -13, -0.6f), setSpeed, CarDirction);

                    
                    car_hor_down_list[0]++;
                }

                break;
            case CarRoad.CAR_HOR_DOWN_2:
                if( car_hor_down_list[1] < car_hor_Limit)
                {
                    CarDirction = AllObjectS.getCarDirectionRight();

                    carob.setCarObject(AllObjectS.CAR_HOR_DOWN, carCreate(("car_hor_down_Black"), -13, -1.1f), setSpeed, CarDirction);

                    car_hor_down_list[1]++;
                }

                break;
            case CarRoad.CAR_VER_LEFT_1:
                if (car_ver_left_list[0] < car_ver_Limit)
                {
                    CarDirction = AllObjectS.getCarDirectionLeft();

                    carob.setCarObject(AllObjectS.CAR_VER_LEFT, carCreate(("car_ver_left_Black"), -0.8f, 10), setSpeed, CarDirction);

                    car_ver_left_list[0]++;
                }

                break;
            case CarRoad.CAR_VER_LEFT_2:
                if (car_ver_left_list[1] < car_ver_Limit)
                {

                    CarDirction = AllObjectS.getCarDirectionRight();

                    carob.setCarObject(AllObjectS.CAR_VER_LEFT, carCreate(("car_ver_left_Black"), -1.35f, 10), setSpeed, CarDirction);

                    car_ver_left_list[1]++;
                }

                break;
            case CarRoad.CAR_VER_RIGHT_1:
                if (car_ver_right_list[0] < car_ver_Limit)
                {
                    CarDirction = AllObjectS.getCarDirectionLeft();

                    carob.setCarObject(AllObjectS.CAR_VER_RIGHT, carCreate(("car_ver_right_Black"), 0.25f, -10), setSpeed, CarDirction);

                    car_ver_right_list[0]++;

                }

                break;
            case CarRoad.CAR_VER_RIGHT_2:
                if (car_ver_right_list[1] < car_ver_Limit)
                {

                    CarDirction = AllObjectS.getCarDirectionRight();

                    carob.setCarObject(AllObjectS.CAR_VER_RIGHT, carCreate(("car_ver_right_Black"), 0.75f, -10), setSpeed, CarDirction);
                    
                    car_ver_right_list[1]++;
                }
  
                break;

            default:
                break;
        }

    }

    public GameObject carCreate(string tag, float x, float y)
    {
        
        return Instantiate(GameObject.Find(tag), new Vector3(x, y), Quaternion.identity) as GameObject;
    }

    public static void changeTrafficLight()
    {

        trafficNum++;
        if (trafficNum > 14)
            trafficNum = 0;


        // 신호 테스트 car_hor_down
        // 0. 정지(기본) 1. 직진 2. 좌회전 3. 직진 및 좌회전

        /*
           public static int TRAFFIC_LIGHT_RED = 10;
           public static int TRAFFIC_LIGHT_GREEN = 11;
           public static int TRAFFIC_LIGHT_RIGHT = 12;             // 우회전 신호 ->
           public static int TRAFFIC_LIGHT_LEFT = 13;
           public static int TRAFFIC_LIGHT_LEFT_GREEN = 14;
        */

        switch (trafficNum)
        {

            case 0:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_GREEN;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_GREEN;


                    break;
                }
            case 1:
                {
                   
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_GREEN;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_GREEN;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;

                }

            case 2:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RIGHT;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }
            case 3:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }
            case 4:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }

            case 5:
                {
                   
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RIGHT;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }
            case 6:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }
            case 7:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }

            case 8:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RIGHT;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }
            case 9:
                {
                   
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }
            case 10:
                {
                   
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;



                    break;
                }

            case 11:
                {
                   
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RIGHT;



                    break;
                }
            case 12:
                {
                   
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT;



                    break;
                }
            case 13:
                {
                    
                    hor_Up_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    hor_Down_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;

                    ver_Right_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_RED;
                    ver_Left_Traffic_Light = AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN;



                    break;
                }



            default:
                break;
        }


    }

}
