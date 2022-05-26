package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.SeatMapper
import com.deepseat.server.DeepSeatServer.vo.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SeatServiceImpl : SeatService {

    @Autowired
    private lateinit var mapper: SeatMapper

    override fun insertSeat(seat: Seat) {
        mapper.insertSeat(seat)
    }

    override fun getSeats(roomID: Int): List<Seat> {
        return mapper.getSeats(roomID)
    }

    override fun getSeatByID(seatID: Int): Seat? {
        return mapper.getSeatByID(seatID)
    }

    override fun deleteSeat(seatID: Int) {
        mapper.deleteSeat(seatID)
    }

    override fun updateSeat(seat: Seat) {
        mapper.updateSeat(seat)
    }
}