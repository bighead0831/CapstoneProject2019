using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public  class carFactory : MonoBehaviour{
    GameObject car_object;
    float timer = 0;
    float y;
    float x;
    float angle;

    public float speed = 8f;
    public float init_Speed;

    car_Object carob;
    public bool isStop = false;

    bool isRotate = false;
    int n = -1;
    // Use this for initialization


    int CarDirection;

    bool ishitBlock = false;

    GameObject colGo = null;

    int myRoad = 0;

    bool isResetNum = false;

    void Start()
    {
        getMyRoad();
        
        carob = car_Object.getObject();
        car_object = carob.getCarObject(myRoad);
        carob.setCarStop(myRoad, car_object, isStop);

        init_Speed = getInitSpeed();
        speed = init_Speed;

       

        angle = 0;
        CarDirection = carob.getCarDirection(myRoad, car_object);

    }

    public float getInitSpeed()
    {
        return sign(carob.getCarSpeed(myRoad, car_object));
    }

    public float sign(float number)
    {
       
        if (myRoad == AllObjectS.CAR_HOR_UP)
        {
            number = -number;
        }

        else if (myRoad == AllObjectS.CAR_VER_LEFT)
        {
            number = -number;

        }

        return number;
    }


    private void OnTriggerEnter2D(Collider2D collision)
    {


        if (collision.gameObject.tag == "back_col")
        {
            ishitBlock = true;

            if (getMyTraffic() == AllObjectS.TRAFFIC_LIGHT_RED)
            {
                isStop = true;
                isRotate = false;
                speed = 0;

            }

            // 좌회전 - 1차선 좌회전, 2차선 정지
            else if (getMyTraffic() == AllObjectS.TRAFFIC_LIGHT_LEFT)
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
            else if (getMyTraffic() == AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN)
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
            else if (getMyTraffic() == AllObjectS.TRAFFIC_LIGHT_RIGHT)
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
            carob.setTrafficBlock(myRoad, car_object, isStop);

        }

        if (collision.gameObject.tag == this.gameObject.tag)
        {

            int col_car_index = carob.getCarIndex(myRoad, collision.gameObject);
            int this_car_index = carob.getCarIndex(myRoad, car_object);

            if (col_car_index < this_car_index)
            {
                colGo = collision.gameObject;

                float col_car_speed = carob.getCarSpeed(myRoad, collision.gameObject);

                if (Mathf.Abs(col_car_speed) < Mathf.Abs(init_Speed))
                {
                    init_Speed = sign(col_car_speed - 0.001f);
                    carob.setCarSpeed(myRoad, car_object, Mathf.Abs(init_Speed));
                }
                if (collision is PolygonCollider2D)
                {
                    if (carob.getTrafficBlock(myRoad, collision.gameObject))
                    {
                        carob.setTrafficBlock(myRoad, car_object, true);
                        isStop = true;

                        speed = 0;
                    }

                }
                else
                {


                    if (carob.getTrafficBlock(myRoad, collision.gameObject))
                    {
                        carob.setTrafficBlock(myRoad, car_object, true);
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

        carob.setCarStop(myRoad, car_object, isStop);
    }


    // Update is called once per frame
    void Update()
    {
        if (isStop)
        {
            timer += Time.deltaTime;
            carob.setCarTime(myRoad, car_object, timer);
        }
        y = car_object.transform.position.y;
        x = car_object.transform.position.x;
        if (ishitBlock)
        {

            if (getMyTraffic() != AllObjectS.TRAFFIC_LIGHT_RED)
            {
                // speed = -car_create_th.init_speed;
                if ((n == 1 && getMyTraffic() == AllObjectS.TRAFFIC_LIGHT_LEFT))
                {
                    ;
                }
                else if (getMyTraffic() == AllObjectS.TRAFFIC_LIGHT_RIGHT)
                {

                    if (n == 1 && CarDirection == AllObjectS.CAR_DIRECTION_RIGHT)
                    {
                        isStop = false;
                        isRotate = true;
                        speed = init_Speed;
                        carob.setCarStop(myRoad, car_object, isStop);
                        ishitBlock = false;

                    }

                }
                else
                {
                    speed = init_Speed;
                    isStop = false;
                    ishitBlock = false;
                    carob.setCarStop(myRoad, car_object, isStop);
                }
            }

        }
        else
        {
            if (colGo != null && !carob.getCarStop(myRoad, colGo))
            {

                speed = init_Speed;
                isStop = false;
                carob.setCarStop(myRoad, car_object, isStop);

            }
        }


        if (isRotate)
        {

            carRotate();

        }
        goCar();

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


        carDestroy();


    }

    public void goCar()
    {
        if (myRoad == AllObjectS.CAR_HOR_UP)
        {
            car_object.transform.Translate(speed * Time.deltaTime, 0, 0);

        }
        else if (myRoad == AllObjectS.CAR_HOR_DOWN)
        {
            car_object.transform.Translate(speed * Time.deltaTime, 0, 0);

        }
        else if (myRoad == AllObjectS.CAR_VER_LEFT)
        {
            car_object.transform.Translate(0, speed * Time.deltaTime, 0);

        }
        else if (myRoad == AllObjectS.CAR_VER_RIGHT)
        {
            car_object.transform.Translate(0, speed * Time.deltaTime, 0);

        }
    }

    public void getMyRoad()
    {
        int r = Random.Range(0, 10);

        if (this.tag == "car_hor_up")
        {
            myRoad = AllObjectS.CAR_HOR_UP;
            GetComponent<SpriteRenderer>().sprite = (Sprite)Resources.Load("hor_up/" + r.ToString(), typeof(Sprite));
            if (this.gameObject.transform.position.y < 0.75f)
                n = 0;
            else
                n = 1;
        }
        else if (this.tag == "car_hor_down")
        {
            myRoad = AllObjectS.CAR_HOR_DOWN;
            GetComponent<SpriteRenderer>().sprite = (Sprite)Resources.Load("hor_down/" + r.ToString(), typeof(Sprite));
            if (this.gameObject.transform.position.y > -0.85)
                n = 0;
            else
                n = 1;
        }
        else if (this.tag == "car_ver_left")
        {
            myRoad = AllObjectS.CAR_VER_LEFT;
            GetComponent<SpriteRenderer>().sprite = (Sprite)Resources.Load("ver_left/" + r.ToString(), typeof(Sprite));
            if (this.gameObject.transform.position.x > -1.15)
                n = 0;
            else
                n = 1;
        }
        else if (this.tag == "car_ver_right")
        {
            myRoad = AllObjectS.CAR_VER_RIGHT;
            GetComponent<SpriteRenderer>().sprite = (Sprite)Resources.Load("ver_right/" + r.ToString(), typeof(Sprite));
            if (this.gameObject.transform.position.x < 0.5)
                n = 0;
            else
                n = 1;
        }


    }

    public int getMyTraffic()
    {
        int result = 0;
        if (myRoad == AllObjectS.CAR_HOR_UP)
        {
            result = car_create_th.hor_Up_Traffic_Light;
        }
        else if (myRoad == AllObjectS.CAR_HOR_DOWN)
        {
            result = car_create_th.hor_Down_Traffic_Light;
        }
        else if (myRoad == AllObjectS.CAR_VER_LEFT)
        {
            result = car_create_th.ver_Left_Traffic_Light;
        }
        else if (myRoad == AllObjectS.CAR_VER_RIGHT)
        {
            result = car_create_th.ver_Right_Traffic_Light;
        }
        return result;
    }
    public void carRotate()
    {
        if (myRoad == AllObjectS.CAR_HOR_UP)
        {
            // 아래
            //1차선 - 좌회전만
            if (n == 0)
            {
                if (car_object.transform.position.x <= -0.8)
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
                if (car_object.transform.position.x <= 0.75)
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
        else if (myRoad == AllObjectS.CAR_HOR_DOWN)
        {
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
        else if (myRoad == AllObjectS.CAR_VER_LEFT)
        {
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
        }
        else if (myRoad == AllObjectS.CAR_VER_RIGHT)
        {
            // 아래
            // 1차선 - 좌회전
            if (n == 0)
            {

                if (car_object.transform.position.y >= 0.5)
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, 90);

                    isRotate = false;

                }
                else
                {
                    car_object.transform.eulerAngles = new Vector3(0, 0, angle);

                    //angle += (1.95f * Mathf.Abs(speed)) / 6.0f;
                    angle += (19.9f * Mathf.Abs(speed * Time.deltaTime));
                }

            }


            // 2차선 - 우회전만
            else if (n == 1)
            {

                if (car_object.transform.position.y >= -1.1f)
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
        }
    }
    public void carDestroy()
    {
        if(isResetNum)
        {
            if (myRoad == AllObjectS.CAR_HOR_UP)
            {

                if (x < -12f || y < -6f || y > 6f)
                {

                    //car_create_th.car_hor_up_list[n]--;
                    carob.RemoveCar(AllObjectS.CAR_HOR_UP, car_object);
                    Destroy(this.gameObject);

                }
            }
            else if (myRoad == AllObjectS.CAR_HOR_DOWN)
            {
                if (x > 12f || y < -6f || y > 6f)
                {

                    //car_create_th.car_hor_down_list[n]--;
                    carob.RemoveCar(AllObjectS.CAR_HOR_DOWN, car_object);
                    Destroy(this.gameObject);
                }

            }
            else if (myRoad == AllObjectS.CAR_VER_LEFT)
            {
                if (y < -6f || x > 12f || x < -12f)
                {

                    //car_create_th.car_ver_left_list[n]--;
                    carob.RemoveCar(AllObjectS.CAR_VER_LEFT, car_object);
                    Destroy(this.gameObject);

                }
            }
            else if (myRoad == AllObjectS.CAR_VER_RIGHT)
            {
                if (y > 6f || x > 12f || x < -12f)
                {

                    //car_create_th.car_ver_right_list[n]--;

                    carob.RemoveCar(myRoad, car_object);
                    Destroy(this.gameObject);

                }
            }
        }
        else
        {
            if ((x < 1.6 && x > -2.1 && y < 1.8 && y > -1.9))
            {
                isResetNum = true;

                if (myRoad == AllObjectS.CAR_HOR_UP)
                {
                    car_create_th.car_hor_up_list[n]--;
                    
                }
                else if (myRoad == AllObjectS.CAR_HOR_DOWN)
                {
                    car_create_th.car_hor_down_list[n]--;

                }
                else if (myRoad == AllObjectS.CAR_VER_LEFT)
                {
                    car_create_th.car_ver_left_list[n]--;

                }
                else if (myRoad == AllObjectS.CAR_VER_RIGHT)
                {
                    car_create_th.car_ver_right_list[n]--;

                }

            }
        }

     
    }
}
