using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class car_ver_left_start : MonoBehaviour {
    GameObject car_object;
    float timer = 0;
    float y;
    float x;
    float angle;

    public float speed;
    public float init_Speed;

    car_Object carob;
    public bool isStop = false;


    bool isRotate = false;
    int n = -1;

    int CarDirection;

    bool ishitBlock = false;

    GameObject colGo = null;


    // Use this for initialization
    void Start()
    {
        int r = Random.Range(0, 10);
        GetComponent<SpriteRenderer>().sprite = (Sprite)Resources.Load("ver_left/" + r.ToString(), typeof(Sprite));


        carob = car_Object.getObject();
        car_object = carob.getCarObject(AllObjectS.CAR_VER_LEFT);
        carob.setCarStop(AllObjectS.CAR_VER_LEFT, car_object, isStop);
        init_Speed = -carob.getCarSpeed(AllObjectS.CAR_VER_LEFT, car_object);

        speed = -carob.getCarSpeed(AllObjectS.CAR_VER_LEFT, car_object);


        if (car_object.transform.position.x > -1.15)
            n = 0;
        else
            n = 1;

        angle = 0;

        CarDirection = carob.getCarDirection(AllObjectS.CAR_VER_LEFT, car_object);

    }
    private void OnTriggerEnter2D(Collider2D collision)
    {

        if (collision.gameObject.tag == "back_col")
        {
            ishitBlock = true;
            
            // 정지
            if (car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RED)
            {
                isStop = true;
                isRotate = false;
                speed = 0;
            }

            // 좌회전 - 1차선 좌회전, 2차선 정지
            else if (car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_LEFT)
            {
                if (n == 0)
                {
                    isStop = false;
                    isRotate = true;
                    speed = init_Speed;


                }
                else if (n == 1)
                {
                    isStop = true;
                    isRotate = false;
                    speed = 0;
                }


            }
            // 직진 및 좌회전 - 1차선 직진 or 좌회전, 2차선 직진
            else if (car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN)
            {

                if (n == 0)
                {

                    if (CarDirection == AllObjectS.CAR_DIRECTION_STRAIGHT)
                    {

                        isStop = false;
                        isRotate = false;

                    }
                    else if (CarDirection == AllObjectS.CAR_DIRECTION_LEFT)
                    {


                        isStop = false;
                        isRotate = true;
                    }

                }
                else if (n == 1)
                {
                    isStop = false;
                    isRotate = false;

                }
                speed = init_Speed;


            }

            // 우회전
            else if (car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RIGHT)
            {
                if (n == 0)
                {
                    isStop = true;
                    isRotate = false;
                    speed = 0;

                }
                else if (n == 1 && CarDirection == AllObjectS.CAR_DIRECTION_RIGHT)
                {
                    isStop = false;
                    isRotate = true;
                    speed = init_Speed;


                }
                else
                {
                    isStop = true;
                    isRotate = false;
                    speed = 0;

                }
            }

            // 직진
            else
            {
                isStop = false;
                isRotate = false;
                speed = init_Speed;
            }


            // 신호에 의해서 차량이 멈추는 경우
            carob.setTrafficBlock(AllObjectS.CAR_VER_LEFT, car_object, isStop);

        }

        if (collision.gameObject.tag == "car_ver_left")
        {
            int col_car_index = carob.getCarIndex(AllObjectS.CAR_VER_LEFT, collision.gameObject);
            int this_car_index = carob.getCarIndex(AllObjectS.CAR_VER_LEFT, car_object);

            if (col_car_index < this_car_index)
            {
                colGo = collision.gameObject;

                float col_car_speed = carob.getCarSpeed(AllObjectS.CAR_VER_LEFT, collision.gameObject);

                if (col_car_speed < -init_Speed)
                {
                    init_Speed = -(col_car_speed - 0.05f);
                    carob.setCarSpeed(AllObjectS.CAR_VER_LEFT, car_object, -init_Speed);
                }

                if (collision is PolygonCollider2D)
                {

                    if (carob.getTrafficBlock(AllObjectS.CAR_VER_LEFT, collision.gameObject))
                    {
                        carob.setTrafficBlock(AllObjectS.CAR_VER_LEFT, car_object, true);
                        isStop = true;

                        speed = 0;
                    }
                }
                else
                {

                    if (carob.getTrafficBlock(AllObjectS.CAR_VER_LEFT, collision.gameObject))
                    {
                        carob.setTrafficBlock(AllObjectS.CAR_VER_LEFT, car_object, true);
                        isStop = true;

                        speed = 0;
                    }
                    else
                    {

                        isStop = false;
                        speed = init_Speed;
                    }

                   

                }

                    
            }


        }

        carob.setCarStop(AllObjectS.CAR_VER_LEFT, car_object, isStop);
    }
    // Update is called once per frame
    void Update()
    {
        if (isStop)
        {
            timer += Time.deltaTime;
            carob.setCarTime(AllObjectS.CAR_VER_LEFT, car_object, timer);
        }

        // timer값 조절로 가는 길이 조정
        y = car_object.transform.position.y;
        x= car_object.transform.position.x;
        if (ishitBlock)
        {
            if (car_create_th.ver_Left_Traffic_Light != AllObjectS.TRAFFIC_LIGHT_RED)
            {
                // speed = -car_create_th.init_speed;
                if ((n == 1 && car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_LEFT))
                {
                    ;
                }
                else if (car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RIGHT)
                {

                    if (n == 1 && CarDirection == AllObjectS.CAR_DIRECTION_RIGHT)
                    {
                        isStop = false;
                        isRotate = true;
                        speed = init_Speed;
                        carob.setCarStop(AllObjectS.CAR_VER_LEFT, car_object, isStop);
                        ishitBlock = false;

                    }

                }
                else
                {
                    speed = init_Speed;
                    isStop = false;
                    ishitBlock = false;
                    carob.setCarStop(AllObjectS.CAR_VER_LEFT, car_object, isStop);
                }
            }
        }
        else
        {
            if (colGo != null && !carob.getCarStop(AllObjectS.CAR_VER_LEFT, colGo))
            {

                speed = init_Speed;
                isStop = false;
                carob.setCarStop(AllObjectS.CAR_VER_LEFT, car_object, isStop);

            }
        }
        /*
        if (car_create_th.ver_Left_Traffic_Light != AllObjectS.TRAFFIC_LIGHT_RED)
        {

            if (n == 1 && car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_LEFT)
            {
                ;
            }
            else if ((n == 0 && car_create_th.ver_Left_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RIGHT))
            {
                ;
            }
            else if (n == 1 && CarDirection != AllObjectS.CAR_DIRECTION_RIGHT)
            {
                ;
            }
            else
            {
                speed = init_Speed;

                isStop = false;
                carob.setCarStop(AllObjectS.CAR_VER_LEFT, car_object, isStop);
            }
 
        }*/

        if (isRotate)
        {
            // 아래
            //
            if (n == 0)
            {
                if (car_object.transform.position.y <= -0.6)
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, 90);

                    isRotate = false;
                    
                }
                else
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, angle);

                    //angle += (1.95f * Mathf.Abs(speed)) / 6.0f;
                    angle += (19.8f * Mathf.Abs(speed * Time.deltaTime));
                }


            }


            // 2차선 - 우회전만
            else if (n == 1)
            {
                if (car_object.transform.position.y <= 1.0)
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, -90);

                    isRotate = false;

                }
                else
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, angle);

                    //angle += (1.95f * Mathf.Abs(speed)) / 6.0f;
                    angle -= (43f * Mathf.Abs(speed * Time.deltaTime));
                }
            }
            /*
            else if (n == 1)
            {

                car_object.transform.eulerAngles = new Vector3(0, 0, angle);
                angle += (2f * Mathf.Abs(speed)) / 6;

            }*/


        }
        car_object.transform.Translate(0, speed * Time.deltaTime, 0);

        if(angle >= 90)
        {
            car_object.transform.eulerAngles = new Vector3(0, 0, 90);

            isRotate = false;

        }
        else if (angle <= -90)
        {
            car_object.transform.eulerAngles = new Vector3(0, 0, -90);

            isRotate = false;

        }


        if (y < -6f || x > 12f || x < -12f)
        {

            car_create_th.car_ver_left_list[n]--;
            carob.RemoveCar(AllObjectS.CAR_VER_LEFT, car_object);
            Destroy(this.gameObject);

        }



    }
}
