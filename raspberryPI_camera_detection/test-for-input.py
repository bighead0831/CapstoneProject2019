# -*- coding: utf-8 -*-
import threading
import socket
import json
import os
from PIL import Image
import io
from PIL import ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

class ImageRecieve(threading.Thread):
    
    def set (self,conn,s,mypath):
        self.__s=s
        self.__conn=conn
        self.__car=0
        self.__back=0
        self.__mypath=mypath

        
    def receiveCar(self,image):
        image.save(self.__mypath+"/"+str(self.__car)+"Car.png")
        self.__car+=1
    def receiveBack(self,image):
        image.save(self.__mypath+"/"+str(self.__back)+"Back.png")
        self.__back+=1
        
    def run(self):
        print("클라이언트 접속 완료 " )

       
        
        while True:
            
            carImageByte=b""
            backImageByte=b""
            count=0
            
            # 이미지 정보 받기
            msg = self.__conn.recv(1024)
            #print("[msg]") ###
            #print(msg) ###
            json_data = json.loads(msg.decode('utf-8'))
            #print("[json_data]") ###
            #print(json_data) ###
            
            #JSON 데이터 받았다고 전송하기!!!
            msg="Complete"
            self.__conn.send(msg.encode('utf-8'))
            
            # CAR 이미지 받아오기
            #carImageByte=self.__conn.recv(int(json_data["CAR"]))
            count=int(json_data["CAR"])
            #print("CAR image size : "+str(count)) ###
            while count:
                newbuf = self.__conn.recv(count)
                if not newbuf: return None
                carImageByte += newbuf
                count -= len(newbuf)
            # 정상적으로 받았다고 전달.
            msg="Complete"
            self.__conn.send(msg.encode('utf-8'))

            # BACK 이미지 받아오기
            #backImageByte=self.__conn.recv(int(json_data["BACK"])
            count=int(json_data["BACK"])
            #print("BACK image size : "+str(count)) ###
            while count:
                newbuf = self.__conn.recv(count)
                if not newbuf: return None
                backImageByte += newbuf
                count -= len(newbuf)
            # 정상적으로 받았다고 전달.
            msg="Complete"
            self.__conn.send(msg.encode('utf-8'))

            # CAR 이미지 처리
            carImage=Image.open(io.BytesIO(carImageByte))
            self.receiveCar(carImage)
           
            # BACK 이미지 처리
            backImage=Image.open(io.BytesIO(backImageByte))
            self.receiveBack(backImage)



if __name__=="__main__":
    
    mypath=os.getcwd()+"/DisplayImages"
    HOST=''
    PORT=8886
    s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    s.bind((HOST,PORT))

    s.listen(1)
    conn, addr=s.accept()
    
    if not(os.path.isdir(mypath)):
        os.makedirs(os.path.join(mypath))
    

    glog=ImageRecieve(name="receive")
    glog.set(conn,s,mypath)
    glog.start()
