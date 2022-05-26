//
//  Draw.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/26.
//

import Foundation
import UIKit
class Draw: UIView{
    
    var shapeLayers: [CAShapeLayer] = [CAShapeLayer]()
    var statusList: Status?
    func clearLayer() {
        for layer in shapeLayers {
            layer.removeFromSuperlayer()
        }
        shapeLayers.removeAll()
    }
    
    func setData(seats: [Seat], status: [Int: Status]) {
        clearLayer()
        
        for s in seats {
            
            let layer = CAShapeLayer()
            
            let path = UIBezierPath(rect: CGRect(x: s.x - (s.width)/2, y: s.y - (s.height)/2, width: s.width, height: s.height))
            
            layer.path = path.cgPath
            
            switch status[s.seatID]?.state {
            case 1:
                //자리 사용중
                layer.fillColor = UIColor.using?.cgColor
                break
                
            case 2:
                //잠깐 비움
                layer.fillColor = UIColor.absent?.cgColor
                break
                
            case 3:
                //오래 비움
                layer.fillColor = UIColor.long?.cgColor
                break
                
            default:
                //자리 empty
                layer.fillColor = UIColor.empty?.cgColor
                break
            }
            //layer.fillColor = UIColor.red.cgColor
            
            shapeLayers.append(layer)
            
            self.layer.addSublayer(layer)
            
            
        }
    }
    func setColor(_ status: [Status]){
        for status in status{
            self.statusList = status
        }
    }
}
