Date = '05 NOV 2019'
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

image_hub = imagezmq.ImageHub()

try:
    print("Ready!!")
    while True:
        rpi_name, img = image_hub.recv_image()
        (raspH, raspW) = img.shape[:2]
        cv2.line(img, (int(raspW*0.38), int(raspH*0.31)), (int(raspW*0.59) ,int(raspH*0.69)), (0,255,0), 2)
        cv2.line(img, (int(raspW*0.38), int(raspH*0.69)), (int(raspW*0.59) ,int(raspH*0.31)), (0,255,0), 2)
        cv2.line(img, (int(raspW/2), 0), (int(raspW/2), int(raspH)), (0,0,255), 2)
        cv2.line(img, (0, int(raspH/2)), (int(raspW), int(raspH/2)), (0,0,255), 2)
        # Draw Recognition Area
        cv2.rectangle(img, (int(raspW*0.41), 0), (int(raspW/2), int(raspH*0.31)), (255,0,0), 2) # Top Area
        cv2.rectangle(img, (int(raspW/2), int(raspH*0.69)), (int(raspW*0.56), int(raspH)), (255,0,0), 2) # Bottom Area
        cv2.rectangle(img, (0, int(raspH/2)), (int(raspW*0.38), int(raspH*5/8)), (255,0,0), 2) # Left Area
        cv2.rectangle(img, (int(raspW*0.59), int(raspH*3/8)), (int(raspW), int(raspH/2)), (255,0,0), 2) # Right Area
        cv2.rectangle(img, (int(raspW*0.38), int(raspH*0.31)), (int(raspW*0.59) ,int(raspH*0.69)), (255,255,255), 2)
        
        testimage = cv2.imread("standard.png")
        (testH, testW) = testimage.shape[:2]
        cv2.line(testimage, (int(testW*0.38), int(testH*0.31)), (int(testW*0.59) ,int(testH*0.69)), (0,255,0), 2)
        cv2.line(testimage, (int(testW*0.38), int(testH*0.69)), (int(testW*0.59) ,int(testH*0.31)), (0,255,0), 2)
        cv2.line(testimage, (int(testW/2), 0), (int(testW/2), int(testH)), (0,0,255), 2)
        cv2.line(testimage, (0, int(testH/2)), (int(testW), int(testH/2)), (0,0,255), 2)
        # Draw Recognition Area
        cv2.rectangle(testimage, (int(testW*0.41), 0), (int(testW/2), int(testH*0.31)), (255,0,0), 2) # Top Area
        cv2.rectangle(testimage, (int(testW/2), int(testH*0.69)), (int(testW*0.56), int(testH)), (255,0,0), 2) # Bottom Area
        cv2.rectangle(testimage, (0, int(testH/2)), (int(testW*0.38), int(testH*5/8)), (255,0,0), 2) # Left Area
        cv2.rectangle(testimage, (int(testW*0.59), int(testH*3/8)), (int(testW), int(testH/2)), (255,0,0), 2) # Right Area
        cv2.rectangle(testimage, (int(testW*0.38), int(testH*0.31)), (int(testW*0.59) ,int(testH*0.69)), (255,255,255), 2)
        
        
        text = "{}".format("[ADJUSTMENT]")
        x = 30
        y = 80
        rx = x * raspW/testW
        ry = y * raspH/testH
        cv2.putText(testimage, text, (x, y), cv2.FONT_HERSHEY_SIMPLEX, 2, (255,255,0), 4)
        cv2.putText(img, text, (rx, ry), cv2.FONT_HERSHEY_SIMPLEX, 2, (255,255,0), 4)
        
        cv2.namedWindow("RaspberryPI", cv2.WINDOW_NORMAL)
        cv2.imshow("RaspberryPI", img)
        cv2.namedWindow("testImage", cv2.WINDOW_NORMAL)
        cv2.imshow("testImage", testimage)
        
        if cv2.waitKey(1) == ord('q'):
            break
            
        image_hub.send_reply(b'OK')
except Exception as e:
    print(e)
