using LitJson;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using UnityEngine;
using UnityEngine.Video;

public class sendImageInfo
{
    public int CAR;
    public int BACK;

    public sendImageInfo(int car,int back)
    {
        CAR = car;
        BACK = back;
    }
}

public class captureVideo : MonoBehaviour {

    public static float imageTime;

    public Camera camera;
    VideoPlayer vp;
    float timer = 0;

    MemoryStream ms;

    int resWidth;
    int resHeight;

    sendImageInfo si;

    byte[] CarByte;
    byte[] BackByte;

    public bool isComplete = false;
    public bool isNext = false;

    public int test = 0;

    // Use this for initialization
    void Start()
    {

        vp = GameObject.Find("Video Player").GetComponent<VideoPlayer>();

        Thread t1 = new Thread(new ThreadStart(Run));


        t1.Start();

        isNext = true;
        
        resWidth = Screen.width;
        resHeight = Screen.height;

    }

    public void Run()
    {
        while(true)
        {
            // error 발생 시키기
            /*
            if(test>3)
            {
                AllObjectS.client_image.Send(Encoding.ASCII.GetBytes("에러 발생"));
                test = 0;
            }*/
            if(isComplete)
            {

                try
                {
                    // 이미지 정보 전송
                    si = new sendImageInfo(CarByte.Length, BackByte.Length);

                    JsonData infoJson = JsonMapper.ToJson(si);

                    AllObjectS.client_image.Send(Encoding.UTF8.GetBytes(infoJson.ToString()));

                    Byte[] _data = new Byte[1024];

                    AllObjectS.client_image.Receive(_data);

                    string _buf = Encoding.UTF8.GetString(_data);

                    // Car이미지 전송

                    AllObjectS.client_image.Send(CarByte);

                    _data = new Byte[1024];

                    AllObjectS.client_image.Receive(_data);

                    _buf = Encoding.UTF8.GetString(_data);


                    // Back 이미지 전송

                    AllObjectS.client_image.Send(BackByte);

                    _data = new Byte[1024];

                    AllObjectS.client_image.Receive(_data);

                    _buf = Encoding.UTF8.GetString(_data);

                    isNext = true;
                    isComplete = false;
                }
                catch(Exception)
                {
                    /*
                    AllObjectS.ServerConnect_image = false;
                    isNext = false;

                    
                    while (!AllObjectS.client_image.Connected)
                    {
                        // 서버 다시 연결
                        AllObjectS.client_image = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                        //AllObjectS.client_image.Connect(AllObjectS.ipep_image);

                       
                        IAsyncResult result = AllObjectS.client_image.BeginConnect(AllObjectS.ipep_image, null, null);

                        bool success = result.AsyncWaitHandle.WaitOne(1000, true);
                        

                        Thread.Sleep(1000);
                    }
                    AllObjectS.ServerConnect_image = true;
                    isNext = true;
                    isComplete = false;

                    */
                }

            }
            else
            {
               // Thread.Sleep(1000);
            }
        }
    }

    // Update is called once per frame
    void Update () {
        timer += Time.deltaTime;

        if (timer >= imageTime && isNext)
        {
            isNext = false;
            screenShot();
            timer = 0;
            //test++;
        }

    }

    // 배경(동영상) 만
    public void sendBack()
    {
        try
        {

            RenderTexture texture2D = (RenderTexture)vp.texture;

            Texture2D tex = new Texture2D(1920, 1080, TextureFormat.RGB24, false);
            RenderTexture.active = texture2D;

            tex.ReadPixels(new Rect(0, 0, texture2D.width, texture2D.height), 0, 0);

            tex.Apply();

            //byte[] by = tex.EncodeToPNG();
            BackByte = tex.EncodeToPNG();



            /*
            // 정보 전달
            si = new sendImageInfo("CAR", by.Length);

            JsonData infoJson = JsonMapper.ToJson(si);

            AllObjectS.client_image.Send(Encoding.UTF8.GetBytes(infoJson.ToString()));

            AllObjectS.client_image.Send(by);
            
            
            Byte[] _data = new Byte[1024];


            AllObjectS.client_image.Receive(_data);

            string _buf = Encoding.UTF8.GetString(_data);
            Debug.Log(_buf);
            if (!_buf.Contains("Complete"))
                Debug.Log("전송 오류");
                
        */

        }
        catch (Exception e)
        {
            Debug.Log(e);
        }
    }

    // 배경 + 차량까지
    public void sendCar()
    {
        
        
        RenderTexture rt = new RenderTexture(resWidth, resHeight, 24);
        camera.targetTexture = rt;
        Texture2D screenShot = new Texture2D(resWidth, resHeight, TextureFormat.RGB24, false);
        //Rect rec = new Rect(0, 0, screenShot.width, screenShot.height);
        camera.Render();
        RenderTexture.active = rt;


        screenShot.ReadPixels(new Rect(0, 0, resWidth, resHeight), 0, 0);
        screenShot.Apply();
        
        //byte[] bytes = screenShot.EncodeToPNG();
        CarByte = screenShot.EncodeToPNG();

        /*
        // 정보 전달
        si = new sendImageInfo("BACK", bytes.Length);

        JsonData infoJson = JsonMapper.ToJson(si);

        AllObjectS.client_image.Send(Encoding.UTF8.GetBytes(infoJson.ToString()));


        AllObjectS.client_image.Send(bytes);
        
        
        Byte[] _data = new Byte[1024];

        AllObjectS.client_image.Receive(_data);

        string _buf = Encoding.UTF8.GetString(_data);

        if (!_buf.Contains("Complete"))
            Debug.Log("2전송 오류");
            
    */
    }

    public void screenShot()
    {
        
        if (!AllObjectS.ServerConnect_image)
            return;

        sendCar();

        sendBack();

        isComplete = true;
    }
}
