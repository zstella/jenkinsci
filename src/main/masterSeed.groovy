
import groovy.json.JsonSlurper



void createProjectSeed(projectName) {
    Job("seed.${projectName}") {
        parameters {
            choiceParam("platform", ['android', 'ios'])
        }
        steps {
            shell("echo ${projectName}")
        }
        wrappers {
            colorizeOutput()
            timestamps()
        }
    }
}


def loadConfig = {
    filePath -> {
        println "config file path is $filePath"
        def file = new File(filePath)
        def data = new JsonSlurper().parseText(file.text)
        println data
        data.projects
    }
}



loadConfig("masterSeed.json").each {
    println " --- $it --- "
    createProjectSeed(it.Name)
}


