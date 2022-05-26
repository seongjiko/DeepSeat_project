//
//  CommunityViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/12.
//
//like comment 개수 출력 - ok
//like버튼 기능 구현
//swipe cell - ok
//write document - ok
//community list값에 다르게 리스트 출력 - ok
import UIKit

class CommunityViewController: UIViewController , SendIDDelegate{
    
    @IBOutlet weak var navigationButton: UIBarButtonItem!
    @IBOutlet weak var documentTableView: UITableView!
    @IBOutlet weak var addButton: UIBarButtonItem!
    
    let likedAPI = LikedAPI()
    let documentAPI = DocumentAPI()
    var documents: [Document] = []
    var docID: Int = 0
    var roomID: Int = 0
    var seatID: Int = 0
    
    var roomIdForPass: Int = 0
    var seatIDForPass: Int = 0
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        documentTableView.delegate = self
        documentTableView.dataSource = self
        
        loadData()
        
    }
    
    //    override func viewWillAppear(_ animated: Bool) {
    //        loadSelectData(roomID: roomIdForPass, seatID: seatIDForPass)
    //    }
    
    
    func loadData(){
        documentAPI.getAllDocument { documents in
            if let documents = documents{
                self.documents = documents
            }
            DispatchQueue.main.async {
                self.documentTableView.reloadData()
            }
        }
    }
    func loadSelectData(roomID: Int, seatID: Int){
        documentAPI.getSelectDocument(roomID: roomID, seatID: seatID) { documents in
            if let documents = documents{
                self.documents = documents
            }
            DispatchQueue.main.async {
                self.documentTableView.reloadData()
                
            }
        }
    }
    
    @IBAction func NavigationButtonPressed(_ sender: UIBarButtonItem) {
        
        guard let vcName = self.storyboard?.instantiateViewController(withIdentifier: "CommunityListViewController") as? CommunityListViewController else {return}
        vcName.delegate = self
        
        self.present(vcName, animated: true, completion: nil)
    }
    
    func sendID(roomID: Int, seatID: Int) {
        if seatID == 0{
            loadData()
        }
        else{
            roomIdForPass = roomID
            seatIDForPass = seatID
            loadSelectData(roomID: roomID, seatID: seatID)
        }
        
    }
    
    
    
    
    
    
    
    
}
extension CommunityViewController: UITableViewDataSource, UITableViewDelegate{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int  {
        
        return documents.count
    }
    // cell 구현하기
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let documentCell = documentTableView.dequeueReusableCell(withIdentifier: "DocumentCell", for: indexPath) as! DocumentTableViewCell
        documentCell.layer.cornerRadius = 10
        documentCell.document.text = documents[indexPath.row].content
        documentCell.writeInfo.text = "\(documents[indexPath.row].nickname) . \(documents[indexPath.row].wrote)"
        documentCell.roomLabel.text = documents[indexPath.row].roomName
        documentCell.seatLabel.text = "Seat #\(documents[indexPath.row].seatName)"
        documentCell.likeCount.text = "\(documents[indexPath.row].liked)"
        documentCell.commentCount.text = "\(documents[indexPath.row].comments)"
        
        likedAPI.getLiked(docID: documents[indexPath.row].docID, roomID: roomIdForPass, seatID:seatIDForPass, session: AppDelegate.session!) { count in
            if let count = count{
                documentCell.likeCount.text = "\(count)"
            }
        }
        
        //like버튼 클릭 이벤트 처리
        documentCell.likePressed = {[unowned self] in
            if documents[indexPath.row].iLiked == false{
                likedAPI.liked(docID: self.documents[indexPath.row].docID, session: AppDelegate.session!) { responseCode in
                    if responseCode == 200{
                        DispatchQueue.main.async {
                            self.loadData()
                        }
                    }
                }
            } else{
                likedAPI.unliked(docID: self.documents[indexPath.row].docID,roomID:self.roomIdForPass,seatID:self.seatIDForPass, session: AppDelegate.session!) { responseCode in
                    if responseCode == 200 {
                        DispatchQueue.main.async {
                            self.loadData()
                        }
                    }
                }
            }
            
        }
        if documents[indexPath.row].iLiked == true{
            documentCell.iLikedImage.tintColor = .darkred
            documentCell.iLikedButton.tintColor = .darkred
        } else{
            documentCell.iLikedImage.tintColor = .black
            documentCell.iLikedButton.tintColor = .black
        }
        
        return documentCell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        docID = documents[indexPath.row].docID
        performSegue(withIdentifier: "GoToComment", sender: self)
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 216
        
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "GoToComment"{
            let vc = segue.destination as! CommentViewController
            vc.docID = docID
        }
        else if segue.identifier == "GoToWriteDoc"{
            let vc = segue.destination as! WriteViewController
            vc.roomID = roomIdForPass
            vc.seatID = seatIDForPass
            vc.docID = docID
        }
        
    }
    
    
    
    //cell swipe
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        docID = documents[indexPath.row].docID
        let updateButton = UIContextualAction(style: .normal, title: "UPDATE") { (updateButton, view, completionHandler) in
            self.performSegue(withIdentifier: "GoToWriteDoc", sender: self)
            completionHandler(true)
        }
        let deleteButton = UIContextualAction(style: .normal, title: "Delete") { (deleteButton, view, completionHandler) in
            self.documentAPI.deleteDucument(roomID: self.roomIdForPass, seatID: self.seatIDForPass, docID: self.docID, session: AppDelegate.session!) { responseCode in
                if responseCode == 200{
                    self.loadSelectData(roomID: self.roomIdForPass, seatID: self.seatIDForPass)
                }
            }
            completionHandler(true)
            
        }
        deleteButton.backgroundColor = .darkred
        updateButton.backgroundColor = .indigo
        return UISwipeActionsConfiguration(actions: [deleteButton,updateButton])
    }
    
}

