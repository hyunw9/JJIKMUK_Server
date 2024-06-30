# Nutrition analysis & Food Recommendation Service
- 2024 Capstone Design Project 대상 수상

## 서비스 소개 
'찍먹' 서비스는 사용자의 목표에 맞게 체중 및 영양성분 관리를 제공하는 어플리케이션 서비스 입니다. 

## 주요 기능
- 사용자의 정보를 기반으로 목표에 맞는 영양소 추천
- 음식 사진 촬영을 통해 음식의 영양소 분석
- 사용자 섭취 영양 성분 트래킹
- 사용자 요구사항과 목표 영양성분을 종합하여 식단 추천
  
# Project Architecture 

![서버아키텍쳐](https://github.com/hyunw9/JJIKMUK_Server/assets/43662405/4e7e323b-5ab4-4541-a48d-f7550fe3b9a3)

# ER Diagram
![capstone_ERD](https://github.com/mjuCapstone/mjuCapstone_Server/assets/43662405/f54a1520-e781-4bcc-93c2-b80d0307ea4e)

# Tech Stack
- Spring boot
- Spring Security
- Json Web Token(JWT)
- MySQL
- Github
- Github Actions
- Docker
- AWS EC2
- AWS RDS
- FastAPI
- Gunicorn (WSGI)

## Member
|[박태우](https://github.com/ta-mj)|[강현욱](https://github.com/hyunw9)|[구지민](https://github.com/jimizip)|
|:---:|:---:|:---:|
|<img src="https://github.com/ta-mj.png" width="180" height="180" >|<img src="https://github.com/hyunw9.png" width="180" height="180" >|<img src="https://github.com/jimizip.png" width="180" height="180" >|
| **Team Leader** & <br> **프롬프트 튜닝** | **백엔드 및 AI 서버 개발**| **메뉴 데이터 개발** |

## 고민점 1
1. 기존에 존재하는 음식 데이터와 실제 추천받는 데이터와 불일치가 발생하는 문제 
메뉴 추천에 사용되는 DB와 사용자 기록 조회용 DB를 따로 구성하려고 하였으나, 
이미지를 받아와야 하고, 추천 받은 음식의 영양소와 기존 데이터에 존재하는 영양소의 불일치가 발생할 가능성이 많아집니다.

## 해결방법 1
데이터의 중복 관리, 영양소 불일치 관리, 추가적인 이미지 요청 등 불필요한 리소스가 지속적으로 발생할 수 있다고 생각했습니다. 
또한 기존 메뉴 데이터에 이름들이 불필요하게 세분화 되어있음을 확인하였습니다.
따라서 기존 메뉴 데이터에 불필요한 메뉴 명을 삭제하고, 이미지 링크 등 추가적인 데이터 전처리를 진행하였습니다.
전처리한 데이터를 이용해 추천과 메뉴기록 데이터를 통일하였습니다. 

하지만 위 방식을 사용하여도, OpenAI에서 추천받는 음식과는 불일치가 발생합니다. 따라서 OpenAI Assistant V2를 활용하여 기존 저장하는 메뉴를 학습시키고, file_search 기능을 활용하여 학습된 파일 내에서 정보를 추출해 원하는 형식으로 반환하도록 하였습니다. 

## 아쉬운 점 
1. Spring boot 서버에서는 RestTemplate로 Python 서버에 HTTP 프로토콜로 요청을 전송합니다. 
   하지만 RestTemplate 는 동기적으로 작동하기 때문에, 기존 추론 요청에 존재하는 병목 현상과 더불어 성능적으로 효율적이지 못하다고 생각했습니다. 비동기 적으로 요청을 처리하기 위해서 메세지큐, WebFlux 와 같은 비동기적인 방안을 고려할 수 있겠지만, 제한된 기간과 개발 비용을 감당하기 힘들다는 판단이 들었습니다. 
2. 또한 현재는 JWT와 Spring Security를 이용하여 인증 / 인가를 처리하고 있습니다. 다양한 소셜미디어가 활성화된 지금 시점에, 전통적인 ID/PW 방식의 로그인은 UX를 저해시킬 수 있다는 생각이 들었습니다. OAuth2 와 같은 추가적인 인증 방식을 채택할 수 있을 것 같습니다. 

