package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.vo.Seat

interface SeatService {

    public fun insertSeat(seat: Seat)
    public fun getSeats(roomID: Int): List<Seat>
    public fun getSeatByID(seatID: Int): Seat?
    public fun deleteSeat(seatID: Int)

}