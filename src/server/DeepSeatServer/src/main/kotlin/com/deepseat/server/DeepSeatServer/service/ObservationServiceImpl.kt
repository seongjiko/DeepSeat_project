package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.ObservationMapper
import com.deepseat.server.DeepSeatServer.vo.Observation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ObservationServiceImpl : ObservationService {

    @Autowired
    private lateinit var mapper: ObservationMapper

    override fun insertObservation(observation: Observation) {
        mapper.insertObservation(observation)
    }

    override fun getObservationsByDay(roomID: Int, seatID: Int, date: String): List<Observation> {
        return mapper.getObservationsByDay(roomID, seatID, date)
    }
}