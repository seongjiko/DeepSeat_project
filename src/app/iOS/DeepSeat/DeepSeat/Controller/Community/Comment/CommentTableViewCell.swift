//
//  CommentTableViewCell.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/17.
//

import UIKit

class CommentTableViewCell: UITableViewCell {
    
    @IBOutlet weak var nickname: UILabel!
    @IBOutlet weak var commentWrote: UILabel!
    @IBOutlet weak var commentLikeCount: UILabel!
    @IBOutlet weak var comment: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
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
       }


}
