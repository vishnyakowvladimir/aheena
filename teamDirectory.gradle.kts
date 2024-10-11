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
    val featureDirectory: String = "",
) {

    val mainTeam: Team = teams.first()
    val allTeams = listOf(*teams)
}

enum class Team(
    val teamDirectory: String,
) {
    UI("ui"),
}

//fun ModuleSettings.buildPath(
//    basePath: String,
//    fileName: String = "",
//    separator: CharSequence = "/",
//    prefix: CharSequence = "",
//) =
//    listOf(basePath, mainTeam.teamDirectory, featureDirectory, fileName)
//        .filter { it.isNotEmpty() }
//        .joinToString(separator = separator, prefix = prefix)

fun ModuleSettings.buildPath(
    fileName: String = "",
    separator: CharSequence = "/",
    prefix: CharSequence = "",
) =
    listOf(mainTeam.teamDirectory, featureDirectory, fileName)
        .filter { it.isNotEmpty() }
        .joinToString(separator = separator, prefix = prefix)

val excludedProjects = setOf("app", "build", "src", ".gradle", ".idea", "coverage")

fun includeRecursive(files: Array<java.io.File>?, parentsChain: String) {
    if (files == null) return
    files.forEach { file ->
        println("check111 file: ${file.name}")
        if (file.isDirectory && !excludedProjects.contains(file.name)) {
            val level = parentsChain.split(":").count()
            println("check111 level: $level")
            val pfx = "-".repeat(level)
            println("check111 pfx: $pfx")
            val buildScript = file.listFiles()?.firstOrNull { child ->
                println("check111 child.name: ${child.name}")
                child.name == "build.gradle"
            }
            if (buildScript != null) {
                moveToTeamDirectory(file, parentsChain)
                println("check1111 ${pfx}including project `$parentsChain:${file.name}`...")
            } else {
                println("check1113 $pfx o_O checking content of `${file.name}`...")
                includeRecursive(file.listFiles(), "$parentsChain:${file.name}")
            }
        }
    }
}

fun getMapKey(teams: List<Team>, keys: Set<String>): String? {
    var result: String? = null
    keys.forEach() { it ->
        val teamsSet = it.split("##")
        var finded = true
        if (teams.size != teamsSet.size) {
            finded = false
        } else {
            teams.forEach() { team ->
                if (!teamsSet.contains(team.teamDirectory)) {
                    finded = false
                }
            }
        }
        if (finded) {
            return it
        }
    }
    return result
}

fun writeModuleTeamFile(path: String) {
    val buildFolder = java.io.File(path)
    if (!buildFolder.exists()) {
        buildFolder.mkdir()
    }
    val modulesFile = java.io.File("$path/team-module.txt")
    val modulesFileNew = java.io.File("$path/all-team-module.txt")
    val modulesText = modules.map {
        ":${it.key}/:${it.value.mainTeam.teamDirectory}" + if (it.value.featureDirectory.isEmpty()) "" else ":${it.value.featureDirectory}"
    }.joinToString("\n")

    // Формируем списки команд
    var teamVars = ArrayList<ArrayList<String>>()
    var moduleTeamVars = HashMap<String, ArrayList<String>>()
    modules.forEach() { (key, value) ->
        var mapkeys = getMapKey(value.allTeams, moduleTeamVars.keys)
        if (mapkeys.isNullOrBlank()) {
            value.allTeams.forEach() { teamName ->
                if (mapkeys.isNullOrBlank()) {
                    mapkeys = teamName.teamDirectory
                } else {
                    mapkeys = "$mapkeys##${teamName.teamDirectory}"
                }
            }
            var list = ArrayList<String>()
            list.add(key)
            moduleTeamVars.put(mapkeys!!, list)
        } else {
            var list = moduleTeamVars.get(mapkeys!!)
            list!!.add(key)
            moduleTeamVars.put(mapkeys!!, list)
        }
    }
    val allTeamsText = moduleTeamVars.map { (key, value) ->
        val modulesText = value.map {
            "$it"
        }.joinToString("$$$")
        "$key:::$modulesText"
    }.joinToString("\n")

//    modulesFile.writeText(modulesText)
//    modulesFileNew.writeText(allTeamsText)
}

//fun moveToTeamDirectory(file: java.io.File, parentChain: String) {
//    println("check111 file.name: ${file.name}")   // mylibrary
//    println("check111 parentChain: $parentChain")   // :aheena
//    val team = modules[file.name]
//    println("check111 team: $team")
//
//    if (team != null) {
//        val baseDirectory = parentChain.split(":").first { it.isNotEmpty() }   // aheena
//        println("check111 baseDirectory: $baseDirectory")
//        val modulePath = team.buildPath(baseDirectory, file.name, ":", ":")   // :aheena:ui:mylibrary
//        println("check111 modulePath: $modulePath")
//        println("check111 settingsDir: $settingsDir")   // /Users/vladimir/projects/aheena
//        println("check111 team.buildPath(baseDirectory): ${team.buildPath(baseDirectory)}")   // aheena/ui
//        val teamFile = File(settingsDir, team.buildPath(baseDirectory))   // /Users/vladimir/projects/aheena/aheena/ui
//        println("check111 teamFile: $teamFile")
//        if (!teamFile.exists()) {
//            teamFile.mkdirs()
//        }
//        include(modulePath)
//        project(modulePath).projectDir = file(file)
//    } else {
//        include("$parentChain:${file.name}")
//    }
//}

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
        val teamFile = File(settingsDir, team.buildPath())   // /Users/vladimir/projects/aheena/aheena/ui
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

writeModuleTeamFile("aheena/build")
val children: Array<java.io.File>? = rootProject.projectDir.listFiles()

children?.forEach { file ->
    println("check111 children: ${file.name}")
}
includeRecursive(children, ":aheena")

//val children: Array<java.io.File>? = project(":telecard").projectDir.listFiles()
//includeRecursive(children, ":telecard")
