# Android Kotlin Fundamentals: 03.3 Start an external Activity
### What you'll learn
1. bundle
2. Safe Args 
3. share menu
4. intent

## 1. bundle
Fragment 에서 다른 Fragment 로 데이터를 전달하는 한가지 방법 

- Type mismatch errors : 컴파일 때 발생하지 않음. 런타임에 발생 
- Missing key errors : bundle 에 없는 arguments 를 요청하는 경우 

## 2. Safe Args
컴파일 타임에 찾지 못하는 오류를 감지하는데 도움이 되는 코드와 클래스를 생성하는 gradle plugin 

```gradle
// Adding the apply plugin statement for safeargs
apply plugin: 'androidx.navigation.safeargs'

// Adding the safe-args dependency to the project Gradle file
dependencies {
   ...
classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"

}
```


 `NavDirection` 클래스 생성
 > 프로젝트가 컴파일 될 때마다 다시 생성되며, 편집 내용이 손실된다.

``` kotlin

// Adding the parameters to the Action -> 넘길때
view.findNavController()
.navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions, questionIndex))

// -> 받을 때 
val args = GameWonFragmentArgs.fromBundle(requireArguments())
Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()
``` 

## 3. share menu
`onCreateView()` 에서 다음 호출

```kotlin
setHasOptionsMenu(true)
```

## 4.  implicit intent
 - 다른 앱 실행을 원할 때 선언 
 - 여러 개 가능한 경우 chooser 를 보여줌 
 - intent : https://developer.android.com/reference/android/content/Intent



