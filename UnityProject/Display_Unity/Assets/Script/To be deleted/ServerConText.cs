using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ServerConText : MonoBehaviour
{

    Text CText;
    string last = "";

    bool ischange;

    // Use this for initialization
    void Start()
    {
        last = "연결 대기 중";
        CText = GetComponent<Text>();   // 해당 오브젝트의 구성 요소중 하나 불러오기

        ischange = false;
    }

    // Update is called once per frame
    void Update()
    {
        CText.text = "서버 연결 상태 : " + last;   // 텍스트 변경

    }

    void changeText()
    {
        last = "연결 완료";

    }
}
