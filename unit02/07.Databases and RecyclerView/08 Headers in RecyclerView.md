# 7-8 Headers in RecyclerView

Created: Apr 1, 2021 12:03 PM
link: https://developer.android.com/codelabs/kotlin-android-training-headers?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fkotlin-fundamentals-seven%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fkotlin-android-training-headers#9

# Sealed class

[https://kotlinlang.org/docs/sealed-classes.html](https://kotlinlang.org/docs/sealed-classes.html)

A sealed class defines a closed type, which means that all subclasses of DataItem must be defined in this file.

```kotlin
sealed class DataItem {
    abstract val id: Long
    data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
        override val id = sleepNight.nightId
    }
    object Header: DataItem() {
        override val id: Long = Long.MIN_VALUE
    }
}
```

## **Sealed classes and when expression**

The key benefit of using sealed classes comes into play when you use them in a `[when` expression](https://kotlinlang.org/docs/control-flow.html#when-expression). If it's possible to verify that the statement covers all cases, you don't need to add an `else` clause to the statement. However, this works only if you use `when` as an expression (using the result) and not as a statement.

```kotlin
fun eval(expr: Expr): Double = when(expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANumber -> Double.NaN
    // the `else` clause is not required because we've covered all the cases
}`
```

# RecyclerView Patterns

1. getItemViewType() : position 에 따라 ViewType 반환

    ```kotlin
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }
    ```

2. onCreateViewHolder() : ViewType 에 따라 ViewHolder 반환

    ```kotlin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }
    ```

3. onBindViewHolder() : ViewHolder 마다 Data 를 바인딩

    ```kotlin
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }
    ```

# Coroutine

Use coroutines for long-running tasks that could slow the UI.

Imagine a list with hundreds of items, multiple headers, and logic to decide where items need to be inserted. This work belongs in a coroutine

```kotlin
 fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
```

# DiffUtil

```kotlin
class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}
```

## **Answer these questions**

### **Question 1**

Which of the following statements is true about **`ViewHolder`**?

▢ An adapter can use multiple **`ViewHolder`** classes to hold headers and various types of data.

▢ You can have exactly one view holder for data, and one view holder for a header.

▢ A **`RecyclerView`** supports multiple types of headers, but the data has to be uniform.

▢ When adding a header, you subclass **`RecyclerView`** to insert the header at the correct position.

### **Question 2**

When should you use coroutines with a **`RecyclerView`**? Select all the statements that are true.

▢ Never. A **`RecyclerView`** is a UI element and should not use coroutines.

▢ Use coroutines for long-running tasks that could slow the UI.

▢ List manipulations can take a long time, and you should always do them using coroutines.

▢ Use coroutines with suspend functions to avoid blocking the main thread.

### **Question 3**

Which of the following do you NOT have to do when using more than one **`ViewHolder`**?

▢ In the **`ViewHolder`**, provide multiple layout files to inflate as needed.

▢ In **`onCreateViewHolder()`**, return the correct type of view holder for the data item.

▢ In **`onBindViewHolder()`**, only bind data if the view holder is the correct type of view holder for the data item.

▢ Generalize the adapter class signature to accept any **`RecyclerView.ViewHolder`**.