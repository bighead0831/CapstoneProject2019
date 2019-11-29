using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PeopleCreate : MonoBehaviour {
    float timer = 0;
    public static float PeopleCreatTime = 0.5f;

    List<int> peopleCreateList;
    

    int createNowNum = 0;
    int positon = 0;

    // true 면 갈 수 있는것 -> green신호
    public static bool peopleTrafficUP=false;
    public static bool peopleTrafficDown = false;
    public static bool peopleTrafficLeft = true;
    public static bool peopleTrafficRight = true;

    public static double peopleTrafficTimeUp = 0f;
    public static double peopleTrafficTimeDown = 0f;
    public static double peopleTrafficTimeLeft = 0f;
    public static double peopleTrafficTimeRight = 0f;

    public static int peopleTrafficWaitNumUp = 0;
    public static int peopleTrafficWaitNumDown = 0;
    public static int peopleTrafficWaitNumLeft = 0;
    public static int peopleTrafficWaitNumRight = 0;

    public int TrafficNum = 0;

    // Use this for initialization
    void Start () {
        peopleCreateList = new List<int>();
        initList();

    }

    public void initList()
    {
        peopleCreateList.Clear();
        for (int i = 0; i < 16; i++)
            peopleCreateList.Add(i);
    }



    // Update is called once per frame
    void Update()
    {
        timer += Time.deltaTime;
        if (timer >= PeopleCreatTime)
        {
            int r = Random.Range(0, peopleCreateList.Count);
            timer -= PeopleCreatTime;
            CreatePeople(peopleCreateList[r]);

            peopleCreateList.RemoveAt(r);

            if (peopleCreateList.Count < 1)
                initList();
                
        }

        if (Input.GetKeyDown(KeyCode.Space))
        {
            changeTrafficLight();
        }

    }

    public static void initTime()
    {

        if (peopleTrafficUP)
        {
            peopleTrafficTimeUp = 0;
            peopleTrafficWaitNumUp = 0;
        }
        if (peopleTrafficDown)
        {
            peopleTrafficTimeDown = 0;
            peopleTrafficWaitNumDown = 0;
        }
        if (peopleTrafficLeft)
        {
            peopleTrafficTimeLeft = 0;
            peopleTrafficWaitNumLeft = 0;
        }
        if (peopleTrafficRight)
        {
            peopleTrafficTimeRight = 0;
            peopleTrafficWaitNumRight = 0;
        }
    }
    
    void changeTrafficLight()
    {
        TrafficNum++;
        if (TrafficNum > 1)
            TrafficNum = 0;
        switch(TrafficNum)
        {
            case 0:
                peopleTrafficUP = false;
                peopleTrafficDown = false;
                peopleTrafficLeft = true;
                peopleTrafficRight = true;
                
                break;
            case 1:
                peopleTrafficUP = true;
                peopleTrafficDown = true;
                peopleTrafficLeft = false;
                peopleTrafficRight = false;
                break;
            default:
                
                break;
        }

        initTime();


    }

    public void CreatePeople(int number)
    {
        float x = 0;
        float y = 0;
        // 위,오른쪽부터 시계 방향으로 0->7 (1시 부터 시작)
        string findString = "";
        switch(number)
        {
            case 0:
                x = 1.5f;y = 6f;findString = "PeopleVerU"; break;
            case 1:
                x = 1.62f; y = 6f; findString = "PeopleVerU"; break;
            case 2:
                x = 11f;y = 1.75f; findString = "PeopleHorR"; ; break;
            case 3:
                x = 11f; y = 1.88f; findString = "PeopleHorR"; ; break;
            case 4:
                x = 11f;y = -1.83f; findString = "PeopleHorR";  break;
            case 5:
                x = 11f; y = -1.93f; findString = "PeopleHorR";  break;
            case 6:
                x = 1.5f;y = -6f; findString = "PeopleVerD"; break;
            case 7:
                x = 1.62f; y = -6f; findString = "PeopleVerD"; break;
            case 8:
                x = -2.11f;y = -6f; findString = "PeopleVerD"; break;
            case 9:
                x = -2.20f; y = -6f; findString = "PeopleVerD"; break;
            case 10:
                x = -11f; y = -1.83f; findString = "PeopleHorL"; break;
            case 11:
                x = -11f; y = -1.93f; findString = "PeopleHorL"; break;
            case 12:
                x = -11f; y = 1.75f; findString = "PeopleHorL"; break;
            case 13:
                x = -11f; y = 1.88f; findString = "PeopleHorL"; break;
            case 14:
                x = -2.11f; y = 6f; findString = "PeopleVerU"; break;
            case 15:
                x = -2.20f; y = 6f; findString = "PeopleVerU"; break;
            default:return; 
        }
        var instance = Instantiate(GameObject.Find(findString), new Vector3(x, y), Quaternion.identity) as GameObject;
        instance.SendMessage("Init", new List<int> { number, createNowNum++ });
        
    }
}
