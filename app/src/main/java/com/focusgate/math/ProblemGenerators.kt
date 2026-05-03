package com.focusgate.math

import com.focusgate.domain.model.MathProblem
import com.focusgate.domain.model.ProblemType
import kotlin.random.Random

// ─── Interface ────────────────────────────────────────────────────────────────
interface ProblemGenerator {
    fun generate(): MathProblem
}

// ─── 2×2 Determinant ──────────────────────────────────────────────────────────
class DeterminantProblem : ProblemGenerator {
    override fun generate(): MathProblem {
        val a = Random.nextInt(-9, 10)
        val b = Random.nextInt(-9, 10)
        val c = Random.nextInt(-9, 10)
        val d = Random.nextInt(-9, 10)
        val det = a * d - b * c
        return MathProblem(
            displayText = buildString {
                appendLine("Find the determinant of matrix A:")
                appendLine()
                appendLine("    | $a   $b |")
                append("    | $c   $d |")
            },
            inputHint = "Enter an integer",
            correctAnswer = det.toString(),
            type = ProblemType.DETERMINANT
        )
    }
}

// ─── Dot Product ──────────────────────────────────────────────────────────────
class DotProductProblem : ProblemGenerator {
    override fun generate(): MathProblem {
        val u = List(3) { Random.nextInt(-6, 7) }
        val v = List(3) { Random.nextInt(-6, 7) }
        val dot = u.zip(v).sumOf { (a, b) -> a * b }
        return MathProblem(
            displayText = buildString {
                appendLine("Compute the dot product  u · v")
                appendLine()
                appendLine("u = [${u[0]},  ${u[1]},  ${u[2]}]")
                append("v = [${v[0]},  ${v[1]},  ${v[2]}]")
            },
            inputHint = "Enter an integer",
            correctAnswer = dot.toString(),
            type = ProblemType.DOT_PRODUCT
        )
    }
}

// ─── 2×2 Matrix Multiplication (single element) ───────────────────────────────
class MatrixMultiplyProblem : ProblemGenerator {
    override fun generate(): MathProblem {
        // A (2x2) × B (2x2), ask for one specific element C[row][col]
        val a = Array(2) { Array(2) { Random.nextInt(-5, 6) } }
        val b = Array(2) { Array(2) { Random.nextInt(-5, 6) } }
        val row = Random.nextInt(2)
        val col = Random.nextInt(2)
        val answer = (0 until 2).sumOf { k -> a[row][k] * b[k][col] }
        return MathProblem(
            displayText = buildString {
                appendLine("Multiply A × B. Find element C[${row + 1}][${col + 1}]:")
                appendLine()
                appendLine("A = | ${a[0][0]}  ${a[0][1]} |      B = | ${b[0][0]}  ${b[0][1]} |")
                appendLine("    | ${a[1][0]}  ${a[1][1]} |          | ${b[1][0]}  ${b[1][1]} |")
                appendLine()
                append("What is C[${row + 1}][${col + 1}]?")
            },
            inputHint = "Enter an integer",
            correctAnswer = answer.toString(),
            type = ProblemType.MATRIX_MULTIPLY
        )
    }
}

// ─── Registry: picks a random generator ──────────────────────────────────────
class ProblemRegistry {
    private val generators: List<ProblemGenerator> = listOf(
        DeterminantProblem(),
        DotProductProblem(),
        MatrixMultiplyProblem()
    )

    fun next(): MathProblem = generators.random().generate()
}
