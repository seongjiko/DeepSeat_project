//
//  RoomListViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/14.
//

import UIKit

class RoomListViewController: UITableViewController {
    var rooms: [Room] = []
    let roomAPI = RoomAPI()

    override func viewDidLoad() {
        super.viewDidLoad()

        if let presentationController = presentationController as? UISheetPresentationController {
            presentationController.detents = [.medium()]
                // grabber 속성 추가
            presentationController.prefersGrabberVisible = true
            presentationController.prefersScrollingExpandsWhenScrolledToEdge = false
            presentationController.largestUndimmedDetentIdentifier = .medium
        }
        
        loadRooms()
    }

    func loadRooms(){
        roomAPI.getRooms { rooms in
            if let rooms = rooms{
                self.rooms = rooms
            }
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
            
        }
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        return rooms.count
    }
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let roomCell = tableView.dequeueReusableCell(withIdentifier: "RoomCell") as! RoomTableViewCell
        roomCell.roomLabel.text = rooms[indexPath.row].roomName

        return roomCell
    }

}
