//
//  SeatViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/11.
//

import UIKit

class SeatViewController: UIViewController {
    
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var seatView: UIView!
    
    private var seatDrawView: Draw!
    
    let seatAPI = SeatAPI()
    var seats: [Seat]? = nil
    var status : [Int:Status]? = nil
    let path = UIBezierPath()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        scrollView.minimumZoomScale = 0.1
        scrollView.maximumZoomScale = 10.0
        scrollView.bounces = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        
        scrollView.delegate = self
        
        seatDrawView = Draw(frame: self.seatView.frame)
        seatDrawView.backgroundColor = .clear
        updateLoop()
        
        self.seatView.addSubview(seatDrawView)
    }
    
    
    @IBAction func NavigationButtonPressed(_ sender: UIBarButtonItem) {
        
        let vcName = self.storyboard?.instantiateViewController(withIdentifier: "RoomListViewController")
        self.present(vcName!, animated: true, completion: nil)
    }
    
    func updateLoop(){
        DispatchQueue.global(qos: .background).async {
            while true {
                
                self.updateData()
                sleep(10)
            }
        }
    }
    
    private func updateData() {
        seatAPI.getSeats(roomID: 1) { seats in
            self.seats = Array()
            if let seats = seats{
                self.seats = seats
                DispatchQueue.main.async {
                    self.setSeatDrawView()
                }
            }
        }
        seatAPI.getStatus(roomID: 1) { status in
            self.status = Dictionary()
            if let status = status{
                for s in status{
                    self.status![s.seatID] = s
                }
                
                DispatchQueue.main.async {
                    self.setSeatDrawView()
                }
            }
        }
    }
    
    private func setSeatDrawView() {
        if seats != nil && status != nil {
            self.seatDrawView.setData(seats: seats!, status: status!)
        }
    }
    
    
}
extension SeatViewController: UIScrollViewDelegate{
    func viewForZooming(in scrollView: UIScrollView) -> UIView? {
        self.seatView
    }
}




