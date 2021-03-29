# Use LinearLayout with the Layout Editor

## 뷰와 뷰그룹의 관계(Relationship between views and viewgroups)

![Alt text](1.UseLinearLayoutwiththeLayoutEditor/Relationshipbetweenviewsandviewgroups.png)

- View는 ViewGroup에 배치되며, ViewGroup조차도 뷰로 다루기때문에 위와같은 계층구조로 이루어질 수 있다.
- View의 종류는 다음과 같다.
![Alt text](1.UseLinearLayoutwiththeLayoutEditor/ViewClassInheritanceDiagram.PNG)

- View Group의 종류는 다음과 같다.(즉, ViewGroup은 모든 Layout과 레이아웃을 상속받고있는 뷰들을 말한다.)
![Alt text](1.UseLinearLayoutwiththeLayoutEditor/ViewGroupClassInheritanceDiagram.PNG)

## 리니어 레이아웃(LinearLayout)

- 단일 열에서 가로로 정렬 또는 단일 행에서 세로로 정렬하는 레이아웃이다.
- android:orientation = "vertical / horizontal"로 가로로 정렬할 것인지, 세로로 정렬할것인지 지정한다.
![Alt text](1.UseLinearLayoutwiththeLayoutEditor/LinearLayout.PNG)
