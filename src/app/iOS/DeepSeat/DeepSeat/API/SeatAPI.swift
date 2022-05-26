//
//  SeatAPI.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/25.
//

import Foundation
import SwiftHTTP

class SeatAPI{
    func getSeats(roomID:Int,handler:(([Seat]?) -> Void)?){
        var seats: [Seat] = []
        var array : Array = [Dictionary<String, Any>]()
        var result: Dictionary<String, Any> = [String : Any]()
        HTTP.GET("http://soc06212.iptime.org:8080/api/room/\(roomID)"){
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
                
                let seatID = result["seatID"] as! Int
                let roomID = result["roomID"] as! Int
                let x = result["x"] as! Int
                let y = result["y"] as! Int
                let width = result["width"] as! Int
                let height = result["height"] as! Int
                

                let seat = Seat(seatID: seatID, roomID: roomID, x: x, y: y, width: width, height: height)
                seats.append(seat)

            }
            if let handler = handler {
                handler(seats)
            }
            
        }
    }
    func getStatus(roomID: Int, handler:(([Status]?) -> Void)?){
        var statusList: [Status] = []
        var array : Array = [Dictionary<String, Any>]()
        var result: Dictionary<String,Any > = [String : Any]()
        HTTP.GET("http://soc06212.iptime.org:8080/api/room/\(roomID)/status"){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            array  = dicData["data"] as! [[String:Any]]
            
            
            for i in 0..<array.count{
                //print((array[i])["roomID"] as! Int)
                result = array[i]
                
                let observationID = result["roomID"] as! Int
                let roomID = result["roomID"] as! Int
                let seatID = result["seatID"] as! Int
                let date = result["date"] as! String
                let time = result["time"] as! String
                let state = result["state"] as! Int

                let status = Status(observationID: observationID, roomID: roomID, seatID: seatID, date: date, time: time, state: state)
                statusList.append(status)
            }
            if let handler = handler {
                handler(statusList)
            }
            
        }
    }
    
}
