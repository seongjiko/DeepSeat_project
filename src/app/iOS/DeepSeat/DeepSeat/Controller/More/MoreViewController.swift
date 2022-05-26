//
//  MoreViewController.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/12.
//
//TODO: 닉네임 수정 버튼 클릭시 서버에 저장
//TODO: 애플리케이션 정보 리스트 구현
//TODO: 로그아웃

import UIKit

class MoreViewController: UIViewController {
    
    
    @IBOutlet weak var userView: UIView!
    @IBOutlet weak var applicationView: UITableView!
    @IBOutlet weak var nicknameLabel: UILabel!
    @IBOutlet weak var emailLabel: UILabel!
    
    var userAPI = UserAPI()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        userView.layer.cornerRadius = 10
        applicationView.layer.cornerRadius = 10
        setUser()
        
        applicationView.delegate = self
        applicationView.dataSource = self
        
    }
    func setUser(){
        userAPI.getUser(session: AppDelegate.session!, user: "nickname"){
            nickname in
            
            DispatchQueue.main.async{
                self.nicknameLabel.text = "\(nickname!)"
            }
        }
        userAPI.getUser(session: AppDelegate.session!, user: "userID"){
            userID in
            
            DispatchQueue.main.async{
                self.emailLabel.text = "\(userID!)"
            }
        }
        
    }
    
   //로그아웃 버튼 클릭시 이밴트 처리
    @IBAction func LogoutButtonPressed(_ sender: UIButton) {
        //서버에서 responseCode값을 가져와서 성공 code와 일치할 시 로그아웃 처리 되고 맨처음 로그인 페이지로 이동된다.
        userAPI.userLogout(session: AppDelegate.session!){
            responseCode in
            if responseCode == 200{
                DispatchQueue.main.async {
                    let vcName = self.storyboard?.instantiateViewController(withIdentifier: "RootViewController")
                    vcName?.modalPresentationStyle = .fullScreen
                    vcName?.modalTransitionStyle = .crossDissolve
                    self.present(vcName!, animated: true, completion: nil)
                }
                
            }
            //로그아웃 실패시 alert띄움
            else{
                 DispatchQueue.main.async{
                     self.alert(title: "Error", message: "로그아웃 실패")
                }
            }
        }
    }
    //닉네임 수정 버튼 클릭시 이벤트 처리
    @IBAction func renameButtonPressed(_ sender: UIButton) {
        var textField = UITextField()
        let alert = UIAlertController(title: "NICKNAME 수정", message: " ", preferredStyle: .alert)
        let actionADD = UIAlertAction(title: "OK", style: .default) { (actionADD) in
            self.userAPI.nicknameUpdate(session: AppDelegate.session!, nickname: textField.text!){
                data in
                if data == true{
                    self.userAPI.getUser(session: AppDelegate.session!, user: "nickname"){
                        nickname in
                        
                        DispatchQueue.main.async{
                            self.nicknameLabel.text = "\(nickname!)"
                        }
                    }
                } else{
                    DispatchQueue.main.async{
                        self.alert(title: "Error", message: "닉네임 수정 실패")
                   }
                }
            }
        }
        let actionCancel = UIAlertAction(title: "CANCEL", style: .default, handler: nil)
        
        alert.addAction(actionADD)
        alert.addAction(actionCancel)
        alert.addTextField { (field) in
            textField = field
            textField.placeholder = "닉네임을 입력하시오"
        }
        self.present(alert, animated: false)
    }
    
    
    
    
    func alert(title: String, message: String){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let action = UIAlertAction(title: "OK", style: .default, handler: nil)
        
        alert.addAction(action)
        
        self.present(alert, animated: true, completion: nil)
    }
    
}
extension MoreViewController:UITableViewDelegate,UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let applicationCell = applicationView.dequeueReusableCell(withIdentifier: "ApplicationCell", for: indexPath) as! ApplicationTableViewCell
        
        applicationCell.applicationText.text = LicenseDataSource.title[indexPath.row]
        
        return applicationCell
    }
    
    
}

class LicenseDataSource {
    static var title: [String] = ["DeepSeat","빌드 버전","오픈소스 라이선스","개인정보처리방침"]
}
