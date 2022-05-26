//
//  UserAPI.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/21.
//
import Foundation
import SwiftHTTP


class UserAPI{
    func userLogin(userID: String, userPW: String, handler:((String?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/login",parameters: ["userID" :userID, "userPW": userPW]){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            if let handler = handler {
                handler(dicData["data"] as? String)
            }
        }

    }
    func getUser(session: String, user: String , handler:((String?) -> Void)?){
        var result: Dictionary<String, Any> = [String : Any]()
        
        
        HTTP.POST("http://soc06212.iptime.org:8080/user", headers: ["Set-Cookie":session]){
            response in
            
            var dicData : Dictionary<String, Any> = [String : Any]()
            
            
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
           
            result = dicData["data"] as! [String: Any]
            if let handler = handler{
                handler(result["\(user)"] as? String)
            }
            
            
        }
    }
    
    func userLogout(session: String, handler:((Int?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/logout",headers: ["Set-Cookie":session]){
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
    
    func nicknameUpdate(session: String,nickname: String, handler:((Bool?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/edit-user",parameters: ["nickname":nickname],headers: ["Set-Cookie":session]){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            
            if let handler = handler {
                handler(dicData["data"] as? Bool)
            }
            
        }
    }
    
    
    
}





