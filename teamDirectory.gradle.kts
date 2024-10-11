val modules = hashMapOf(
    "aheena-lib-ui" to ModuleSettings(Team.UI),
)

/**
 * Path модуля
 * @property teams - команды ответсвенные за модуль,
 * первая команда в списке также будет обозначать
 * дирикторию в которой будет находится модуль
 */
class ModuleSettings(
    vararg teams: Team,
) {
    val mainTeam: Team = teams.first()
    val allTeams = listOf(*teams)
}

enum class Team(
    val teamDirectory: String,
) {
    UI("ui"),
}

fun ModuleSettings.buildPath(
    fileName: String = "",
    separator: CharSequence = "/",
    prefix: CharSequence = "",
) =
    listOf(mainTeam.teamDirectory, fileName)
        .filter { it.isNotEmpty() }
        .joinToString(separator = separator, prefix = prefix)

val excludedProjects = setOf("app", "build", "src", ".gradle", ".idea", "coverage")

fun includeRecursive(files: Array<java.io.File>?, parentsChain: String) {
    if (files == null) return
    files.forEach { file ->
        if (file.isDirectory && !excludedProjects.contains(file.name)) {
            val buildScript = file.listFiles()?.firstOrNull { child ->
                child.name == "build.gradle"
            }
            if (buildScript != null) {
                moveToTeamDirectory(file, parentsChain)
            } else {
                includeRecursive(file.listFiles(), "$parentsChain:${file.name}")
            }
        }
    }
}

fun moveToTeamDirectory(file: java.io.File, parentChain: String) {
    println("check111 file.name: ${file.name}")   // mylibrary
    println("check111 parentChain: $parentChain")   // :aheena
    val team = modules[file.name]
    println("check111 team: $team")

    if (team != null) {
        val baseDirectory = parentChain.split(":").first { it.isNotEmpty() }   // aheena
        println("check111 baseDirectory: $baseDirectory")
        val modulePath = team.buildPath(file.name, ":", ":")   // :aheena:ui:mylibrary
        println("check111 modulePath: $modulePath")
        println("check111 settingsDir: $settingsDir")  // /Users/vladimir/projects/aheena
        println("check111 team.buildPath(): ${team.buildPath()}")   // aheena/ui
        val teamFile =
            File(settingsDir, team.buildPath())   // /Users/vladimir/projects/aheena/aheena/ui
        println("check111 teamFile: $teamFile")
        if (!teamFile.exists()) {
            teamFile.mkdirs()
        }
        include(modulePath)
        println("check111 project(modulePath).projectDir: ${project(modulePath).projectDir}")   // /Users/vladimir/projects/aheena/ui/mylibrary
        project(modulePath).projectDir = file(file)
    } else {
        include("${file.name}")
    }
}

val children: Array<java.io.File>? = rootProject.projectDir.listFiles()

children?.forEach { file ->
    println("check111 children: ${file.name}")
}
includeRecursive(children, ":aheena")