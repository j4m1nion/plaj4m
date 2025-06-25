# Plaj4m

**A modern, customizable Android media player UI built with Jetpack Compose and Media3 (ExoPlayer)** 

---

## Overview

**Plaj4m** is a fully customizable and lightweight media player UI component for Android apps using Jetpack Compose. It was developed as a fun side project. The library is modular, follows MVVM with unidirectional data flow, and supports Picture-in-Picture and rich player configuration.


---

##  Features

-  Built on **Media3 (ExoPlayer)** for reliable media playback
-  Custom UI written in **Jetpack Compose**
-  MVVM architecture with `ViewModel`, `State`, and sealed event classes
-  Easy configuration via `PlayerViewConfiguration.kt`
-  Picture-in-Picture (PiP) support
   Pluggable `PlayerListener` for event callbacks
-  Compose Previews for UI components
-  Published to **JitPack**

---

##  Architecture

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Playback**: Media3 (ExoPlayer)
- **Pattern**: MVVM + Unidirectional Data Flow
- **Modules**:
  - `model/`: domain models (`MediaFile`, `MediaChapter`)
  - `player/`: controller, state, events, and `ViewModel`
  - `views/`: reusable Compose UIs
  - `configuration/`: player settings, PiP, etc.
  - `theme/`: custom theming

---

##  Getting Started

### Add Dependency (via JitPack)

In your **`settings.gradle`**:

```
dependencyResolutionManagement {
    repositories {
        ...
        maven { url = uri("https://jitpack.io") }
    }
}

```
In the app's `build.gradle`:

```kotlin
dependencies {
    implementation("com.github.j4m1nion:plaj4m:1.0.20")
}

```

Then you can use the Player Screen calling 
```kotlin
fun PlayerScreen(
    mediaFiles : List<MediaFile>,
    viewModel: PlayerViewModel,
    playerViewConfiguration: PlayerViewConfiguration = PlayerViewConfiguration(),
    loadingView: @Composable (BoxScope.() -> Unit) ? = null,
    errorView: @Composable (BoxScope.() -> Unit) ? = null)
```

##  Usage Example

```kotlin
PlayerScreen(MediaFilesBuilder.playlist(), viewModel { PlayerViewModel(application) },
                                    playerViewConfiguration = PlayerViewConfiguration(
                                        features = PlayerFeatureConfiguration(
                                            mediaInfos = PlayerFeatureMediaInfos(
                                                autoplay = true,
                                                shuffle = true,
                                                title = true,
                                                description = true,
                                                chapters = true,
                                                enabled = true
                                            )
                                        ),
                                        controllerConfiguration = PlayerControllerConfiguration(
                                            zoomInPortrait = false,
                                            zoomInLandscape = true,
                                            topConfiguration = PlayerViewTopConfiguration(
                                                showTitleInPortrait = false,
                                                showTitleInLandscape = true),
                                            bottomConfiguration = PlayerViewBottomConfiguration(false),
                                            centerConfiguration = PlayerViewCenterConfiguration(
                                                showPlayPauseButton = true,
                                                showPreviousButton = true,
                                                showFastReplay = false,
                                                showNextButton = true,
                                                showFastForward = false
                                            ),
                                            enabled = true
                                        ),

                                        animation = PlayerViewAnimation()
                                    )
                                )

```

Alternatively, use PlayerViewModel directly and build your own UI around it.

##  Configuration

###  Theming 

If you're using the bundled PlayerTheme, colors can be customized via your colors.xml:

```xml
<resources>
    <color name="primary">#FFFF0000</color>
    <color name="secondary_light">@android:color/white</color>
    <color name="secondary">@android:color/white</color>
    <color name="secondary_dark">#FF282828</color>
</resources>
 ```

###  Player Behaviour 
The PlayerViewConfiguration class allows you to control player features, layout behavior, and animations via Compose parameters.
  
##  Security Notes
Plaj4m focuses purely on UI and playback. For remote media playback in production, consider:

• Adding a network_security_config.xml

• Enforcing HTTPS traffic

• Using file URI sanitization for untrusted media

##   Screenshots
<img src="https://github.com/user-attachments/assets/0c10943f-8cca-41ad-8477-3e4f2664ff7c" width="300"/>
<img src="https://github.com/user-attachments/assets/01848a18-c619-4e76-a40a-f904b840cc1a" width="300"/>
<img src="https://github.com/user-attachments/assets/6f74fa6c-e982-4f0d-800c-60fb54ec1f79" width="300"/>
<img src="https://github.com/user-attachments/assets/1c9dd799-773c-4f7f-b685-d6d260d9542d" height="300"/>



##   Development Notes
Requirements
• Min SDK: 21+

• Target SDK: 35

• Java: 17+

• Gradle: 8.9+

##   Why I Built This
Plaj4m was created as a hands-on project to apply my knowledge of modern Android development, architecture patterns, and Compose-based UI design. It was also a way to experiment with modular publishing and player customization at scale. It was made mainly for fun. 

##  Author
@j4m1nion — open to feedback, collaboration, or a chat over coffee.

##  License
Apache License 2.0 — free to use, modify, or integrate into your own projects.

