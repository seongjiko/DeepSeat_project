package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.vo.Observation
import org.apache.ibatis.annotations.Param

interface ObservationService {

    public fun insertObservation(observation: Observation)
    public fun getObservationsByDay(
        @Param("roomID") roomID: Int,
        @Param("seatID") seatID: Int,
        @Param("date") date: String
    ): List<Observation>

}