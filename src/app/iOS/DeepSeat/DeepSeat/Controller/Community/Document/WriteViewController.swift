//
//  WriteViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/21.
//

import UIKit

class WriteViewController: UIViewController,UITextFieldDelegate {

    @IBOutlet weak var writeTV: UITextView!
    
    var roomID: Int = 0
    var seatID: Int = 0
    var docID: Int = 0
    
    let documentAPI = DocumentAPI()
    
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }
    override func viewWillAppear(_ animated: Bool) {
        self.writeTV.becomeFirstResponder()
    }

    @IBAction func DoneButtonPressed(_ sender: UIBarButtonItem) {
        //TODO: 서버에 post
        if docID == 0{
            documentAPI.writeDocument(roomID: roomID, seatID: seatID, session: AppDelegate.session!,content: writeTV.text) { responseCode in
                if responseCode == 200{
                    DispatchQueue.main.async {
                        self.navigationController?.popToRootViewController(animated: true)
                    }
                }
                    
                else { print("error")}
            }
        } else{
            
            documentAPI.updateDocument(roomID: roomID, seatID: seatID,docID: docID, session: AppDelegate.session!, content: writeTV.text) { responseCode in
                if responseCode == 200{
                    DispatchQueue.main.async {
                        self.navigationController?.popToRootViewController(animated: true)
                    }
                } else {print("error")}
            }
        }
        
    }
    

}
