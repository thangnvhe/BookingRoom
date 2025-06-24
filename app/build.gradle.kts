plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.thangnvhe.bookingroom"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.thangnvhe.bookingroom"
        minSdk = 30
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // ✅ Thêm đoạn này để loại bỏ duplicate annotations
    configurations.all {
        exclude(group = "com.intellij", module = "annotations")
    }
}

dependencies {
    val room_version = "2.7.2"

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    implementation("androidx.room:room-guava:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    implementation("de.hdodenhof:circleimageview:3.1.0") // ✅ Đúng cú pháp Kotlin DSL

    // Libraries khác
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
