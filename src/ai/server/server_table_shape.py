import requests
import pprint
import json

import re
p = re.compile('(?<!\\\\)\'')

def sendData(url, data):
    print(url, data)
    print("=================================================RESPONSE=================================================")
    return requests.post(url, params=data)


def request_shape(table_info):
    print(table_info)
    
    # 0      1           2       3           4             5                  6
    # xpos , ypos , box_width, box_height, box_shape, table_number(ID), table_state
    p = re.compile('(?<!\\\\)\'')

    # seatList.append({"seatID" : 2, "x":0, "y":0, "width":0, "height":0})
    seatList = []
    seatList.append({"seatID" : table_info[5], "x":table_info[0], "y":table_info[1], "width":table_info[2], "height":table_info[3]})

    data_value = {"seats": seatList}
    data_value = json.dumps(data_value)

    format_data = {"apiKey" : "1_gceia9YPSGD$b8)QEX7u^WsYPvMOn!IGQF_i_k8SB8hG!_gGj2T)a@*Odonl51" ,
     "totalCnt" : 0,
     "roomID" :  1,
     "roomName" : "Software Village",
     "data": data_value,
     "text": "Hi MG!!"}



    # table Shape URL = 'http://soc06212.iptime.org:8080/api/test/1/register'
    # table State URL = "http://soc06212.iptime.org:8080/api/room/1/status"
    url = 'http://soc06212.iptime.org:8080/api/room/1/register'

    result = sendData(url, format_data)
    print(json.loads(result.text))

def main():
    request_func(table_info)


if __name__ == "__main__":
    # opt = parse_opt()
    main()
