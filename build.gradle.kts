plugins {
    id("com.android.library") version "8.7.2"
    kotlin("android") version "1.9.24"
    id("maven-publish")
}


android {
    namespace = "com.j4m1nion.j4player.player"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "consumer-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }


}

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
                groupId = "com.github.j4m1nion"
                artifactId = "j4player"
                version = "1.0.20"
            }
        }
    }
}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2024.11.00")
    val kotlinVersion = "1.9.24"
    val material3 = "1.3.2"
    val ktx = "1.16.0"
    val appCompat = "1.7.1"
    val navigationCompose = "2.8.4"
    val composeRuntime = "1.8.2"
    val coil = "3.0.4"
    val media3 = "1.7.1"
    implementation("androidx.core:core-ktx:$ktx")
    implementation("androidx.appcompat:appcompat:$appCompat")

    //COMPOSE
    implementation(composeBom)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.compose.material3:material3:$material3")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:$navigationCompose")
    implementation("androidx.compose.runtime:runtime:$composeRuntime")
    implementation("io.coil-kt.coil3:coil-compose:$coil")
    implementation("io.coil-kt.coil3:coil-network-okhttp:$coil")
    debugApi("androidx.compose.ui:ui-tooling")

    //EXOPLAYER
    implementation("androidx.media3:media3-exoplayer:$media3")
    implementation("androidx.media3:media3-ui:$media3")
    implementation("androidx.media3:media3-session:$media3")
    implementation("androidx.media3:media3-exoplayer-hls:$media3")
    implementation("androidx.media3:media3-exoplayer-dash:$media3")

}

