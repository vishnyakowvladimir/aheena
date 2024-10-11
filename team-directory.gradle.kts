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

fun includeRecursive(files: Array<java.io.File>?) {
    if (files == null) return
    files.forEach { file ->
        if (file.isDirectory && !excludedProjects.contains(file.name)) {
            val buildScript = file.listFiles()?.firstOrNull { child ->
                child.name == "build.gradle"
            }
            if (buildScript != null) {
                moveToTeamDirectory(file)
            } else {
                includeRecursive(file.listFiles())
            }
        }
    }
}

fun moveToTeamDirectory(file: java.io.File) {
    val team = modules[file.name]

    if (team != null) {
        val modulePath = team.buildPath(file.name, ":", ":")
        val teamFile = File(settingsDir, team.buildPath())

        if (!teamFile.exists()) {
            teamFile.mkdirs()
        }

        include(modulePath)
        project(modulePath).projectDir = file(file)
    } else {
        include("${file.name}")
    }
}

val children: Array<java.io.File>? = rootProject.projectDir.listFiles()
includeRecursive(children)