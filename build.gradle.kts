plugins {
    java
}

val groupId = "rs.ac.bg.etf.pp1"
val groupDir = "rs/ac/bg/etf/pp1"

val specDir = file("src/spec")
val genDir = file("src/gen")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

val java21Launcher = javaToolchains.launcherFor {
    languageVersion = JavaLanguageVersion.of(21)
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
    }
}

dependencies {
    implementation(fileTree("lib") {
        include("*.jar")
    })
}

tasks.register<JavaExec>("generateLexer") {

    group = "generation"
    description = "Generates lexer from MJLexer.flex"

    classpath = files("lib/JFlex.jar")

    mainClass.set("JFlex.Main")

    args(
        "-d",
        genDir.absolutePath,
        file("$specDir/MJLexer.flex").absolutePath
    )

    inputs.file("src/spec/MJLexer.flex")
    outputs.file("src/gen/MJScanner.java")
}

tasks.register<Exec>("generateParser") {

    group = "generation"
    description = "Generates parser and AST from MJParser.cup"

    doFirst {
        file("$genDir/$groupDir").mkdirs()
    }

    executable(
        java21Launcher.get().executablePath.asFile.absolutePath
    )

    args(
        "-jar",
        file("lib/cup_v10k.jar").absolutePath,

        "-destdir",
        file("$genDir/$groupDir").absolutePath,

        "-ast",
        "src.gen.$groupId.ast",

        "-parser",
        "MJParser",

        "-buildtree",

        file("$specDir/MJParser.cup").absolutePath
    )

    doLast {

    val astDir = file("$genDir/$groupDir/ast")

    fileTree(astDir).forEach { generatedFile ->

        if (generatedFile.isFile) {

            val oldText = generatedFile.readText()

            val newText = oldText.replace(
                "src.gen.$groupId.ast",
                "$groupId.ast"
            )

            generatedFile.writeText(newText)
        }
    }
    }

    inputs.file("$specDir/MJParser.cup")

    outputs.file("$genDir/$groupDir/MJParser.java")
    outputs.file("$genDir/$groupDir/sym.java")
    outputs.dir("$genDir/$groupDir/ast")  
}

tasks.register("generate") {

    group = "generation"
    description = "Generates lexer, parser and AST"

    dependsOn(
        "generateLexer",
        "generateParser"
    )
}

tasks.named("compileJava") {
    dependsOn("generate")
}

tasks.register<Delete>("cleanGenerated") {

    group = "build"
    description = "Deletes the gen directory."

    delete(genDir)
}

tasks.named("clean") {
    dependsOn("cleanGenerated")
}

tasks.register<JavaExec>("runCompiler") {
    dependsOn("classes")
    group = "application"
    description = "Runs the MicroJava compiler"

    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("rs.ac.bg.etf.pp1.Compiler")
}

tasks.register<JavaExec>("runVM") {
    group = "application"
    description = "Runs a MikroJava object file on the MikroJava VM"

    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("rs.etf.pp1.mj.runtime.Run")

    standardInput = System.`in`
}