//
//  Seat.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/20.
//

import Foundation

class Seat{
    var seatID: Int
    var roomID: Int
    var x: Int
    var y: Int
    var width: Int
    var height: Int 
    
    init(seatID:Int, roomID: Int, x: Int, y: Int, width: Int, height: Int){
        self.seatID = seatID
        self.roomID = roomID
        self.x = x
        self.y = y
        self.width = width
        self.height = height
        
    }
}
