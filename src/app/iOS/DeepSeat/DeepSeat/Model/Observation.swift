//
//  Observation.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/20.
//

import Foundation

class Observation{
    var observationID: Int
    var roomID: Int
    var seatID: Int
    var date: String
    var time: String
    var state: Int
    
    init(observationID: Int, roomID: Int, seatID: Int, date: String, time: String, state: Int){
        self.observationID = observationID
        self.roomID = roomID
        self.seatID = seatID
        self.date = date
        self.time = time
        self.state = state
    }
}
