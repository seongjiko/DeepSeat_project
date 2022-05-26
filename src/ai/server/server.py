import requests
import pprint
import json

import re
p = re.compile('(?<!\\\\)\'')

def sendData(url, data):
    print(url, data)
    print("=================================================RESPONSE=================================================")
    return requests.post(url, params=data)

def request_func(table_info):
    print(table_info)
        
    ############## table_info ##############
    # 0                    1              #
    # table_number(ID),  state_of_table   #
    #######################################
    p = re.compile('(?<!\\\\)\'')

    seatStats = []
    seatStats.append({"seatID" : table_info[0], "status":table_info[1]})

    data_value = {"seatStats": seatStats}
    data_value = json.dumps(data_value)

    format_data = {"apiKey" : "1_gceia9YPSGD$b8)QEX7u^WsYPvMOn!IGQF_i_k8SB8hG!_gGj2T)a@*Odonl51" ,
     "totalCnt" : 0,
     "roomName": "Software Village",
     "data": data_value,
    }

    # table Shape URL = 'http://soc06212.iptime.org:8080/api/test/1/register'
    # table State URL = "http://soc06212.iptime.org:8080/api/room/1/status"
    url = 'http://soc06212.iptime.org:8080/api/room/1/status'

    result = sendData(url, format_data)
    print(json.loads(result.text))

def main():
    request_func(table_info)

if __name__ == "__main__":
    main()
