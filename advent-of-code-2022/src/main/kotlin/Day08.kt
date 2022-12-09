//Day 08: Treetop Tree House
//https://adventofcode.com/2022/day/8

import java.io.File

data class Tree(val index: Pair<Int, Int>, val height: Int)

fun main() {
    val trees = mutableListOf<Tree>()
    val input = File("src/main/resources/Day08.txt").readLines()

    input.forEachIndexed { x, line ->
        line.toList().forEachIndexed { y, height ->
            trees.add(Tree(x to y, height.digitToInt()))
        }
    }

    countVisibleTrees(trees)
    findTopScenicScore(trees)
}

private fun countVisibleTrees(trees: List<Tree>) {
    val count = trees.count { tree ->
        isVisible(trees, tree)
    }
    println(count)
}

private fun isVisible(trees: List<Tree>, tree: Tree): Boolean {
    val right = getTreesToTheRight(trees, tree).find {
        it.height >= tree.height
    }

    val left = getTreesToTheLeft(trees, tree).find {
        it.height >= tree.height
    }

    val top = getTreesAbove(trees, tree).find {
        it.height >= tree.height
    }

    val bottom = getTreesBelow(trees, tree).find {
        it.height >= tree.height
    }

    return left == null || right == null || top == null || bottom == null
}

private fun getTreesToTheRight(trees: List<Tree>, tree: Tree): List<Tree> {
    return trees.filter {
        it.index.first == tree.index.first && it.index.second > tree.index.second
    }
}

private fun getTreesToTheLeft(trees: List<Tree>, tree: Tree): List<Tree> {
    return trees.filter {
        it.index.first == tree.index.first && it.index.second < tree.index.second
    }
}

private fun getTreesAbove(trees: List<Tree>, tree: Tree): List<Tree> {
    return trees.filter {
        it.index.first < tree.index.first && it.index.second == tree.index.second
    }
}

private fun getTreesBelow(trees: List<Tree>, tree: Tree): List<Tree> {
    return trees.filter {
        it.index.first > tree.index.first && it.index.second == tree.index.second
    }
}

private fun findTopScenicScore(trees: List<Tree>) {
    val highestScenicScore = trees.maxOf { tree ->
        val right = findViewingDistance(getTreesToTheRight(trees, tree), tree.height)
        val top = findViewingDistance(getTreesAbove(trees, tree).reversed(), tree.height)
        val left = findViewingDistance(getTreesToTheLeft(trees, tree).reversed(), tree.height)
        val bottom = findViewingDistance(getTreesBelow(trees, tree), tree.height)

        right * top * left * bottom
    }

    println(highestScenicScore)
}

private fun findViewingDistance(trees: List<Tree>, height: Int): Int {
    var count = 0

    trees.forEach { tree ->
        if (tree.height < height) {
            count++
        } else {
            count++
            return count
        }
    }

    return count
}
