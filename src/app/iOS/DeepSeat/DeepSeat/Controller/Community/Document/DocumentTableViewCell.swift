//
//  DocumentTableViewCell.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/16.
//

import UIKit

class DocumentTableViewCell: UITableViewCell {

    @IBOutlet weak var document: UILabel!
    @IBOutlet weak var writeInfo: UILabel!
    @IBOutlet weak var roomLabel: UILabel!
    @IBOutlet weak var seatLabel: UILabel!
    @IBOutlet weak var roomView: UIView!
    @IBOutlet weak var seatView: UIView!
    @IBOutlet weak var likeCount: UILabel!
    @IBOutlet weak var commentCount: UILabel!
    @IBOutlet weak var iLikedImage: UIImageView!
    @IBOutlet weak var iLikedButton: UIButton!
    
    
    let communityTableView = CommunityViewController().documentTableView
    
    var likePressed: (()->()) = {}
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        roomView.layer.cornerRadius = 10
        seatView.layer.cornerRadius = 10
        document.numberOfLines = 0
        //layoutSubviews()
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    override func layoutSubviews() {
        super.layoutSubviews()

        contentView.frame = contentView.frame.inset(by: UIEdgeInsets(top: 0, left: 0, bottom: 16, right: 0))
        contentView.backgroundColor = .white
        contentView.layer.cornerRadius = 10
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOpacity = 0.5
        layer.shadowRadius = 10
       }

    @IBAction func LikeButtonPressed(_ sender: UIButton) {
        likePressed()
    }
    
}
