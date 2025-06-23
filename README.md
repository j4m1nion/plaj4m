# Plaj4m

**A modern, customizable Android media player UI built with Jetpack Compose and Media3 (ExoPlayer)** 

•  Kotlin 
•  Compose 
•  Modular
•  Media3 

---

## Overview

**Plaj4m** is a fully customizable, lightweight Android media player UI component that can be dropped into any Compose-based app. 
It was developed for fun and it includes clean architecture, state management, Jetpack libraries, and publishing to Maven.

<p align="center">
  <img src="https://github.com/j4m1nion/j4player/raw/main/art/player_demo.gif" width="320"/>
</p>

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
- **Playback**: Media3
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
In the app gradle file:

```
dependencies {
    implementation("com.github.j4m1nion:plaj4m:1.0.20")
}

```

Then you can use the Player Screen calling 
```
fun PlayerScreen(
    mediaFiles : List<MediaFile>,
    viewModel: PlayerViewModel,
    playerViewConfiguration: PlayerViewConfiguration = PlayerViewConfiguration(),
    loadingView: @Composable (BoxScope.() -> Unit) ? = null,
    errorView: @Composable (BoxScope.() -> Unit) ? = null)

Eg:
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

Or just use the PlayerViewModel providing your custom ui and playerview. 

##  Configuration: 

The player can be easily configurated in: 

UI
  If you use the theme included PlayerTheme the color can be easily changed modifying these properties in your colors.xml
  ```
<resources>
    <color name="primary">#FFFF0000</color>
    <color name="secondary_light">@android:color/white</color>
    <color name="secondary">@android:color/white</color>
    <color name="secondary_dark">#FF282828</color>
</resources>
 ```
Features: 
  The playes can be customized using the PlayerViewConfiguration that the composable has as parameters. 
  
##  Security Notes
This is a UI/player component and does not include networking logic.
For production use with remote media sources, consider:

• Adding a network_security_config.xml

• Enforcing HTTPS traffic

• Using file URI sanitization for untrusted media

##   Screenshots
Controller UI	Media Info	PiP Support

(Add your screenshots or GIFs under /art/ folder)

##   Development Notes
Requirements
• Min SDK: 21+

• Target SDK: 35

• Java: 17+

• Gradle: 8.9+

##   Why I Built This
I created J4Player as a fun and challenging way to apply my experience with Compose, architecture patterns, and media APIs.

##  Author
@j4m1nion
Always open to feedback, collaboration, or coffee ☕.

##  License
MIT — use freely, credit appreciated.

