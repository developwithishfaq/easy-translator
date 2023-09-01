# Easy FCM

Easy FCM is a powerful and user-friendly library designed to streamline the process of integrating Firebase Cloud Messaging (FCM) into your projects, enabling developers to effortlessly implement push notifications and enhance user engagement. With its intuitive API and comprehensive features, Easy FCM empowers developers to focus on delivering engaging content to their users rather than dealing with complex FCM setup.

## Features

- **Simplified Integration**: Easy FCM takes care of the intricate FCM setup, allowing you to quickly implement push notifications without the hassle.

- **Intuitive API**: With an intuitive and straightforward API, Easy FCM lets you send push notifications seamlessly.

- **Topics And Tokens**: Effortlessly manages to push notifications on topics/tokens.



> Step 1: Add it to your build.gradle (project):
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
> Step 2: Add it to your build.gradle (app)

```gradle
dependencies {
     implementation 'com.github.developwithishfaq:easy-fcm:latest_version'
}
```

## Usage

> First Step:
**Create Instance**
- Get server key from firebase console
- For **setTokenOrTopic()** in value section provide token of a device or a topic name, change **isTopic** value accordingly
- In **setNotificationData()** Provide model **NotificationData()** containing title and body of a notification
- Finally Build It

```
val helper = FcmPushHelper.Builder()
            .setServerKey(SERVER_KEY)
            .setTokenOrTopic(value = TOKEN_OR_TOPIC, isTopic = false)
            .setNotificationData(notification = NotificationData(TITLE, BODY))
            .build()
```
> Last Step:
Using that instance call **pushNotification()** function
```
helper.pushNotification(onSuccess = {
            Log.d(TAG, "onSuccess: ")
        }, onError = {
            Log.d(TAG, "onError: ")
        })
``` 