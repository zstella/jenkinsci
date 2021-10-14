
import groovy.json.JsonSlurper
import javaposse.jobdsl.dsl.Job


def defaultPathBase = new File( "." ).getCanonicalPath()

void createProjectSeed(projectName) {
    println "create project seed: $projectName"
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
    filePath -> 
        println "config file path is $filePath"
        // def file = new File(defaultPathBase + "/" + filePath)
        def data = new JsonSlurper().parseText("""
        {
            "projects": [
                {
                    "name": "plant"
                },
                {
                    "name": "zombie"
                }
            ]
        }
        """)
        println data
        data.projects
}



loadConfig("etc/masterSeed.json").each {
    println " --- $it --- "
    createProjectSeed(it.name)
}


