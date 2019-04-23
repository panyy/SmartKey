[![](https://jitpack.io/v/Vove7/SmartKey.svg)](https://jitpack.io/#Vove7/SmartKey)

# SmartKey

> 利用Kotlin委托实现优雅地持久化存储App配置。



### 基本使用

0. 在Application中初始化：

```kotlin
SmartKet.init(context)
```

1. 定义配置类：
```kotlin
//这里可以注解配置存储文件名，多个配置类可分文件存储
@Config("app_config")
object AppConfig {

    /**
     * 基本类型存储
     */
    var text: String by SmartKey("defaultValue")
    var number: Int  by SmartKey(50)

    /**
     * 其他类型使用：[SmartKey.auto]
     */
    var intArr: Array<Int> by SmartKey.auto(emptyArray())

    var userInfo: UserInfo? by SmartKey.auto(null)

}

data class UserInfo(
        val name: String
)
```

2. 使用

此时你可以像这样使用：

```kotlin
//获取存储值
val value = AppConfig.text
val n = AppConfig.number 

//实时存储
AppConfig.text = "setValue"
AppConfig.number = 0

//存储登录用户数据
AppConfig.userInfo = UserInfo("new_user")

```

### demo

<img src="screenshot/Screenshot.jpg" width= "300px" />

### 更多

- 你可以指定变量对应存储的key：
```kotlin
    //指定key 或 keyId
    var text: String by SmartKey("defaultValue", key = "your_key", keyId = R.string.key_text)
```

- 选择是否加密数据：

```kotlin
    //使用encrypt来声明加密存储数据
    var userInfo: UserInfo? by SmartKey.auto(null, encrypt = true)

```

- 使用`SmartKey`直接操作key和value

```kotlin
//使用默认存储文件存取
SmartKey["text"] = "aaa" 
val n = SmartKey["number", -1] //key, defaultValue

//指定配置文件存取
SmartKey["app2", "key"] = 1
val s = SmartKey["app2", "text", "default"]//configName, key, defaultValue

//另外，key可使用StringRes 来代替 
val t = SmartKey[R.string.key_of_text, "default"]


```



### 引入SmartKey

###### Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```
###### Step 2. Add the dependency
```groovy
dependencies {
    implementation "com.github.Vove7:SmartKey:$lastest_version"
}
```
> lastest_version : [![](https://jitpack.io/v/Vove7/SmartKey.svg)](https://jitpack.io/#Vove7/SmartKey)


### 注意

使用可空基本类型，需使用SmartKey.auto
```kotlin
    var last by SmartKey.auto<Long?>(null)

```

### TODO

- 完成加密


### Thanks

- 底层存储使用[multiplatform-settings](https://github.com/russhwolf/multiplatform-settings)
- [Gson](https://github.com/google/gson)