using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ChangeText : MonoBehaviour {

    string hor = "←→";
    string ver = "↑↓";
    string hor_donw_left = "좌회전";
    Text CText;
    string last = "";

    // Use this for initialization
    void Start() {
        last = hor;
        CText = GetComponent<Text>();   // 해당 오브젝트의 구성 요소중 하나 불러오기
    }

    // Update is called once per frame
    void Update() {
        //CText.text = "신호 방향 : " + last;   // 텍스트 변경
        if (this.gameObject.name == "hor_up_text")
        {
            CText.text = "hor_up : " + getTraffic(0);

        }
        else if (this.gameObject.name == "hor_down_Text")
        {
            CText.text = "hor_down : " + getTraffic(1);

        }
        else if (this.gameObject.name == "ver_left_text")
        {
            CText.text = "ver_left : " + getTraffic(2);

        }
        else if (this.gameObject.name == "ver_right_text")
        {
            CText.text = "ver_right : " + getTraffic(3);

        }
 


    }

    string getTraffic(int n)
    {

        string traffic ="";
        int nowTraffic;

        switch(n)
        {
            case 0:
                nowTraffic= car_create_th.hor_Up_Traffic_Light;
                break;
            case 1:
                nowTraffic = car_create_th.hor_Down_Traffic_Light;

                break;
            case 2:
                nowTraffic = car_create_th.ver_Left_Traffic_Light;

                break;
            case 3:
                nowTraffic = car_create_th.ver_Right_Traffic_Light;

                break;
            default:
                nowTraffic = -1;
                break;
        }
        if(nowTraffic==AllObjectS.TRAFFIC_LIGHT_RED)
        {
            traffic = "정지";
        }
        else if (nowTraffic == AllObjectS.TRAFFIC_LIGHT_GREEN)
        {
            traffic = "직진";
        }
        else if (nowTraffic == AllObjectS.TRAFFIC_LIGHT_RIGHT)
        {
            traffic = "우회전";
        }
        else if (nowTraffic == AllObjectS.TRAFFIC_LIGHT_LEFT)
        {
            traffic = "좌회전";
        }
        else if (nowTraffic == AllObjectS.TRAFFIC_LIGHT_LEFT_GREEN)
        {
            traffic = "직진 및 좌회전";
        }
        return traffic;
    }

    void changever()
    {
        last = ver;

    }
    void changehor()
    {

        last = hor;
    }
    void changehorDownLeft()
    {

        last = hor_donw_left;
    }
}
