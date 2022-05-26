//
//  CommentViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/17.
//
//docment출력 하면 완료

import UIKit

class CommentViewController: UIViewController {

    @IBOutlet weak var documentView: UIView!
    @IBOutlet weak var commentTableView: UITableView!
    @IBOutlet weak var sendCommentView: UIView!
    @IBOutlet weak var sendButton: UIButton!
    @IBOutlet weak var sendTF: UITextField!
    
    @IBOutlet weak var nickname: UILabel!
    @IBOutlet weak var documentWrote: UILabel!
    @IBOutlet weak var documentTV: UITextView!
    @IBOutlet weak var likedCount: UILabel!
    @IBOutlet weak var commentCount: UILabel!
    
    @IBOutlet weak var fieldConstraint: NSLayoutConstraint!
    
    let commentAPI = CommentAPI()
    let documentAPI = DocumentAPI()
    var comments: [Comment] = []
    var document: [Doc] = []
    var commentID: Int = 0
    
    var docID: Int = 0
    let communityTableView = CommunityViewController().documentTableView
    
    override func viewDidLoad() {
        super.viewDidLoad()
        documentView.layer.cornerRadius = 10
        commentTableView.delegate = self
        commentTableView.dataSource = self
        sendCommentView.layer.cornerRadius = 10
        sendButton.layer.cornerRadius = 10
        sendButton.titleLabel?.textColor = .black
        loadDocument()
        loadComments()
        
        
        
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillAppear(notification: )), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillDisappear(notification:)), name: UIResponder.keyboardWillHideNotification, object: nil)

        
    }
    func loadDocument(){
        documentAPI.getDocument(docID: docID,info: "nickname") { document in
            if let document = document{
                DispatchQueue.main.async {
                    self.nickname.text = "\(document)"
                }
            }
        }
        documentAPI.getDocument(docID: docID,info: "wrote") { document in
            if let document = document{
                DispatchQueue.main.async {
                    self.documentWrote.text = "\(document)"
                }
            }
        }
        documentAPI.getDocument(docID: docID,info: "content") { document in
            if let document = document{
                DispatchQueue.main.async {
                    self.documentTV.text = "\(document)"
                }
            }
        }
        documentAPI.getDocument(docID: docID,info: "liked") { document in
            if let document = document{
                DispatchQueue.main.async {
                    self.likedCount.text = "\(document)"
                }
            }
        }
        documentAPI.getDocument(docID: docID,info: "comments") { document in
            if let document = document{
                DispatchQueue.main.async {
                    self.commentCount.text = "\(document)"
                }
            }
        }
    }
    
    func loadComments(){
        commentAPI.getComments(docID: docID) { comments in
            if let comments = comments{
                self.comments = comments
            }
            DispatchQueue.main.async {
                self.commentTableView.reloadData()
            }
        }
    }
    
    
    @objc
      private func keyboardWillAppear(notification: NSNotification) {
          guard let keyboardFrame = notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue else {
              return
          }
          
          let keyboardHeight: CGFloat
          if #available(iOS 11.0, *) {
              keyboardHeight = keyboardFrame.cgRectValue.height - self.view.safeAreaInsets.bottom
          } else {
              keyboardHeight = keyboardFrame.cgRectValue.height
          }
          fieldConstraint.constant = keyboardHeight + 14
          
          
      }
      
      @objc
      private func keyboardWillDisappear(notification: NSNotification?) {
          fieldConstraint.constant = 14
          
      }
    
    @IBAction func sendButtonPressed(_ sender: UIButton) {
        if let sendText = sendTF.text{
            commentAPI.writeComments(docID: docID, comment: sendText, session: AppDelegate.session!) { responseCode in
                if responseCode == 200{
                    self.loadComments()
                }
            }
        }
        sendTF.text = ""
    }
    


}
extension CommentViewController: UITableViewDataSource, UITableViewDelegate{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int  {
        return comments.count
    }
    // cell 구현하기
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let commentCell = commentTableView.dequeueReusableCell(withIdentifier: "CommentCell", for: indexPath) as! CommentTableViewCell
        commentCell.layer.cornerRadius = 10
        commentCell.comment.text = comments[indexPath.row].content
        commentCell.nickname.text = comments[indexPath.row].userID
        commentCell.commentWrote.text = comments[indexPath.row].wrote

        
        
        return commentCell
    }
    //셀 높이 조절 constraint 설정하고 다시 동적으로 바꿀것
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
           return 135
        
    }
    
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        commentID = comments[indexPath.row].commentID
        let deleteCM = UIContextualAction(style: .normal, title: "delete") { (actionUpdate, view, completionHandler) in
            self.commentAPI.deleteComment(commnetID: self.commentID, session: AppDelegate.session!) { responseCode in
                if responseCode == 200{
                    self.loadComments()
                }
            }
            completionHandler(true)

        }
        deleteCM.backgroundColor = .darkred
        return UISwipeActionsConfiguration(actions: [deleteCM])
    }

    
}
