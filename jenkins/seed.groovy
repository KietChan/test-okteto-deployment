pipelineJob("Knote-pipeline") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github("KietChan/test-okteto-deployment", "https")
					}
					branch("*/master")
				}
			}
			scriptPath("jenkins/DSL/knote.groovy")
		}
	}
	disabled(false)
}