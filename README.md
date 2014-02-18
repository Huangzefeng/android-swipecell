# Android SwipeCell

This is a custom view that extends HorizontalScrollView to show action views when slid left or right.

## Including it

SwipeCell doens't need any resources so you can straight up download/generate the jar and add it to your project.

*When generating the jar, simply export the source without the AndroidManifest.*

## Using it

To use SwipeCell, you'll need to create a layout for the left action view, right action view and for the content view of the cell. 

```
SwipeCell swipeCell = new SwipeCell(context);
swipeCell.setContentView(R.layout.cell, R.layout.left_action, R.layout.right_action);
```

SwipeCell will inflate those layouts. Layout parameters will be set to MATCH_PARENT for width and WRAP_CONTENT for height for those layouts.

__NOTE:__ Do not use use RelativeLayout. There seems to be a bug on Android with RelativeLayout parameters being ignored when inflated dynamically. If you absolutely need a RelativeLayout, put it inside a FrameLayout first.

### Action Listeners

Use ```setRightActionListener``` and ```setLeftActionListener``` to handle clicks on the respective views. SwipeCell will automatically dismiss on click. You can disable this behaviour with ```setAutoDismiss(false)```

-> See the included sample for a detailed implementation.