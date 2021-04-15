# RecyclerView fundamental

## 1. Concept of RecyclerView

---

- RecyclerView only does work to process or draw items that are currently visible on the screen.
- When an item scrolls off the screen, the item's views are recycled.
- This RecyclerView behavior saves a lot of processing time and helps lists scroll smoothly.
- When an item changes, instead of redrawing the entire list, RecyclerView can update that one item. This is a huge efficiency gain when displaying long lists of complex items!

## 2. Adapter pattern

---

- The adapter pattern in software engineering uses a similar concept. This pattern allows the API of one class to be used as another API.

1. onBindViewHolder

```
override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
}
```

- The onBindViewHolder() function is called by RecyclerView to display the data for one list item at the specified position.

2. onCreateViewHolder

```
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
}
```

- onCreateViewHolder() is called when the RecyclerView needs a view holder.
- The parent parameter, which is the view group that holds the view holder, is always the RecyclerView.
- The viewType parameter is used when there are multiple views in the same RecyclerView.
- In onCreateViewHolder(), create the view by asking the layoutinflater to inflate it.
- The layout inflater knows how to create views from XML layouts. The context contains information on how to correctly inflate the view. so you always pass in the context of the parent view group

3. Why recyclerview need notify method 

```
var data =  listOf<SleepNight>()
   set(value) {
       field = value
       notifyDataSetChanged()
   }
```

- The adapter needs to let the RecyclerView know when the data has changed, because the RecyclerView knows nothing about the data. It only knows about the view holders that the adapter gives to it.
- When notifyDataSetChanged() is called, the RecyclerView redraws the whole list, not just the changed items.

## 3. How view holders are recycled

---

- RecyclerView recycles view holders, which means that it reuses them. As a view scrolls off the screen, RecyclerView reuses the view for the item that's about to scroll onto the screen.
- Because these view holders are recycled, make sure onBindViewHolder() sets or resets any customizations that previous items might have set on a view holder.
- If RecyclerView does need to access the views stored in the ViewHolder, it can do so using the view holder's itemView property. so RecyclerView uses itemView when it's binding an item to display on the screen, when drawing decorations around a view like a border, and for implementing accessibility.