package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.vo.Seat
import org.springframework.stereotype.Repository

@Repository
interface SeatMapper {

    public fun insertSeat(seat: Seat)
    public fun getSeats(roomID: Int): List<Seat>
    public fun getSeatByID(seatID: Int): Seat?
    public fun deleteSeat(seatID: Int)

}