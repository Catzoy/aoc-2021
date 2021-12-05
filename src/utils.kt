import java.io.File

fun readFile(parent: String, name: String): List<String> = File("src/$parent/$name.txt").readLines()
fun readTest(parent: String) = readFile(parent, name = "input_test")
fun readFull(parent: String) = readFile(parent, name = "input_full")