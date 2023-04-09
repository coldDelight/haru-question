# HARU Q (하루 질문) : 질문 다이어리

![Group 242](https://user-images.githubusercontent.com/94586184/230772869-10666a35-d9cd-4246-9a09-58aa45499138.png)
```
❓  하루에 한 개씩 질문을 받아 답변하며 본인을 찾아가는 질문 다이어리 앱
```
- 프로젝트 기간 : `2022.08` ~ `2022.10`
- 출시일 : `2022.10.01`

  <a href="https://play.google.com/store/apps/details?id=com.colddelight.haru_question"><img src="https://img.shields.io/badge/Goolge Play-414141?style=flat&logo=googleplay&logoColor=white&link=https://play.google.com/store/apps/details?id=com.colddelight.haru_question"/></a>   <a href="https://strong-marlin-f95.notion.site/SRS-a1c9777dfa4b485195dafb0dcc8d7d9d"><img src="https://img.shields.io/badge/SRS-414141?style=flat&logo=readthedocs&logoColor=white&link=https://strong-marlin-f95.notion.site/SRS-a1c9777dfa4b485195dafb0dcc8d7d9d"/></a>
  
# :one: Screenshots

| ![sc_12](https://user-images.githubusercontent.com/94586184/230772664-7a97f9d5-26e3-482f-ba4b-05366f0648e4.png)| ![sc_1](https://user-images.githubusercontent.com/94586184/230772672-9bd7733b-7f96-40ee-bd1c-a85109b55852.png)|![sc_2](https://user-images.githubusercontent.com/94586184/230772676-0b55067a-52b4-4b05-8a05-3036e91ec8fb.png) | ![sc_6](https://user-images.githubusercontent.com/94586184/230772685-073df27c-0d9b-417d-8f6c-157100ab1320.png)|![sc_3](https://user-images.githubusercontent.com/94586184/230772750-bbeef7db-2c87-4de7-97db-dafacf385d24.png) |
|-|-|-|-|-|
| ![sc_5](https://user-images.githubusercontent.com/94586184/230772785-c817f468-1457-4158-9075-82ab47cade2d.png) | ![sc_7](https://user-images.githubusercontent.com/94586184/230772797-9a5da01c-a981-4209-a379-4a556eff4b99.png) |![sc_8](https://user-images.githubusercontent.com/94586184/230772813-e6301b9b-5ca1-40ab-9e0f-f6f6a8d233da.png) |![sc_9](https://user-images.githubusercontent.com/94586184/230772829-7ec44617-037b-4f46-8b8e-400908203447.png)| ![sc_11](https://user-images.githubusercontent.com/94586184/230772836-f486b8a7-fdad-439b-aeeb-3581748f4fa9.png)| 

# :two: Skill
| Name | Version |
| --- | --- |
| ```Android``` |  | 
| ```Kotlin``` | *```1.7.10```* | 
| ```Min SDK``` | *```26```* | 
| ```Target SDK```| *```32```* | 
| ```MVVM``` |  |
| ```Clean Architecture```  |   |
| ```Coroutines``` | *```1.6.4```* |
| ```Dagger-Hilt``` | *```2.43.2```* |
| ```Data Binding``` | *```3.5```* |
| ```Navigation``` | *```2.5.1```* |
| ```Drawerlayout``` | *```1.1.1```* | 
| ```Room``` | *```2.4.3```* |
| ```BillingClient``` | *```5.0```* |
| ```Lottie``` | *```5.2.0```* | 
| ```Ballon``` | *```1.4.7```* |
| ```git flow```  |   |


# :three: Structure
```
📦 haru_question
 ┣ 📂 app
 ┃ ┗ 📜 haru_db.db
 ┃ ┗ 📂 di
 ┃ ┗ 📂 presentation
 ┃ ┃ ┣ 📂 adapter
 ┃ ┃ ┣ 📂 viewmodel
 ┣ 📂 data
 ┃ ┗ 📂 local
 ┃ ┃ ┣ 📂 dao
 ┃ ┃ ┣ 📂 entity
 ┃ ┃ ┣ 📂 model
 ┃ ┃ ┣ 📜 HaruDatabase.kt
 ┃ ┃ ┣ 📜 Prefs.kt
 ┃ ┗ 📂 repository
 ┣ 📂 domin
 ┃ ┗ 📂 model
 ┃ ┗ 📂 repository
 ┃ ┗ 📂 use_case
```
# :four: Feature
- **하루 질문**
    - 하루에 하나씩 인생을 돌아보는 **```질문```** 을 받는다.
    - 질문과 관련된 **```글귀```** 를 받는다.
    - 사용자가 답변을 하면 하루모음에 **```추가```** 된다.
- **하루 모음**
    - 사용자가 답변한 **```질문내역 목록```** 을 조회 가능하다.
    - 하나의 질문을 선택하면 상세정보가 나온다.
    - 상세정보를 스크린샷으로 **```공유```** 와 **```다운로드```** 가 가능하다.
- **하루 고민**
    - 사용자가 마음속으로 고민을 생각하고 화면을 터치하면 무작위로 **```답변```** 이 나온다.
    - 다시하기 버튼을 통해 다시 답변을 받는게 가능하다.
- **앱 상세**
    - 앱 평가하기를 통해 플레이 스토어로 이동하여 **```리뷰```** 가 가능하다.
    - 후원하기를 통해 **```후원```** 이 가능하다.
    
    
    
# :five: Etc



<details>
<summary> 스토어실적 </summary>
2023.04.09 기준 스토어 실적

![image](https://user-images.githubusercontent.com/94586184/230775751-8d982c34-07a4-4d46-b2f6-f797ebf6d300.png)

</details>

