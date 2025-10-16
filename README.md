# App README

- Short video showing the features implemented: https://youtu.be/zZHPAAsuR_g
- 
## Continuous Integration (CI) Pipeline

This project uses **GitHub Actions** to automate the build and packaging process. Whenever a `push` is made to the main branch (`main`), a pipeline is triggered to ensure code quality and generate the executable file.

### Pipeline Functionality (`build.yml`)

The CI pipeline, defined in `.github/workflows/build.yml`, executes the following main steps:

1.  **Trigger**: Triggered on pushes to the `main` branch.
2.  **Setup**: Configures the environment with **Java 21**.
3.  **Build**: Executes the `mvn clean package` command to compile the code and generate the `.jar` file.
4.  **Artifact**: Publishes the generated `.jar` as a workflow artifact, allowing it to be downloaded for testing or deployment.

### Configuration Excerpt

The excerpt below demonstrates the build execution setup and artifact publishing configuration:

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - name: Set up Java 21
      uses: actions/setup-java@v4
      with:
        java-version: '21'
        distribution: 'temurin'
        cache: 'maven'
        
    - name: Build with Maven
      run: mvn clean package -DskipTests 
      
    - name: Find JAR File
      id: find_jar
      run: |
        JAR_FILE=$(find target -name "*.jar" -not -name "*original*" -print -quit)
        echo "jar_path=$JAR_FILE" >> $GITHUB_OUTPUT
        
    - name: Publish JAR Artifact
      uses: actions/upload-artifact@v4
      with:
        name: my-application-jar
        path: ${{ steps.find_jar.outputs.jar_path }}
## Project Structure

The sources of your App have the following structure:

```
src
├── main/frontend
│   └── themes
│       └── default
│           ├── styles.css
│           └── theme.json
├── main/java
│   └── [application package]
│       ├── base
│       │   └── ui
│       │       ├── component
│       │       │   └── ViewToolbar.java
│       │       ├── MainErrorHandler.java
│       │       └── MainLayout.java
│       ├── examplefeature
│       │   ├── ui
│       │   │   └── TaskListView.java
│       │   ├── Task.java
│       │   ├── TaskRepository.java
│       │   └── TaskService.java                
│       └── Application.java       
└── test/java
    └── [application package]
        └── examplefeature
           └── TaskServiceTest.java                 
```

The main entry point into the application is `Application.java`. This class contains the `main()` method that start up 
the Spring Boot application.

The skeleton follows a *feature-based package structure*, organizing code by *functional units* rather than traditional 
architectural layers. It includes two feature packages: `base` and `examplefeature`.

* The `base` package contains classes meant for reuse across different features, either through composition or 
  inheritance. You can use them as-is, tweak them to your needs, or remove them.
* The `examplefeature` package is an example feature package that demonstrates the structure. It represents a 
  *self-contained unit of functionality*, including UI components, business logic, data access, and an integration test.
  Once you create your own features, *you'll remove this package*.

The `src/main/frontend` directory contains an empty theme called `default`, based on the Lumo theme. It is activated in
the `Application` class, using the `@Theme` annotation.

## Starting in Development Mode

To start the application in development mode, import it into your IDE and run the `Application` class. 
You can also start the application from the command line by running: 

```bash
./mvnw
```

## Building for Production

To build the application in production mode, run:

```bash
./mvnw -Pproduction package
```

To build a Docker image, run:

```bash
docker build -t my-application:latest .
```

If you use commercial components, pass the license key as a build secret:

```bash
docker build --secret id=proKey,src=$HOME/.vaadin/proKey .
```

## Getting Started

The [Getting Started](https://vaadin.com/docs/latest/getting-started) guide will quickly familiarize you with your new
App implementation. You'll learn how to set up your development environment, understand the project 
structure, and find resources to help you add muscles to your skeleton — transforming it into a fully-featured 
application.
