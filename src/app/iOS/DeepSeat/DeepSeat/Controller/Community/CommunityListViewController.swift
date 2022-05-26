//
//  CommunityListViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/14.
//

import UIKit
protocol SendIDDelegate{
    func sendID(roomID: Int, seatID: Int)
}
class CommunityListViewController: UITableViewController {
    var rooms: [Room] = []
    var seats: Dictionary<Int,[Seat]> = [Int:[Seat]]()
    var data: [Any] = []
    var roomSize: Int = 0
    var seatCount: Int = 0
    let roomAPI = RoomAPI()
    let seatAPI = SeatAPI()
    
    var delegate : SendIDDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        if let presentationController = presentationController as? UISheetPresentationController {
            presentationController.detents = [.medium()]
            presentationController.prefersGrabberVisible = true
            presentationController.prefersScrollingExpandsWhenScrolledToEdge = false
            presentationController.largestUndimmedDetentIdentifier = .medium
        }
        loadRooms()
        
        
    }
    
    func loadRooms(){
        roomAPI.getRooms { rooms in
            if let rooms = rooms{
                self.roomSize = rooms.count
                self.rooms = rooms
                for i in self.rooms{
                    self.loadSeats(roomID: i.roomID)
                }
            }
            
        }
    }
    func loadSeats(roomID: Int){
        seatAPI.getSeats(roomID: roomID) { seats in
            if let seats = seats{
                self.seatCount += 1
                self.seats[roomID] = seats
                if self.roomSize >= self.seatCount{
                    DispatchQueue.main.async {
                        for i in self.rooms{
                            self.data.append(i)
                            for j in self.seats[i.roomID] ?? []{
                                self.data.append(j)
                            }
                        }
                        self.tableView.reloadData()
                    }
                }
            }
        }
    }
    
   
    let rows = [8]
    
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return data.count
    }
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if data[indexPath.row] is Room{ //방정보
            let communityListCell = tableView.dequeueReusableCell(withIdentifier: "CommunityListCell") as! CommunityListTableViewCell
            
            communityListCell.l.text = (data[indexPath.row] as! Room).roomName
            
            return communityListCell
        } else{ //seat정보
            let communitySeatCell = tableView.dequeueReusableCell(withIdentifier: "CommunityListSeatCell") as! CommunityListSeatTableViewCell
            
            communitySeatCell.seatCellLabel.text = "Seat #\((data[indexPath.row] as! Seat).seatID)"
            return communitySeatCell
        }
        
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
//        let communityListView = self.presentingViewController
//        guard let communityView = communityListView as? CommunityViewController else { return }
//        communityView.roomID = (data[indexPath.row] as! Seat).roomID
//        communityView.seatID = (data[indexPath.row] as! Seat).seatID
        if data[indexPath.row] is Room{
            delegate?.sendID(roomID: (data[indexPath.row] as! Room).roomID, seatID: 0)
        }
        else{
            delegate?.sendID(roomID: (data[indexPath.row] as! Seat).roomID, seatID: (data[indexPath.row] as! Seat).seatID)
        }
        
        
        self.presentingViewController?.dismiss(animated: true)

    
    }
}
