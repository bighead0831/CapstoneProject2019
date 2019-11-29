using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;
using UnityEngine;
using UnityEngine.Video;

public class video : MonoBehaviour {

    public static VideoPlayer vp;
    GameObject go;

    public static int Days;
    public static bool isDay;


    void Awake()
    {
        
    }

    // Use this for initialization
    void Start () {
        
        Days = 0;
        go = GameObject.Find("Video Player");



        vp = go.GetComponent<VideoPlayer>();
        vp.loopPointReached += loopVideo;

        vp.sendFrameReadyEvents = true;


        var test = GameObject.Find("Back").GetComponent<RectTransform>().rect.width;

    }
    void loopVideo(VideoPlayer vp)
    {
        isDay = true;
    }

   
    


	// Update is called once per frame
	void Update () {

       


        if (vp.time > 230f && isDay)
        {
            Days++;
            isDay = false;

        }
        // 러시아워1 - 출근
        if (Input.GetKeyDown(KeyCode.Alpha1))
        {
            //vp.time = 115;
            car_create_th.carCreatTime = car_create_th.carCreateTimeRush;
            vp.time = 240;
        }
        // 평상시
        else if (Input.GetKeyDown(KeyCode.Alpha2))
        {
            //vp.time = 135;
            car_create_th.carCreatTime = car_create_th.carCreateTimeNormal;
            vp.time = 280;
        }
        // 러시아워2 - 퇴근
        else if (Input.GetKeyDown(KeyCode.Alpha3))
        {
            //vp.time = 40;
            car_create_th.carCreatTime = car_create_th.carCreateTimeRush;
            vp.time = 90;

        }
        // 야간
        else if (Input.GetKeyDown(KeyCode.Alpha4))
        {
            //vp.time = 70;
            car_create_th.carCreatTime = car_create_th.carCreateTimeNight;
            vp.time = 160;

        }

    }
}
