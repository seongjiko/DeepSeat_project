# AI

1. AI 모델 실행 방법.
- yolov5 폴더를 다운 받아 실행시킵니다.
- 다른 폴더들은 주요 소스코드입니다.
``` python:
  #train , train.py 파일을 통해 학습, --data를 통해 경로, --epochs를 통해 학습 횟수 지정
  !python train.py --data "data/custom_dataset_plus_longtable.yaml" --epochs 150 #epoch 150회
  
  #test  , test.py 파일을 통해 결과 확인. --weights 파일을 통해 weight가 저장된 pt파일 지정
  # --source를 통해 test할 이미지 지정.
  python detect.py --weights "/home/nyh/yolov5/runs/train/exp3/weights/best.pt"  \
    --source "/home/nyh/yolov5/custom_dataset/test/images"
```
