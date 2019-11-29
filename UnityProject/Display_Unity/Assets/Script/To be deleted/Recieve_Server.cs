using System;
using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using UnityEngine;

public class Recieve_Server : MonoBehaviour {

    IPAddress localAddr;
    Socket client;
    Byte[] _data;
    String _buf;
    /*
     * 안씀
    // Use this for initialization
    void Start () {
        // 서버랑 연결
        localAddr = IPAddress.Parse("127.0.0.1");
        int portnum = 8888;

        IPEndPoint ipep = new IPEndPoint(localAddr, portnum);

        client = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

        client.Connect(ipep);

        _data = new Byte[1024];
        client.Receive(_data);
        _buf = Encoding.Default.GetString(_data);

        Debug.Log(_buf);
    }

    void sendMessage()
    {
        // 서버로 전달
        _data = new Byte[1024];
        _buf = infoJson.ToString();
        _data = Encoding.Default.GetBytes(_buf);

        client.Send(_data);

        _data = new Byte[1024];
        client.Receive(_data);
        _buf = Encoding.Default.GetString(_data);

        Debug.Log(_buf);
    }
	
	// Update is called once per frame
	void Update () {
		
	}*/
}
