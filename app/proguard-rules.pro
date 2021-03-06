# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/buglai/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
# (3)Not remove unused code
-dontshrink

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}




#retrofit2
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8

#jsoup
-keep public class org.jsoup.** {
public *;
}

#rxjava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


#simple xml
-keep public class org.simpleframework.**{ *;}
-keep class org.simpleframework.xml.**{ *; }
-keep class org.simpleframework.xml.core.**{ *; }
-keep class org.simpleframework.xml.util.**{ *; }
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes ElementList, Root
-keepattributes Element
-keepattributes SourceFile,LineNumberTable
-dontwarn javax.xml.stream.XMLInputFactory
-dontwarn javax.xml.stream.XMLEventReader
-dontwarn javax.xml.stream.XMLEvent
-dontwarn javax.xml.stream.events.XMLEvent
-dontwarn javax.xml.stream.events.Attribute
-dontwarn javax.xml.stream.Attribute
-dontwarn javax.xml.stream.Location
-dontwarn javax.xml.stream.events.StartElement
-dontwarn javax.xml.stream.events.Characters

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


#umeng
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class com.buglai.rxrss.R$*{
public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}