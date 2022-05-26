//
//  DocumentAPI.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/23.
//

import Foundation
import SwiftHTTP

class DocumentAPI{
    
    func getAllDocument(handler:(([Document]?) -> Void)?){
        var documents: [Document] = []
        var array : Array = [Dictionary<String, Any>]()
        var result: Dictionary<String, Any> = [String : Any]()
        HTTP.GET("http://soc06212.iptime.org:8080/doc/vo"){
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
                let docID = result["docId"] as! Int
                let userID = result["userId"] as! String
                let nickname = result["nickname"] as! String
                let roomName = result["roomName"] as! String
                let seatName = result["seatName"] as! String
                let content = result["content"] as! String
                let wrote = result["wrote"] as! String
                let edited = result["edited"] as! Bool
                let commennts = result["comments"] as! Int
                let liked = result["liked"] as! Int
                let iLiked = result["iLiked"] as! Bool

                let document = Document(docID: docID, userID: userID, nickname: nickname, roomName: roomName, seatName: seatName,content: content, wrote: wrote, edited: edited, comments: commennts,liked: liked,iLiked: iLiked)
                documents.append(document)

            }
            if let handler = handler {
                handler(documents)
            }
            
        }
        
    }
   
    func getDocument(docID: Int,info: String,handler:((Any?) -> Void)?){
        
        var result: Dictionary<String, Any> = [String : Any]()
        HTTP.GET("http://soc06212.iptime.org:8080/doc/\(docID)/vo"){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            result = dicData["data"] as! [String: Any]
            if let handler = handler{
                handler(result["\(info)"])
            }
            
        }
        
        
    }
    
    func getSelectDocument(roomID: Int, seatID: Int, handler:(([Document]?) -> Void)? ){
        var documents: [Document] = []
        var array : Array = [Dictionary<String, Any>]()
        var result: Dictionary<String, Any> = [String : Any]()
        HTTP.GET("http://soc06212.iptime.org:8080/doc/\(roomID)/\(seatID)/vo"){
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
                let docID = result["docId"] as! Int
                let userID = result["userId"] as! String
                let nickname = result["nickname"] as! String
                let roomName = result["roomName"] as! String
                let seatName = result["seatName"] as! String
                let content = result["content"] as! String
                let wrote = result["wrote"] as! String
                let edited = result["edited"] as! Bool
                let commennts = result["comments"] as! Int
                let liked = result["liked"] as! Int
                let iLiked = result["iLiked"] as! Bool

                let document = Document(docID: docID, userID: userID, nickname: nickname, roomName: roomName, seatName: seatName,content: content, wrote: wrote, edited: edited, comments: commennts,liked: liked,iLiked: iLiked)
                documents.append(document)

            }
            if let handler = handler {
                handler(documents)
            }
            
        }
        
    }
    
    func writeDocument(roomID: Int, seatID: Int,session: String,content:String, handler:((Int?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/doc/\(roomID)/\(seatID)/",parameters: ["content":content],headers: ["Set-Cookie":session]){
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
    func updateDocument(roomID: Int, seatID: Int, docID: Int,session: String, content: String, handler:((Int?) -> Void)?){
        HTTP.PUT("http://soc06212.iptime.org:8080/doc/\(roomID)/\(seatID)/\(docID)/",parameters: ["content":content],headers: ["Set-Cookie":session]){
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
    
    func deleteDucument(roomID: Int, seatID: Int, docID: Int, session: String, handler:((Int?) -> Void)?){
        HTTP.DELETE("http://soc06212.iptime.org:8080/doc/\(roomID)/\(seatID)/\(docID)/",headers: ["Set-Cookie":session]){
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

}

