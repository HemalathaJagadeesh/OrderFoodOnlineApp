plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)

}

android {
    namespace = "com.android.onlinefoodorderingapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.android.onlinefoodorderingapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "ZOMATO_API_KEY", "\"YOUR_API_KEY_HERE\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "ZOMATO_API_KEY", "\"YOUR_API_KEY_HERE\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // For Compose ViewModel injection
    implementation(libs.hilt.navigation.compose)

    // UI + Retrofit
    implementation("com.google.android.material:material:1.9.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //commenting, as image is not loading-
    /*implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")*/
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ✅ Paging - FIX duplicate version
    implementation("androidx.paging:paging-compose:3.3.2")
    implementation("androidx.paging:paging-runtime:3.3.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Room DB
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    kapt("androidx.room:room-compiler:2.6.1")

    implementation("androidx.compose.material3:material3:1.2.0")
    implementation ("androidx.compose.material:material-icons-extended")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //unit testing
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest:kotest-framework-engine:5.8.0")

    //Mockk
    testImplementation("io.mockk:mockk:1.13.8")
    androidTestImplementation("io.mockk:mockk-android:1.13.8")

    //Coroutine test support
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Turbine
    testImplementation("app.cash.turbine:turbine:1.1.0")


    //UI Testing (Espresso)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    //firebase Authentication
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation (platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Compose integration
    implementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}