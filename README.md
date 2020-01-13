# React-Native-Firebase-Sample-Project
Associated Plugin - https://invertase.io/ (Applicable for version 6x)

Two solutions for this project :-

1> Brief- Create a custom listner and route the notification accordingly to the required service.
 a> In your android manifest file create a custom listner
 b> Create a custom file under java/sample_listner and pass the payload to the subsequent provider
 
2> Brief - Create a background service 
Refrence - https://developer.android.com/reference/android/app/Service.html#startForeground(int,%20android.app.Notification)
 a> For android 8.0 and above the foreground service needs to be started t listen for incoming FCM notifiactions .
 b> In the firebasePlugin.java file handle the notification accordingly.
 
 
