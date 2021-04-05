# Define Navigation path

## 1.  NavHostFragment

---

- A navigation host fragment acts as a host for the fragments in a navigation graph.
- the navigation host Fragment swaps fragments in and out as necessary.
- The Fragment also **creates** and **manages** the appropriate **Fragment back stack**.

```
!-- The NavHostFragment within the activity_main layout -->
            <fragment
                android:id="@+id/myNavHostFragment"
                android:name="androidx.navigation.fragment.NavHostFragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:navGraph="@navigation/navigation"
                app:defaultNavHost="true" />
```

## 2. NavGraph

---

- A navigation graph is a resource file that contains all of your destinations and actions. The graph represents all of your app's navigation paths.
- you've let the **navigation controller handle the back stack** for you. When the user navigates to a destination in your app, Android adds this destination to the back stack.

![Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled.png](Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled.png)

```
//The complete onClickListener with Navigation
binding.playButton.setOnClickListener { view : View ->
       view.findNavController().**navigate**(**R.id.fragmentMain_to_gameFragment**)
}
```

## 3. Backstack

---

- A task is a collection of activities that users interact with when performing a certain job. The activities are arranged in a stack—the back stack)—in the order in which each activity is opened.

![Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%201.png](Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%201.png)

- The **popUpTo attribute** of an action "pops up" the back stack to a given destination before navigating. (Destinations are removed from the back stack.)
- If the **popUpToInclusive attribute** is **false** or is not set, popUpTo removes destinations up to the specified destination, but leaves the specified destination in the back stack.
- If **popUpToInclusive** is set to **true**, the popUpTo attribute removes all destinations up to and including the given destination from the back stack.

![Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%202.png](Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%202.png)

-winFragment of navGraph

```
<fragment
    android:id="@+id/wonFragment"
    android:name="com.jeoksyeo.mvvmpractice.WonFragment"
    tools:layout="@layout/won_fragment"
    android:label="WonFragment" >
    <action
        android:id="@+id/action_wonFragment_to_gameFragment2"
        app:popUpTo="@id/gameFragment"
        app:popUpToInclusive="true"
        app:destination="@id/gameFragment" />
</fragment>
```

-loseFragment of navGraph

```
<fragment
    android:id="@+id/loseFragment"
    android:name="com.jeoksyeo.mvvmpractice.LoseFragment"
    tools:layout="@layout/lose_fragment"
    android:label="LoseFragment" >
    <action
        android:id="@+id/action_loseFragment_to_gameFragment3"
        app:popUpTo="@id/gameFragment"
        app:destination="@id/gameFragment" />
</fragment>
```

## 4. Up button(on the app bar) versus Back button

---

- The Up button navigates within the app, based on the hierarchical relationships between screens. The Up button never navigates the user out of the app.
- The Back button, shown as 2 in the screenshot below, appears in the system navigation bar or as a mechanical button on the device itself, no matter what app is open.
- The Back button navigates backward through screens that the user has recently worked with (the back stack).

![Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%203.png](Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%203.png)

## 5. Add support for an Up button

---

- The navigation components include a UI library called **NavigationUI**. The Navigation component includes a NavigationUI class.
- This class contains static methods that manage navigation with the **top app bar**, **the navigation drawer**, and **bottom navigation**.

-mainActivity

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
                DataBindingUtil.setContentView<ActivityMainBinding>(this,
                        R.layout.activity_main
                )
        
        NavigationUI.setupActionBarWithNavController(this,
                this.findNavController(R.id.nav_host_fragment))
    }
```

```

override fun onSupportNavigateUp(): Boolean {

    return this.findNavController(R.id.nav_host_fragment).navigateUp()
}
```

-navigationUp()

![Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%204.png](Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%204.png)

## 6. DrawerLayout

---

- The navigation drawer is a panel that slides out from the edge of the screen.
- add a DrawerLayout as the **root view**.
- you must add a layout_gravity attribute to NavigationView.
- The drawer typically contains a header and a menu.
- The navigation drawer will have two menu items, each representing a Fragment that can be reached from the navigation drawer. **Both destinations must have an ID in the navigation graph**.
- If you use **the same ID** for the menu item as for the destination Fragment, you **don't need to** **write any code** at all to implement the **onClick listener**!

![Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%205.png](Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%205.png)

-drawerlayout.xml

```
<layout xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <androidx.drawerlayout.widget.DrawerLayout
        android:id="@+id/drawerLayout"
        tools:openDrawer="start"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <include
            layout="@layout/activity_main"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

        <com.google.android.material.navigation.NavigationView
            android:id="@+id/nav_view"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_gravity="start"
            android:fitsSystemWindows="true"
            app:headerLayout="@layout/nav_header_main"
            app:menu="@menu/activity_main_drawer" />

    </androidx.drawerlayout.widget.DrawerLayout>
</layout>
```

-activity_main_drawer.xml

```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:showIn="navigation_view">

        <item
            android:id="@+id/**aboutFragment**"
            android:title="@string/menu_home" />

</menu>
```

-navGraph

![Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%206.png](Define%20Navigation%20path%208685fa6c979e4a8d94d24c245877ba37/Untitled%206.png)

-MainActivity.class

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.drawer_layout)

        navController = this.findNavController(R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            binding.drawerLayout
        )

        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }
```