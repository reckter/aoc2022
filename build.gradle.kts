/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}
// sourceSets {
//    val commonTest by getting {
//        dependencies {
//            implementation(kotlin("test"))
//        }
//    }
// }
sourceSets {
    getByName("main").java.srcDirs("main/")
    getByName("test").java.srcDirs("test/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
//    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

group = "me.reckter"
version = "1.0-SNAPSHOT"
description = "aoc2022"

tasks.test {
    minHeapSize = "1024m" // initial heap size
    maxHeapSize = "4096m" // maximum heap size
    val etags = System.getProperty("excludeTags") ?: "no-tag-given"
    useJUnitPlatform {
        excludeTags = etags.split(",").toMutableSet()
    }
}
