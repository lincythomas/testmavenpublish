import java.util.Properties

plugins {
    alias(libs.plugins.android.library) // ✅ REQUIRED
    alias(libs.plugins.kotlin.android)
    id("com.vanniktech.maven.publish") version "0.35.0"
    id("signing")

}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { inputStream ->
        localProperties.load(inputStream)
    }
}

android {
    namespace = "com.example.paymentsdktest"
    compileSdk = 36

    defaultConfig {
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
}

// <module directory>/build.gradle.kts

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates("com.jiocoders", "PaymentSDK", "1.0.5")

    pom {
        name = "PaymentSDK"
        description = "PaymentSDK: Library for Android Application"
        inceptionYear = "2024"
        url = "https://github.com/jiocoders/payment_sdk_android.git"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "JioCoders"
                name = "Jio Coders"
                url = "https://github.com/kotlin-hands-on/"

            }
        }
        scm {
            url = "https://github.com/jiocoders/payment_sdk_android.git"
            connection = "scm:git@github.com:jiocoders/payment_sdk_android"
            developerConnection = "scm:git@github.com:jiocoders/payment_sdk_android.git"
        }

    }
}

signing{
        useInMemoryPgpKeys(
            localProperties["signing.keyId"].toString(),
            File(localProperties["signing.secretKeyRingFile"].toString()).readText(),
            localProperties["signing.password"].toString()
        )
    }

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}