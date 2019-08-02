# FaceApp Photo Stealer

FaceApp is a very famous Android app available [on the PlayStore](https://play.google.com/store/apps/details?id=io.faceapp). With this app you can apply some filters on your face: old, swap gender, add hairs, ...

# Vulnerability Description
Once you applied the filters, FaceApp is storing insecurely your modified photo in the folder /sdcard/FaceApp/.
```shell
fs0c131y@Elliots-MacBook-Pro:~$ adb shell
angler:/ $ ls -al /sdcard/FaceApp
total 1120
drwxrwx--x  2 root sdcard_rw   4096 2019-08-02 13:09 .
drwxrwx--x 40 root sdcard_rw   4096 2019-08-02 13:12 ..
-rw-rw----  1 root sdcard_rw 548231 2019-07-17 00:45 FaceApp_1563317124380.jpg
-rw-rw----  1 root sdcard_rw 588557 2019-08-02 13:09 FaceApp_1564744179158.jpg
```

This is an issue because this is very common for an app to get the READ_EXTERNAL_STORAGE permission, so any external app can get your FaceApp photos without your consent. To show you the issue, this POC app is able to list all the available FaceApp photos and open it.
[![Demo](https://img.youtube.com/vi/nfSn2Wanv9I/maxresdefault.jpg)](https://youtu.be/nfSn2Wanv9I)


To be fair with FaceApp, this is a very common error in the Android world. For example, WhatsApp and WeChat store the attachments on the sdcard too.

Moreover, the name of the photo contains the time where you took the photo. It's never good to leak meta data like this...

# Affected versions
3.4.11 and below

# Takeaways
- Don't save personal data in te SDCARD
- Now, it's the good time to yell

# Contact
Follow me on [Twitter](https://twitter.com/fs0c131y)! You can also find a small part of my work at [https://fs0c131y.com](https://fs0c131y.com)

# Credits
The investigation and the POC has been made with ❤ by [@fs0c131y](https://twitter.com/fs0c131y)
