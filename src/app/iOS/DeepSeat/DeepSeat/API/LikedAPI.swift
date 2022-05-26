//
//  LikedAPI.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/26.
//

import Foundation
import SwiftHTTP

class LikedAPI{
    func liked(docID: Int,session: String,handler:((Int?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/liked/\(docID)",headers: ["Set-Cookie":session]){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            
            if let handler = handler {
                handler(dicData["responseCode"] as? Int)
            }
        }
    }
    func getLiked(docID: Int, roomID: Int, seatID: Int, session: String, handler:((Int?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/liked/\(roomID)/\(seatID)/\(docID)",headers: ["Set-Cookie":session]){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            
            if let handler = handler {
                handler(dicData["data"] as? Int)
            }
        }
    }
    func unliked(docID: Int, roomID: Int, seatID: Int, session: String, handler:((Int?) -> Void)?){
        HTTP.DELETE("http://soc06212.iptime.org:8080/liked/\(roomID)/\(seatID)/\(docID)",headers: ["Set-Cookie":session]){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            
            if let handler = handler {
                handler(dicData["data"] as? Int)
            }
        }
    }
}
