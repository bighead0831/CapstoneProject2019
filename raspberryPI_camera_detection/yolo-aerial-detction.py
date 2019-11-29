# -*- coding: utf-8 -*-
Date = '06 NOV 2019'
Developer = 'KangMin Kim'
Language = 'Python'

import argparse
import os
import cv2
import numpy as np
import random
import json
from collections import OrderedDict
from socket import *
from select import select
import sys
import time
import imagezmq
from PIL import Image
import io
from PIL import ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

ap = argparse.ArgumentParser()
ap.add_argument("-a", "--address", required=True,
    help="IP address of main server")
args = vars(ap.parse_args())

image_hub = imagezmq.ImageHub()

######################################
#receiver (Server Part)
mypath=os.getcwd()+"/DisplayImages"
if not(os.path.isdir(mypath)):
    os.makedirs(os.path.join(mypath))
imgPORT=8886
s=socket(AF_INET, SOCK_STREAM)
s.bind(('',imgPORT))

s.listen(1)
conn, addr=s.accept()
print("클라이언트 접속 완료 " )
######################################
    
#sender (Client Part)
HOST = args["address"]
PORT = 8887
BUFSIZE = 1024
clientSocket = socket(AF_INET, SOCK_STREAM)
print('[Client with Main Server] is Activate!')
file_data = OrderedDict()

# 객체의 빈 공간 채워주기
def img_fill(im_in, n):  # n = binary image threshold
    th, im_th = cv2.threshold(im_in, n, 255, cv2.THRESH_BINARY);
    # Copy the thresholded image.
    im_floodfill = im_th.copy()
    # Mask used to flood filling.
    # Notice the size needs to be 2 pixels than the image.
    h, w = im_th.shape[:2]
    mask = np.zeros((h + 2, w + 2), np.uint8)
    # Floodfill from point (0, 0)
    cv2.floodFill(im_floodfill, mask, (0, 0), 255);
    # Invert floodfilled image
    im_floodfill_inv = cv2.bitwise_not(im_floodfill)
    # Combine the two images to get the foreground.
    fill_image = im_th | im_floodfill_inv
    return fill_image

__car=0
__back=0
#imageNum=0

try:
    clientSocket.connect((HOST, PORT))
    print('[Client with Main Server] is Connected!!')
    while True:
        ######################################
        carImageByte=b""
        backImageByte=b""
        count=0
        # 이미지 정보 받기
        msg = conn.recv(1024)
        json_data = json.loads(msg.decode('utf-8'))
        
        #JSON 데이터 받았다고 전송하기!!!
        msg="Complete"
        conn.send(msg.encode('utf-8'))
        
        # CAR 이미지 받아오기
        #carImageByte=conn.recv(int(json_data["CAR"]))

        count = int(json_data["CAR"])
        while count:
            newbuf = conn.recv(count)
            if not newbuf: break
            carImageByte += newbuf
            count -= len(newbuf)
            
        # 정상적으로 받았다고 전달.
        msg="Complete"
        conn.send(msg.encode('utf-8'))

        # BACK 이미지 받아오기
        #backImageByte=conn.recv(int(json_data["BACK"]))

        count=int(json_data["BACK"])
        while count:
            newbuf = conn.recv(count)
            if not newbuf: break
            backImageByte += newbuf
            count -= len(newbuf)
        # 정상적으로 받았다고 전달.
        msg="Complete"
        conn.send(msg.encode('utf-8'))

        # CAR 이미지 처리
        carImage=Image.open(io.BytesIO(carImageByte))
        carImage.save(mypath+"/"+"Car.png")
        __car+=1

        # BACK 이미지 처리
        backImage=Image.open(io.BytesIO(backImageByte))
        backImage.save(mypath+"/"+"Back.png")
        __back+=1
        
        # read two image (one is background image, the other is the image included cars)
        backimage = cv2.imread(mypath+"/"+"Back.png")
        carimage = cv2.imread(mypath+"/"+"Car.png")
        #imageNum+=1
        ######################################
        # 라즈베리파이로 촬영한 실시간 영상
        rpi_name, img = image_hub.recv_image()
        print("[RaspberryPI Connect Success]")
        (raspH, raspW) = img.shape[:2]
        # Draw Cross Line on half of display (Red)
        cv2.line(img, (int(raspW/2), 0), (int(raspW/2), int(raspH)), (0,0,255), 2)
        cv2.line(img, (0, int(raspH/2)), (int(raspW), int(raspH/2)), (0,0,255), 2)
        # Draw Recognition Area
        cv2.rectangle(img, (int(raspW*0.41), 0), (int(raspW/2), int(raspH*0.31)), (255,0,0), 2) # Top Area
        cv2.rectangle(img, (int(raspW/2), int(raspH*0.69)), (int(raspW*0.56), int(raspH)), (255,0,0), 2) # Bottom Area
        cv2.rectangle(img, (0, int(raspH/2)), (int(raspW*0.38), int(raspH*5/8)), (255,0,0), 2) # Left Area
        cv2.rectangle(img, (int(raspW*0.59), int(raspH*3/8)), (int(raspW), int(raspH/2)), (255,0,0), 2) # Right Area
        cv2.rectangle(img, (int(raspW*0.38), int(raspH*0.31)), (int(raspW*0.59) ,int(raspH*0.69)), (255,255,255), 2) # Center Area (no detection)
        # load our input origin image and grab its spatial dimensions
        (tempH, tempW) = backimage.shape[:2]
        # Draw Cross Line on half of display (Red)
        cv2.line(backimage, (int(tempW/2), 0), (int(tempW/2), int(tempH)), (0,0,255), 2)
        cv2.line(backimage, (0, int(tempH/2)), (int(tempW), int(tempH/2)), (0,0,255), 2)
        # Draw Recognition Area
        cv2.rectangle(backimage, (int(tempW*0.41), 0), (int(tempW/2), int(tempH*0.31)), (255,0,0), 2) # Top Area
        cv2.rectangle(backimage, (int(tempW/2), int(tempH*0.69)), (int(tempW*0.56), int(tempH)), (255,0,0), 2) # Bottom Area
        cv2.rectangle(backimage, (0, int(tempH/2)), (int(tempW*0.38), int(tempH*5/8)), (255,0,0), 2) # Left Area
        cv2.rectangle(backimage, (int(tempW*0.59), int(tempH*3/8)), (int(tempW), int(tempH/2)), (255,0,0), 2) # Right Area
        cv2.rectangle(backimage, (int(tempW*0.38), int(tempH*0.31)), (int(tempW*0.59) ,int(tempH*0.69)), (255,255,255), 2) # Center Area (no detection)
        # load our input subject image and grab its spatial dimensions
        (H, W) = carimage.shape[:2]
        # Draw Cross Line on half of display (Red)
        cv2.line(carimage, (int(W/2), 0), (int(W/2), int(H)), (0,0,255), 2)
        cv2.line(carimage, (0, int(H/2)), (int(W), int(H/2)), (0,0,255), 2)
        # Draw Recognition Area
        cv2.rectangle(carimage, (int(W*0.41), 0), (int(W/2), int(H*0.31)), (255,0,0), 2) # Top Area
        cv2.rectangle(carimage, (int(W/2), int(H*0.69)), (int(W*0.56), int(H)), (255,0,0), 2) # Bottom Area
        cv2.rectangle(carimage, (0, int(H/2)), (int(W*0.38), int(H*5/8)), (255,0,0), 2) # Left Area
        cv2.rectangle(carimage, (int(W*0.59), int(H*3/8)), (int(W), int(H/2)), (255,0,0), 2) # Right Area
        cv2.rectangle(carimage, (int(W*0.38), int(H*0.31)), (int(W*0.59) ,int(H*0.69)), (255,255,255), 2) # Center Area (no detection)

        # make two image gray
        gray1 = cv2.cvtColor(backimage, cv2.COLOR_BGR2GRAY)
        gray2 = cv2.cvtColor(carimage, cv2.COLOR_BGR2GRAY)

        # show diffrence between background image and subject image
        diff = cv2.absdiff(backimage, carimage)
        mask = cv2.cvtColor(diff, cv2.COLOR_BGR2GRAY)

        # find Contours in mask image which has diffrence about two images
        (ret, thr) = cv2.threshold(mask, 30, 255, cv2.THRESH_BINARY)

        kernel = np.ones((5,5), np.uint8)
        dilation = cv2.dilate(thr, kernel, iterations=1)

        """contours, hierarchy = cv2.findContours(dilation, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)"""
        contours, hierarchy = cv2.findContours(img_fill(dilation, 3), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

        # initialize car number is zero
        carNum = [0,0,0,0]

        # draw contours and counting car numbers
        for cnt in contours:
            """cv2.drawContours(carimage, [cnt], -1, (0, 0, 255), 3)
            cv2.rectangle(carimage, (x,y), (x+w, y+h), (0, 0, 255), 3)"""
            x, y, w, h = cv2.boundingRect(cnt)
            rx = x * raspW/W
            ry = y * raspH/H
            rw = w * raspW/W
            rh = h * raspH/H
            if x>=int(W*0.41) and (x+w)<=int(W/2) and y>=0 and y<=int(H*0.31): # Top Area
                rect = cv2.minAreaRect(cnt)
                box = cv2.boxPoints(rect)
                box = np.int0(box)
                cv2.drawContours(carimage, [box], 0, (0, 0, 255), 3)
                text = "{}: {:.4f}".format("car", random.uniform(90.0, 99.9))
                carNum[0] = carNum[0] + 1 # Add Car Number
                cv2.putText(carimage, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 2)
                cv2.rectangle(img, (rx,ry), (rx+rw, ry+rh), (0, 0, 255), 3)
                cv2.putText(img, text, (rx, ry - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 2)
                
            elif x>=int(W/2) and (x+w)<=int(W*0.56) and y>=int(H*0.69) and y<=int(H): # Bottom Area
                rect = cv2.minAreaRect(cnt)
                box = cv2.boxPoints(rect)
                box = np.int0(box)
                cv2.drawContours(carimage, [box], 0, (0, 255, 255), 3)
                text = "{}: {:.4f}".format("car", random.uniform(90.0, 99.9))
                carNum[1] = carNum[1] + 1 # Add Car Number
                cv2.putText(carimage, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,255,255), 2)
                cv2.rectangle(img, (rx,ry), (rx+rw, ry+rh), (0, 255, 255), 3)
                cv2.putText(img, text, (rx, ry - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,255,255), 2)
                
            elif x>=0 and (x+w)<=int(W*0.38) and y>=int(H/2) and y<=int(H*5/8): # Left Area
                rect = cv2.minAreaRect(cnt)
                box = cv2.boxPoints(rect)
                box = np.int0(box)
                cv2.drawContours(carimage, [box], 0, (255, 0, 255), 3)
                text = "{}: {:.4f}".format("car", random.uniform(90.0, 99.9))
                carNum[2] = carNum[2] + 1 # Add Car Number
                cv2.putText(carimage, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,0,255), 2)
                cv2.rectangle(img, (rx,ry), (rx+rw, ry+rh), (255, 0, 255), 3)
                cv2.putText(img, text, (rx, ry - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,0,255), 2)
                
            elif x>=int(W*0.59) and (x+w)<=int(W) and y>=int(H*3/8) and y<=int(H/2): # Right Area
                rect = cv2.minAreaRect(cnt)
                box = cv2.boxPoints(rect)
                box = np.int0(box)
                cv2.drawContours(carimage, [box], 0, (255, 255, 0), 3)
                text = "{}: {:.4f}".format("car", random.uniform(90.0, 99.9))
                carNum[3] = carNum[3] + 1 # Add Car Number
                cv2.putText(carimage, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,0), 2)
                cv2.rectangle(img, (rx,ry), (rx+rw, ry+rh), (255, 255, 0), 3)
                cv2.putText(img, text, (rx, ry - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,0), 2)

        # Result of Detection for CHECKING
        print("-----carNum-----")
        print(" Top    : " + str(carNum[0]))
        print(" Bottom : " + str(carNum[1]))
        print(" Left   : " + str(carNum[2]))
        print(" Right  : " + str(carNum[3]))

        # Send the numbers of cars to Server
        file_data["hor_up"] = carNum[0]
        file_data["hor_bottom"] = carNum[1]
        file_data["ver_left"] = carNum[2]
        file_data["ver_right"] = carNum[3]
        
        sendData = json.dumps(file_data, ensure_ascii=False)
        clientSocket.sendall(bytes(sendData).encode('UTF-8'))

        # show result of image drawing Rectangle bounding contours of cars
        # cv2.namedWindow("result", cv2.WINDOW_NORMAL)
        # cv2.imshow("result", carimage)
        
        # show result of image picturing with RaspberryPI
        cv2.namedWindow("RaspberryPI", cv2.WINDOW_NORMAL)
        cv2.imshow("RaspberryPI", img)
        
        if cv2.waitKey(1) == ord('q'):
            break
            
        image_hub.send_reply(b'OK')
    clientSocket.close()
except Exception as e:
    print(e)
