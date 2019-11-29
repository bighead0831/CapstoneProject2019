using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class car_hor_down_start : MonoBehaviour {
    GameObject car_object;
    float timer = 0;

    float x;
    float y;
    float angle;


    public float speed = 8f;

    public float init_Speed;

    public bool isStop = false;

    car_Object carob;

    bool isRotate = false;
    public float yspeed = 0;
    int n = -1;

    bool ishitBlock = false;

    GameObject colGo = null;

    int CarDirection;
    // Use this for initialization
    void Start () {


        int r = Random.Range(0, 10);
        GetComponent<SpriteRenderer>().sprite = (Sprite)Resources.Load("hor_down/" + r.ToString(), typeof(Sprite));


        carob = car_Object.getObject();
        car_object = carob.getCarObject(AllObjectS.CAR_HOR_DOWN);
        carob.setCarStop(AllObjectS.CAR_HOR_DOWN, car_object, isStop);
        init_Speed = carob.getCarSpeed(AllObjectS.CAR_HOR_DOWN, car_object);

        //car_object = car_create_th.car_object;
        speed = carob.getCarSpeed(AllObjectS.CAR_HOR_DOWN, car_object);

        y = car_object.transform.position.y;

        CarDirection=carob.getCarDirection(AllObjectS.CAR_HOR_DOWN, car_object);
        if (y > -0.85)
            n = 0;
        else
            n = 1;

        angle = 0;
    }
    private void OnTriggerEnter2D(Collider2D collision)
    {


        if (collision.gameObject.tag == "back_col")
        {
            ishitBlock = true;
            
            // 정지
            if (car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RED)
            {
                isStop = true;
                isRotate = false;
                speed = 0;
            }

            // 좌회전 - 1차선 좌회전, 2차선 정지
            else if (car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_LEFT)
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
            else if (car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN)
            {
                if (n == 0)
                {
                    if(CarDirection==AllObjectS.CAR_DIRECTION_STRAIGHT)
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

            else if (car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RIGHT)
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
            carob.setTrafficBlock(AllObjectS.CAR_HOR_DOWN, car_object, isStop);

        }



        if (collision.gameObject.tag == "car_hor_down")
        {
            int col_car_index = carob.getCarIndex(AllObjectS.CAR_HOR_DOWN, collision.gameObject);
            int this_car_index = carob.getCarIndex(AllObjectS.CAR_HOR_DOWN, car_object);
            // 내가 뒤에 있는 차량인 경우
            if (col_car_index < this_car_index)
            {
                colGo = collision.gameObject;

                float col_car_speed = carob.getCarSpeed(AllObjectS.CAR_HOR_DOWN, collision.gameObject);

                if (col_car_speed < init_Speed)
                {

                    //init_Speed = (col_car_speed - 0.1f);
                    init_Speed = (col_car_speed-0.05f);
                    carob.setCarSpeed(AllObjectS.CAR_HOR_DOWN, car_object, init_Speed);
                }

                if (collision is PolygonCollider2D)
                {
                  

                    if (carob.getTrafficBlock(AllObjectS.CAR_HOR_DOWN, collision.gameObject))
                    {
                        carob.setTrafficBlock(AllObjectS.CAR_HOR_DOWN, car_object, true);
                        isStop = true;

                        speed = 0;
                    }
                }
                else
                {
               

                    if (carob.getTrafficBlock(AllObjectS.CAR_HOR_DOWN, collision.gameObject))
                    {
                        carob.setTrafficBlock(AllObjectS.CAR_HOR_DOWN, car_object, true);
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

        carob.setCarStop(AllObjectS.CAR_HOR_DOWN, car_object, isStop);
    }
    // Update is called once per frame

   
	void Update() {
        if (isStop)
        {
            timer += Time.deltaTime;
            carob.setCarTime(AllObjectS.CAR_HOR_DOWN, car_object, timer);
        }


        x = car_object.transform.position.x;
        y = car_object.transform.position.y;


        if (ishitBlock)
        {
            if (car_create_th.hor_Down_Traffic_Light != AllObjectS.TRAFFIC_LIGHT_RED)
            {
                // speed = -car_create_th.init_speed;
                if ((n == 1 && car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_LEFT))
                {
                    ;
                }
                else if (car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RIGHT)
                {

                    if (n == 1 && CarDirection == AllObjectS.CAR_DIRECTION_RIGHT)
                    {
                        isStop = false;
                        isRotate = true;
                        speed = init_Speed;
                        carob.setCarStop(AllObjectS.CAR_HOR_DOWN, car_object, isStop);
                        ishitBlock = false;

                    }

                }
                else
                {
                    speed = init_Speed;
                    isStop = false;
                    ishitBlock = false;
                    carob.setCarStop(AllObjectS.CAR_HOR_DOWN, car_object, isStop);
                }
            }
        }
        else
        {
            if (colGo != null && !carob.getCarStop(AllObjectS.CAR_HOR_DOWN, colGo))
            {

                speed = init_Speed;
                isStop = false;
                carob.setCarStop(AllObjectS.CAR_HOR_DOWN, car_object, isStop);

            }
        }

        /*
        if (car_create_th.hor_Down_Traffic_Light != AllObjectS.TRAFFIC_LIGHT_RED)
        {
            if(n==1 && car_create_th.hor_Down_Traffic_Light==AllObjectS.TRAFFIC_LIGHT_LEFT)
            {
                ;
            }
            else if ((n == 0 && car_create_th.hor_Down_Traffic_Light == AllObjectS.TRAFFIC_LIGHT_RIGHT))
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
                carob.setCarStop(AllObjectS.CAR_HOR_DOWN, car_object, isStop);
            }

        }*/

        if (isRotate)
        {
            // 아래
            // 1차선 좌회전

            if (n == 0)
            {
                if (car_object.transform.position.x >= 0.25)
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, 90);

                    isRotate = false;
                   
                }
                else
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, angle);

                    //angle += (1.95f * Mathf.Abs(speed)) / 6.0f;
                    angle += (20.3f * Mathf.Abs(speed * Time.deltaTime));
                }

            }
            // 우회전
            else if (n == 1)
            {
                if (car_object.transform.position.x >= -1.35f)
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, -90);

                    isRotate = false;

                }
                else
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, angle);
                    angle -= (43f * Mathf.Abs(speed * Time.deltaTime));
                }
            }


        }

        car_object.transform.Translate(speed * Time.deltaTime, yspeed * Time.deltaTime, 0);

        if (angle >= 90)
        {
            car_object.transform.eulerAngles = new Vector3(0, 0, 90);

            isRotate = false;

        }
        else if (angle <= -90)
        {
            car_object.transform.eulerAngles = new Vector3(0, 0, -90);

            isRotate = false;

        }


        if (x > 12f || y < -6f || y > 6f)
        {

            car_create_th.car_hor_down_list[n]--;
            carob.RemoveCar(AllObjectS.CAR_HOR_DOWN, car_object);
            Destroy(this.gameObject);
        }


    }
}
