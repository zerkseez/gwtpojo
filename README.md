# GWTPojo
POJO-JSON serialization for GWT

## About
GWTPojo provides an alternative solution to GWT's AutoBean framework for POJO-JSON serialization/deserialization; but
unlike AutoBean, GWTPojo allows the client and the server code to share the same Pojo classes. GWTPojo also has support
for generics (except for wildcards) and partial support for polymorphism using Jackson's @JsonTypeInfo and @JsonSubTypes annotations.

## Features
* Sharing Pojo implementation between client and server
* Generics support (except for wildcards)
* Partial polymorphism support (using Jackson's @JsonTypeInfo and @JsonSubTypes annotations)

## Usage
To share pojo classes between client and server, it is highly recommended to put all your pojo classes into a separate
"model" jar and have both your client and server to reference the model jar.

### pom.xml of your client
```xml
<project>

    <build>
        <plugins>
            <plugin>
                <groupId>com.github.zerkseez</groupId>
                <artifactId>gwtpojo-maven-plugin</artifactId>
                <version>${gwtpojo.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <!-- Add mycompany-model as a dependency for gwtpojo-maven-plugin -->
                    <dependency>
                        <groupId>com.mycompany</groupId>
                        <artifactId>mycompany-model</artifactId>
                        <version>${project.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <!-- Add mycompany-model as a dependency the client as well -->
        <dependency>
            <groupId>com.mycompany</groupId>
            <artifactId>mycompany-model</artifactId>
            <version>${project.version}</version>
            <scope>provided</scope>
        </dependency>
        <!-- If the source code for mycompany-model is in a separate jar, include it as well -->
        <dependency>
            <groupId>com.mycompany</groupId>
            <artifactId>mycompany-model</artifactId>
            <version>${project.version}</version>
            <classifier>sources</classifier>
            <scope>provided</scope>
        </dependency>
        <!-- gwtpojo-core is also needed -->
        <dependency>
            <groupId>com.github.zerkseez</groupId>
            <artifactId>gwtpojo-core</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

### Sample project
https://github.com/zerkseez/gwtpojo-sample

## License
Apache License, Version 2.0
