# AI
- 다른 폴더들은 주요 소스코드를 경로에 맞게 저장하였습니다.
- 실행 결과를 확인하고 싶으시다면 yolov5 폴더를 다운 받아 실행시킵니다.

<p>
  <img src = "https://user-images.githubusercontent.com/38518648/170577833-d0bdf1df-5deb-48cc-9303-9845848b6e2b.png" width = "35%"/>
  <img src = "https://user-images.githubusercontent.com/38518648/170653277-0a6bd3af-7f8e-4ce2-a8dc-a2d3f63eff72.jpeg" width = "35%"/>
</p>

## Custom Model
### Custom data를 통한 Custom model 제작과정.
  - 촬영한 이미지 약 200장 + a
    - 이미지 한 장 당 7 ~ 8 개의 라벨이 들어가므로 모델 하나 당 1400개, 1500개 정도의 라벨링을 직접 하였습니다.
    - 저희는 DeepSeat은 `테이블 상태 탐지 모델` 과 `테이블 정보 탐지 모델` 두 가지를 제작하였습니다.
      - 사람이 등지고 앉는 경우 테이블 인식이 안되는 문제를 해결하기 위해  두가지로 분리.
      - `테이블 정보 제공 모델` = `테이블 상태 탐지 모델` + `테이블 정보 탐지 모델` 분리
    - 사용툴은 labeling이며 [해당 링크](https://github.com/tzutalin/labelImg) 참조하였습니다.
    - 라벨링 된 이미지를 첨부하려 하였으나 용량이 너무 커 라벨링 결과 파일인 txt 파일만 zip 파일로 첨부하였습니다.
      - train 폴더의 zip 혹은 7z 파일을 확인하시면 됩니다. 
      - yolov5/exp 폴더 안의 train_batch 파일들을 통해 라벨링 한 이미지를 보실 수 있습니다.
  - Data Argumentation
    - 밝기 +50 증가, -50 감소, Gaussian Noise, Gaussian Blur, GrayScale 을 사용하였습니다.
    - 원본 이미지의 2배 3배 정도를 Image Augmentation으로 처리하였습니다.
      - 총 학습에 사용된 이미지 수 = 모델 당 400장 ~ 500장 내외
      - train : 전체 이미지의 90%
      - valid : 전체 이미지의 10%
      - test  : 라즈베리파이에서 찍은 최신 사진 한 장을 test set으로 사용. 이미 사용된 test set은 valid 폴더로 옮겨겨서 사용
    - 영상처리 프로그래밍 수업에서 배운 내용을 활용하여 보았습니다.

### Custom Model Train & Test

``` python:
  #train , train.py 파일을 통해 학습, --data를 통해 경로, --epochs를 통해 학습 횟수 지정
  !python train.py --data "data/custom_dataset_plus_longtable.yaml" --epochs 150 #epoch 150회
  
  #test  , test.py 파일을 통해 결과 확인. --weights 파일을 통해 weight가 저장된 pt파일 지정
  # --source를 통해 test할 이미지 지정.
  python detect.py --weights "/home/nyh/yolov5/runs/train/exp3/weights/best.pt"  \
    --source "/home/nyh/yolov5/custom_dataset/test/images"
```

### Custom Model 과 Server
- Custom Model 중 `테이블 정보 탐지 모델` 을 통해 나온 테이블 정보 PerspectiveTransform을 통해 BirdEye View로 변환합니다.
- BirdEye View 로 변환된 이미지에서 cv2.warpPerspectiveTransform() 변환 공식을 통해 테이블 좌표만 변환시킵니다.
- 반환 된 결과를 서버 혹은 pickle 파일을 서버에 저장합니다. (일정 시간마다 한번씩 실행)
  - 서버에서 결과를 받아오는 경우 동기화 지연이 발생하는 문제가 발생할 우려가 있어 pickle 파일을 사용하였습니다 
- `테이블 정보 탐지 모델`과 `테이블 상태 탐지 모델`을 좌표에 따른 seatID로 매칭합니다.
- `테이블 정보 탐지 모델`은 위와 같은 과정을 거친 후 서버에 저장됩니다. 
- 이 후 `테이블 상태 탐지 모델`의 결과를 실시간으로 서버에 전달하여 사용자에게 전달이 가능하게 합니다.

### BirdEye View를 이용한 좌석 정보 변환.
- 아래 사진과 같이 위에서 새가 본 것 같은 느낌을 주는 것처럼 변환하는 방법.

![image](https://user-images.githubusercontent.com/38518648/170578016-c379e4ba-6710-48fb-9d28-dc843332d443.png)

- 변환 각도 및 변환 결과

![image](https://user-images.githubusercontent.com/38518648/170578595-c9a3c0c3-4198-4584-8f3c-d938b487dd6d.png)
![image](https://user-images.githubusercontent.com/38518648/170578149-2d7a016c-c1e3-4f70-8ac6-1b3600d0bb88.png)

- 테이블의 중심좌표를 추출하여 시각화. 
 - 중간과정에서 테이블 마다 보정을 해주어 더 깔끔하게 출력

<img width = "30%" src = "https://user-images.githubusercontent.com/38518648/170579135-ebbb5458-ed5a-4188-869e-c0f6e2cb0ed9.png"/> 
<img width = "30%" src = "https://user-images.githubusercontent.com/38518648/170580599-5cff7641-53c8-48eb-b74c-1a800ce344af.png"/> 





