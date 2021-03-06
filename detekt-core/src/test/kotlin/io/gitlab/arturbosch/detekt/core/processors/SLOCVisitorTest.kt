package io.gitlab.arturbosch.detekt.core.processors

import io.gitlab.arturbosch.detekt.core.path
import io.gitlab.arturbosch.detekt.test.compileForTest
import org.assertj.core.api.Assertions
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

class SLOCVisitorTest : Spek({

    it("defaultClass") {
        val file = compileForTest(path.resolve("Default.kt"))
        val loc = with(file) {
            accept(SLOCVisitor())
            getUserData(sourceLinesKey)
        }
        Assertions.assertThat(loc).isEqualTo(3)
    }
})
