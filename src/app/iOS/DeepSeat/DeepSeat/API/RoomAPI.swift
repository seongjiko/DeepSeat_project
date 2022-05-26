//
//  RoomAPI.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/25.
//

import Foundation
import SwiftHTTP

class RoomAPI{
    func getRooms(handler:(([Room]?) -> Void)?){
        var rooms: [Room] = []
        var array : Array = [Dictionary<String, Any>]()
        var result: Dictionary<String, Any> = [String : Any]()
        HTTP.GET("http://soc06212.iptime.org:8080/api/room"){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            array  = dicData["data"] as! [[String:Any]]
            
            
            for i in 0..<array.count{
                result = array[i]
                let roomID = result["roomID"] as! Int
                let roomName = result["roomName"] as! String
                let apiKey = result["apiKey"] as! String

                let room = Room(roomID: roomID, roomName: roomName, apiKey: apiKey)
                rooms.append(room)

            }
            if let handler = handler {
                handler(rooms)
            }
            
        }
        
    }
}
