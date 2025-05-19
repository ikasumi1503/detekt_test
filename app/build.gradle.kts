plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("io.gitlab.arturbosch.detekt") version("1.23.8")
}

android {
    namespace = "com.example.detekt_test"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.detekt_test"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

detekt {
    toolVersion = "1.23.8"
    config.setFrom(rootProject.file("config/detekt/detekt.yml")) // リンタールールの設定ファイル
    buildUponDefaultConfig = true // trueで自分で書いたルール以外はデフォルト値を適用
    ignoreFailures = true // エラー時ビルドを落とさない
}

// detektの検出エラーの出力方法の設定
// 必要な形式をtrueにしてください。
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "17" // 筆者の場合は23が指定されてこれがないと動かなかった。根本原因不明です。
    reports {
        // detektの検出エラーの出力方法の設定
        // 必要な形式をtrueにしてください。
        xml.required.set(true) // xml形式
        html.required.set(true) // html形式
        sarif.required.set(true) // sarif形式
        md.required.set(true) // md形式
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    detektPlugins(libs.detekt.formatting)
}