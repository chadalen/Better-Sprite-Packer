import org.gradle.jvm.tasks.Jar

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.70"
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    application
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml", "javafx.swing")
}

repositories {
    mavenCentral()
    jcenter()
    maven {
        setUrl("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.commons", "commons-imaging", "1.0-alpha1")
    implementation("org.openjfx", "javafx-controls", javafx.version)
    implementation("org.openjfx", "javafx-fxml", javafx.version)
    implementation("org.openjfx", "javafx-swing", javafx.version)
}

var mainClassNamePath = "io.nshusa.App"

application {
    mainClassName = mainClassNamePath
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "bsp4-gui.jar"
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Main-Class"] = mainClassNamePath
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
