# Badge 10-2. Material Design

# Introduce abuot Mateial Design

**Mateial Design**은 **Google의 크로스 플랫폼 디자인 시스템이며 Andoid 용 디자인 시스템**입니다. Material Design은 텍스트 표시 방법부터 화면 레이아웃 방법에 이르기까지 앱의 사용자 인터페이스(UI)에 있는 모든 것에 대한 세부 사양을 제공합니다.

세부 사항 살펴보기 👉  [material.io](https://material.io/)

[material.io](http://material.io) 에서는 Material Design 구성요소와 함께 제공되는 별도의 View들이 있습니다. 그 View 뷰 중에서는 **Bottom navigatin, Floating Action Button(FAB), collapsing toolbar** 등이 있습니다. 

그 외에도 **MaterialComponents** 라고 하는 테마도 제공하는데, 컴포넌트 컨트롤에 대한 테마를 사용자 정의할 수도 있습니다. 

# Task1. FAB(Floating Action Button)

이번 작업에서는 FAB을 프로젝트에 추가해봅시다.  FAB는 

![resources/Untitled.png](resources/Untitled.png)

이렇게 생긴 큰 둥근 버튼입니다. 이름에서 의미하는 Floating 의 의미와 같이 다른 모든 콘텐츠 위에 떠 있습니다. 

![resources/Untitled%201.png](resources/Untitled%201.png)

FAB을 띄우기 위한 Start앱은 [GDGFinderStyle](https://github.com/google-developer-training/android-kotlin-fundamentals-apps/tree/master/GDGFinderStyles) 앱입니다. 

FAB 를 띄우기 전에 Start앱에서 다음과 같은 에러가 발생하니 해결하고 시작하겠습니다. 

```kotlin
2021-04-18 13:47:19.989 7468-7468/com.example.android.gdgfinder E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.example.android.gdgfinder, PID: 7468
    java.lang.RuntimeException: Canvas: trying to draw too large(169590960bytes) bitmap.
```

try to draw too large bitmap 에 관련된 에러인데, ImageView에서 그냥 이미지를 불러와서 보여주기에는 너무 큰 이미지를 불러올 때 발생합니다. 실제 프로젝트를 보니 몇몇의 파일들이 사이즈가 크더군요. 실제 이 에러를 처리하는 방식에는  Bitmap 을 불러오기 전에 사이즈를 정해줘서 처리하는 레거시 방법도 있겠지만, 우리는 이 방법을 훌륭하게 처리해줄 라이브러리들을 많이 알고 있습니다. 

그 중 하나인 Glide 를 사용해보도록 하겠습니다. 왜냐하면 프로젝트에 이미 implementation 되어 있기 때문이죠 😎

- **build.gradle(app)**

```kotlin
dependencies {
	// Glide
	implementation "com.github.bumptech.glide:glide:$version_glide"
}
```

- 처리할 이미지

![resources/Untitled%202.png](resources/Untitled%202.png)

이런 대부분의 이미지들이 상당히 큰 이미지들이군요 😢

databinding을 통해서 작업하도록 BindingAdapter를 하나 생성합시다 

```kotlin
object BindingAdapters{

    @JvmStatic
    @BindingAdapter("hugeBitmap", "resize")
    fun setHugeBitmap(view : ImageView, resId : Drawable, resize: Point = Point(100, 100))
    {
        val options : RequestOptions = RequestOptions().override(resize.x, resize.y)
        Glide.with(view.context)
            .load(resId)
            .apply(options)
            .into(view)
    }

    @JvmStatic
    fun createPoint(x : Int, y : Int) : Point {
        return Point(x, y)
    }

}
```

각 뷰의 크기가 다르므로 resize 도 하나 넣었습니다. 여기에서 Point 객체가 들어가는데, xml에서는 객체를 생성할 수 없으니 생성해주는 녀석을 만듭니다. 

```xml
<data>
    <import type="com.example.android.gdgfinder.utils.BindingAdapters"/>
</data>
<ImageView
        android:id="@+id/hero_image"
        android:layout_width="150dp"
        android:layout_height="match_parent"
        android:contentDescription="@string/app_name"
        app:resize="@{BindingAdapters.createPoint(200, 100)}"
        app:hugeBitmap="@{@drawable/logo}"
				tools:srcCompat="@drawable/logo" />
```

이런식으로 적용해주면 끄읕! 

![resources/Untitled%203.png](resources/Untitled%203.png)

Start 앱이 잘 나오는군요. 😎

## Step1. fragment에 FAB 추가하기

1. 우선 FAB가 포함되어 있는 라이브러리가 있는지 확인!

- **build.gradle(app)**

```kotlin
// material design components
implementation 'com.google.android.material:material:1.2.0'
```

 2. **res/layout/home_fragment.xml** 에는 최상위뷰가 `ScrollView`입니다. FAB는 스크롤 되지 않도록 최상위뷰를 변경합니다. 

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
       android:layout_height="match_parent"
       android:layout_width="match_parent">
...

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

 3. `ScrollView`도 `NestedScrollView` 로 변경합니다.  `NestedScrollView` 는 `CoordinatorLayout` 이 스크롤에 대해서 알 수 있도록 합니다. 

```xml
<androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

...
</androidx.core.widget.NestedScrollView>
```

 4. 이제 `CoordinatorLayout` 하위에 FAB를 추가합니다. 

```xml
<com.google.android.material.floatingactionbutton.FloatingActionButton
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
```

그러면 이런 식으로 표시되겠죠! 

![resources/Untitled%204.png](resources/Untitled%204.png)

## Step2. FAB Style

1. FAB를 하단에 위치시켜봅시다. 

```xml
android:layout_gravity="bottom|end"
```

 2. margin 도 넣어봅시다. 

```xml
android:layout_margin="16dp"
```

 3. 마지막으로 아이콘을 넣어봅시다. 

```xml
app:srcCompat="@drawable/ic_gdg"
```

 

![resources/Untitled%205.png](resources/Untitled%205.png)

깔끔하게 FAB가 이동했습니다! 

## Step3. ClickListener

`viewModel`에서 선언해놓은 `clickListener`를 넣고 바인딩 해봅시다. 데이터 바인딩 방법은 모두들 알고 있죠?!

- **home_fragment.xml**

```xml
<!-- data -->
<variable
   name="viewModel"
   type="com.example.android.gdgfinder.home.HomeViewModel"/>

<!-- fab -->
android:onClick="@{() -> viewModel.onFabClicked()}"
```

- **HomeViewModel**

```xml
class HomeViewModel : ViewModel() {

    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    fun onFabClicked() {
        _navigateToSearch.value = true
    }

    fun onNavigatedToSearch() {
        _navigateToSearch.value = false
    }
}
```

- **HomeFragment.kt**

```xml
// onCreateView()
binding.viewModel = viewModel
viewModel.navigateToSearch.observe(viewLifecycleOwner,
    Observer<Boolean> { navigate ->
        if(navigate) {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_gdgListFragment)
            viewModel.onNavigatedToSearch()
       }
     })
```

이제 FAB를 클릭하면 다음과 같은 리스트 페이지로 넘어가게 됩니다!

![resources/Untitled%206.png](resources/Untitled%206.png)

# Task2. Use styling in a world of Material Design

Material Design 구성요소를 최대한 활용하려면 테마를 사용해야 합니다. 

1. typography Theme 👉 [material.io typography](https://material.io/develop/android/theming/typography/)

텍스트 속성을 Typography Theme 에서 하나 골라 적용시켜봅시다. 

```xml
<TextView
       android:id="@+id/title"
       style="?attr/textAppearanceHeadline5"/>
```

스타일이 적용되면 텍스트 스타일이 변경됩니다.  스타일의 우선순위는 다음과 같은데 이 다이어그램과 같이 뷰에 설정된 스타일에 따라서 **스타일을 재정의** 합니다.

![resources/Untitled%207.png](resources/Untitled%207.png)

- 스타일 **재정의 비교**

![resources/Untitled%208.png](resources/Untitled%208.png)

TextAppearance에서는 텍스트를 표시하는 방법에 대해서만 적용되는 모습을 보여줄 수 있습니다. 

 2. 테마 스타일 변경 

style.xml에서 머터리얼 테마도 개발자가 원하는대로 변경할 수 있습니다.  다음은 그 중 한 예입니다. 

```xml
<style name="TextAppearance.CustomHeadline6" parent="TextAppearance.MaterialComponents.Headline6">
   <item name="android:textSize">18sp</item>
</style>
```

 

# Task3. Change the toolbar theme

때로는 화면 일부만 다른 테마로 변경하고 싶을 때도 있을것입니다. Theme는 글로벌 적으로 테마를 설정할때, ThemeOverlay는 특정View, 특정 Toolbar 등에 대해 해당테마를 덮어쓰는데 사용됩니다. 

## Step1. ThemeOverlay

MaterialComponents는 Dark 라는 어두운 테마가 있습니다.  메인 액티비티의 Toolbar를 변경해봅시다. 

```xml
<androidx.appcompat.widget.Toolbar
    android:theme="@style/ThemeOverlay.MaterialComponents.Dark.ActionBar"
		android:background="?attr/colorPrimaryDark"/>
```

![resources/Untitled%209.png](resources/Untitled%209.png)

로고 이미지가 더이상 눈에 띄지 않습니다. 같은 어두운 계열이기 때문이죠. tint를 통해서 더 눈에 띄도록 바꿔봅시다. tint는 지정된 색상으로 "tinted(염색)" 됩니다. 

```xml
android:tint="?attr/colorOnPrimary"
```

![resources/Untitled%2010.png](resources/Untitled%2010.png)

# Task3. Use dimensions

개발자들은 앱의 일관된 모양과 느낌을 적용하기위해 정해진 길이의 치수를 전체에 적용하고 싶을 것입니다. `dimen`은 이를 효과적으로 적용할 수 있습니다. 

home_fragment에 Padding을 한번 적용해볼까요?! 

```xml
<androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clipToPadding="true"
            android:paddingBottom="16dp"
            android:paddingStart="16dp"
            android:paddingEnd="16dp"
            tools:context=".home.HomeFragment">
```

디자인 탭에서 dimen을 정의할 수도 있습니다. 

![resources/Untitled%2011.png](resources/Untitled%2011.png)

![resources/Untitled%2012.png](resources/Untitled%2012.png)

이 dimen을 적용하면 다 같은 치수의 padding이 적용됩니다.

```xml
<androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clipToPadding="true"
            android:paddingStart="@dimen/padding_main"
            android:paddingEnd="@dimen/padding_main"
            android:paddingBottom="@dimen/padding_main"
            tools:context=".home.HomeFragment">
```

모두 변경하고 싶다면 Android 스튜디오에서 모두 바꾸기 ( Cmd+Shift+RMac 또는 Ctrl+Shift+RWindows)를 통해서 변경할 수 있습니다. 

![resources/Untitled%2013.png](resources/Untitled%2013.png)

# Task4. Use colors

색상도 마찬가지로 `colors` 를 통해서 일관되게 색상을 유지할 수 있습니다.  Material Design에서 추천하는 색상 구성표도 있으니 참고할 수도 있습니다. 👉 [material color](https://material.io/tools/color/)

잠깐 색상 툴 사이트에 대해서 설명하자면 

- MATERIAL PALETTE에서 Primary 컬러, Secondary 컬러를 선택할 수 있습니다.

![resources/Untitled%2014.png](resources/Untitled%2014.png)

- ACCESSIBILITY 를 선택하면 아래와 같은 보고서에서 현재 선택된 색상이 얼마나 읽기 쉬운지에 대해서 나타내고 있습니다.

![resources/Untitled%2015.png](resources/Untitled%2015.png)

**삼각형의 느낌표** 표시는 시각 장애가 있는 사람을 포함한 대다수의 사람들이 읽기 어려울 수도 있다는 것을 나타냅니다. 어떤 색을 선택하더라도 완벽한 색상은 없으므로 디테일한 조정은 필요합니다.

- Export > Android 를 선택하면 color.xml에 정의한 내용이 다운로드 됩니다.

![resources/Untitled%2016.png](resources/Untitled%2016.png)

- 다운받아진 color.xml

```xml
<!--?xml version="1.0" encoding="UTF-8"?-->
<resources>
  <color name="primaryColor">#ef5350</color>
  <color name="primaryLightColor">#ff867c</color>
  <color name="primaryDarkColor">#b61827</color>
  <color name="secondaryColor">#ec407a</color>
  <color name="secondaryLightColor">#ff77a9</color>
  <color name="secondaryDarkColor">#b4004e</color>
  <color name="primaryTextColor">#000000</color>
  <color name="secondaryTextColor">#000000</color>
</resources>
```

- 스타일에 해당 내용을 완벽하게 적용할 수 있습니다.

```xml
<!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/primaryColor</item>
        <item name="colorPrimaryDark">@color/primaryDarkColor</item>
        <item name="colorPrimaryVariant">@color/primaryLightColor</item>
        <item name="colorOnPrimary">@color/primaryTextColor</item>
        <item name="colorSecondary">@color/secondaryColor</item>
        <item name="colorSecondaryVariant">@color/secondaryDarkColor</item>
        <item name="colorOnSecondary">@color/secondaryTextColor</item>
    </style>
```

![resources/Untitled%2017.png](resources/Untitled%2017.png)

# Answer these questions

## Question 1

### Which of the following is true about the floating action button (FAB)?

Simply : True about FAB

▢ The FAB is usually associated with a primary action the user can take on the screen. FAB can do primary action

▢ The FAB **must be positioned** in the **bottom-right corner**, **16 dp** from the edge of the screen. 

▢ The FAB uses a special click handler so that you don't have to write your own view model code. FAB not need viewmodel.

▢ The FAB is a mandatory element for apps that implement Material Design principles. FAB is principles for material

- Answer

    N1

## Question 2

### Which ViewGroup allows you to stack views on top of each other?

Simply : 서로 겹쳐서 쌓을수 있는 ViewGroup

▢ **`CoordinatorLayout`**

▢ **`StackedViewsLayout`**

▢ **`ConstraintLayout`**

▢ You cannot stack views

- Answer

    N1,3

## Question 3

### Which of the following are reasons for using Material Design components? Select all that apply.

Simply : Reason about using the material design

▢ They are designed to be **beautiful, functional**, and work together. 

▢ They help you create an app that **uses consistent styling**.

▢ They help you make your **app accessible for vision impaired users**.

▢ Android Studio will give you a **warning** if you are using a **poor color scheme**.

- Answer

    N1,2

## Question 4

### What is a theme overlay for? Select all that are true.

▢ Allows you to **apply** attributes from a **different theme** to a **view and all its children**.

▢ **With a theme overlay**, **all** the styles of that **theme are applied** automatically.

▢ You **create a theme overlay** by defining **styles in XML.**

▢ When you **apply a theme** to a **view**, you **can use** that theme's attributes ****for the **view and all its children**.

- Answer

    N1,3,4

## Question 5

### Which of the following are reasons for defining and using dimensions? Select all that apply.

Simply : Reason to use dimens

▢ Makes it **easier** to **apply the same measurements** across the app.

▢ Allows you to give **meaningful semantic names** to measurements.

▢ Makes it **easier** to **change measurements** across your app.

▢ Dimensions are **required for using Material Design**.

- Answer

    N1,2,3