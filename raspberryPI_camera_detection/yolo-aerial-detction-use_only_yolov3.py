# USAGE
# python yolo-nocrop-detection-json.py -y yolo-coco -a 10.20.29.211

# import the necessary packages
import numpy as np
import argparse
import time
import cv2
import os
import imagezmq
from PIL import Image
import json
from collections import OrderedDict
from socket import *
from select import select
import sys

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-a", "--address", required=True,
    help="IP address of main server")
ap.add_argument("-y", "--yolo", required=True,
	help="base path to YOLO directory")
ap.add_argument("-c", "--confidence", type=float, default=0.5,
	help="minimum probability to filter weak detections")
ap.add_argument("-t", "--threshold", type=float, default=0.3,
	help="threshold when applyong non-maxima suppression")
args = vars(ap.parse_args())

# load the COCO class labels our YOLO model was trained on
labelsPath = os.path.sep.join([args["yolo"], "aerial.names"])
LABELS = open(labelsPath).read().strip().split("\n")

# initialize a list of colors to represent each possible class label
np.random.seed(42)
COLORS = np.random.randint(0, 255, size=(len(LABELS), 3),
	dtype="uint8")

# derive the paths to the YOLO weights and model configuration
weightsPath = os.path.sep.join([args["yolo"], "yolov3-aerial.weights"])
configPath = os.path.sep.join([args["yolo"], "yolov3-aerial.cfg"])

#sender
HOST = '127.0.0.1'#args["address"]
PORT = 8887
BUFSIZE = 1024

# load our YOLO object detector trained on COCO dataset (80 classes)
print("[INFO] loading YOLO from disk...")
net = cv2.dnn.readNetFromDarknet(configPath, weightsPath)

image_hub = imagezmq.ImageHub()
print('[Server with RPI] is Activate!')

clientSocket = socket(AF_INET, SOCK_STREAM)
print('[Client with Main Server] is Activate!')
file_data = OrderedDict()

try:
    clientSocket.connect((HOST, PORT))
    print('[Client with Main Server] is Connected!!')
    while True:
        rpi_name, img = image_hub.recv_image()
        
        
        carNum = [0, 0, 0, 0]
        # load our input image and grab its spatial dimensions
        (H, W) = img.shape[:2]
        
        # Draw Vertical Line on half of display (Red)
        cv2.line(img, (int(W/2), 0), (int(W/2), int(H)), (0,0,255), 2)
        # Draw Horizonal Line on half of display (Red)
        cv2.line(img, (0, int(H/2)), (int(W), int(H/2)), (0,0,255), 2)
        """
        # Draw Vertical Line (Yellow)
        cv2.line(img, (int(W*3/8), 0), (int(W*3/8), int(H)), (0,255,255), 1)
        cv2.line(img, (int(W*5/8), 0), (int(W*5/8), int(H)), (0,255,255), 1)
        # Draw Horizonal Line (Yellow)
        cv2.line(img, (0, int(H*1/3)), (int(W), int(H*1/3)), (0,255,255), 1)
        cv2.line(img, (0, int(H*2/3)), (int(W), int(H*2/3)), (0,255,255), 1)
        """
        # Draw Recognition Area
        cv2.rectangle(img, (int(W*3/8), 0), (int(W/2), int(H/3)), (255,0,0), 2) # Top Area
        cv2.rectangle(img, (int(W/2), int(H*2/3)), (int(W*5/8), int(H)), (255,0,0), 2) # Bottom Area
        cv2.rectangle(img, (0, int(H/2)), (int(W*3/8), int(H*2/3)), (255,0,0), 2) # Left Area
        cv2.rectangle(img, (int(W*5/8), int(H/3)), (int(W), int(H/2)), (255,0,0), 2) # Right Area
        
        image = []
        # Top, Bottom, Left, Right
        
        # Resolution of PI-camera is 320x240 and Mac-camera is 1280x720
        """
        print("\n[RESOLUTION]")
        print("width, height")
        print(W, H)
        """

        # determine only the *output* layer names that we need from YOLO
        ln = net.getLayerNames()
        ln = [ln[i[0] - 1] for i in net.getUnconnectedOutLayers()]

        # construct a blob from the input image and then perform a forward
        # pass of the YOLO object detector, giving us our bounding boxes and
        # associated probabilities
        blob = cv2.dnn.blobFromImage(img,  1 / 255.0, (416, 416), swapRB=True, crop=False)
        net.setInput(blob)
        """start = time.time()"""
        layerOutputs = net.forward(ln)
        """end = time.time()"""

        # show timing information on YOLO
        """print("[INFO] YOLO took {:.6f} seconds".format(end - start))"""

        # initialize our lists of detected bounding boxes, confidences, and
        # class IDs, respectively
        boxes = []
        confidences = []
        classIDs = []

        # loop over each of the layer outputs
        for output in layerOutputs:
            # loop over each of the detections
            for detection in output:
                # extract the class ID and confidence (i.e., probability) of
                # the current object detection
                scores = detection[5:]
                classID = np.argmax(scores)
                confidence = scores[classID]

                # filter out weak predictions by ensuring the detected
                # probability is greater than the minimum probability
                if confidence > args["confidence"]:
                    # scale the bounding box coordinates back relative to the
                    # size of the image, keeping in mind that YOLO actually
                    # returns the center (x, y)-coordinates of the bounding
                    # box followed by the boxes' width and height
                    box = detection[0:4] * np.array([W, H, W, H])
                    (centerX, centerY, width, height) = box.astype("int")

                    # use the center (x, y)-coordinates to derive the top and
                    # and left corner of the bounding box
                    x = int(centerX - (width / 2))
                    y = int(centerY - (height / 2))

                    # update our list of bounding box coordinates, confidences,
                    # and class IDs
                    boxes.append([x, y, int(width), int(height)])
                    confidences.append(float(confidence))
                    classIDs.append(classID)

        # apply non-maxima suppression to suppress weak, overlapping bounding
        # boxes
        idxs = cv2.dnn.NMSBoxes(boxes, confidences, args["confidence"],
            args["threshold"])

        # ensure at least one detection exists
        if len(idxs) > 0:
            # loop over the indexes we are keeping
            for i in idxs.flatten():
                # extract the bounding box coordinates
                (x, y) = (boxes[i][0], boxes[i][1])
                (w, h) = (boxes[i][2], boxes[i][3])

                # draw a bounding box rectangle and label on the image
                # draw boxes recognizing cars on each areas
                if x>=int(W*3/8)     and (x+w)<=int(W/2)   and y>0          and y<=int(H/3): # Top Area
                    cv2.rectangle(img, (x, y), (x + w, y + h), (0,0,255), 2)
                    text = "{}: {:.4f}".format(LABELS[classIDs[i]], confidences[i])
                    if text[0] == 'c' and text[1] == 'a' and text[2] == 'r':
                        carNum[0] = carNum[0] + 1
                    carNum[0] = carNum[0] + 1 # Test
                    cv2.putText(img, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 2)
                    
                elif x>=int(W/2)     and (x+w)<=int(W*5/8) and y>=int(H*2/3) and y<=int(H): # Bottom Area
                    cv2.rectangle(img, (x, y), (x + w, y + h), (0,255,255), 2)
                    text = "{}: {:.4f}".format(LABELS[classIDs[i]], confidences[i])
                    if text[0] == 'c' and text[1] == 'a' and text[2] == 'r':
                        carNum[1] = carNum[1] + 1
                    carNum[1] = carNum[1] + 1 # Test
                    cv2.putText(img, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0,255,255), 2)
                    
                elif x>=0            and (x+w)<=int(W*3/8) and y>=int(H/2)   and y<=int(H*2/3): # Left Area
                    cv2.rectangle(img, (x, y), (x + w, y + h), (255,0,255), 2)
                    text = "{}: {:.4f}".format(LABELS[classIDs[i]], confidences[i])
                    if text[0] == 'c' and text[1] == 'a' and text[2] == 'r':
                        carNum[2] = carNum[2] + 1
                    carNum[2] = carNum[2] + 1 # Test
                    cv2.putText(img, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,0,255), 2)
                    
                elif x>=int(W*5/8)   and (x+w)<=int(W)     and y>=int(H/3)   and y<=int(H/2): # Right Area
                    cv2.rectangle(img, (x, y), (x + w, y + h), (255,255,0), 2)
                    text = "{}: {:.4f}".format(LABELS[classIDs[i]], confidences[i])
                    if text[0] == 'c' and text[1] == 'a' and text[2] == 'r':
                        carNum[3] = carNum[3] + 1
                    carNum[3] = carNum[3] + 1 # Test
                    cv2.putText(img, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,0), 2)
                ##### This part is test #####
                else:
                    cv2.rectangle(img, (x, y), (x + w, y + h), (255,255,255), 2)
                    text = "{}: {:.4f}".format(LABELS[classIDs[i]], confidences[i])
                    cv2.putText(img, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,0), 2)
                #############################
                    
        # show the output image
        cv2.namedWindow(rpi_name+" - Detecting Result!", cv2.WINDOW_NORMAL)
        cv2.imshow(rpi_name+" - Detecting Result!", img)
        
        print("-----carNum-----")
        print(" Top    : " + str(carNum[0]))
        print(" Bottom : " + str(carNum[1]))
        print(" Left   : " + str(carNum[2]))
        print(" Right  : " + str(carNum[3]))
        
        file_data["hor_up"] = carNum[0]
        file_data["hor_bottom"] = carNum[1]
        file_data["ver_left"] = carNum[2]
        file_data["ver_right"] = carNum[3]
        
        sendData = json.dumps(file_data, ensure_ascii=False)
        clientSocket.sendall(bytes(sendData).encode('UTF-8'))
        
        ###
        if cv2.waitKey(1) == ord('q'):
            break
        
        image_hub.send_reply(b'OK')
    clientSocket.close()
except Exception as e:
    print(e)
