//
//  LoginViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/11.
//

import UIKit

class LoginViewController: UIViewController {

    @IBOutlet weak var loginIDTF: UITextField!
    @IBOutlet weak var loginPWTF: UITextField!
    var userAPI = UserAPI()
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    @IBAction func LoginButtonPressed(_ sender: UIButton) {
        //사용자가 입력한 값 API로 넘기기
        if let loginID = loginIDTF.text, let loginPW = loginPWTF.text{
            userAPI.userLogin(userID: loginID, userPW: loginPW){
                sessionID in
                if let sessionID = sessionID{
                    AppDelegate.session = sessionID
                    DispatchQueue.main.async {
                        let vcName = self.storyboard?.instantiateViewController(withIdentifier: "RootTabBarViewController")
                        vcName?.modalPresentationStyle = .fullScreen
                        vcName?.modalTransitionStyle = .crossDissolve
                        self.present(vcName!, animated: true, completion: nil)
                    }
                    
                } else{
                    //로그인 오류 다이얼로그
                    DispatchQueue.main.async {
                        let alert = UIAlertController(title: "Error", message: "로그인 오류", preferredStyle: .alert)
                        let action = UIAlertAction(title: "OK", style: .default, handler: nil)
                        
                        alert.addAction(action)
                        
                        self.present(alert, animated: true, completion: nil)
                    }
                    
                }
            }
        }
        
    }
    
    
}
