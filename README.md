#Vaio Hazard

Developed by **Yutaek Park**, **Juwoong Bae**

##계기
내 뒷자리가 정승진임.

##기획
###기본 게임 기획
* 플레이어는 **바이오 고객센터**의 사장. 
* 목표는 손님들을 속여서 **1년 내에 1억**을 모으는 것.

###플레이 요소
* 가게의 평판
* 유저들의 의심도
*

###고장난 문제들
|문제\부품|나사|서멀구리스|메인보드|CPU|램|SATA 케이블|액정|하드 디스크|배터리|
|:-----:|:-:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|
|**액정 문제**|O|X|△|X|X|X|O|X|X|
|**부팅 불량**|O|X|O|O|O|O|X|O|O|
|**전원 불량**|O|X|O|X|X|X|X|X|O|
|**LCD파손**|O|X|X|X|X|X|O|X|X|
|**기능불량**|O|O|O|O|O|O|X|O|O|
|**놋북청소**|O|X|X|X|X|X|X|X|X|
|**배터리교체**|X|X|X|X|X|X|X|X|O|

O : 무조건 쓰입니다 (2)
△ : 쓰일 수 있습니다 (1)
x : 안 쓰입니다. (0)

N개의 문제가 발생하면 최대 값은 2*N개. 
즉, **각 요인의 합 / 2N**으로 나누면 거짓말을 해도 걸리지 않을 확률이 됨.

##할일
* [x] 박유택 : 체크박스 다이얼로그
* [ ] 배주웅 : 뺑끼 알고리즘
* [x] 배주웅 : 플로우에서 바이오 기기 고장 생성 > 검증 > 결과 리턴 알고리즘 작성

##Conversation에 무엇을 포함하는가

```json
{
	"question" : "질문",
	"hogang" : "HIGH", //호갱도 퍼센트
	"doubt" : "LOW", //의심도 급간
}
```

##가격?
|부품|가격|
|:-:|:-:|
|모니터|500,000원|
|메인보드|1,100,000원|
|배터리|110,000원|
|상판|300,000원|
|하판|300,000원|
|먼지청소|20,000원|
|복구영역|25,000원|

너무 많이 나가면 새로 사세염
무상기간중 부품없으면 환불해드릴게염