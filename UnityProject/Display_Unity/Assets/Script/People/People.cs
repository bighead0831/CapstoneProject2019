using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class People : MonoBehaviour {

    bool isStart = false;
    GameObject peopleGO;
    float xspeed = 1f;
    float yspeed = 1f;
    float initx = 1f;
    float inity = 1f;
    int nowNum;
    int createNum;

    string CollisonTag;

    float FixedSpeed =1.5f;

    bool isStop = false;

	// Use this for initialization
	void Start () {
        //Debug.Log("!!");
	}

    void Init(List<int> now)
    {
        createNum = now[0];
        nowNum = now[1];
        isStart = true;
        peopleGO = this.gameObject;
        CollisonTag = getTag();
        switch (createNum)
        {
            case 0:
            case 1:
                initx = 0f; inity = -FixedSpeed; break;
            case 2:
            case 3:
                initx = -FixedSpeed; inity = 0;break;
            case 4:
            case 5:
                initx = -FixedSpeed; inity = 0; break;
            case 6:
            case 7:
                initx = 0; inity = FixedSpeed; break;
            case 8:
            case 9:
                initx = 0; inity = FixedSpeed; break;
            case 10:
            case 11:
                initx = FixedSpeed; inity = 0; break;
            case 12:
            case 13:
                initx = FixedSpeed; inity = 0; break;
            case 14:
            case 15:
                initx = 0; inity = -FixedSpeed; break;
            default: break;
        }

        xspeed = initx;
        yspeed = inity;

    }

    public bool Traffic()
    {
        bool t = false;
        switch (createNum)
        {
            case 0:
            case 1:
                t = PeopleCreate.peopleTrafficRight; break;
            case 2:
            case 3:
                t = PeopleCreate.peopleTrafficUP; break;

            case 4:
            case 5:
                t = PeopleCreate.peopleTrafficDown; break;

            case 6:
            case 7:
                t = PeopleCreate.peopleTrafficRight; break;

            case 8:
            case 9:
                t = PeopleCreate.peopleTrafficLeft; break;

            case 10:
            case 11:
                t = PeopleCreate.peopleTrafficDown; break;

            case 12:
            case 13:
                t = PeopleCreate.peopleTrafficUP; break;
                
            case 14:
            case 15:
                t = PeopleCreate.peopleTrafficLeft; break;

            default: break;
        }

        return t;
    }

    public string getTag()
    {
        string findString = "";
        switch (createNum)
        {
            case 0:
            case 1:
                findString = "PeopleVerU"; break;
            case 2:
            case 3:
                findString = "PeopleHorR"; ; break;
            case 4:
            case 5:
                findString = "PeopleHorR"; ; break;
            case 6:
            case 7:
                findString = "PeopleVerD"; break;
            case 8:
            case 9:
                findString = "PeopleVerD"; break;
            case 10:
            case 11:
                findString = "PeopleHorL"; break;
            case 12:
            case 13:
                findString = "PeopleHorL"; break;
            case 14:
            case 15:
                findString = "PeopleVerU"; break;
            default: break;
        }

        return findString;
    }

    public void Stop(bool isStop)
    {
        int number = 0;
        if (isStop)
            number = 1;
        switch (createNum)
        {
            case 0:
            case 1:
                PeopleCreate.peopleTrafficWaitNumRight += number; break;
            case 2:
            case 3:
                PeopleCreate.peopleTrafficWaitNumUp += number;break;
            case 4:
            case 5:
                PeopleCreate.peopleTrafficWaitNumDown += number; break;
            case 6:
            case 7:
                PeopleCreate.peopleTrafficWaitNumRight += number; break;
            case 8:
            case 9:
                PeopleCreate.peopleTrafficWaitNumLeft += number; break;
            case 10:
            case 11:
                PeopleCreate.peopleTrafficWaitNumDown += number; break;
            case 12:
            case 13:
                PeopleCreate.peopleTrafficWaitNumUp += number; break;
            case 14:
            case 15:
                PeopleCreate.peopleTrafficWaitNumLeft += number; break;
            default: break;
        }
    }

    public void waitTime(double time)
    {
        switch (createNum)
        {
            case 0:
            case 1:
                PeopleCreate.peopleTrafficTimeRight += time; break;
            case 2:
            case 3:
                PeopleCreate.peopleTrafficTimeUp += time; break;
            case 4:
            case 5:
                PeopleCreate.peopleTrafficTimeDown += time; break;
            case 6:
            case 7:
                PeopleCreate.peopleTrafficTimeRight += time; break;
            case 8:
            case 9:
                PeopleCreate.peopleTrafficTimeLeft += time; break;
            case 10:
            case 11:
                PeopleCreate.peopleTrafficTimeDown += time; break;
            case 12:
            case 13:
                PeopleCreate.peopleTrafficTimeUp += time; break;
            case 14:
            case 15:
                PeopleCreate.peopleTrafficTimeLeft += time; break;
            default: break;
        }
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        // 이미 멈춰있는 경우 충돌이 발생해도 아무런 일이 발생하지 않음.
        if (isStop)
            return;
        // 충돌 && 빨간불일 경우
        if (collision.gameObject.tag == "PeopleBack"&& !Traffic())
        {
            isStop = true;
            xspeed = 0f;
            yspeed = 0f;
            Stop(isStop);
        }

        if(collision.gameObject.tag == CollisonTag)
        {
            isStop = true;
            xspeed = 0f;
            yspeed = 0f;
            Stop(isStop);


        }

    }

    // Update is called once per frame

    void Update ()
    {
        if (isStart)
        {
            if(isStop)
            {
                if(Traffic())
                {
                    xspeed = initx;
                    yspeed = inity;
                    isStop = false;
                    Stop(isStop);

                }
                else
                {
                    waitTime(Time.deltaTime);
                }

            }
            peopleGO.transform.Translate(xspeed * Time.deltaTime, yspeed * Time.deltaTime, 0);
        }

        if(peopleGO.transform.position.x>13f|| peopleGO.transform.position.x < -13f|| peopleGO.transform.position.y > 8f||peopleGO.transform.position.y < -8f)
            Destroy(this.gameObject);
    }
}
