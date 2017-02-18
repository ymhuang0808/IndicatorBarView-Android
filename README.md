# IndicatorBarView-Android

**_Still DEVELOPING_! The API MAY be changed frequently.**

The IndicatorBarView-Android displays value on the pin with corresponding location on the bar.

![Screenshot](https://github.com/ymhuang0808/IndicatorBarView-Android/blob/develop/screenshots/Screenshot_20160218_001.png)

The example project is in _indicatorviewbar-android-sample/_ directory

## Example code

The indicator bar with minimum and maximum values.

```xml
<tw.fmbase.app.indicatorviewbar_android.IndicatorBar
    android:id="@+id/indicator_bar"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    indicatorbar:minValue="0"
    indicatorbar:maxValue="100"
    indicatorbar:value="55"
    indicatorbar:barHeight="10dp">
```

The indicator bar with multiple colors.

```xml
<tw.fmbase.app.indicatorviewbar_android.IndicatorBar
    android:id="@+id/indicator_bar"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    indicatorbar:minValue="0"
    indicatorbar:maxValue="100"
    indicatorbar:value="55"
    indicatorbar:barHeight="10dp"
    indicatorbar:isMultipleLevelColors="true"/>
```

## TODO

* Implement `onMeasure()` in `IndicatorBar` view class
* **Performance:** Pass certain area when invoking `invalidate()` in `IndicatorBar` view
* Configure the text font on pin
* API document
* Publish on jCenter
* Add unit testing with Travis CI
