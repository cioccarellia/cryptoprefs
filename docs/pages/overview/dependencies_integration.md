# Dependencies and Integration

### Publishing

All the dependencies are published on MavenCentral. Make sure to have it included in your `reporitories` block in the top level `build.gradle` file.

```groovy
repositories {
    mavenCentral()
}
```

### Project Requirements

- Java 8+ is required;
	```groovy
	compileOptions {
	    sourceCompatibility JavaVersion.VERSION_1_8
	    targetCompatibility JavaVersion.VERSION_1_8
	}
	```
- `minSdk` 19+ is required.

### Dependency
???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "com.github.cioccarellia:ksprefs:2.4.0"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("com.github.cioccarellia:ksprefs:2.4.0")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>com.github.cioccarellia</groupId>
	    <artifactId>ksprefs</artifactId>
	    <version>2.4.0</version>
	    <type>pom</type>
	</dependency>
	```