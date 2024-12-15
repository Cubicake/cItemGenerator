plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ua.valeriishymchuk"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri( "https://oss.sonatype.org/content/groups/public/")
    }

    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/central") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.extendedclip.com/releases/") }
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    //compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")

    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") // you can compile with this only

    //compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")


    //compileOnly("org.spigotmc:spigot:1.8-R0.1-SNAPSHOT") // can be obtained from buildtools, being used only for investigation purposes
    val adventureVersion = "4.17.0"
    implementation("net.kyori:adventure-text-minimessage:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-gson:$adventureVersion")


    compileOnly("com.github.LoneDev6:API-ItemsAdder:3.6.1")
    compileOnly("me.clip:placeholderapi:2.11.6")


    implementation("de.tr7zw:item-nbt-api:2.14.0")


    val configVersion = "4.1.2"
    implementation("org.spongepowered:configurate-core:$configVersion")
    implementation("org.spongepowered:configurate-yaml:$configVersion")
    implementation("io.vavr:vavr:0.10.4")

    val cloudVersion = "1.8.4"
    //implementation("cloud.commandframework:cloud-paper:$cloudVersion")
    implementation("cloud.commandframework:cloud-bukkit:$cloudVersion")
    implementation("cloud.commandframework:cloud-core:$cloudVersion")
    implementation("cloud.commandframework:cloud-minecraft-extras:$cloudVersion")
}

val targetJavaVersion = 8
java {
    withSourcesJar()
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

//tasks.withType(JavaCompile).configureEach {
//    options.encoding = "UTF-8"
//
//    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
//        options.release.set(targetJavaVersion)
//    }
//}

tasks.processResources {
    val props = mapOf(
        "version" to version
    )
    inputs.properties.putAll(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.named("shadowJar", com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
    val mainPackage = group + "." + project.name.lowercase()
    relocate("cloud.commandframework", "$mainPackage.commandframework")
    relocate("net.kyori", "$mainPackage.kyori")
    relocate("de.tr7zw.changeme.nbtapi", "$mainPackage.nbtapi")
}


