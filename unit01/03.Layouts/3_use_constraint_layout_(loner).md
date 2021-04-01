# 소개.컨스트레이아웃 

특징 -> 뷰 사이에 제약을 걸어서 위치를 정함 제약에 따라 유연한 UI 작업이 가능함       
![Alt text](3_use_constraint_layout_(loner)_res/2.png)
컨스트레이아웃의 장점 -> 많은 상위 뷰 그룹을 만들 필요가 없어서 작업 속도가 빠르고, 
여러차례 부모 뷰계층을 만들 필요가 없어서 UI 처리속도도 빠르며, 
다른 사람과 협업할 때 XML을 쉽게 파악할 수 있기도 하다.  

[ConstraintLayout의 성능 이점 이해]
(https://android-developers.googleblog.com/2017/08/understanding-performance-benefits-of.html)

# 컨스트 레이아웃 사용법 

## 제약
![Alt text](3_use_constraint_layout_(loner)_res/1.png)
 
1.수평 제약 : B는 A의 오른쪽에 유지되도록 제약됩니다. (완성 된 앱에서 B는이 수평 제약에 추가로 하나 이상의 수직 제약이 필요합니다.)
2.수직 제약 : C는 A 미만으로 유지되도록 제약됩니다. (완성 된 앱에서 C는이 수직 제약에 추가로 하나 이상의 수평 제약이 필요합니다.)

## 뷰 인스펙터 응용 하는 법 
![Alt text](3_use_constraint_layout_(loner)_res/3.png)

뷰 인스펙터에는 제약, 제약 유형, 제약 바이어스 및 뷰 여백과 같은 레이아웃 속성에 대한 컨트롤이 포함되어 있습니다.

1.바이어스 
![Alt text](3_use_constraint_layout_(loner)_res/4.png) 

이 슬라이드를 이용하여 제약 간 어느쪽으로 위치를 기울일 것인지 설정 가능합니다.
(기본적으로 50이 default)

2. 제약간의 마진
![Alt text](3_use_constraint_layout_(loner)_res/5.png) 

뷰 제약 사이의 마진을 넣을 수 있습니다. 

3. 제약간의 여백 조정 및 제약 조건 변경
 * 컨텐츠 줄 바꿈:![Alt text](3_use_constraint_layout_(loner)_res/6.png) 뷰가 컨텐츠를 포함하는데 필요한 만큼만 확장합니다.
 * 고정: ![Alt text](3_use_constraint_layout_(loner)_res/7.png) 고정 제약 화살표 옆에있는 텍스트 상자에서 치수를 뷰 여백으로 지정할 수 있습니다.
 * Match Constraints: ![Alt text](3_use_constraint_layout_(loner)_res/8.png) 뷰의 자체 여백을 고려한 후 각 측면의 제약 조건을 충족하도록 뷰가 최대한 확장됩니다.
 
사용 예시: 

![Alt text](3_use_constraint_layout_(loner)_res/9.png)

![Alt text](3_use_constraint_layout_(loner)_res/10.png)

![Alt text](3_use_constraint_layout_(loner)_res/11.png)
 
4. 그외 사용 팁
![Alt text](3_use_constraint_layout_(loner)_res/12.png)

직접 마우스나 코드로 제약을 설정하지 않아도
 + 버튼 을 누르면 가까운 상위 뷰를 잡아서 자동으로 제약을 걸어줍니다.

## 체인
![Alt text](3_use_constraint_layout_(loner)_res/13.png) 

체인은 뷰와 뷰사이의 양방향 제약이 걸린 상황 입니다.

1. 체인의 머리 
![Alt text](3_use_constraint_layout_(loner)_res/14.png)

체인은 체인의 머리를 기준으로 모든 뷰를 제어 및 배치 및 배포 합니다.

수직일 경우 가장 왼쪽이 HEAD 
수평일 경우 가장 위쪽이 HEAD 


2. 체인의 스타일

* 스프레드: 기본적인 스타일 입니다. 여백이 고려하여 사용 가능한 공간에 뷰가 균등하게 분산됩니다.
![Alt text](3_use_constraint_layout_(loner)_res/15.png)

* 스프레드 인사이드: 첫 번째 와 마지막 뷰가 체인의 각 끝에 있는 부모에 연결됩니다. 나머지 뷰는 균등하게 분산됩니다.
![Alt text](3_use_constraint_layout_(loner)_res/16.png)

* 패킹 됨: 뷰가 함께 묶입니다. 그런 다음 체인 헤드의 바이어스를 변경하여 전체 체인의 위치를​조정할 수 있습니다.
![Alt text](3_use_constraint_layout_(loner)_res/17.png)

* 가중치: layout_constraintHorizontal_weight 또는 layout_constraintVertical_weight 속성에 
설정된 값에 따라 모든 공간을 채우도록보기의 크기가 조정됩니다.

![Alt text](3_use_constraint_layout_(loner)_res/18.png)


## 베이스 라인
![Alt text](3_use_constraint_layout_(loner)_res/19.png)

베이스 라인 제약 조건은 뷰의 텍스트를 기준으로 다른 뷰의 텍스트와 정렬합니다.

![Alt text](3_use_constraint_layout_(loner)_res/20.png)

**현재 안스 4.1.2 기준으로 XML 의 디자인 화면에서 기준으로 잡는 뷰를 오른쪽 클릭 후 SHOW 버튼을 눌러야 초록색 버튼이 활성화가 됬습니다.
.BY LONER**


#이 질문에 답하십시오

질문 1
컨스트레이아웃 에서 다음 중 런타임 중에도 VIEW를 제자리에 유지하는 데 필요한 제약 조건을 설명하는 것은 무엇입니까?

1) 두 개의 수평 제약.
2) 하나의 수직 제약.
3) 하나 이상의 수평 및 수직 제약.
4) 뷰에 제약을 걸 필요가 없습니다.

질문 2
다음 제약 유형 중 콘텐츠에 맞게 필요한만큼만 뷰를 확장하는 것은 무엇입니까?

1) 내용 감싸기
2) 매치 제약
3) 고정 제약
4) 기준선 제약

질문 3
베이스라인 제약 조건은 뷰의 텍스트 기준으로 다른 뷰의 텍스트와 연결하여 라인을 맞춥니다. 정답은?

1) 맞습니다.
2) 사실이 아닙니다.

질문 4
뷰 인스펙터는 _______의 뷰에서만 사용할 수 있습니다.

1) ConstraintLayout
2) ConstraintLayout 또는 LinearLayout
3) 모든 ViewGroup
4) LinearLayout

질문 5
체인은 ______ 이 걸린 뷰의 그룹이다.  ____에 들어갈 말을 맞추세요

1) 상하 제약
2) 양방향 제약
3) 오른쪽 및 왼쪽 제약
4) 기준선 제약

  



