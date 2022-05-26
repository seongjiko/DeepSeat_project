//
//  RegisterAPI.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/21.
//

import Foundation
import SwiftHTTP

class RegisterAPI{
    func userRegister(userID: String, userPW: String, userPWCheck: String, nickname: String, email: String, handler:((Int?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/register",parameters: ["userID":userID,"userPW":userPW,"userPWCheck":userPWCheck,"nickname":nickname,"email":email]){
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
    func checkID(userID: String, handler:((Bool?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/id-check",parameters: ["userID":userID]){
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
    func checkNickname(nickname: String, handler:((Bool?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/nickname-check",parameters: ["nickname":nickname]){
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

