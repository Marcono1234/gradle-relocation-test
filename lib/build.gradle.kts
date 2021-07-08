plugins {
    `java-library`
    `maven-publish`
}

group = "com.new-example"
version = "2.0.0"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
        }

        create<MavenPublication>("relocation") {
            pom {
                // Old artifact coordinates
                groupId = "com.example"
                artifactId = "lib"
                version = "2.0.0"

                distributionManagement {
                    relocation {
                        groupId.set("com.new-example")
                        message.set("groupId has been changed")
                    }
                }
            }
        }
    }

    // See https://docs.github.com/en/actions/guides/publishing-java-packages-with-gradle#publishing-packages-to-github-packages
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Marcono1234/gradle-relocation-test")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
