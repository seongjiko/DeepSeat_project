//
//  ApplicationTableViewCell.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/17.
//

import UIKit

class ApplicationTableViewCell: UITableViewCell {

    @IBOutlet weak var applicationText: UILabel!
    @IBOutlet weak var applicationImage: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
