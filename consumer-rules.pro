
-dontwarn java.lang.invoke.StringConcatFactory
-dontwarn coil3.compose.SingletonAsyncImageKt
-dontwarn coil3.request.ImageRequest$Builder
-dontwarn coil3.request.ImageRequest
-dontwarn coil3.request.ImageRequestsKt

-keep,allowobfuscation class **.R$* { *; }
-keep class kotlin.Metadata { *; }

-keep class com.j4m1nion.j4player.player.theme.** { *; }
-keep class com.j4m1nion.j4player.player.configuration.** { *; }
-keep class com.j4m1nion.j4player.player.model.** { *; }
-keep class com.j4m1nion.j4player.player.PlayerScreenKt { *; }
-keep class com.j4m1nion.j4player.player.PlayerViewModel { *; }
-keep class com.j4m1nion.j4player.player.theme.PlayerThemeKt { *; }


-keepattributes *Annotation*



