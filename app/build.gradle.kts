plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.petapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.petapp"
        minSdk = 31
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

dependencies {
    // --- ViewModel e Lifecycle ---
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    // A dependência abaixo já é incluída pelo viewmodel-compose, mas a deixamos para clareza
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")

    // --- Room Database ---
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // --- Core e Activity ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose) // Mantemos a versão do 'libs'

    // --- Compose Bill of Materials (BoM) ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // --- Ícones do Material Design ---
    implementation("androidx.compose.material:material-icons-core:1.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    // --- Navegação ---
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // --- DataStore para Preferências ---
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // --- Testes ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // --- Debug ---
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
