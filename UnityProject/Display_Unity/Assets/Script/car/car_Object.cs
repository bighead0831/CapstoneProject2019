using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using UnityEngine;



public class car_Object{


    private static car_Object thisObject;

    private List<List<GameObject>> CarObjects;

    private List<List<float>> car_speed;
    private List<List<bool>> is_Traffic_block;

    private List<List<float>> carTime;
    private List<List<bool>> carStop;

    private List<List<int>> carDirection;



    private car_Object()
    {


        CarObjects = new List<List<GameObject>>();

        car_speed = new List<List<float>>();
        is_Traffic_block = new List<List<bool>>();
        carTime = new List<List<float>>();
        carStop = new List<List<bool>>();
        carDirection = new List<List<int>>();

        for (int i = 0; i < 4; i++)
        {
            CarObjects.Add(new List<GameObject>());
            car_speed.Add(new List<float>());
            is_Traffic_block.Add(new List<bool>());
            carTime.Add(new List<float>());
            carStop.Add(new List<bool>());
            carDirection.Add(new List<int>());

        }

       
    }

    public static car_Object getObject()
    {
        if (thisObject == null)
            thisObject = new car_Object();

        return thisObject;
    }

    public float getCarSpeed(int n,GameObject go)
    {
        int index = CarObjects[n].IndexOf(go);
        return car_speed[n][index];
    }

    public void setCarSpeed(int n, GameObject go,float speed)
    {
        int index = CarObjects[n].IndexOf(go);
        car_speed[n][index]=speed;
    }

    public void setTrafficBlock(int n, GameObject go, bool tb)
    {
        int index = CarObjects[n].IndexOf(go);
        is_Traffic_block[n][index]=tb;
    }

    public bool getTrafficBlock(int n, GameObject go)
    {
        int index = CarObjects[n].IndexOf(go);

        return is_Traffic_block[n][index];
    }

    public void initTrafficBlock(int n)
    {
        for(int i=0;i<is_Traffic_block[n].Count;i++)
        {
            is_Traffic_block[n][i] = false;
        }
    }

    public int getCarIndex(int n,GameObject go)
    {
        int index= CarObjects[n].IndexOf(go);
        return index;
    }

    public double getTime(int n)
    {
        double result = 0;

        for (int num = 0; num < carStop[n].Count; num++)
        {
            if (carStop[n][num] == true)
                result += carTime[n][num];

        }
        return result;
     
    }

    
    public void showTime()
    {
        double result = 0 ;
        for (int i = 0; i < 4; i++)
        {
            result = getTime(i);
            Debug.Log(i + ". :" + result);
        }

    }

    public int TrafficBlockNum(int n)
    {
        var query = from q in carStop[n]
                    where (q == true)
                    select q;
                
        return query.Count();
    }

    public bool getCarStop(int n,GameObject go)
    {
        int index = CarObjects[n].IndexOf(go);
        return carStop[n][index];
    }

    public void setCarStop(int n,GameObject go,bool b)
    {
        carStop[n][getCarIndex(n, go)] = b;
     
    }


    public void setCarTime(int n, GameObject go, float time)
    {
        carTime[n][getCarIndex(n, go)] = time;
      
    }

    public void setCarObject(int n,GameObject go,float speed=8f,int direction=20)
    {
        car_speed[n].Add(speed);
        is_Traffic_block[n].Add(false);
        CarObjects[n].Add(go);
   
        carTime[n].Add(0);
        carStop[n].Add(false);

        carDirection[n].Add(direction);

      
    }


    public GameObject getCarObject(int n)
    {

        int count = CarObjects[n].Count;
        return CarObjects[n][count - 1];
  
    }

    public int getCarDirection(int n, GameObject go)
    {
        int index = CarObjects[n].IndexOf(go);
        return carDirection[n][index];
    }


    public void RemoveCar(int n,GameObject go)
    {
        int num;


        num = CarObjects[n].IndexOf(go);
        car_speed[n].RemoveAt(num);
        is_Traffic_block[n].RemoveAt(num);
        carStop[n].RemoveAt(num);
        carTime[n].RemoveAt(num);

        carDirection[n].RemoveAt(num);

        CarObjects[n].RemoveAt(num);


    }

}
