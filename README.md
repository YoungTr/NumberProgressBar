# NumberProgressBar [ ![Download](https://api.bintray.com/packages/youngtr/maven/numberprogress/images/download.svg) ](https://bintray.com/youngtr/maven/numberprogress/_latestVersion)
A progress bar with both horizontal and circular styles. Inspired by [daimajia](https://github.com/daimajia/NumberProgressBar).

-----

## Demo

![NumberProgressBar](https://github.com/YoungTr/NumberProgressBar/blob/master/gif/demo.gif)

-----


###Usage
----

#### Gradle

```groovy
dependencies {
   compile 'compile 'com.youngtr:numberprogress:1.0.1''
}
```

#### Maven 

```xml
<dependency>
  <groupId>com.youngtr</groupId>
  <artifactId>numberprogress</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
`````

Use it in your own code:

```xml
    <com.youngtr.numberprogressbar.NumberProgressBar
        android:id="@+id/progress_circle"
        android:layout_width="30dp"
        android:layout_height="30dp"
        app:currentValue="0"
        app:numberTextColor="@color/colorAccent"
        app:numberTextSize="10sp"
        app:progressBarShape="circle"
        app:reachedBarColor="@color/colorAccent"
        app:reachedBarHeight="1.5dp"
        app:textOffset="3dp"
        app:unreachedBarColor="#CCCCCC"
        app:unreachedBarHeight="0.75dp" />
```

Or just use the preset style:

```xml
        <com.youngtr.numberprogressbar.NumberProgressBar
        android:id="@+id/progress_horizontal_red"
        style="@style/NumberProgressBar.Horizontal.Red" />
```

I made some style, you can also contribute your favorite style.

