package io.gitlab.arturbosch.detekt

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.TaskOutcome
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * @author Markus Schwarz
 */
internal class CreateBaselineTaskDslTest : Spek({
    describe("The detektBaseline task of the Detekt Gradle plugin") {
        listOf(DslTestBuilder.groovy(), DslTestBuilder.kotlin()).forEach { builder ->
            describe("using ${builder.gradleBuildName}") {
                it("can be executed when baseline file is specified") {
                    val detektConfig = """
						|detekt {
						| 	baseline = file("build/baseline.xml")
						|}
						"""
                    val gradleRunner = builder
                            .withDetektConfig(detektConfig)
                            .build()

                    gradleRunner.runTasksAndCheckResult("detektBaseline") { result ->
                        assertThat(result.task(":detektBaseline")?.outcome).isEqualTo(TaskOutcome.SUCCESS)
                        assertThat(projectFile("build/baseline.xml")).exists()
                    }
                }
            }
        }
    }
})
