using System;
using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class ServerConnect : MonoBehaviour {

    IPAddress localAddr;
    Socket client;
    Byte[] _data;
    String _buf;
    string ServerIP;
    int ServerPort;

    IPAddress localAddr_image;
    Socket client_image;
    Byte[] _data_image;
    String _buf_image;
    string ServerIP_image;
    int ServerPort_image;

    bool changeScene = false;
    bool changeScene_image = false;
    Text text;
    Text text_image;

    bool ConnectFail=false;
    bool ConnectFail_image = false;


    // Use this for initialization
    void Start () {
        Screen.SetResolution(1920, 1080, true);
        text = GameObject.Find("nowconnect").GetComponent<Text>();
        text_image = GameObject.Find("nowconnect_image").GetComponent<Text>();
        
    }

    // Update is called once per frame
    void Update () {
		if(changeScene && changeScene_image)
        {
            SceneManager.LoadScene("Main");

        }

        if(ConnectFail)
        {
            text.text = "서버 상태 : 연결 실패";

        }

        if (ConnectFail_image)
        {
            text_image.text = "이미지 서버 상태 : 연결 실패";

        }
    }

    // 서버와 연결 시도
    public void ConnectServer()
    {

        var ip = GameObject.Find("Ip").GetComponent<InputField>();
        var Port = GameObject.Find("Port").GetComponent<InputField>();

        if(String.IsNullOrEmpty(ip.text) || String.IsNullOrEmpty(Port.text))
        {
            text.text = "IP 또는 Port를 입력하지 않았습니다.";
            return;

        }

        ServerIP = ip.text;
        ServerPort = int.Parse(Port.text);

        Thread t1 = new Thread(new ThreadStart(Run));
        t1.Start();
    }

    public void Run()
    {

        try
        {
            localAddr = IPAddress.Parse(ServerIP);
            int portnum = ServerPort;

            IPEndPoint ipep = new IPEndPoint(localAddr, portnum);

            AllObjectS.client = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            AllObjectS.client.Connect(ipep);

            changeScene = true;

            AllObjectS.ServerConnect = true;


        }
        catch (Exception)
        {
            ConnectFail = true;


        }
    }

    public void NotConnectServer()
    {

        AllObjectS.ServerConnect = false;

        changeScene = true;


    }

    // 서버와 연결 시도
    public void ConnectServer_image()
    {

        var ip = GameObject.Find("Ip_image").GetComponent<InputField>();
        var Port = GameObject.Find("Port_image").GetComponent<InputField>();
        var time = GameObject.Find("Image_Time").GetComponent<InputField>();

        if (string.IsNullOrEmpty(time.text))
            time.text = "3";
        captureVideo.imageTime = float.Parse(time.text);
        
        if (String.IsNullOrEmpty(ip.text) || String.IsNullOrEmpty(Port.text))
        {
            text_image.text = "IP 또는 Port를 입력하지 않았습니다.";
            return;

        }


        ServerIP_image = ip.text;
        ServerPort_image = int.Parse(Port.text);

        Thread t1 = new Thread(new ThreadStart(Run_image));
        t1.Start();
    }

    public void Run_image()
    {

        try
        {

            localAddr_image = IPAddress.Parse(ServerIP_image);
            int portnum = ServerPort_image;

            AllObjectS.ipep_image = new IPEndPoint(localAddr_image, portnum);

            AllObjectS.client_image = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            AllObjectS.client_image.Connect(AllObjectS.ipep_image);

            changeScene_image = true;

            AllObjectS.ServerConnect_image = true;


        }
        catch (Exception aa)
        {
            ConnectFail_image = true;

        }
    }

    public void NotConnectServer_image()
    {

        AllObjectS.ServerConnect_image = false;

        changeScene_image = true;


    }
}
