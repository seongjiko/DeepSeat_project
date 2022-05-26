//
//  RegisterViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/11.
//
//TODO: 등록 성공시 확인 버튼 누르면 로그인 페이지로 넘어가기
//TODO: 아이디, 닉네임 중복 확인
//TODO: 모든 조건 true일시 등록 성공 조건 걸기

import UIKit

class RegisterViewController: UIViewController {

    @IBOutlet weak var registerIdTF: UITextField!
    @IBOutlet weak var registerPwTF: UITextField!
    @IBOutlet weak var checkPwTF: UITextField!
    @IBOutlet weak var checkPwLabel: UILabel!
    @IBOutlet weak var nicknameTF: UITextField!
    var registerAPI = RegisterAPI()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.checkPwTF.addTarget(self, action: #selector(self.textFieldDidChange(_:)), for: .editingChanged)
    }
    

    

    @objc func textFieldDidChange(_ sender: Any?) {
        if let registerPW = registerPwTF.text, let chechPW = checkPwTF.text{
            if registerPW == chechPW{
                self.checkPwLabel.text = "패스워드가 일치합니다."
                self.checkPwLabel.textColor = .indigo
            } else{
                self.checkPwLabel.text = "패스워드가 일치하지 않습니다."
                self.checkPwLabel.textColor = .darkred
            }
        }
    }
    
    @IBAction func RegisterButtonPressed(_ sender: UIButton) {
        if let registerID = registerIdTF.text, let registerPW = registerPwTF.text, let checkPW = checkPwTF.text, let nickname = nicknameTF.text{
                registerAPI.userRegister(userID: registerID, userPW: registerPW, userPWCheck: checkPW, nickname: nickname, email: registerID){
                responseCode in
                if responseCode == 200{
                    DispatchQueue.main.async{
                        let alert = UIAlertController(title: "Success", message: "회원가입에 성공하셨습니다. 로그인을 시도해 주세요.", preferredStyle: .alert)
                        let action = UIAlertAction(title: "OK", style: .default) { (action) in
                            self.navigationController?.popToRootViewController(animated: true)
                        }
                        alert.addAction(action)
                        self.present(alert, animated: false, completion: nil)
                        
                    }
                } else{
                    
                    DispatchQueue.main.async{
                        self.alert(title: "Error", message: "회원가입에 실패하였습니다.")
                    }
                }
            }
               
        }
    }
    
    //사용자 중복확인
    @IBAction func nicknameCheckButtonPressed(_ sender: UIButton) {
        if let nickname = nicknameTF.text{
            registerAPI.checkNickname(nickname: nickname) { data in
                if data == false{
                    DispatchQueue.main.async {
                        self.alert(title: "Error", message: "이미 사용중인 아이디 입니다.")
                    }
                } else{
                    DispatchQueue.main.async {
                        self.alert(title: "Success", message: "사용 가능한 ID입니다.")
                    }
                }
            }
        }
    }
    
    @IBAction func IdCheckButtonPressed(_ sender: UIButton) {
        if let registerID = registerIdTF.text{
            registerAPI.checkID(userID: registerID) { data in
                if data == false{
                    DispatchQueue.main.async {
                        self.alert(title: "Error", message: "이미 사용중인 아이디 입니다.")
                    }
                } else{
                    DispatchQueue.main.async {
                        self.alert(title: "Success", message: "사용 가능한 ID입니다.")
                    }
                }
            }
        }
        
        
    }
    
    func alert(title: String, message: String){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let action = UIAlertAction(title: "OK", style: .default, handler: nil)
        
        alert.addAction(action)
        
        self.present(alert, animated: true, completion: nil)
    }
    
}
extension UIColor{
    class var darkred: UIColor? { return UIColor(named: "darkred") }
    class var indigo: UIColor? { return UIColor(named: "indigo") }
    class var empty: UIColor? { return UIColor(named: "empty") }
    class var absent: UIColor? { return UIColor(named: "absent") }
    class var long: UIColor? { return UIColor(named: "long") }
    class var using: UIColor? { return UIColor(named: "using") }
}

