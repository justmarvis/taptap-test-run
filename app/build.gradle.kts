plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}



android {
    namespace = "com.ambasolutions.taptap"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.ambasolutions.taptap"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation ("com.google.android.material:material:1.10.0")


    // OkHttp for making HTTP requests
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")

    // GSON for JSON parsing
    implementation ("com.google.code.gson:gson:2.10.1")

    // Stripe SDK
    implementation("com.stripe:stripe-android:20.34.4")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.firebaseui:firebase-ui-database:8.0.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.firebase:firebase-storage")

    // picasso
    implementation ("com.squareup.picasso:picasso:2.71828")

    // stripe
    implementation ("com.stripe:stripe-android:20.34.4")

    // google pay
    implementation ("com.google.android.gms:play-services-wallet:19.2.1")

    // design library
    implementation ("com.google.android.material:material:1.10.0")

    // circle image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")



    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:@latest")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}